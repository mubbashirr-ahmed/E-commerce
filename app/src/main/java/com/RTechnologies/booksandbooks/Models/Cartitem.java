package com.RTechnologies.booksandbooks.Models;

public class Cartitem {
    private String productName;
    private int numOfItems;
    private double price;
    private String date;
    private String imgURL;
    private String discription;

    public Cartitem(String productName,String imgURL, int numOfItems, double price, String date , String discription) {
        this.productName = productName;
        this.imgURL = imgURL;
        this.numOfItems = numOfItems;
        this.price = price;
        this.date = date;
        this.discription=discription;
    }

    public String getProductName() {
        return productName;
    }

    public String getDiscription(){return discription;}

    public int getNumOfItems() {
        return numOfItems;
    }

    public String getImgURL() {
        return imgURL;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }

    public double getPrice() {
        return price;
    }

    public String getDate() {
        return date;
    }
}
