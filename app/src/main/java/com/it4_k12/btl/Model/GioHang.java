package com.it4_k12.btl.Model;

public class GioHang {
    private int maGioHang;
    private int maUser;
    private int maSanPham;
    private int maThuocTinh;
    private String size; // Kích thước
    private String mauSac; // Màu sắc
    private int soLuong;
    private String ngayThem;
    // Thêm thông tin sản phẩm
    private String tenSanPham; // Tên sản phẩm
    private String anhSanPham; // Đường dẫn ảnh sản phẩm
    private int gia; // Giá sản phẩm
    private boolean isSelected;

    // Default constructor
    public GioHang() {}

    // Parameterized constructor
    public GioHang(int maGioHang, int maUser, int maSanPham,int maThuocTinh, String size, String mauSac, int soLuong, String ngayThem, String tenSanPham, String anhSanPham, int gia) {
        this.maGioHang = maGioHang;
        this.maUser = maUser;
        this.maSanPham = maSanPham;
        this.maThuocTinh = maThuocTinh;
        this.size = size;
        this.mauSac = mauSac;
        this.soLuong = soLuong;
        this.ngayThem = ngayThem;
        this.tenSanPham = tenSanPham;
        this.anhSanPham = anhSanPham;
        this.gia = gia;
        this.isSelected = false;
    }

    // Getter and Setter cho isSelected
    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    // Getters and Setters
    public int getMaGioHang() { return maGioHang; }
    public void setMaGioHang(int maGioHang) { this.maGioHang = maGioHang; }

    public int getMaUser() { return maUser; }
    public void setMaUser(int maUser) { this.maUser = maUser; }

    public int getMaSanPham() { return maSanPham; }
    public void setMaSanPham(int maSanPham) { this.maSanPham = maSanPham; }

    public int getMaThuocTinh() { return maThuocTinh; }
    public void setMaThuocTinh(int maThuocTinh) { this.maThuocTinh = maThuocTinh; }

    public String getSize() { return size; }
    public void setSize(String size) { this.size = size; }

    public String getMauSac() { return mauSac; }
    public void setMauSac(String mauSac) { this.mauSac = mauSac; }

    public int getSoLuong() { return soLuong; }
    public void setSoLuong(int soLuong) { this.soLuong = soLuong; }

    public String getNgayThem() { return ngayThem; }
    public void setNgayThem(String ngayThem) { this.ngayThem = ngayThem; }

    public String getTenSanPham() { return tenSanPham; }
    public void setTenSanPham(String tenSanPham) { this.tenSanPham = tenSanPham; }

    public String getAnhSanPham() { return anhSanPham; }
    public void setAnhSanPham(String anhSanPham) { this.anhSanPham = anhSanPham; }

    public int getGia() { return gia * soLuong; }
    public void setGia(int gia) { this.gia = gia; }
}
