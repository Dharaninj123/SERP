package com.example.schoolerp;

public class LoginRequest {
    private String mobile_number;
    private String password;

    private String user_type;

    public String getUser_type() { return user_type;
    }

    public void setUser_type(String user_type) { this.user_type = user_type;
    }

    public String getMobile_number() {
        return mobile_number;
    }

    public void setMobile_number(String mobile_number) {
        this.mobile_number = mobile_number;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
