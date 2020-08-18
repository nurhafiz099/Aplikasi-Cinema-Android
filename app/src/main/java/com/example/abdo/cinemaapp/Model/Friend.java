package com.example.abdo.cinemaapp.Model;

import android.net.Uri;

public class Friend {
    String id;
    String name;
    Uri img;

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

    public Uri getImg() {
        return img;
    }

    public void setImg(Uri img) {
        this.img = img;
    }

    public Friend(String id, String name, Uri img) {
        this.id = id;
        this.name = name;
        this.img = img;
    }
}
