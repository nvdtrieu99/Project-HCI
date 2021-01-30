package com.example.quananvat.obj;

import java.io.Serializable;

public class Combo implements Serializable {
    private String combo;
    private String mota;
    private double gia;
    private String hinhanh;
    private double danhgia;
    private int hinh;
    private String id;


    public Combo(){}
    public Combo(String combo, String mota, double gia, int hinh) {
        this.combo = combo;
        this.mota = mota;
        this.gia = gia;
        this.hinh = hinh;
    }

    public Combo(String combo, String mota, double gia, String hinhanh) {
        this.combo = combo;
        this.mota = mota;
        this.gia = gia;
        this.hinhanh = hinhanh;
    }

    public Combo(String combo, String mota, double gia, String hinhanh, double danhgia) {
        this.combo = combo;
        this.mota = mota;
        this.gia = gia;
        this.hinhanh = hinhanh;
        this.danhgia = danhgia;
    }

    public double getDanhgia() {
        return danhgia;
    }

    public void setDanhgia(double danhgia) {
        this.danhgia = danhgia;
    }

    public String getHinhanh() {
        return hinhanh;
    }

    public void setHinhanh(String hinhanh) {
        this.hinhanh = hinhanh;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCombo() {
        return combo;
    }

    public void setCombo(String combo) {
        this.combo = combo;
    }

    public String getMota() {
        return mota;
    }

    public void setMota(String mota) {
        this.mota = mota;
    }

    public double getGia() {
        return gia;
    }

    public void setGia(double gia) {
        this.gia = gia;
    }

    public int getHinh() {
        return hinh;
    }

    public void setHinh(int hinh) {
        this.hinh = hinh;
    }
}
