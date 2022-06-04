package com.dacs.adminmodules.utils;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;

public class MessageRedirectUtils {
    public static void setMessageRedirect(String message, String status, RedirectAttributes rd) {
        rd.addFlashAttribute("message", message);
        rd.addFlashAttribute("status", status);
    }
}
