package com.RTechnologies.booksandbooks.Models;

public class Item {
    private int discount;
    private String imgURL;
    private String itemName;
    private double price;
    private String discription;
    int itemId;

    public Item(int discount, String imgURL, String itemName, double price, String discription, int itemId) {
        this.discount = discount;
        this.imgURL = imgURL;
        this.itemName = itemName;
        this.price = price;
        this.discription=discription;
        this.itemId=itemId;
    }

    public int getDiscount() {
        return discount;
    }

    public  String getDiscription(){ return discription;}

    public int getItemId(){return itemId;}

    public void setItemId(int itemId) { this.itemId=itemId; }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public String getImgURL() {
        return imgURL;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
