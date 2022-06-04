package com.dacs.adminmodules.utils;

import javax.servlet.http.HttpServletRequest;

public class AdminLoginUtils {

    public static String getEmail(HttpServletRequest request) {
        return request.getUserPrincipal().getName();
    }

}
