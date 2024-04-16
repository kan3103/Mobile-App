package com.example.mobile_app.ui.thuoc_patient;

public class in4medicine {
    private  String name , soluong, ngay_cap;

    public in4medicine(String name, String soluong, String ngay_cap) {
        this.name = name;
        this.soluong = soluong;
        this.ngay_cap = ngay_cap;
    }
    public String getName() {return name;}
    public void setName(String name) {this.name = name;}
    public String getSoluong() {return soluong;}
    public void setSoluong(String soluong) {this.soluong = soluong;}
    public String getNgay_cap() {return ngay_cap;}
    public void setNgay_cap(String ngay_cap) {this.ngay_cap = ngay_cap;}
}
