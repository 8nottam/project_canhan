package com.it4_k12.btl.View.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.it4_k12.btl.Adapter.CartAdapter;
import com.it4_k12.btl.Controller.DAO;
import com.it4_k12.btl.Model.GioHang;
import com.it4_k12.btl.R;

import java.util.ArrayList;
import java.util.List;

public class GioHangA extends AppCompatActivity {
    private static int TongTien = 0; // Total price
    private RecyclerView recyclerViewCart;
    private CartAdapter cartAdapter;
    private List<GioHang> cartItems;
    private Button btnDeleteSelected, btnThanhToan, btnDonHang;
    private SearchView searchViewCart;
    private int currentUserId;
    private TextView txtTongTien;
    private ImageView btnback;
    private DAO dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gio_hang);

        initializeViews();
        setupDAO();
        getUserId();
        loadCartItems();
        setupButtonListeners();
        btnback = findViewById(R.id.btnback);
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Kết thúc Activity hiện tại và quay về trang trước đó
            }
        });
    }


    private void initializeViews() {
        recyclerViewCart = findViewById(R.id.recyclerViewCart);
        btnDeleteSelected = findViewById(R.id.btnDeleteSelected);
        searchViewCart = findViewById(R.id.searchViewCart);
        btnThanhToan = findViewById(R.id.btnThanhToan);
        btnDonHang = findViewById(R.id.btnDonHang);
        txtTongTien = findViewById(R.id.txtTongTien1);
    }

    private void setupDAO() {
        dao = new DAO();
    }

    private void getUserId() {
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        currentUserId = sharedPreferences.getInt("userId", -1);
        if (currentUserId == -1) {
            Toast.makeText(this, "Vui lòng đăng nhập trước!", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void loadCartItems() {
        cartItems = dao.getCartItemsByUser(currentUserId);
        if (cartItems == null || cartItems.isEmpty()) {
            Toast.makeText(this, "Giỏ hàng trống!", Toast.LENGTH_SHORT).show();
        } else {
            setupRecyclerView();
            updateTotalPrice();
        }
    }

    private void setupRecyclerView() {
        cartAdapter = new CartAdapter(cartItems, this);
        recyclerViewCart.setAdapter(cartAdapter);
        recyclerViewCart.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setupButtonListeners() {
        btnDeleteSelected.setOnClickListener(v -> deleteSelectedItems());

        searchViewCart.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterCartItems(newText);
                return true;
            }
        });

        btnThanhToan.setOnClickListener(v -> processPayment());
        btnDonHang.setOnClickListener(v -> startActivity(new Intent(GioHangA.this, DonHangA.class)));
        if (!cartItems.isEmpty()) {
            // Add listener to recalculate total price when checkbox state changes
            cartAdapter.setOnCheckboxChangeListener(this::updateTotalPrice);
        }
    }

    private void deleteSelectedItems() {
        List<GioHang> selectedItems = cartAdapter.getSelectedItems();
        if (selectedItems.isEmpty()) {
            Toast.makeText(this, "Vui lòng chọn sản phẩm để xóa!", Toast.LENGTH_SHORT).show();
            return;
        }

        for (GioHang item : selectedItems) {
            dao.deleteFromCart(item.getMaGioHang());
        }
        cartItems.removeAll(selectedItems);
        cartAdapter.notifyDataSetChanged();
        updateTotalPrice();
    }

    private void filterCartItems(String query) {
        List<GioHang> filteredItems = new ArrayList<>();
        for (GioHang item : cartItems) {
            if (item.getTenSanPham().toLowerCase().contains(query.toLowerCase())) {
                filteredItems.add(item);
            }
        }
        cartAdapter.updateItems(filteredItems);
    }

    private void processPayment() {
        List<GioHang> selectedItems = cartAdapter.getSelectedItems();
        if (selectedItems.isEmpty()) {
            Toast.makeText(GioHangA.this, "Vui lòng chọn sản phẩm để thanh toán!", Toast.LENGTH_SHORT).show();
            return;
        }

        boolean isSuccess = dao.addDonHang(currentUserId, selectedItems);
        if (isSuccess) {
            Toast.makeText(GioHangA.this, "Thanh toán thành công!", Toast.LENGTH_SHORT).show();
            recreate(); // Refresh the activity
        } else {
            Toast.makeText(GioHangA.this, "Thanh toán thất bại!", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateTotalPrice() {
        TongTien = cartItems.stream()
                .filter(GioHang::isSelected) // Chỉ tính các sản phẩm được chọn
                .mapToInt(GioHang::getGia) // Tính tổng giá
                .sum();
        txtTongTien.setText("Tổng tiền: " + TongTien + " VNĐ");
    }

// Đảm bảo gọi updateTotalPrice trong phương thức xử lý checkbox và phương thức tăng/giảm số lượng trong CartAdapter

}
