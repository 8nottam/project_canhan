package com.it4_k12.btl.Model;

public class ThuocTinhSanPham {
    private int maThuocTinh;
    private int maSanPham;
    private String size;
    private String mauSac;

    // Default constructor
    public ThuocTinhSanPham() {}

    // Parameterized constructor
    public ThuocTinhSanPham(int maThuocTinh, int maSanPham, String size, String mauSac) {
        this.maThuocTinh = maThuocTinh;
        this.maSanPham = maSanPham;
        this.size = size;
        this.mauSac = mauSac;
    }

    // Getters and Setters
    public int getMaThuocTinh() { return maThuocTinh; }
    public void setMaThuocTinh(int maThuocTinh) { this.maThuocTinh = maThuocTinh; }

    public int getMaSanPham() { return maSanPham; }
    public void setMaSanPham(int maSanPham) { this.maSanPham = maSanPham; }

    public String getSize() { return size; }
    public void setSize(String size) { this.size = size; }

    public String getMauSac() { return mauSac; }
    public void setMauSac(String mauSac) { this.mauSac = mauSac; }
}
