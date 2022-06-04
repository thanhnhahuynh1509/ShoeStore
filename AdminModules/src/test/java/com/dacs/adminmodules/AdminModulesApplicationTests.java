package com.dacs.adminmodules;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AdminModulesApplicationTests {

    @Test
    void contextLoads() {

    }

    @Test
    public void newTest() {
        String name = "Huỳnh Thanh Nhã";
        // a: aáàảãạâấầẩẫậăắằẳẵặ
        // d: đ
        // e: éèẻẽẹêếềểễệ
        // y: ýỳỷỹỵ
        // u: úùủũụưứừửữự
        // i: íìỉĩị
        // o: óòỏõọôốồổỗộơớờởỡợ
        name = name.replaceAll("[aáàảãạâấầẩẫậăắằẳẵặ]", "a")
                .replaceAll("[đ]", "d")
                .replaceAll("[éèẻẽẹêếềểễệ]", "e")
                .replaceAll("[ýỳỷỹỵ]", "y")
                .replaceAll("[úùủũụưứừửữự]", "u")
                .replaceAll("[íìỉĩị]", "i")
                .replaceAll("[óòỏõọôốồổỗộơớờởỡợ]", "o");
        System.out.println(name);


    }

}
