package com.dacs.adminmodules.controller;

import com.dacs.adminmodules.service.SettingService;
import com.dacs.adminmodules.utils.AliasNameMaker;
import com.dacs.adminmodules.utils.FileUploadUtils;
import com.dacs.entitymodules.Brand;
import com.dacs.entitymodules.Setting;
import com.dacs.entitymodules.enumType.SettingType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/settings")
public class SettingController {

    private final SettingService settingService;

    public SettingController(SettingService settingService) {
        this.settingService = settingService;
    }

    @GetMapping("")
    public String index(Model model) {
        List<Setting> settings = settingService.listAll();
        for(Setting setting : settings) {
            model.addAttribute(setting.getKey(), setting.getValue());
        }

        return "/setting/index";
    }

    @PostMapping("/save")
    public String save(HttpServletRequest request) {
        String type = request.getParameter("type");
        SettingType settingType = getType(type);

        List<Setting> settings = settingService.listByType(settingType);

        for(Setting setting : settings) {
            String key = setting.getKey();
            String value = request.getParameter(key);
            if(value != null) {
                setting.setValue(value);
                settingService.save(setting);
            }
        }

        return "redirect:/settings";
    }

    @PostMapping("/saveGeneral")
    public String saveGeneral(HttpServletRequest request, @RequestParam MultipartFile fileUpload) {
        List<Setting> settings = settingService.listByType(SettingType.GENERAL);
        if(fileUpload != null && !fileUpload.isEmpty()) {
            String fileName = fileUpload.getOriginalFilename();
            String dir = "logo-images/";
            FileUploadUtils.cleanDir(dir);
            FileUploadUtils.saveFile(dir, fileName, fileUpload);
            Setting setting = settingService.getByKey("LOGO");
            setting.setValue(fileName);
            settingService.save(setting);
        }

        for(Setting setting : settings) {
            String key = setting.getKey();
            String value = request.getParameter(key);
            if(value != null) {
                setting.setValue(value);
                settingService.save(setting);
            }
        }

        return "redirect:/settings";
    }

    private SettingType getType(String type) {
        switch (type) {
            case "MAIL_SERVER":
                return SettingType.MAIL_SERVER;
            case "MAIL_TEMPLATES":
                return SettingType.MAIL_TEMPLATES;
            case "PAYPAL":
                return SettingType.PAYPAL;
        }
        return SettingType.GENERAL;
    }


}
