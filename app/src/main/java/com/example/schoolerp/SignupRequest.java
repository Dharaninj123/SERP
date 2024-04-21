package com.example.schoolerp;

public class SignupRequest {

        private String name;
        private String mobile_number;
        private String dob;
        private String password;
        private String confirmpassword;




    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getMobile_number() {
        return mobile_number;
    }

    public void setMobile_number(String mobile_number) {
        this.mobile_number = mobile_number;
    }
    public String getDob() { return dob; }

    public void setDob(String dob) { this.dob = dob; }
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmpassword() { return confirmpassword;}
    public void setConfirmpassword(String confirmpassword) {this.confirmpassword = confirmpassword;}
}
