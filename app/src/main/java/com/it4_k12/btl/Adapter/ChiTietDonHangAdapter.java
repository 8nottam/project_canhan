package com.it4_k12.btl.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.it4_k12.btl.Model.ChiTietDonHang;
import com.it4_k12.btl.R;

import java.util.ArrayList;
import java.util.List;

public class ChiTietDonHangAdapter extends RecyclerView.Adapter<ChiTietDonHangAdapter.ViewHolder> {
    private List<ChiTietDonHang> chiTietDonHangList;

    public ChiTietDonHangAdapter(List<ChiTietDonHang> chiTietDonHangList) {
        this.chiTietDonHangList = chiTietDonHangList != null ? chiTietDonHangList : new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_chitietdonhang, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ChiTietDonHang chiTiet = chiTietDonHangList.get(position);

        holder.txtTenSanPham.setText("Tên sản phẩm: " + chiTiet.getTenSanPham());
        holder.txtSoLuong.setText(String.valueOf("Số lượng: " + chiTiet.getSoLuong()));
        holder.txtGia.setText("Giá: " + chiTiet.getGia() + ""); // Hiển thị giá với định dạng số thập phân
        holder.txtTongTien.setText("Tổng tiền: " + chiTiet.getTongTien() + ""); // Hiển thị tổng tiền

        // Hiển thị thêm thông tin size, màu sắc và ngày đặt hàng
        holder.txtSize.setText("Size: " + chiTiet.getSize());
        holder.txtMauSac.setText("Màu sắc: " + chiTiet.getMauSac());
        holder.txtNgayDatHang.setText("Ngày đặt hàng: " + chiTiet.getNgayDatHang());

        // Sử dụng Glide để tải ảnh sản phẩm
        Glide.with(holder.itemView.getContext())
                .load(chiTiet.getAnhSanPham())
                .placeholder(R.drawable.close) // Thay thế bằng ảnh mặc định nếu không có ảnh
                .into(holder.imgSanPham);
    }

    @Override
    public int getItemCount() {
        return chiTietDonHangList.size();
    }

    public void updateData(List<ChiTietDonHang> newChiTietDonHangList) {
        if (newChiTietDonHangList != null) {
            chiTietDonHangList.clear();
            chiTietDonHangList.addAll(newChiTietDonHangList);
            notifyDataSetChanged(); // Cập nhật dữ liệu
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtTenSanPham, txtSoLuong, txtGia, txtTongTien, txtSize, txtMauSac, txtNgayDatHang;
        ImageView imgSanPham;

        public ViewHolder(View itemView) {
            super(itemView);
            txtTenSanPham = itemView.findViewById(R.id.txtTenSanPham);
            txtSoLuong = itemView.findViewById(R.id.txtSoLuong);
            txtGia = itemView.findViewById(R.id.txtGia);
            txtTongTien = itemView.findViewById(R.id.txtTongTien);
            txtSize = itemView.findViewById(R.id.txtSize); // TextView cho size
            txtMauSac = itemView.findViewById(R.id.txtMauSac); // TextView cho màu sắc
            txtNgayDatHang = itemView.findViewById(R.id.txtNgayDatHang); // TextView cho ngày đặt hàng
            imgSanPham = itemView.findViewById(R.id.imgSanPham); // ImageView cho ảnh sản phẩm
        }
    }
}
