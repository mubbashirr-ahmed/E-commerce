package com.RTechnologies.booksandbooks.Models;

public class Historyitem {

    private String imgURL;
    private String itemName;
    private double price;
    private  int itemnum;



    private  String date;
    public Historyitem(int itemnum, String date, String itemName, String imgURL, double price)
    {

        this.imgURL = imgURL;
        this.itemName = itemName;
        this.price = price;
        this.itemnum = itemnum;
        this.date = date;

    }
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
    public int getItemId() {
        return itemnum;
    }
    public void setItemId(int itemnum)
    {
        this.itemnum = itemnum;
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
