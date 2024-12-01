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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.it4_k12.btl.Controller.DAO;
import com.it4_k12.btl.Model.DonHang;
import com.it4_k12.btl.R;
import com.it4_k12.btl.View.Fragment.chitietdonhang;

import java.util.List;

public class DonHangAdapterAdmin extends RecyclerView.Adapter<DonHangAdapterAdmin.ViewHolder> {
    private List<DonHang> donHangList;
    private Context context;
    int maUser;

    public DonHangAdapterAdmin(Context context, List<DonHang> donHangList, int maUser) {
        this.context = context;
        this.donHangList = donHangList;
        this.maUser = maUser;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_donhang_admin, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtMaDonHang.setText(String.valueOf("Mã đơn hàng: " + donHangList.get(position).getMaDonHang()));
        holder.txtNgayDatHang.setText("Ngày đặt hàng: " + donHangList.get(position).getNgayDatHang());
        holder.txtTrangThai.setText("Trạng thái đơn hàng: " + donHangList.get(position).getTrangThai());
        holder.txtTongTien.setText(String.valueOf("Tổng tiền: " + donHangList.get(position).getTongTien()));
        holder.txtTenKhachHang.setText("Tên khách hàng: " + donHangList.get(position).getTenKhachHang());
        holder.txtSoDienThoai.setText("Số điện thoại: " + donHangList.get(position).getSoDienThoai());
        holder.txtDiaChi.setText("Địa chỉ: " + donHangList.get(position).getDiaChi());

        // Hiển thị ảnh sản phẩm bằng Glide
        Glide.with(context)
                .load(donHangList.get(position).getImageUrl())
                .into(holder.imgSanPham);

        // Set tên và giá sản phẩm
        holder.txtTenSanPham.setText("Tên sản phẩm: " + donHangList.get(position).getTenSanPham());

        // Xử lý click để chuyển đến activity chi tiết đơn hàng
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, chitietdonhang.class);
            intent.putExtra("maDonHang", donHangList.get(position).getMaDonHang());
            context.startActivity(intent);
        });
        holder.btnXoaDonHang.setOnClickListener(v -> {
                new AlertDialog.Builder(context)
                        .setTitle("Xác nhận đơn hàng")
                        .setMessage("Bạn có chắc chắn đươn hàng này đã được thanh toán không?")
                        .setPositiveButton("Có", (dialog, which) -> {
                            // Gọi phương thức xóa đơn hàng từ DAO
                            if(new DAO().addHoaDon(donHangList.get(position).getMaDonHang(), maUser)){
                                Toast.makeText(context, "Thanh toán đơn hàng thành công", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Toast.makeText(context, "Thanh toán đơn hàng thất bại", Toast.LENGTH_SHORT).show();
                            }
                            donHangList.remove(position);
                            notifyDataSetChanged();
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
        TextView txtMaDonHang, txtNgayDatHang, txtTrangThai, txtTongTien, txtTenKhachHang, txtSoDienThoai, txtDiaChi;
        TextView txtTenSanPham, txtGiaSanPham; // Thêm tên và giá sản phẩm
        ImageView imgSanPham; // Thêm ImageView cho ảnh sản phẩm
        Button btnXoaDonHang; // Nút xóa đơn hàng

        public ViewHolder(View itemView) {
            super(itemView);
            txtMaDonHang = itemView.findViewById(R.id.txtMaDonHang);
            txtNgayDatHang = itemView.findViewById(R.id.txtNgayDatHang);
            txtTrangThai = itemView.findViewById(R.id.txtTrangThai);
            txtTongTien = itemView.findViewById(R.id.txtTongTien);
            txtTenKhachHang = itemView.findViewById(R.id.txtTenKhachHang);
            txtSoDienThoai = itemView.findViewById(R.id.txtSoDienThoai);
            txtDiaChi = itemView.findViewById(R.id.txtDiaChi);
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
