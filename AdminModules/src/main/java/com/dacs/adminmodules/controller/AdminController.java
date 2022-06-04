package com.dacs.adminmodules.controller;

import com.dacs.adminmodules.security.AdminUserDetails;
import com.dacs.adminmodules.service.AdminService;
import com.dacs.adminmodules.service.RoleService;
import com.dacs.adminmodules.utils.AliasNameMaker;
import com.dacs.adminmodules.utils.FileUploadUtils;
import com.dacs.adminmodules.utils.MessageRedirectUtils;
import com.dacs.entitymodules.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admins")
public class AdminController {

    private final AdminService adminService;
    private final RoleService roleService;


    public AdminController(AdminService adminService, RoleService roleService) {
        this.adminService = adminService;
        this.roleService = roleService;
    }

    @GetMapping("")
    public String index(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String currentPrincipalName = !(authentication instanceof AnonymousAuthenticationToken)
                ? ((AdminUserDetails)authentication.getPrincipal()).getUsername() : "";

        List<Admin> admins = adminService.list();

        admins = admins.stream()
                .filter(m -> !m.getEmail().equals(currentPrincipalName))
                .collect(Collectors.toList());

        model.addAttribute("admins", admins);

        return "/admin/index";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("admin", new Admin());
        model.addAttribute("roles", roleService.list());
        return "/admin/form";
    }

    @GetMapping("/update")
    public String update(Integer id,@RequestParam(required = false) String isNotInAdmin, Model model, RedirectAttributes rd) {
        Admin admin = adminService.get(id);

        if(isNotInAdmin != null) {
            model.addAttribute("isNotInAdmin", "OK");
        }

        if(admin == null) {
            MessageRedirectUtils.setMessageRedirect("Không tìm thấy tài khoản yêu cầu!", "danger", rd);
            return "redirect:/admins";
        }
        model.addAttribute("roles", roleService.list());
        model.addAttribute("admin", admin);
        return "/admin/form";
    }

    @PostMapping("/saveOrUpdate")
    public String saveOrUpdate(Admin admin, MultipartFile fileUpload, RedirectAttributes rd) {
        if(admin.getId() == null || admin.getId() == 0) {
            MessageRedirectUtils.setMessageRedirect("Thêm mới tài khoản thành công!", "success", rd);
        } else {
            MessageRedirectUtils.setMessageRedirect("Cập nhật tài khoản thành công!", "success", rd);
        }
        if(!fileUpload.isEmpty()) {
            String fileName = fileUpload.getOriginalFilename();
            fileName = AliasNameMaker.make(fileName);
            admin.setImage(fileName);
            admin = adminService.saveOrUpdate(admin);
            String dir = Admin.IMAGE_ROOT_DIR + "/" + admin.getId();
            FileUploadUtils.cleanDir(dir);
            FileUploadUtils.saveFile(dir, fileName, fileUpload);
        } else {
            if(admin.getImage().isEmpty()) {
                admin.setImage(null);
            }
            adminService.saveOrUpdate(admin);
        }

        return "redirect:/admins";
    }

    @GetMapping("/details")
    public String details(Integer id, Model model) {
        Admin admin = adminService.get(id);
        model.addAttribute("admin", admin);

        return "/admin/details";
    }

}
