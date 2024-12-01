package com.it4_k12.btl.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.it4_k12.btl.Model.SanPham;
import com.it4_k12.btl.R;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SanPhamAdapter extends RecyclerView.Adapter<SanPhamAdapter.ViewHolder> {
    private List<SanPham> sanPhamList;
    private List<SanPham> sanPhamListFull; // Danh sách đầy đủ để tìm kiếm
    private Context context;
    private OnItemClickListener onItemClickListener;

    public SanPhamAdapter(Context context, List<SanPham> sanPhamList) {
        this.context = context;
        this.sanPhamList = sanPhamList;
        this.sanPhamListFull = new ArrayList<>(sanPhamList); // Sao chép danh sách gốc
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_sanpham, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SanPham sanPham = sanPhamList.get(position);
        holder.tvTenSanPham.setText(sanPham.getTenSanPham());
        // Hiển thị giá sản phẩm với định dạng tiền tệ
        NumberFormat currencyFormatter = NumberFormat.getInstance(new Locale("vi", "VN"));
        String formattedPrice = currencyFormatter.format(sanPham.getGia()) + " VNĐ";
        holder.tvGiaSanPham.setText( formattedPrice);

        // Sử dụng Glide để tải hình ảnh từ URL hoặc đường dẫn
        Glide.with(context)
                .load(sanPham.getAnhSanPham()) // Đường dẫn hoặc URL của hình ảnh
                .placeholder(R.drawable.placeholder) // Hình ảnh thay thế khi đang tải
                .error(R.drawable.error) // Hình ảnh thay thế khi xảy ra lỗi
                .into(holder.imgAnhSanPham); // ImageView để hiển thị hình ảnh

        holder.itemView.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(sanPham);
            }
        });
    }

    @Override
    public int getItemCount() {
        return sanPhamList.size();
    }

    public void updateData(List<SanPham> newList) {
        this.sanPhamList = newList;
        this.sanPhamListFull = new ArrayList<>(newList); // Cập nhật danh sách đầy đủ
        notifyDataSetChanged();
    }

    // Thêm phương thức tìm kiếmz
    public void filter(String query) {
        if (query == null || query.isEmpty()) {
            sanPhamList = new ArrayList<>(sanPhamListFull); // Hiển thị lại toàn bộ danh sách
        } else {
            List<SanPham> filteredList = new ArrayList<>();
            for (SanPham sanPham : sanPhamListFull) {
                if (sanPham.getTenSanPham().toLowerCase().contains(query.toLowerCase())) {
                    filteredList.add(sanPham);
                }
            }
            sanPhamList = filteredList; // Cập nhật danh sách được lọc
        }
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(SanPham sanPham);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTenSanPham, tvGiaSanPham;
        ImageView imgAnhSanPham;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTenSanPham = itemView.findViewById(R.id.tvTenSanPham);
            tvGiaSanPham = itemView.findViewById(R.id.tvGiaSanPham);
            imgAnhSanPham = itemView.findViewById(R.id.imgAnhSanPham);
        }
    }
}
