package com.example.foodpalace.Models;

import java.util.ArrayList;

public class StoreModel extends ArrayList<StoreModel> {
    String fname,fimage,ingredients,price;

    public StoreModel()
    {

    }

    public StoreModel(String fname, String fimage, String ingredients, String price) {
        this.fname = fname;
        this.fimage = fimage;
        this.ingredients = ingredients;
        this.price = price;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getFimage() {
        return fimage;
    }

    public void setFimage(String fimage) {
        this.fimage = fimage;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
