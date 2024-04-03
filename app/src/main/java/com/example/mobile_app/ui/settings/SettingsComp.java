package com.example.mobile_app.ui.settings;

public class SettingsComp {
    private int Img;
    private String Des;

    public SettingsComp(int img, String des) {
        Img = img;
        Des = des;
    }

    public int getImg() {
        return Img;
    }

    public void setImg(int img) {
        Img = img;
    }

    public String getDes() {
        return Des;
    }

    public void setDes(String des) {
        Des = des;
    }
}
