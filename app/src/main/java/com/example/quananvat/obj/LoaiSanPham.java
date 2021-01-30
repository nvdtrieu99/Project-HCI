package com.example.quananvat.obj;

import java.io.Serializable;

public class LoaiSanPham implements Serializable {
    private String hinh;
    private String ten;

    public LoaiSanPham(){}

    public LoaiSanPham(String hinh, String ten) {
        this.hinh = hinh;
        this.ten = ten;
    }

    public String getHinh() {
        return hinh;
    }

    public void setHinh(String hinh) {
        this.hinh = hinh;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }
}
