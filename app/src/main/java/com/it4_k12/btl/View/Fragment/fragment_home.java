package com.it4_k12.btl.View.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.it4_k12.btl.Adapter.SanPhamAdapter;
import com.it4_k12.btl.Controller.DAO;
import com.it4_k12.btl.Adapter.ImageSliderAdapter;
import com.it4_k12.btl.Model.SanPham;
import com.it4_k12.btl.R;

import java.util.ArrayList;
import java.util.List;

public class fragment_home extends Fragment {
    private FrameLayout bannerAdContainer;
    private View bannerAdView;
    private ImageButton closeButton;
    private ImageButton bt_giohang;
    private RecyclerView recyclerViewSanPham;
    private SearchView searchView;
    private List<SanPham> danhSachSanPham;
    DAO dao = new DAO();
    private SanPhamAdapter sanPhamAdapter;

    public fragment_home() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        searchView = view.findViewById(R.id.searchView);
        recyclerViewSanPham = view.findViewById(R.id.recyclerViewSanPham);

        // Khởi tạo danh sách sản phẩm từ DAO
        danhSachSanPham = dao.getAllSanPham();

        // Khởi tạo adapter và set cho RecyclerView
        sanPhamAdapter = new SanPhamAdapter(getContext(), danhSachSanPham);
        recyclerViewSanPham.setAdapter(sanPhamAdapter);
        recyclerViewSanPham.setLayoutManager(new GridLayoutManager(getContext(), 2)); // Hiển thị dạng lưới


        ViewPager2 imageSlider = view.findViewById(R.id.imageSlider);

        // Array of 5 image resource IDs
        int[] imageResIds = {
                R.drawable.banner,
                R.drawable.banner2,
                R.drawable.banner3,
                R.drawable.nura
        };

        ImageSliderAdapter adapter = new ImageSliderAdapter(imageResIds);
        imageSlider.setAdapter(adapter);

        // Lấy FrameLayout chứa banner quảng cáo
        bannerAdContainer = view.findViewById(R.id.bannerAdContainer);

        // Inflate layout cho banner quảng cáo từ banner_ad.xml
        bannerAdView = inflater.inflate(R.layout.quangcao, bannerAdContainer, false);

        // Lấy nút tắt (closeButton) từ banner
        closeButton = bannerAdView.findViewById(R.id.closeButton);

        // Thêm banner vào bannerAdContainer
        bannerAdContainer.addView(bannerAdView);

        // Hiển thị banner khi fragment mở
        bannerAdContainer.setVisibility(View.VISIBLE);

        // Xử lý sự kiện tắt banner khi nhấn nút "X"
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bannerAdContainer.setVisibility(View.GONE); // Ẩn banner khi nhấn nút tắt
            }
        });
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

        return view;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        bt_giohang = view.findViewById(R.id.bt_giohang);
        bt_giohang.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), GioHangA.class); // Replace with your target Activity
            startActivity(intent);
        });
    }
}
