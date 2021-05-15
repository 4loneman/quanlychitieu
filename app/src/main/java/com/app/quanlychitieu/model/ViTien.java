package com.app.quanlychitieu.model;

public class ViTien {
    private Integer id;
    private String ten;
    private Float sodu;
    private Integer loai; // 1 vi tien, 2 the,
    private Integer trangthai;

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

    public Float getSodu() {
        return sodu;
    }

    public void setSodu(Float sodu) {
        this.sodu = sodu;
    }

    public Integer getLoai() {
        return loai;
    }

    public void setLoai(Integer loai) {
        this.loai = loai;
    }

    public ViTien() {
    }

    public ViTien(Integer id, String ten, float sodu, Integer loai) {
        this.id = id;
        this.ten = ten;
        this.sodu = sodu;
        this.loai = loai;
    }
}
