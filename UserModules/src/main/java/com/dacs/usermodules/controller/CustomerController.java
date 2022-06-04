package com.dacs.usermodules.controller;

import com.dacs.entitymodules.Address;
import com.dacs.entitymodules.Customer;
import com.dacs.entitymodules.Order;
import com.dacs.entitymodules.Track;
import com.dacs.usermodules.security.WebSecurityConfig;
import com.dacs.usermodules.security.oauth2.CustomerOauth2User;
import com.dacs.usermodules.service.*;
import com.dacs.usermodules.utils.CustomerLoginUtils;
import com.dacs.usermodules.utils.FileUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.RememberMeAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/account")
public class CustomerController {

    private final AddressService addressService;
    private final CustomerService customerService;
    private final CityService cityService;
    private final DistrictService districtService;
    private final WardService wardService;
    private final TrackService trackService;
    private final OrderService orderService;

    public CustomerController(CustomerService customerService, AddressService addressService, CityService cityService, DistrictService districtService, WardService wardService, TrackService trackService, OrderService orderService) {
        this.customerService = customerService;
        this.addressService = addressService;
        this.cityService = cityService;

        this.districtService = districtService;
        this.wardService = wardService;
        this.trackService = trackService;
        this.orderService = orderService;
    }

    @GetMapping("")
    public String index(Model model, HttpServletRequest request) {
        String email = CustomerLoginUtils.getEmail(request);
        Customer customer = customerService.getByEmail(email);
        List<Address> addresses = addressService.listByCustomerEmail(email);
        List<Order> orders = orderService.listByCustomer(customer)
                .stream()
                .sorted(Comparator.comparing(Order::getOrderDate).reversed())
                .collect(Collectors.toList());
        model.addAttribute("addresses", addresses);
        model.addAttribute("customer", customer);
        model.addAttribute("orders", orders);

        return "/customer/index";
    }

    @PostMapping("/info")
    public String info(String fullName, String email, MultipartFile fileUpload) {
        Customer customer = customerService.getByEmail(email);
        customer.setFullName(fullName);
        if (fileUpload != null && !fileUpload.isEmpty()) {
            String fileName = fileUpload.getOriginalFilename();
            String dir = Customer.IMAGE_ROOT_DIR + "/" + customer.getId();
            FileUploadUtil.cleanDir(dir);
            FileUploadUtil.saveFile(dir, fileName, fileUpload);
            customer.setImage(fileName);
        }
        customerService.save(customer);
        return "redirect:/account";
    }

    @GetMapping("/create-address")
    public String create(Model model) {
        model.addAttribute("cities", cityService.list());
        model.addAttribute("address", new Address());
        return "/customer/address-form";
    }

    @GetMapping("/update-address")
    public String update(Integer id, Model model) {
        Address address = addressService.get(id);
        model.addAttribute("cities", cityService.list());
        model.addAttribute("districts", districtService.listByCity(address.getCity()));
        model.addAttribute("wards", wardService.listByDistrict(address.getDistrict()));
        model.addAttribute("address", address);
        return "/customer/address-form";
    }

    @PostMapping("/create-address")
    public String create(Address address, HttpServletRequest request) {
        String email = CustomerLoginUtils.getEmail(request);
        Customer customer = customerService.getByEmail(email);
        List<Address> addresses = addressService.listByCustomerEmail(email);
        if(addresses == null || addresses.size() <= 0)
            address.setDefaultAddress(true);
        address.setCustomer(customer);
        addressService.save(address);
        if(address.isDefaultAddress())
            addressService.setDefaultAddress(customer, address.getId());
        return "redirect:/account#address";
    }

    @GetMapping("/delete-address")
    public String deleteAddress(Integer id) {
        Address address = addressService.get(id);
        if(!address.isDefaultAddress())
            addressService.delete(id);
        return "redirect:/account#address";
    }

    @PostMapping("/password")
    public String changePassword(String newPassword, String email) {
        Customer customer = customerService.getByEmail(email);
        if(customer != null) {
            newPassword = WebSecurityConfig.passwordEncoder().encode(newPassword);
            customer.setPassword(newPassword);
            customerService.save(customer);
        }
        return "redirect:/account#password";
    }

    @GetMapping("/order-details")
    public String orderDetails(Integer id, Model model) {
        Order order = orderService.get(id);
        Track track = trackService.getLastTrack(order);

        model.addAttribute("order", order);
        model.addAttribute("track", track);

        return "/customer/order-detail";
    }

    @GetMapping("/tracks")
    public String showTracks(Integer orderId, Model model) {
        Order order = orderService.get(orderId);
        List<Track> tracks = trackService.listByOrder(order);

        model.addAttribute("order", order);
        model.addAttribute("tracks", tracks);
        return "/customer/order-track";
    }

    @PostMapping("/cancel")
    public String cancelOrder(Integer orderId, String reason) {
        orderService.cancelOrder(orderId, reason);
        return "redirect:/account#order";
    }
}
