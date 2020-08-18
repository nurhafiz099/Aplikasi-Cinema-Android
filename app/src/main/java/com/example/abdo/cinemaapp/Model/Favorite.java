package com.example.abdo.cinemaapp.Model;

public class Favorite  {
    String id;
    String name;
    String img;
    String type;

    public Favorite(String id, String name, String img, String type) {
        this.id = id;
        this.name = name;
        this.img = img;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
