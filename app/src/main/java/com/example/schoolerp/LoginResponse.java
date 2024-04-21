package com.example.schoolerp;

public class LoginResponse {

    private int user_id;
    private String name;
    private String mobile_number;

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUser_type() {
        return name;
    }

    public void setUser_type(String user_type) {
        this.name = user_type;
    }

    public String getMobile_number() {
        return mobile_number;
    }

    public void setMobile_number(String mobile_number) {
        this.mobile_number = mobile_number;
    }

    public String getAccessToken() {
        return null;
    }
}
