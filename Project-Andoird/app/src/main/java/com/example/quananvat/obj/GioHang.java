package com.example.quananvat.obj;

import java.io.Serializable;

public class GioHang implements Serializable {
    private SanPham sanPham;
    private Combo combo;
    private int soLuong;

    public GioHang(){}
    public GioHang(SanPham sanPham, int soLuong){
        this.sanPham = sanPham;
        this.soLuong = soLuong;
    }

    public GioHang(Combo combo, int soLuong) {
        this.combo = combo;
        this.soLuong = soLuong;
    }

    public Combo getCombo() {
        return combo;
    }

    public void setCombo(Combo combo) {
        this.combo = combo;
    }

    public SanPham getSanPham() {
        return sanPham;
    }

    public void setSanPham(SanPham sanPham) {
        this.sanPham = sanPham;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }
}
