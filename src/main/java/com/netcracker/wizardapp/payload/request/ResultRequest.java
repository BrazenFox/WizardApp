package com.netcracker.wizardapp.payload.request;

public class ResultRequest {
    Long wizard_id;
    Long user_id;
    String data;

    public Long getWizard_id() {
        return wizard_id;
    }

    public void setWizard_id(Long wizard_id) {
        this.wizard_id = wizard_id;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
