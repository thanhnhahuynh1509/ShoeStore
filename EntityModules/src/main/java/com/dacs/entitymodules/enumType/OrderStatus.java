package com.dacs.entitymodules.enumType;

public enum OrderStatus {
    NEW("Mới"), PACKAGE("Đóng gói"),
    SHIPPING("Vận chuyển"), SUCCESS("Giao hàng thành công"),
    CANCEL("Hủy"), REFUND("Hoàn trả");

    private String description;

    OrderStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
