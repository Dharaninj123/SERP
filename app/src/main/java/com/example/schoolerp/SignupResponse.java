package com.example.schoolerp;

public class SignupResponse {
    private String first_name;
    private String date_of_birth;
    private String mobile_number;
    private String password;
    private String password2;
    private int user_type;

    public String getFirst_name() { return first_name; }

    public void setFirst_name(String first_name) { this.first_name = first_name; }

    public String getDate_of_birth() { return date_of_birth; }

    public void setDate_of_birth(String date_of_birth) { this.date_of_birth = date_of_birth; }

    public String getMobile_number() { return mobile_number; }

    public void setMobile_number(String mobile_number) { this.mobile_number = mobile_number; }

    public String getPassword() { return password; }

    public void setPassword(String password) { this.password = password; }

    public String getPassword2() { return password2; }

    public void setPassword2(String password2) { this.password2 = password2; }

    public int getUser_type() { return user_type; }

    public void setUser_type(int user_type) { this.user_type = user_type; }
}
