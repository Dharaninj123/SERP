package com.example.schoolerp;

public class VerifyOTPResponse {
    private int user_type;

    public int getUser_type() { return user_type; }

    public void setUser_type(int user_type) { this.user_type = user_type; }

    private int otp;

    public int getOtp() {
        return otp;
    }

    public void setOtp(int otp) {
        this.otp = otp;
    }
}
