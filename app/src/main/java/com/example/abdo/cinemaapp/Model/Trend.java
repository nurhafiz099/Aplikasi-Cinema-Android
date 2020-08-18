package com.example.abdo.cinemaapp.Model;

import com.google.gson.annotations.SerializedName;

public class Trend {
    private String id;
    @SerializedName("poster_path")
    private String image;
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Trend(String id, String image, String type) {
        this.id = id;
        this.image = image;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
