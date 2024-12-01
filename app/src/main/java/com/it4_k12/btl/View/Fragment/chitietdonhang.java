package com.it4_k12.btl.View.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.it4_k12.btl.Adapter.ChiTietDonHangAdapter;
import com.it4_k12.btl.Controller.DAO;
import com.it4_k12.btl.Model.ChiTietDonHang;
import com.it4_k12.btl.R;

import java.util.ArrayList;
import java.util.List;

public class chitietdonhang extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ChiTietDonHangAdapter adapter;
    private List<ChiTietDonHang> chiTietDonHangList;
    private ImageView btnback;
    private DAO dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chitietdonhang);

        // Khởi tạo các thành phần UI
        recyclerView = findViewById(R.id.recyclerViewChiTietDonHang);
        chiTietDonHangList = new ArrayList<>();
        adapter = new ChiTietDonHangAdapter(chiTietDonHangList);

        btnback = findViewById(R.id.btnback);
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Kết thúc Activity hiện tại và quay về trang trước đó
            }
        });

        // Thiết lập LayoutManager cho RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        // Khởi tạo DAO
        dao = new DAO();

        // Lấy thông tin từ SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        int userId = sharedPreferences.getInt("userId", -1);
        int maDonHang = getIntent().getIntExtra("maDonHang", -1); // Gán mã đơn hàng từ Intent

        // Kiểm tra giá trị
        if (userId != -1 && maDonHang != -1) {
            // Truy vấn chi tiết đơn hàng
            fetchChiTietDonHang(userId, maDonHang);
        } else {
            Toast.makeText(this, "Thông tin người dùng hoặc mã đơn hàng không hợp lệ.", Toast.LENGTH_SHORT).show();
        }
    }

    private void fetchChiTietDonHang(int userId, int maDonHang) {
        // Lấy danh sách chi tiết đơn hàng từ DAO
        chiTietDonHangList = dao.getChiTietDonHang(maDonHang);

        // Kiểm tra và cập nhật UI
        if (chiTietDonHangList.isEmpty()) {
            Toast.makeText(this, "Không có chi tiết đơn hàng nào được tìm thấy.", Toast.LENGTH_SHORT).show();
        } else {
            adapter.updateData(chiTietDonHangList);
        }
    }
}
