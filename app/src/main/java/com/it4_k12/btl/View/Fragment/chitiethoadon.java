package com.it4_k12.btl.View.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.it4_k12.btl.Adapter.ChiTietDonHangAdapter;
import com.it4_k12.btl.Adapter.ChitiethoadonAdapter;
import com.it4_k12.btl.Controller.DAO;
import com.it4_k12.btl.Model.ChiTietHoaDon;
import com.it4_k12.btl.Model.HoaDon;
import com.it4_k12.btl.R;

import java.util.ArrayList;
import java.util.List;

public class chitiethoadon extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ChitiethoadonAdapter adapter;
    private List<ChiTietHoaDon> chiTietHoaDonList;
    private ImageView btnback;
    private DAO dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chitiethoadon);

        // Khởi tạo các thành phần UI
        recyclerView = findViewById(R.id.recyclerViewChiTietHoaDon);
        btnback = findViewById(R.id.btnback);
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Kết thúc Activity hiện tại và quay về trang trước đó
            }
        });



        // Khởi tạo DAO
        dao = new DAO();

        // Lấy thông tin từ SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        int userId = sharedPreferences.getInt("userId", -1);
        int maHoaDon = getIntent().getIntExtra("maHoaDon", -1); // Gán mã đơn hàng từ Intent

        // Kiểm tra giá trị
        if (userId != -1 && maHoaDon != -1) {
            // Truy vấn chi tiết đơn hàng
            chiTietHoaDonList =  dao.getChiTietHoaDon(maHoaDon);
            adapter = new ChitiethoadonAdapter(chiTietHoaDonList);

            // Thiết lập LayoutManager cho RecyclerView
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(adapter);
        } else {
            Toast.makeText(this, "Thông tin người dùng hoặc mã đơn hàng không hợp lệ.", Toast.LENGTH_SHORT).show();
        }
    }

}