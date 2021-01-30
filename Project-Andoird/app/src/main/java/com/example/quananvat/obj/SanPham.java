package com.example.quananvat.obj;

import java.io.Serializable;

public class SanPham implements Serializable {
    private String tensanpham;
    private double giasanpham;
    private double giakhisale;
    private boolean sale;
    private boolean sanphammoi;
    private boolean sanphambanchay;
    private double danhgia;
    private int hinh;
    private String hinhanh;
    private String id;
    public SanPham(){}

    public SanPham(String tensanpham, double giasanpham, double danhgia, int hinh) {
        this.tensanpham = tensanpham;
        this.giasanpham = giasanpham;
        this.danhgia = danhgia;
        this.hinh = hinh;
    }


    public SanPham(String tensanpham, double giasanpham, double giakhisale, boolean sale, double danhgia, int hinh) {
        this.tensanpham = tensanpham;
        this.giasanpham = giasanpham;
        this.giakhisale = giakhisale;
        this.sale = sale;
        this.danhgia = danhgia;
        this.hinh = hinh;
    }

    public SanPham(String tensanpham, double giasanpham, double giakhisale, boolean sale, boolean sanphammoi, boolean sanphambanchay, double danhgia, String hinhanh) {
        this.tensanpham = tensanpham;
        this.giasanpham = giasanpham;
        this.giakhisale = giakhisale;
        this.sale = sale;
        this.sanphammoi = sanphammoi;
        this.sanphambanchay = sanphambanchay;
        this.danhgia = danhgia;
        this.hinhanh = hinhanh;
    }

    public boolean isSanphammoi() {
        return sanphammoi;
    }

    public void setSanphammoi(boolean sanphammoi) {
        this.sanphammoi = sanphammoi;
    }

    public boolean isSanphambanchay() {
        return sanphambanchay;
    }

    public void setSanphambanchay(boolean sanphambanchay) {
        this.sanphambanchay = sanphambanchay;
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

    public int getHinh() {
        return hinh;
    }

    public void setHinh(int hinh) {
        this.hinh = hinh;
    }

    public String getTensanpham() {
        return tensanpham;
    }

    public void setTensanpham(String tensanpham) {
        this.tensanpham = tensanpham;
    }

    public double getGiasanpham() {
        return giasanpham;
    }

    public void setGiasanpham(double giasanpham) {
        this.giasanpham = giasanpham;
    }

    public double getGiakhisale() {
        return giakhisale;
    }

    public void setGiakhisale(double giakhisale) {
        this.giakhisale = giakhisale;
    }

    public boolean isSale() {
        return sale;
    }

    public void setSale(boolean sale) {
        this.sale = sale;
    }

    public double getDanhgia() {
        return danhgia;
    }

    public void setDanhgia(double danhgia) {
        this.danhgia = danhgia;
    }
}
