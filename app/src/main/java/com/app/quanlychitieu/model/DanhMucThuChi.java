package com.app.quanlychitieu.model;

import android.graphics.Bitmap;

public class DanhMucThuChi {
    private Integer id;
    private String ten;
    private Bitmap hinhanh;
    private Integer loai; // 0 - thu , 1 - chi

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }


    public Integer getLoai() {
        return loai;
    }

    public void setLoai(Integer loai) {
        this.loai = loai;
    }

    public Bitmap getHinhanh() {
        return hinhanh;
    }

    public void setHinhanh(Bitmap hinhanh) {
        this.hinhanh = hinhanh;
    }

    public DanhMucThuChi() {
    }

    public DanhMucThuChi(String ten, Bitmap hinhanh) {
        this.ten = ten;
        this.hinhanh = hinhanh;
    }

    public DanhMucThuChi(String ten, Bitmap hinhanh, Integer loai) {
        this.ten = ten;
        this.hinhanh = hinhanh;
        this.loai = loai;
    }

    public DanhMucThuChi(Integer id, String ten, Bitmap hinhanh, Integer loai) {
        this.id = id;
        this.ten = ten;
        this.hinhanh = hinhanh;
        this.loai = loai;
    }
}
