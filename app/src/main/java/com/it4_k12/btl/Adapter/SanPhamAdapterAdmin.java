package com.it4_k12.btl.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.it4_k12.btl.Model.SanPham;
import com.it4_k12.btl.R;
import com.it4_k12.btl.View.Fragment.chitietdonhang;
import com.it4_k12.btl.View.Fragment.chitietsanpham;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SanPhamAdapterAdmin extends RecyclerView.Adapter<SanPhamAdapterAdmin.SanPhamViewHolder> {

    private Context context;
    private List<SanPham> sanPhamList; // Danh sách sản phẩm hiện tại
    private List<SanPham> sanPhamListFull; // Danh sách sản phẩm đầy đủ (không bị lọc)
    private OnEditClickListener onEditClickListener; // Giao diện để lắng nghe sự kiện chỉnh sửa

    // Giao diện cho sự kiện chỉnh sửa sản phẩm
    public interface OnEditClickListener {
        void onEditClick(SanPham sanPham);
    }

    // Constructor
    public SanPhamAdapterAdmin(Context context, List<SanPham> sanPhamList, OnEditClickListener onEditClickListener) {
        this.context = context;
        this.sanPhamList = sanPhamList;
        this.sanPhamListFull = new ArrayList<>(sanPhamList); // Lưu danh sách sản phẩm đầy đủ
        this.onEditClickListener = onEditClickListener; // Khởi tạo listener
    }

    @NonNull
    @Override
    public SanPhamViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate layout cho item sản phẩm
        View view = LayoutInflater.from(context).inflate(R.layout.item_sanpham_admin, parent, false);
        return new SanPhamViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SanPhamViewHolder holder, int position) {
        SanPham sanPham = sanPhamList.get(position); // Lấy sản phẩm tại vị trí hiện tại
        holder.bind(sanPham); // Gọi phương thức bind để hiển thị thông tin sản phẩm

        // Xử lý sự kiện chọn/bỏ chọn CheckBox mà không bị ảnh hưởng khi ViewHolder bị tái sử dụng
        holder.checkBox.setOnCheckedChangeListener(null); // Xóa listener cũ để tránh bị gọi lại khi tái sử dụng
        holder.checkBox.setChecked(sanPham.isSelected()); // Cập nhật trạng thái của CheckBox

        // Đặt lại listener sau khi cập nhật trạng thái để xử lý sự kiện chọn/bỏ chọn
        holder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            sanPham.setSelected(isChecked); // Cập nhật trạng thái isSelected của sản phẩm

            // Hiển thị giá sản phẩm với định dạng tiền tệ
            NumberFormat currencyFormatter = NumberFormat.getInstance(new Locale("vi", "VN"));
            String formattedPrice = currencyFormatter.format(sanPham.getGia()) + " VNĐ";
            holder.txtGia.setText( formattedPrice);
        });

        // Thiết lập sự kiện cho nút chỉnh sửa
        holder.btnEdit.setOnClickListener(v -> onEditClickListener.onEditClick(sanPham));

        holder.btnChiTiet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, chitietsanpham.class);
                intent.putExtra("MA_SAN_PHAM", sanPham.getMaSanPham());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return sanPhamList.size(); // Trả về số lượng sản phẩm trong danh sách
    }

    // Phương thức lọc sản phẩm theo tên
    public void filter(String text) {
        sanPhamList.clear(); // Xóa danh sách hiện tại để lọc
        if (text.isEmpty()) {
            sanPhamList.addAll(sanPhamListFull); // Nếu không có gì để lọc, hiển thị tất cả sản phẩm
        } else {
            for (SanPham sanPham : sanPhamListFull) {
                if (sanPham.getTenSanPham().toLowerCase().contains(text.toLowerCase())) {
                    sanPhamList.add(sanPham); // Thêm sản phẩm vào danh sách nếu tên chứa chuỗi lọc
                }
            }
        }
        if (!sanPhamList.isEmpty()) { // Kiểm tra xem danh sách có thay đổi không
            notifyDataSetChanged(); // Thông báo cho adapter cập nhật giao diện
        }
    }

    class SanPhamViewHolder extends RecyclerView.ViewHolder {
        private TextView txtTenSanPham; // TextView cho tên sản phẩm
        private TextView txtGia; // TextView cho giá sản phẩm
        private ImageView imgSanPham; // ImageView cho ảnh sản phẩm
        private CheckBox checkBox; // Checkbox để chọn sản phẩm
        private View btnEdit; // Nút chỉnh sửa sản phẩm
        private Button btnChiTiet; // Nút chi tiết sản phẩm

        public SanPhamViewHolder(@NonNull View itemView) {
            super(itemView);
            // Ánh xạ các view từ layout
            txtTenSanPham = itemView.findViewById(R.id.txtTenSanPham);
            txtGia = itemView.findViewById(R.id.txtGia);
            imgSanPham = itemView.findViewById(R.id.imgSanPham);
            checkBox = itemView.findViewById(R.id.checkBox);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnChiTiet = itemView.findViewById(R.id.btnChiTiet);
        }


        // Phương thức để bind dữ liệu sản phẩm vào view
        public void bind(SanPham sanPham) {
            txtTenSanPham.setText("Tên sản phẩm: " + sanPham.getTenSanPham()); // Hiển thị tên sản phẩm
            txtGia.setText(String.format("Giá: " + "%,.0f VNĐ", sanPham.getGia())); // Hiển thị giá sản phẩm
            Glide.with(context).load(sanPham.getAnhSanPham()).into(imgSanPham); // Tải ảnh sản phẩm bằng Glide
        }
    }
}
