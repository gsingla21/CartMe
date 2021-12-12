package com.example.foodpalace.Models;

import java.util.ArrayList;

public class MarketModel extends ArrayList<MarketModel> {
    String image;
    String name;

    public MarketModel()
    {}

    public MarketModel(String image, String name) {
        this.image = image;
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}