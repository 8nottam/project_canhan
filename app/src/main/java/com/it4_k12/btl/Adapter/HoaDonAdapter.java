package com.it4_k12.btl.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.it4_k12.btl.Controller.DAO;
import com.it4_k12.btl.Model.DonHang;
import com.it4_k12.btl.Model.HoaDon;
import com.it4_k12.btl.R;
import com.it4_k12.btl.View.Fragment.chitietdonhang;
import com.it4_k12.btl.View.Fragment.chitiethoadon; // Import activity chi tiết hóa đơn

import java.util.List;

public class HoaDonAdapter extends RecyclerView.Adapter<HoaDonAdapter.ViewHolder> {
    private List<HoaDon> hoaDonList;
    private Context context;

    public HoaDonAdapter(Context context, List<HoaDon> hoaDonList) {
        this.context = context;
        this.hoaDonList = hoaDonList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hoadon, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        HoaDon hoaDon = hoaDonList.get(position);
        holder.txtMaDonHang.setText(String.valueOf(hoaDon.getMaHoaDon()));
        holder.txtNgayDatHang.setText(hoaDon.getNgayLap());
        holder.txtTrangThai.setText(hoaDon.getTrangThai());
        holder.txtTongTien.setText(String.valueOf(hoaDon.getTongTien()));

        // Hiển thị ảnh sản phẩm bằng Glide
        Glide.with(context)
                .load(hoaDon.getImageUrl())
                .into(holder.imgSanPham);

        // Set tên và giá sản phẩm
        holder.txtTenSanPham.setText(hoaDon.getTenSanPham());

        // Xử lý click để chuyển đến activity chi tiết đơn hàng
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, chitiethoadon.class);
            intent.putExtra("maHoaDon", hoaDon.getMaHoaDon());
            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return hoaDonList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtMaDonHang, txtNgayDatHang, txtTrangThai, txtTongTien;
        TextView txtTenSanPham; // Thêm tên và giá sản phẩm
        ImageView imgSanPham; // Thêm ImageView cho ảnh sản phẩm

        public ViewHolder(View itemView) {
            super(itemView);
            txtMaDonHang = itemView.findViewById(R.id.txtMaDonHang);
            txtNgayDatHang = itemView.findViewById(R.id.txtNgayDatHang);
            txtTrangThai = itemView.findViewById(R.id.txtTrangThai);
            txtTongTien = itemView.findViewById(R.id.txtTongTien);
            imgSanPham = itemView.findViewById(R.id.imgSanPham); // Khởi tạo ImageView
            txtTenSanPham = itemView.findViewById(R.id.txtTenSanPham); // Khởi tạo TextView cho tên sản phẩm
        }
    }
    public void updateData(List<HoaDon> newHoaDonList) {
        this.hoaDonList = newHoaDonList;
        notifyDataSetChanged();
    }

}
