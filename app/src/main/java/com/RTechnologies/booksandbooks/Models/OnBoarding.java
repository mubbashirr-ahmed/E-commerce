package com.RTechnologies.booksandbooks.Models;

public class OnBoarding {

    private int image1;
    private String title, message;

    public OnBoarding() {
    }

    public OnBoarding(int image1, String title, String message) {
        this.image1 = image1;
        this.title = title;
        this.message = message;
    }

    public int getImage1() {
        return image1;
    }

    public void setImage1(int image1) {
        this.image1 = image1;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
