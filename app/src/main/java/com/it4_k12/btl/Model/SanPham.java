package com.it4_k12.btl.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class SanPham implements Parcelable {
    private int maSanPham;
    private String tenSanPham;
    private String loai;
    private String anhSanPham;
    private double gia;
    private int soLuong;
    private String moTa;
    private String ngayTao;
    private boolean isSelected;

    public SanPham(int maSanPham, String tenSanPham, String loai, String anhSanPham, double gia, int soLuong, String moTa , String ngayTao) {
        this.maSanPham = maSanPham;
        this.tenSanPham = tenSanPham;
        this.loai = loai;
        this.anhSanPham = anhSanPham;
        this.gia = gia;
        this.soLuong = soLuong;
        this.moTa = moTa;
        this.ngayTao = ngayTao;
        this.isSelected = false;
    }

    protected SanPham(Parcel in) {
        maSanPham = in.readInt();
        tenSanPham = in.readString();
        loai = in.readString();
        anhSanPham = in.readString();
        gia = in.readDouble();
        soLuong = in.readInt();
        moTa = in.readString();
        ngayTao = in.readString();
        isSelected = in.readByte() != 0;
    }

    public static final Creator<SanPham> CREATOR = new Creator<SanPham>() {
        @Override
        public SanPham createFromParcel(Parcel in) {
            return new SanPham(in);
        }

        @Override
        public SanPham[] newArray(int size) {
            return new SanPham[size];
        }
    };

    public SanPham() {
    }


    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(maSanPham);
        dest.writeString(tenSanPham);
        dest.writeString(loai);
        dest.writeString(anhSanPham);
        dest.writeDouble(gia);
        dest.writeInt(soLuong);
        dest.writeString(moTa);
        dest.writeString(ngayTao);
        dest.writeByte((byte) (isSelected ? 1 : 0));
    }

    @Override

    public int describeContents() {
        return 0;
    }

    public int getMaSanPham() {
        return maSanPham;
    }

    public void setMaSanPham(int maSanPham) {
        this.maSanPham = maSanPham;
    }

    public String getTenSanPham() {
        return tenSanPham;
    }

    public void setTenSanPham(String tenSanPham) {
        this.tenSanPham = tenSanPham;
    }

    public String getLoai() {
        return loai;
    }

    public void setLoai(String loai) {
        this.loai = loai;
    }

    public String getAnhSanPham() {
        return anhSanPham;
    }

    public void setAnhSanPham(String anhSanPham) {
        this.anhSanPham = anhSanPham;
    }

    public double getGia() {
        return gia;
    }

    public void setGia(double gia) {
        this.gia = gia;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public String getMoTa() { return moTa; }
    public void setMoTa(String moTa) { this.moTa = moTa; }

    public String getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(String ngayTao) {
        this.ngayTao = ngayTao;
    }
    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
