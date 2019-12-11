package com.example.finalproject;
/**
 * This is the HuntText model which stores scavenger hunt information
 * @authors: Cole & Jackson
 * @version: v1.0
 * @date: 12/11/2019
 */

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
