package com.it4_k12.btl.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import com.it4_k12.btl.R;
import com.it4_k12.btl.View.Fragment.chitietdonhang;

import java.util.List;

public class DonHangAdapter extends RecyclerView.Adapter<DonHangAdapter.ViewHolder> {
    private List<DonHang> donHangList;
    private Context context;

    public DonHangAdapter(Context context, List<DonHang> donHangList) {
        this.context = context;
        this.donHangList = donHangList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_don_hang, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DonHang donHang = donHangList.get(position);
        holder.txtMaDonHang.setText(String.valueOf("Mã đơn hàng: " + donHang.getMaDonHang()));
        holder.txtNgayDatHang.setText("Ngày đặt hàng: "+ donHang.getNgayDatHang());
        holder.txtTrangThai.setText("Trạng thái đơn hàng: " + donHang.getTrangThai());
        holder.txtTongTien.setText(String.valueOf("Tổng tiền: " + donHang.getTongTien()));

        // Hiển thị ảnh sản phẩm bằng Glide
        Glide.with(context)
                .load(donHang.getImageUrl())
                .into(holder.imgSanPham);

        // Set tên và giá sản phẩm
        holder.txtTenSanPham.setText("Tên sản phẩm" + donHang.getTenSanPham());

        // Xử lý click để chuyển đến activity chi tiết đơn hàng
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, chitietdonhang.class);
            intent.putExtra("maDonHang", donHang.getMaDonHang());
            context.startActivity(intent);
        });

        // Xử lý nút xóa đơn hàng
        holder.btnXoaDonHang.setOnClickListener(v -> {
            new AlertDialog.Builder(context)
                    .setTitle("Xóa đơn hàng")
                    .setMessage("Bạn có chắc chắn muốn xóa đơn hàng này không?")
                    .setPositiveButton("Có", (dialog, which) -> {
                        // Gọi phương thức xóa đơn hàng từ DAO
                        DAO dao = new DAO();
                        dao.deleteDonHang(donHang.getMaDonHang());

                        // Xóa đơn hàng khỏi danh sách và cập nhật giao diện
                        donHangList.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, donHangList.size());
                    })
                    .setNegativeButton("Không", null)
                    .show();
        });

    }

    @Override
    public int getItemCount() {
        return donHangList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtMaDonHang, txtNgayDatHang, txtTrangThai, txtTongTien;
        TextView txtTenSanPham; // Thêm tên và giá sản phẩm
        ImageView imgSanPham; // Thêm ImageView cho ảnh sản phẩm
        Button btnXoaDonHang; // Nút xóa đơn hàng

        public ViewHolder(View itemView) {
            super(itemView);
            txtMaDonHang = itemView.findViewById(R.id.txtMaDonHang);
            txtNgayDatHang = itemView.findViewById(R.id.txtNgayDatHang);
            txtTrangThai = itemView.findViewById(R.id.txtTrangThai);
            txtTongTien = itemView.findViewById(R.id.txtTongTien);
            imgSanPham = itemView.findViewById(R.id.imgSanPham); // Khởi tạo ImageView
            txtTenSanPham = itemView.findViewById(R.id.txtTenSanPham); // Khởi tạo TextView cho tên sản phẩm
            btnXoaDonHang = itemView.findViewById(R.id.btnXoaDonHang); // Khởi tạo nút xóa đơn hàng
        }
    }
    public void updateData(List<DonHang> newDonHangList) {
        this.donHangList = newDonHangList;
        notifyDataSetChanged();
    }

}
