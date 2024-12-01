package com.it4_k12.btl.Model;

public class DanhGiaSanPham {
    private int maDanhGia;
    private int maUser;
    private int maSanPham;
    private int diem;
    private String binhLuan;
    private String ngayDanhGia;
    private String hoTenNguoiDanhGia;
    // Default constructor
    public DanhGiaSanPham() {}

    // Parameterized constructor
    public DanhGiaSanPham(int maDanhGia, int maUser, int maSanPham, int diem, String binhLuan, String ngayDanhGia, String hoTenNguoiDanhGia) {
        this.maDanhGia = maDanhGia;
        this.maUser = maUser;
        this.maSanPham = maSanPham;
        this.diem = diem;
        this.binhLuan = binhLuan;
        this.ngayDanhGia = ngayDanhGia;
        this.hoTenNguoiDanhGia = hoTenNguoiDanhGia;
    }

    // Getters and Setters
    public int getMaDanhGia() { return maDanhGia; }
    public void setMaDanhGia(int maDanhGia) { this.maDanhGia = maDanhGia; }

    public int getMaUser() { return maUser; }
    public void setMaUser(int maUser) { this.maUser = maUser; }

    public int getMaSanPham() { return maSanPham; }
    public void setMaSanPham(int maSanPham) { this.maSanPham = maSanPham; }

    public int getDiem() { return diem; }
    public void setDiem(int diem) { this.diem = diem; }

    public String getBinhLuan() { return binhLuan; }
    public void setBinhLuan(String binhLuan) { this.binhLuan = binhLuan; }

    public String getNgayDanhGia() { return ngayDanhGia; }
    public void setNgayDanhGia(String ngayDanhGia) { this.ngayDanhGia = ngayDanhGia; }

    public String getHoTenNguoiDanhGia() { return hoTenNguoiDanhGia; }
    public void setHoTenNguoiDanhGia(String hoTenNguoiDanhGia) { this.hoTenNguoiDanhGia = hoTenNguoiDanhGia; }
}
