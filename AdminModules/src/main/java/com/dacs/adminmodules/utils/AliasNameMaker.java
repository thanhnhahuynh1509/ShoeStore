package com.dacs.adminmodules.utils;

import java.util.Locale;

public class AliasNameMaker {

    public static String make(String name) {
        if(name == null)
            return "";
        return name.toLowerCase()
                .replaceAll("[aáàảãạâấầẩẫậăắằẳẵặ]", "a")
                .replaceAll("[đ]", "d")
                .replaceAll("[éèẻẽẹêếềểễệ]", "e")
                .replaceAll("[ýỳỷỹỵ]", "y")
                .replaceAll("[úùủũụưứừửữự]", "u")
                .replaceAll("[íìỉĩị]", "i")
                .replaceAll("[óòỏõọôốồổỗộơớờởỡợ]", "o")
                .replaceAll("\\p{Blank}", "-");
    }
}
