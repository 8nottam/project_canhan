package com.it4_k12.btl.Model;

public class ChiTietHoaDon {
    private int maChiTietHoaDon;
    private int maHoaDon;
    private int maSanPham;
    private int maThuocTinh;
    private int soLuong;
    private int gia;
    private String tenSanPham;
    private String anhSanPham;
    private String size;
    private String mauSac;
    private int tongTien;
    private String ngayDatHang;
    // Default constructor
    public ChiTietHoaDon() {}

    // Parameterized constructor
    public ChiTietHoaDon(int maChiTietHoaDon, int maHoaDon, int maSanPham, int maThuocTinh, int soLuong, int gia) {
        this.maChiTietHoaDon = maChiTietHoaDon;
        this.maHoaDon = maHoaDon;
        this.maSanPham = maSanPham;
        this.maThuocTinh = maThuocTinh;
        this.soLuong = soLuong;
        this.gia = gia;
    }

    // Getters and Setters
    public int getMaChiTietHoaDon() { return maChiTietHoaDon; }
    public void setMaChiTietHoaDon(int maChiTietHoaDon) { this.maChiTietHoaDon = maChiTietHoaDon; }

    public int getMaHoaDon() { return maHoaDon; }
    public void setMaHoaDon(int maHoaDon) { this.maHoaDon = maHoaDon; }

    public int getMaSanPham() { return maSanPham; }
    public void setMaSanPham(int maSanPham) { this.maSanPham = maSanPham; }

    public int getMaThuocTinh() { return maThuocTinh; }
    public void setMaThuocTinh(int maThuocTinh) { this.maThuocTinh = maThuocTinh; }

    public int getSoLuong() { return soLuong; }
    public void setSoLuong(int soLuong) { this.soLuong = soLuong; }

    public int getGia() { return gia; }
    public void setGia(int gia) { this.gia = gia; }

    public String getTenSanPham() {
        return tenSanPham;
    }

    public void setTenSanPham(String tenSanPham) {
        this.tenSanPham = tenSanPham;
    }

    public String getAnhSanPham() {
        return anhSanPham;
    }

    public void setAnhSanPham(String anhSanPham) {
        this.anhSanPham = anhSanPham;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getMauSac() {
        return mauSac;
    }

    public void setMauSac(String mauSac) {
        this.mauSac = mauSac;
    }

    public int getTongTien() {
        return tongTien;
    }

    public void setTongTien(int tongTien) {
        this.tongTien = tongTien;
    }

    public String getNgayDatHang() {
        return ngayDatHang;
    }

    public void setNgayDatHang(String ngayDatHang) {
        this.ngayDatHang = ngayDatHang;
    }
}
