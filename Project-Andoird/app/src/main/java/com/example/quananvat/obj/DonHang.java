package com.example.quananvat.obj;

import java.io.Serializable;
import java.util.ArrayList;

public class DonHang implements Serializable {
    private String idnguoidathang;
    private String tennguoinhan;
    private String sdtnguoinhan;
    private String diachinguoinhan;
    private String thoigiandathang;
    private String madonhang;
    private double tongtien;
    private boolean trangthaithanhtoan;
    private boolean trangthaigiaohang;
    private int loaithanhtoan; //0. Vidientu 1. Thanhtoankhinhanhang
    private ArrayList<ChiTietDonHang> chitietdonhang;

    public DonHang(){
    }

    public DonHang(String madonhang, boolean trangthaigiaohang) {
        this.madonhang = madonhang;
        this.trangthaigiaohang = trangthaigiaohang;
    }

    public String getIdnguoidathang() {
        return idnguoidathang;
    }

    public void setIdnguoidathang(String idnguoidathang) {
        this.idnguoidathang = idnguoidathang;
    }

    public int getLoaithanhtoan() {
        return loaithanhtoan;
    }

    public void setLoaithanhtoan(int loaithanhtoan) {
        this.loaithanhtoan = loaithanhtoan;
    }

    public String getTennguoinhan() {
        return tennguoinhan;
    }

    public void setTennguoinhan(String tennguoinhan) {
        this.tennguoinhan = tennguoinhan;
    }

    public String getSdtnguoinhan() {
        return sdtnguoinhan;
    }

    public void setSdtnguoinhan(String sdtnguoinhan) {
        this.sdtnguoinhan = sdtnguoinhan;
    }

    public String getDiachinguoinhan() {
        return diachinguoinhan;
    }

    public void setDiachinguoinhan(String diachinguoinhan) {
        this.diachinguoinhan = diachinguoinhan;
    }

    public String getThoigiandathang() {
        return thoigiandathang;
    }

    public void setThoigiandathang(String thoigiandathang) {
        this.thoigiandathang = thoigiandathang;
    }

    public String getMadonhang() {
        return madonhang;
    }

    public void setMadonhang(String madonhang) {
        this.madonhang = madonhang;
    }

    public double getTongtien() {
        return tongtien;
    }

    public void setTongtien(double tongtien) {
        this.tongtien = tongtien;
    }

    public boolean isTrangthaithanhtoan() {
        return trangthaithanhtoan;
    }

    public void setTrangthaithanhtoan(boolean trangthaithanhtoan) {
        this.trangthaithanhtoan = trangthaithanhtoan;
    }

    public boolean isTrangthaigiaohang() {
        return trangthaigiaohang;
    }

    public void setTrangthaigiaohang(boolean trangthaigiaohang) {
        this.trangthaigiaohang = trangthaigiaohang;
    }

    public ArrayList<ChiTietDonHang> getChitietdonhang() {
        return chitietdonhang;
    }

    public void setChitietdonhang(ArrayList<ChiTietDonHang> chitietdonhang) {
        this.chitietdonhang = chitietdonhang;
    }
}
