package com.it4_k12.btl.Model;

public class HoaDon {
    private int maHoaDon;
    private int maUser;
    private String ngayLap;
    private int  tongTien;
    private String trangThai;
    private int soluong; // Số lượng sản phẩm
    private String imageUrl; // URL hình ảnh sản phẩm
    private String tenSanPham; // Tên sản phẩm

    // Default constructor
    public HoaDon() {}

    // Parameterized constructor
    public HoaDon(int maHoaDon, int maUser, String ngayLap, int tongTien, String trangThai, int soluong, String imageUrl, String tenSanPham) {
        this.maHoaDon = maHoaDon;
        this.maUser = maUser;
        this.ngayLap = ngayLap;
        this.tongTien = tongTien;
        this.trangThai = trangThai;
        this.soluong = soluong;
        this.imageUrl = imageUrl;
        this.tenSanPham = tenSanPham;
    }

    // Getters and Setters
    public int getMaHoaDon() { return maHoaDon; }
    public void setMaHoaDon(int maHoaDon) { this.maHoaDon = maHoaDon; }

    public int getMaUser() { return maUser; }
    public void setMaUser(int maUser) { this.maUser = maUser; }

    public String getNgayLap() { return ngayLap; }
    public void setNgayLap(String ngayLap) { this.ngayLap = ngayLap; }

    public int getTongTien() { return tongTien; }
    public void setTongTien(int tongTien) { this.tongTien = tongTien; }

    public String getTrangThai() { return trangThai; }
    public void setTrangThai(String trangThai) { this.trangThai = trangThai; }

    public int getSoluong() { return soluong; }
    public void setSoluong(int soluong) { this.soluong = soluong; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public String getTenSanPham() { return tenSanPham; }
    public void setTenSanPham(String tenSanPham) { this.tenSanPham = tenSanPham; }
}
