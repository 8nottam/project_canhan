package com.it4_k12.btl.Model;

public class ChiTietDonHang {
    private int maChiTietDonHang;
    private int maDonHang;
    private int maSanPham;
    private int maThuocTinh;
    private int soLuong;
    private int gia;
    private String tenSanPham; // Tên sản phẩm
    private String anhSanPham; // Ảnh sản phẩm
    private String tenKhachHang; // Tên người dùng
    private String size; // Size sản phẩm
    private String mauSac; // Màu sắc sản phẩm
    private String ngayDatHang; // Ngày đặt hàng
    private int tongTien; // Tổng tiền

    // Default constructor
    public ChiTietDonHang() {}

    // Parameterized constructor
    public ChiTietDonHang(int maChiTietDonHang, int maDonHang, int maSanPham, int maThuocTinh,
                          int soLuong, int gia, String tenSanPham, String anhSanPham,
                          String tenKhachHang, String size, String mauSac,
                          String ngayDatHang, int tongTien) {
        this.maChiTietDonHang = maChiTietDonHang;
        this.maDonHang = maDonHang;
        this.maSanPham = maSanPham;
        this.maThuocTinh = maThuocTinh;
        this.soLuong = soLuong;
        this.gia = gia;
        this.tenSanPham = tenSanPham;
        this.anhSanPham = anhSanPham;
        this.tenKhachHang = tenKhachHang;
        this.size = size;
        this.mauSac = mauSac;
        this.ngayDatHang = ngayDatHang;
        this.tongTien = tongTien;
    }

    // Getters and Setters
    public int getMaChiTietDonHang() { return maChiTietDonHang; }
    public void setMaChiTietDonHang(int maChiTietDonHang) { this.maChiTietDonHang = maChiTietDonHang; }

    public int getMaDonHang() { return maDonHang; }
    public void setMaDonHang(int maDonHang) { this.maDonHang = maDonHang; }

    public int getMaSanPham() { return maSanPham; }
    public void setMaSanPham(int maSanPham) { this.maSanPham = maSanPham; }

    public int getMaThuocTinh() { return maThuocTinh; }
    public void setMaThuocTinh(int maThuocTinh) { this.maThuocTinh = maThuocTinh; }

    public int getSoLuong() { return soLuong; }
    public void setSoLuong(int soLuong) { this.soLuong = soLuong; }

    public int getGia() { return gia; }
    public void setGia(int gia) { this.gia = gia; }

    public String getTenSanPham() { return tenSanPham; }
    public void setTenSanPham(String tenSanPham) { this.tenSanPham = tenSanPham; }

    public String getAnhSanPham() { return anhSanPham; }
    public void setAnhSanPham(String anhSanPham) { this.anhSanPham = anhSanPham; }

    public String getTenKhachHang() { return tenKhachHang; }
    public void setTenKhachHang(String tenKhachHang) { this.tenKhachHang = tenKhachHang; }

    public String getSize() { return size; }
    public void setSize(String size) { this.size = size; }

    public String getMauSac() { return mauSac; }
    public void setMauSac(String mauSac) { this.mauSac = mauSac; }

    public String getNgayDatHang() { return ngayDatHang; }
    public void setNgayDatHang(String ngayDatHang) { this.ngayDatHang = ngayDatHang; }

    public int getTongTien() { return tongTien; }
    public void setTongTien(int tongTien) { this.tongTien = tongTien; }
}
