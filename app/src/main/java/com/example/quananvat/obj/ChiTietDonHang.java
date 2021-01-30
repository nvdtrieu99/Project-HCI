package com.example.quananvat.obj;

import java.io.Serializable;

public class ChiTietDonHang implements Serializable {
    private String idsanpham;
    private String idcombo;
    private int soluong;

    public ChiTietDonHang(){}

    public ChiTietDonHang(String idsanpham, String idcombo, int soluong) {
        this.idsanpham = idsanpham;
        this.idcombo = idcombo;
        this.soluong = soluong;
    }

    public ChiTietDonHang(String idsanpham, int soluong) {
        this.idsanpham = idsanpham;
        this.soluong = soluong;
    }


    public String getIdsanpham() {
        return idsanpham;
    }

    public void setIdsanpham(String idsanpham) {
        this.idsanpham = idsanpham;
    }

    public String getIdcombo() {
        return idcombo;
    }

    public void setIdcombo(String idcombo) {
        this.idcombo = idcombo;
    }

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }
}
