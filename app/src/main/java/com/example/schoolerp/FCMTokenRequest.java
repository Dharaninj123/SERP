package com.example.schoolerp;

public class FCMTokenRequest {

        private String token;

        public FCMTokenRequest(String token) {
            this.token = token;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

    public void saveFCMToken(String token) {
    }
}

