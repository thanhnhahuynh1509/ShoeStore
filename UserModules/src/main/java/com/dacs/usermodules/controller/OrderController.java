package com.dacs.usermodules.controller;

import com.dacs.entitymodules.*;
import com.dacs.entitymodules.enumType.OrderStatus;
import com.dacs.entitymodules.enumType.PaymentMethod;
import com.dacs.usermodules.service.*;
import com.dacs.usermodules.utils.CustomerLoginUtils;
import com.dacs.usermodules.utils.MailUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;
    private final CustomerService customerService;
    private final AddressService addressService;
    private final CartService cartService;
    private final SettingService settingService;
    private final PayPalService payPalService;

    public OrderController(OrderService orderService, CustomerService customerService, AddressService addressService, CartService cartService, SettingService settingService, PayPalService payPalService) {
        this.orderService = orderService;
        this.customerService = customerService;
        this.addressService = addressService;
        this.cartService = cartService;
        this.settingService = settingService;
        this.payPalService = payPalService;
    }

    @GetMapping("/place-order")
    public String placeOrder(Model model, HttpServletRequest request) {
        String email = CustomerLoginUtils.getEmail(request);
        Customer customer = customerService.getByEmail(email);
        Address defaultAddress = addressService.getAddressDefault(customer);
        List<Cart> carts = cartService.listByCustomer(customer);

        if(carts.size() == 0)
            return "redirect:/";

        model.addAttribute("customer", customer);
        model.addAttribute("address", defaultAddress);
        model.addAttribute("carts", carts);
        model.addAttribute("totalQuantity", getTotalQuantity(carts));
        model.addAttribute("totalPrice", getTotalPrice(carts));
        model.addAttribute("clientId", settingService.getByKey("PAYPAL_CLIENT_ID").getValue());

        return "/order/place-order";
    }

    @PostMapping("/place-order")
    public String placeOrder(HttpServletRequest request) throws MessagingException, UnsupportedEncodingException {
        String email = CustomerLoginUtils.getEmail(request);
        Customer customer = customerService.getByEmail(email);
        Address defaultAddress = addressService.getAddressDefault(customer);
        List<Cart> carts = cartService.listByCustomer(customer);
        saveOrder(customer, carts, defaultAddress, PaymentMethod.COD, false);
        return "redirect:/orders/success";
    }

    @PostMapping("/process_paypal_order")
    public String processPayPalOrder(HttpServletRequest request) throws MessagingException, UnsupportedEncodingException {
        String orderId = request.getParameter("orderId");
        if(payPalService.validateOrder(orderId)) {
            String email = CustomerLoginUtils.getEmail(request);
            Customer customer = customerService.getByEmail(email);
            Address address = addressService.getAddressDefault(customer);
            List<Cart> cartItems = cartService.listByCustomer(customer);
            saveOrder(customer, cartItems, address, PaymentMethod.PAYPAL, true);
        }
        return "redirect:/orders/success";
    }

    @GetMapping("/success")
    public String success() {
        return "/order/success";
    }

    private Integer getTotalQuantity(List<Cart> carts) {
        Integer total = 0;
        for(Cart cart : carts) {
            total += cart.getQuantity();
        }
        return total;
    }

    private double getTotalPrice(List<Cart> carts) {
        double total = 0;
        for(Cart cart : carts) {
            total += cart.getTotalPrice();
        }
        return total;
    }

    private void saveOrder(Customer customer, List<Cart> carts, Address defaultAddress, PaymentMethod paymentMethod, boolean isPaid) throws MessagingException, UnsupportedEncodingException {
        Order order = new Order();
        order.setOrderStatus(OrderStatus.NEW);
        order.setCustomer(customer);
        order.setPaid(isPaid);
        order.setTotalPrice(getTotalPrice(carts));
        order.setTotalQuantity(getTotalQuantity(carts));
        order.setAddress(defaultAddress);
        order.setPaymentMethod(paymentMethod);

        for(Cart cart : carts) {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrder(order);
            orderDetail.setCost(cart.getProduct().getCost());
            orderDetail.setDiscountPercent(cart.getProduct().getDiscountPercent());
            orderDetail.setPrice(cart.getPrice());
            orderDetail.setProduct(cart.getProduct());
            orderDetail.setQuantity(cart.getQuantity());

            order.getOrderDetails().add(orderDetail);
        }

        orderService.save(order);

        cartService.clear(customer);

        String storeName = settingService.getByKey("STORE_NAME").getValue();
        String subject = settingService.getByKey("CUSTOMER_ORDER_SUBJECT").getValue();
        String content = settingService.getByKey("CUSTOMER_ORDER_CONTENT").getValue();

        subject = subject.replace("[[storeName]]", storeName);
        content = content.replace("[[storeName]]", storeName);
        content = content.replace("[[name]]", customer.getFullName());

        MailUtils.sendMail(settingService, customer, subject, content);
    }
}
