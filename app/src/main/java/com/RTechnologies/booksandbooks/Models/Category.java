package com.RTechnologies.booksandbooks.Models;

public class Category {
    private String imgURL;
    private String catName;
    public Category(String imgURL, String catName) {
        this.imgURL = imgURL;
        this.catName = catName;
    }
    public String getImgURL() {
        return imgURL;
    }

    public String getCatName() {
        return catName;
    }

}
