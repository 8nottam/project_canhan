package com.it4_k12.btl.Model;

public class User {
    private int maUser;
    private String hoTen;
    private String email;
    private String matKhau;
    private String soDienThoai;
    private String gioiTinh;
    private String ngaySinh;
    private String diaChi;
    private String anhDaiDien;
    private String vaiTro;
    private String ngayTao;

    // Default constructor
    public User() {}

    // Parameterized constructor
    public User(int maUser, String hoTen, String email, String matKhau, String soDienThoai, String gioiTinh,
                String ngaySinh, String diaChi, String anhDaiDien, String vaiTro, String ngayTao) {
        this.maUser = maUser;
        this.hoTen = hoTen;
        this.email = email;
        this.matKhau = matKhau;
        this.soDienThoai = soDienThoai;
        this.gioiTinh = gioiTinh;
        this.ngaySinh = ngaySinh;
        this.diaChi = diaChi;
        this.anhDaiDien = anhDaiDien;
        this.vaiTro = vaiTro;
        this.ngayTao = ngayTao;
    }

    // Getters and Setters
    public int getMaUser() { return maUser; }
    public void setMaUser(int maUser) { this.maUser = maUser; }

    public String getHoTen() { return hoTen; }
    public void setHoTen(String hoTen) { this.hoTen = hoTen; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getMatKhau() { return matKhau; }
    public void setMatKhau(String matKhau) { this.matKhau = matKhau; }

    public String getSoDienThoai() { return soDienThoai; }
    public void setSoDienThoai(String soDienThoai) { this.soDienThoai = soDienThoai; }

    public String getGioiTinh() { return gioiTinh; }
    public void setGioiTinh(String gioiTinh) { this.gioiTinh = gioiTinh; }

    public String getNgaySinh() { return ngaySinh; }
    public void setNgaySinh(String ngaySinh) { this.ngaySinh = ngaySinh; }

    public String getDiaChi() { return diaChi; }
    public void setDiaChi(String diaChi) { this.diaChi = diaChi; }

    public String getAnhDaiDien() { return anhDaiDien; }
    public void setAnhDaiDien(String anhDaiDien) { this.anhDaiDien = anhDaiDien; }

    public String getVaiTro() { return vaiTro; }
    public void setVaiTro(String vaiTro) { this.vaiTro = vaiTro; }

    public String getNgayTao() { return ngayTao; }
    public void setNgayTao(String ngayTao) { this.ngayTao = ngayTao; }
}
