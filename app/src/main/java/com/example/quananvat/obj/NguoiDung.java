package com.example.quananvat.obj;

import java.io.Serializable;
import java.util.ArrayList;

public class NguoiDung implements Serializable {

    private String tennguoidung;
    private String sodienthoai;
    private String email;
    private String hinhanh;
    private String matkhau;
    private String loaidangnhap;
    private String UID;
    private Boolean trangthai;
    private ArrayList<DiaChiNhanHang> danhsachdiachi;
    private ArrayList<DonHang> danhsachdonhang;

    public NguoiDung(){

    }

    public ArrayList<DonHang> getDanhsachdonhang() {
        return danhsachdonhang;
    }

    public void setDanhsachdonhang(ArrayList<DonHang> danhsachdonhang) {
        this.danhsachdonhang = danhsachdonhang;
    }

    public String getTennguoidung() {
        return tennguoidung;
    }

    public void setTennguoidung(String tennguoidung) {
        this.tennguoidung = tennguoidung;
    }

    public String getSodienthoai() {
        return sodienthoai;
    }

    public void setSodienthoai(String sodienthoai) {
        this.sodienthoai = sodienthoai;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHinhanh() {
        return hinhanh;
    }

    public void setHinhanh(String hinhanh) {
        this.hinhanh = hinhanh;
    }

    public String getMatkhau() {
        return matkhau;
    }

    public void setMatkhau(String matkhau) {
        this.matkhau = matkhau;
    }

    public String getLoaidangnhap() {
        return loaidangnhap;
    }

    public void setLoaidangnhap(String loaidangnhap) {
        this.loaidangnhap = loaidangnhap;
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public Boolean getTrangthai() {
        return trangthai;
    }

    public void setTrangthai(Boolean trangthai) {
        this.trangthai = trangthai;
    }

    public ArrayList<DiaChiNhanHang> getDanhsachdiachi() {
        return danhsachdiachi;
    }

    public void setDanhsachdiachi(ArrayList<DiaChiNhanHang> danhsachdiachi) {
        this.danhsachdiachi = danhsachdiachi;
    }
}
