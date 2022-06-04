package com.dacs.usermodules.controller;

import com.dacs.entitymodules.Category;
import com.dacs.entitymodules.Customer;
import com.dacs.entitymodules.Product;
import com.dacs.usermodules.service.CategoryService;
import com.dacs.usermodules.service.CustomerService;
import com.dacs.usermodules.service.ProductService;
import com.dacs.usermodules.service.SettingService;
import com.dacs.usermodules.utils.MailUtils;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class HomeController {

    private static final int CATEGORIES_DISPLAY_LIMIT = 8;
    private static final int PRODUCT_DISPLAY_LIMIT = 30;

    private final SettingService settingService;
    private final CategoryService categoryService;
    private final ProductService productService;
    private final CustomerService customerService;

    public HomeController(CategoryService categoryService, ProductService productService, CustomerService customerService, SettingService settingService) {
        this.categoryService = categoryService;
        this.productService = productService;
        this.customerService = customerService;
        this.settingService = settingService;
    }

    @GetMapping("")
    public String index(Model model) {
        List<Category> categories = categoryService.listAll();
        List<Product> newProducts = productService.listNewProducts();
        categories = categories.stream().limit(CATEGORIES_DISPLAY_LIMIT).collect(Collectors.toList());
        newProducts = newProducts.stream().limit(PRODUCT_DISPLAY_LIMIT).collect(Collectors.toList());
        model.addAttribute("categories", categories);
        model.addAttribute("newProducts", newProducts);
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null || authentication instanceof AnonymousAuthenticationToken)
            return "/login/login";
        return "redirect:/";
    }

    @GetMapping("/register")
    public String register(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null || authentication instanceof AnonymousAuthenticationToken)
            return "/login/register";
        return "redirect:/";
    }

    @PostMapping("/register")
    public String register(String email, String password, HttpServletRequest request) throws MessagingException, UnsupportedEncodingException {
        String subject = settingService.getByKey("CUSTOMER_VERIFY_SUBJECT").getValue();
        String content = settingService.getByKey("CUSTOMER_VERIFY_CONTENT").getValue();
        String storeName = settingService.getByKey("STORE_NAME").getValue();


        Customer customer = new Customer();
        customer.setEmail(email);
        customer.setPassword(password);
        customerService.register(customer);

        String url = MailUtils.getUrl(request) + "/verify?token=" + customer.getVerifyToken();

        subject = subject.replace("[[storeName]]", storeName);
        content = content.replace("[[name]]", customer.getFullName());
        content = content.replace("[[storeName]]", storeName);
        content = content.replace("[[url]]", url);

        MailUtils.sendMail(settingService, customer, subject, content);
        return "redirect:/verify-message";
    }

    @GetMapping("/verify-message")
    public String verifyMessage() {
        return "/login/verify";
    }

    @GetMapping("/verify")
    public String verify(String token) {
        Customer customer = customerService.getByVerifyToken(token);
        if(customer == null)
            return "/login/verify-fail";
        customerService.enabled(customer.getId());
        return "/login/verify-success";
    }

    @GetMapping("/forget-password")
    public String forgetPassword() {
        return "/login/forget";
    }

    @PostMapping("/forget-password")
    public String forgetPassword(String email, RedirectAttributes rd, HttpServletRequest request) throws MessagingException, UnsupportedEncodingException {
        String storeName = settingService.getByKey("STORE_NAME").getValue();
        String subject = storeName + " - Lấy lại mật khẩu";
        String content = "<span style=\"font-size:16px;\">Kính gửi <b>[[name]]</b>,<br>\n" +
                "<br>\n" +
                "Chào mừng bạn đến với <b>[[storeName]]</b>. Vui lòng nhấn vào nút \"Lấy lại mật khẩu\"&nbsp; bên dưới để đặt lại mật khẩu của bạn.</span><span style=\"font-size:16px;\"><font face=\"Arial\"></font></span><div><div style=\"font-weight: bold;\"><br></div><div style=\"font-weight: bold;\"><br></div><h3 style=\"font-weight: bold;\"><b><a href=\"[[url]]\" target=\"_self\" style=\"padding: 10px 20px; background-color: #3bb35b; color: #fff; border-radius: 4px\">Lấy lại mật khẩu</a></b></h3></div><span style=\"font-size:16px;\"><br>\n" +
                "Cảm ơn và chúc bạn có trải nghiệm vui vẻ.<br>\n" +
                "<br>\n" +
                "Trân trọng,</span><span style=\"font-size:16px;\"><br>\n" +
                "<br><b>\n" +
                "[[storeName]]</b></span><div><b></b></div>";

        Customer customer = customerService.getByEmail(email);
        if(customer == null) {
            rd.addFlashAttribute("message", "Email không tồn tại");
            return "redirect:/forget-password";
        }
        customer.setForgetToken(RandomString.make(64));
        customerService.save(customer);
        String url = MailUtils.getUrl(request) + "/forget?token=" + customer.getForgetToken();

        subject = subject.replace("[[storeName]]", storeName);
        content = content.replace("[[name]]", customer.getFullName());
        content = content.replace("[[storeName]]", storeName);
        content = content.replace("[[url]]", url);

        MailUtils.sendMail(settingService, customer, subject, content);

        return "redirect:/confirm-forget";
    }

    @GetMapping("/confirm-forget")
    public String confirmForget() {
        return "/login/verify";
    }

    @GetMapping("/forget")
    public String resetPassword(String token, Model model) {
        Customer customer = customerService.getByForgetToken(token);
        if(customer == null)
            return "/login/verify-fail";
        model.addAttribute("customer", customer);
        return "/login/reset-password";
    }

    @PostMapping("/reset-password")
    public String resetPasswordPost(String email, String password) {
        Customer customer = customerService.getByEmail(email);
        customer.setPassword(password);
        customer.setForgetToken(null);
        customerService.resetPassword(customer);
        return "/login/verify-success";
    }
}
