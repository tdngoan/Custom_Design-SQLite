package com.itnnsoft.custom_design_sqlite.model;

public class LienHe {
    String ten;
    String number;
    String gioi_tinh;

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getGioi_tinh() {
        return gioi_tinh;
    }

    public void setGioi_tinh(String gioi_tinh) {
        this.gioi_tinh = gioi_tinh;
    }

    public LienHe(String number, String ten, String gioi_tinh) {
        this.ten = ten;
        this.number = number;
        this.gioi_tinh = gioi_tinh;
    }

    public LienHe() {
    }
}
