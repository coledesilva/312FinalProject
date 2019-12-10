package com.example.finalproject;

public class HuntText {
    private String huntMessage;
    private String title;

    public HuntText(String huntMessage,String title) {
        this.huntMessage = huntMessage;
        this.title = title;
    }

    public String getHuntMessage() {
        return huntMessage;
    }

    public void setHuntMessage(String huntMessage) {
        this.huntMessage = huntMessage;
    }

    @Override
    public String toString() {
        return title;
    }
}
