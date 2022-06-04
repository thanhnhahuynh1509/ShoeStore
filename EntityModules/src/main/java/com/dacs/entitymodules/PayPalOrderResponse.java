package com.dacs.entitymodules;

public class PayPalOrderResponse {

    private String id;
    private String status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean validate(String id) {
        return this.id.equals(id) && status.equals("COMPLETED");
    }
}
