package com.android.proteam.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public  class GenerateOTPModel {


    @Expose
    @SerializedName("user")
    private User user;
    @Expose
    @SerializedName("otp_code")
    private Otp_code otp_code;
    @Expose
    @SerializedName("message")
    private String message;
    @Expose
    @SerializedName("error")
    private boolean error;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Otp_code getOtp_code() {
        return otp_code;
    }

    public void setOtp_code(Otp_code otp_code) {
        this.otp_code = otp_code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean getError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public static class User {
        @Expose
        @SerializedName("city_id")
        private int city_id;
        @Expose
        @SerializedName("status")
        private String status;
        @Expose
        @SerializedName("Qstatus")
        private String Qstatus;
        @Expose
        @SerializedName("noti_key")
        private String noti_key;
        @Expose
        @SerializedName("email")
        private String email;
        @Expose
        @SerializedName("phno")
        private String phno;
        @Expose
        @SerializedName("last_name")
        private String last_name;
        @Expose
        @SerializedName("name")
        private String name;
        @Expose
        @SerializedName("id")
        private int id;

        public int getCity_id() {
            return city_id;
        }

        public void setCity_id(int city_id) {
            this.city_id = city_id;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getQstatus() {
            return Qstatus;
        }

        public void setQstatus(String Qstatus) {
            this.Qstatus = Qstatus;
        }

        public String getNoti_key() {
            return noti_key;
        }

        public void setNoti_key(String noti_key) {
            this.noti_key = noti_key;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPhno() {
            return phno;
        }

        public void setPhno(String phno) {
            this.phno = phno;
        }

        public String getLast_name() {
            return last_name;
        }

        public void setLast_name(String last_name) {
            this.last_name = last_name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }

    public static class Otp_code {
        @Expose
        @SerializedName("code")
        private String code;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }
    }
}
