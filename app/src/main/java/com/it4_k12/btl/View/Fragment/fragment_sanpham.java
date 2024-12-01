package com.it4_k12.btl.View.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SearchView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.chip.ChipGroup;
import com.it4_k12.btl.Adapter.SanPhamAdapter;
import com.it4_k12.btl.Controller.DAO;
import com.it4_k12.btl.Model.SanPham;
import com.it4_k12.btl.R;

import java.util.ArrayList;
import java.util.List;

public class fragment_sanpham extends Fragment {

    private RecyclerView recyclerViewSanPham;
    private SanPhamAdapter sanPhamAdapter;
    private ChipGroup chipGroup;
    private ImageView imageViewCart;
    private SearchView searchView;
    private DAO dao = new DAO(); // Khởi tạo DAO để lấy dữ liệu sản phẩm
    private List<SanPham> danhSachSanPham;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sanpham, container, false);

        // Ánh xạ các view
        recyclerViewSanPham = view.findViewById(R.id.recyclerViewSanPham);
        imageViewCart = view.findViewById(R.id.imageViewCart);
        searchView = view.findViewById(R.id.searchView);
        chipGroup = view.findViewById(R.id.chipGroup);

        // Khởi tạo danh sách sản phẩm từ DAO
        danhSachSanPham = dao.getAllSanPham();

        // Khởi tạo adapter và set cho RecyclerView
        sanPhamAdapter = new SanPhamAdapter(getContext(), danhSachSanPham);
        recyclerViewSanPham.setAdapter(sanPhamAdapter);
        recyclerViewSanPham.setLayoutManager(new GridLayoutManager(getContext(), 2)); // Hiển thị dạng lưới

        // Cài đặt tìm kiếm sản phẩm qua SearchView
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (!query.isEmpty()) {
                    List<SanPham> result = dao.searchSanPhamByName(query); // Gọi phương thức tìm kiếm theo tên
                    sanPhamAdapter.updateData(result); // Cập nhật lại dữ liệu trong adapter
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.isEmpty()) {
                    sanPhamAdapter.updateData(danhSachSanPham); // Nếu không có nội dung tìm kiếm, hiển thị toàn bộ sản phẩm
                } else {
                    List<SanPham> result = dao.searchSanPhamByName(newText); // Tìm kiếm theo tên
                    sanPhamAdapter.updateData(result); // Cập nhật lại dữ liệu trong adapter
                }
                return false;
            }
        });

        // Cài đặt chip để phân loại sản phẩm
        chipGroup.setOnCheckedStateChangeListener((group, checkedIds) -> {
            String loai = "Tất cả"; // Mặc định là tất cả sản phẩm
            if (checkedIds.contains(R.id.chipWedding)) {
                loai = "Đồ cưới";
            } else if (checkedIds.contains(R.id.chipAccessories)) {
                loai = "Phụ kiện";
            }
            // Lọc danh sách sản phẩm theo loại
            List<SanPham> filteredList = dao.layDanhSachSanPhamTheoLoai(loai);
            sanPhamAdapter.updateData(filteredList); // Cập nhật danh sách trong adapter
        });

        // Xử lý sự kiện click vào sản phẩm
        sanPhamAdapter.setOnItemClickListener(sanPham -> {
            Intent intent = new Intent(getActivity(), chitietsanpham.class);
            intent.putExtra("MA_SAN_PHAM", sanPham.getMaSanPham());
            startActivity(intent);
        });

        // Xử lý click vào giỏ hàng
        imageViewCart.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), GioHangA.class);
            startActivity(intent);
        });

        return view;
    }
}
