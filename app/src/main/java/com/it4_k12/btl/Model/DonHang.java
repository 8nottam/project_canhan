package com.it4_k12.btl.Model;

public class DonHang {
    private int maDonHang;           // Mã đơn hàng
    private int maUser;              // Mã người dùng
    private int maSanPham;
    private String ngayDatHang;      // Ngày đặt hàng
    private String trangThai;        // Trạng thái đơn hàng
    private int  tongTien;         // Tổng tiền
    private String tenKhachHang;     // Tên khách hàng
    private String soDienThoai;      // Số điện thoại
    private String diaChi; // Địa chỉ
    private int soluong ;
    private String imageUrl;         // Ảnh sản phẩm
    private String tenSanPham;       // Tên sản phẩm
    private double giaSanPham;       // Giá sản phẩm

    // Default constructor
    public DonHang() {}

    // Parameterized constructor
    public DonHang(int maDonHang, int maUser, int maSanPham, String ngayDatHang, String trangThai, int tongTien,
                   String tenKhachHang, String soDienThoai, String diaChi,
                   String imageUrl, String tenSanPham, double giaSanPham) {
        this.maDonHang = maDonHang;
        this.maUser = maUser;
        this.maSanPham = maSanPham;
        this.ngayDatHang = ngayDatHang;
        this.trangThai = trangThai;
        this.tongTien = tongTien;
        this.tenKhachHang = tenKhachHang;
        this.soDienThoai = soDienThoai;
        this.diaChi = diaChi;
        this.imageUrl = imageUrl;     // Thêm ảnh sản phẩm
        this.tenSanPham = tenSanPham;  // Thêm tên sản phẩm
        this.giaSanPham = giaSanPham;  // Thêm giá sản phẩm
    }

    // Getters and Setters
    public int getMaDonHang() { return maDonHang; }
    public void setMaDonHang(int maDonHang) { this.maDonHang = maDonHang; }

    public int getMaUser() { return maUser; }
    public void setMaUser(int maUser) { this.maUser = maUser; }

    public int getMaSanPham() { return maSanPham; }
    public void setMaSanPham(int maSanPham) { this.maSanPham = maSanPham; }

    public String getNgayDatHang() { return ngayDatHang; }
    public void setNgayDatHang(String ngayDatHang) { this.ngayDatHang = ngayDatHang; }

    public String getTrangThai() { return trangThai; }
    public void setTrangThai(String trangThai) { this.trangThai = trangThai; }

    public int getTongTien() { return tongTien; }
    public void setTongTien(int tongTien) { this.tongTien = tongTien; }

    public String getTenKhachHang() { return tenKhachHang; }
    public void setTenKhachHang(String tenKhachHang) { this.tenKhachHang = tenKhachHang; }

    public String getSoDienThoai() { return soDienThoai; }
    public void setSoDienThoai(String soDienThoai) { this.soDienThoai = soDienThoai; }

    public String getDiaChi() { return diaChi; }
    public void setDiaChi(String diaChi) { this.diaChi = diaChi; }

    public int getSoluong() { return soluong; }
    public void setSoluong(int soluong) { this.soluong = soluong; }

    public String getImageUrl() { return imageUrl; }           // Getter cho ảnh sản phẩm
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; } // Setter cho ảnh sản phẩm

    public String getTenSanPham() { return tenSanPham; }       // Getter cho tên sản phẩm
    public void setTenSanPham(String tenSanPham) { this.tenSanPham = tenSanPham; } // Setter cho tên sản phẩm

    public double getGiaSanPham() { return giaSanPham; }       // Getter cho giá sản phẩm
    public void setGiaSanPham(double giaSanPham) { this.giaSanPham = giaSanPham; } // Setter cho giá sản phẩm
}
