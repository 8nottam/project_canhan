package com.it4_k12.btl.View.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.it4_k12.btl.R;
import com.it4_k12.btl.View.Fragment.fragment_hoadon;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Lấy thông tin người dùng từ SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        String userRole = sharedPreferences.getString("userRole", "");
        Log.d("MainActivity", "User role: " + userRole);


        ImageButton menu_home = findViewById(R.id.menu_home);
        ImageButton menu_sanpham = findViewById(R.id.menu_sanpham);
        ImageButton menu_hoadon = findViewById(R.id.menu_hoadon);
        ImageButton menu_thongbao = findViewById(R.id.menu_thongbao);
        ImageButton menu_nguoidung = findViewById(R.id.menu_nguoidung);

        // Mặc định hiển thị HomeFragment khi ứng dụng mở
        replaceFragment(new fragment_home());

        // Sự kiện khi nhấn nút Home
        menu_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new fragment_home());
            }
        });

        // Sự kiện khi nhấn nút sản phẩm
        menu_sanpham.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ("admin".equals(userRole)) {
                    replaceFragment(new fragment_sanpham_admin());
                } else {
                    replaceFragment(new fragment_sanpham());
                }
            }
        });

        // Sự kiện khi nhấn nút hóa đơn
        menu_hoadon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new fragment_hoadon());
            }
        });

        // Sự kiện khi nhấn nút thông báo
        menu_thongbao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ("admin".equals(userRole)) {
                    replaceFragment(new fragment_quanlytaikhoan());
                }
                else{
                    replaceFragment(new fragment_thongbao());
                }
            }
        });

        // Sự kiện khi nhấn nút trang cá nhân
        menu_nguoidung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if("admin".equals(userRole)){
                    replaceFragment(new fragment_canhan_admin());
                }
                else{
                    replaceFragment(new fragment_canhan());
                }
            }
        });
    }

    // Hàm thay thế Fragment
    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
    }
}