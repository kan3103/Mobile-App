package com.example.mobile_app.ui.profile_frag;

public class Item_list{
    String name_ , descrip ;

    public Item_list(String name_, String descrip) {
        this.name_ = name_;
        this.descrip = descrip;
    }

    public String getName_() {
        return name_;
    }

    public void setName_(String name_) {
        this.name_ = name_;
    }

    public String getDescrip() {
        return descrip;
    }

    public void setDescrip(String descrip) {
        this.descrip = descrip;
    }
}
