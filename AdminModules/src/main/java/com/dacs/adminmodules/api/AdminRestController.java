package com.dacs.adminmodules.api;

import com.dacs.adminmodules.service.AdminService;
import com.dacs.adminmodules.utils.FileUploadUtils;
import com.dacs.entitymodules.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RestController
@RequestMapping("/api/admins")
public class AdminRestController {

    private final AdminService adminService;

    public AdminRestController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/changeStatus")
    public boolean changeStatus(Integer id, boolean status) {
        adminService.changeStatus(id, status);
        return !status;
    }

    @PostMapping("/checkEmailExists")
    public String checkEmailExists(Integer id, String email) {
        Admin admin = adminService.getByEmail(email);

        if(admin != null && !admin.getId().equals(id))
            return "NOT OK";

        return "OK";
    }


    @GetMapping("/delete")
    public String delete(Integer id) {
        Admin admin = adminService.get(id);

        if(admin == null) {
            return "NOT OKE";
        }

        String dir = Admin.IMAGE_ROOT_DIR + "/" + admin.getId();
        FileUploadUtils.cleanDir(dir);
        FileUploadUtils.deleteDir(dir);

        adminService.delete(id);

        return "OK";
    }
}
