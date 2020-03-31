package com.android.proteam.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public  class HealthDetailsModel {

    @Expose
    @SerializedName("message")
    private String message;
    @Expose
    @SerializedName("status")
    private int status;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
