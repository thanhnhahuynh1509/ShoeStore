package com.dacs.entitymodules.enumType;

public enum PaymentMethod {
    COD("Thanh toán khi nhận hàng"), PAYPAL("Thanh toán thông qua PayPal");

    private String description;

    PaymentMethod(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
