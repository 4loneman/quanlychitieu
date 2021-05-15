package com.app.quanlychitieu.model;

import java.util.Calendar;

public class KhoanThuChi {
    private Integer id;
    private ViTien viTien;       // sử dụng ví nào để thanh toán id ViTien
    private DanhMucThuChi danhMucThuChi;  // id DanhMucThuChi
    private String ten;
    private Float tien;
    private Calendar thoigian;
    private Boolean loai;       // 0 = thu, 1 = chi;
    private String ghiChu;

    public Calendar getThoigian() {
        return thoigian;
    }

    public KhoanThuChi() {
    }

    public void setThoigian(Calendar thoigian) {
        this.thoigian = thoigian;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public KhoanThuChi(Integer id, ViTien viTien, DanhMucThuChi danhMucThuChi, String ten, Float tien, Calendar thoigian, Boolean loai, String ghiChu) {
        this.id = id;
        this.viTien = viTien;
        this.danhMucThuChi = danhMucThuChi;
        this.ten = ten;
        this.tien = tien;
        this.thoigian = thoigian;
        this.loai = loai;
        this.ghiChu = ghiChu;
    }

    public ViTien getViTien() {
        return viTien;
    }

    public void setViTien(ViTien viTien) {
        this.viTien = viTien;
    }

    public DanhMucThuChi getDanhMucThuChi() {
        return danhMucThuChi;
    }

    public void setDanhMucThuChi(DanhMucThuChi danhMucThuChi) {
        this.danhMucThuChi = danhMucThuChi;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public Float getTien() {
        return tien;
    }

    public void setTien(Float tien) {
        this.tien = tien;
    }

    public Boolean getLoai() {
        return loai;
    }

    public void setLoai(Boolean loai) {
        this.loai = loai;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }
}
