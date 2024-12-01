package com.it4_k12.btl.View.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.it4_k12.btl.Adapter.DonHangAdapter;
import com.it4_k12.btl.Adapter.DonHangAdapterAdmin;
import com.it4_k12.btl.Controller.DAO;
import com.it4_k12.btl.Model.DonHang;
import com.it4_k12.btl.R;

import java.util.List;

public class DonHangA_admin extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DonHangAdapterAdmin adapter;
    private List<DonHang> donHangList;
    private ImageView btnback;
    private DAO dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_don_hang_aadmin);

        // Khởi tạo RecyclerView
        recyclerView = findViewById(R.id.recyclerViewDonHang);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        btnback = findViewById(R.id.btnback);
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Kết thúc Activity hiện tại và quay về trang trước đó
            }
        });
        // Khởi tạo DAO
        dao = new DAO();

        // Lấy userId từ SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        int userId = sharedPreferences.getInt("userId", -1);

        // Kiểm tra userId
        if (userId != -1) {
            // Lấy danh sách đơn hàng từ database
            donHangList = dao.getDonHangAdmin();
            adapter = new DonHangAdapterAdmin(this, donHangList, userId);
            recyclerView.setAdapter(adapter);
        } else {
            // Xử lý nếu userId không hợp lệ (người dùng chưa đăng nhập)
            Toast.makeText(this, "Vui lòng đăng nhập để xem đơn hàng!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Cập nhật danh sách đơn hàng khi activity được quay lại
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        int userId = sharedPreferences.getInt("userId", -1);
        if (userId != -1) {
            // Lấy danh sách đơn hàng mới nhất từ cơ sở dữ liệu
            donHangList = dao.getDonHangAdmin();
            // Cập nhật adapter với dữ liệu mới
            adapter.updateData(donHangList);
        }
    }
}