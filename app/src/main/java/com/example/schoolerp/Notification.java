package com.example.schoolerp;


public class Notification {
    private String topic;
    private String subject;

    public Notification(String topic, String subject) {
        this.topic = topic;
        this.subject = subject;
    }

    public String getTopic() {
        return topic;
    }

    public String getSubject() {
        return subject;
    }
}
