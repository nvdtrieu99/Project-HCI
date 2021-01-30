package com.example.quananvat.obj;

import java.io.Serializable;

public class DiaChiNhanHang implements Serializable {
    private String tennguoinhan;
    private String sdtnguoinhan;
    private String tinh_thanhpho;
    private String quan_huyen;
    private String phuong_xa;
    private String diachicuthe;
    private Boolean diachimacdinh;
    private String key;

    public DiaChiNhanHang(){}

    public DiaChiNhanHang(String tennguoinhan, String sdtnguoinhan, String tinh_thanhpho, String quan_huyen, String phuong_xa, String diachicuthe, Boolean diachimacdinh) {
        this.tennguoinhan = tennguoinhan;
        this.sdtnguoinhan = sdtnguoinhan;
        this.tinh_thanhpho = tinh_thanhpho;
        this.quan_huyen = quan_huyen;
        this.phuong_xa = phuong_xa;
        this.diachicuthe = diachicuthe;
        this.diachimacdinh = diachimacdinh;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
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

    public String getTinh_thanhpho() {
        return tinh_thanhpho;
    }

    public void setTinh_thanhpho(String tinh_thanhpho) {
        this.tinh_thanhpho = tinh_thanhpho;
    }

    public String getQuan_huyen() {
        return quan_huyen;
    }

    public void setQuan_huyen(String quan_huyen) {
        this.quan_huyen = quan_huyen;
    }

    public String getPhuong_xa() {
        return phuong_xa;
    }

    public void setPhuong_xa(String phuong_xa) {
        this.phuong_xa = phuong_xa;
    }

    public String getDiachicuthe() {
        return diachicuthe;
    }

    public void setDiachicuthe(String diachicuthe) {
        this.diachicuthe = diachicuthe;
    }

    public Boolean getDiachimacdinh() {
        return diachimacdinh;
    }

    public void setDiachimacdinh(Boolean diachimacdinh) {
        this.diachimacdinh = diachimacdinh;
    }
}
