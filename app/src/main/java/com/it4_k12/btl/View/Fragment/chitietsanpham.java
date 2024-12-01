package com.it4_k12.btl.View.Fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.it4_k12.btl.Adapter.DanhGiaAdapter;
import com.it4_k12.btl.Model.DanhGiaSanPham;
import com.it4_k12.btl.Model.GioHang;
import com.it4_k12.btl.Model.SanPham;
import com.it4_k12.btl.Controller.DAO;
import com.it4_k12.btl.R;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class chitietsanpham extends AppCompatActivity {

    private ImageView imageViewSanPham, btnback;
    private TextView textViewTenSanPham;
    private TextView textViewGia;
    private TextView textViewMoTa;
    private ListView listViewDanhGia;
    private Button btnDanhGia;
    private DanhGiaAdapter danhGiaAdapter;
    private SanPham sanPham;
    private int maSanPham;
    private int currentUserId;
    private DAO dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chitietsanpham);

        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        currentUserId = sharedPreferences.getInt("userId", -1);

        // Khởi tạo DAO
        dao = new DAO();

        // Lấy ID sản phẩm từ Intent
        maSanPham = getIntent().getIntExtra("MA_SAN_PHAM", -1);

        // Khởi tạo các View
        imageViewSanPham = findViewById(R.id.imageViewSanPham);
        textViewTenSanPham = findViewById(R.id.textViewTenSanPham);
        textViewGia = findViewById(R.id.textViewGia);
        textViewMoTa = findViewById(R.id.textViewMoTa);
        listViewDanhGia = findViewById(R.id.listViewDanhGia);
        btnback = findViewById(R.id.btnback);


        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Kết thúc Activity hiện tại và quay về trang trước đó
            }
        });



        // Hiển thị thông tin sản phẩm
        displaySanPhamDetails();

        // Thiết lập sự kiện cho nút thêm vào giỏ hàng
        Button buttonThemVaoGioHang = findViewById(R.id.buttonThemVaoGioHang);
        buttonThemVaoGioHang.setOnClickListener(v -> {
            if (sanPham != null) { // Kiểm tra sanPham có khác null không
                showAttributeDialog(sanPham);
            } else {
                Toast.makeText(this, "Sản phẩm không hợp lệ.", Toast.LENGTH_SHORT).show();
            }
        });

        // Thiết lập sự kiện cho nút mua ngay

    }



    private void displaySanPhamDetails() {
        if (maSanPham == -1) {
            Toast.makeText(this, "ID sản phẩm không hợp lệ.", Toast.LENGTH_SHORT).show();
            return;
        }

        sanPham = dao.getSanPhamById(maSanPham);
        if (sanPham != null) {
            textViewTenSanPham.setText(sanPham.getTenSanPham());
            textViewGia.setText(String.format("Giá: %.0f VNĐ", sanPham.getGia()));
            textViewMoTa.setText(sanPham.getMoTa());

            Glide.with(this)
                    .load(sanPham.getAnhSanPham())
                    .into(imageViewSanPham);

            List<DanhGiaSanPham> danhGiaList = dao.getDanhGiaBySanPhamId(maSanPham);
            if (danhGiaList != null && !danhGiaList.isEmpty()) {
                danhGiaAdapter = new DanhGiaAdapter(this, danhGiaList);
                listViewDanhGia.setAdapter(danhGiaAdapter);
            } else {
                Toast.makeText(this, "Không có đánh giá nào cho sản phẩm này.", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Không tìm thấy thông tin sản phẩm.", Toast.LENGTH_SHORT).show();
        }
    }
    // Tạo dialog chọn thuộc tính sản phẩm
    private void showAttributeDialog(SanPham sanPham) {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_themvaogiohang);

        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT); // Chiều rộng là 100%, chiều cao tự động

        // Ánh xạ các view trong dialog
        ImageView imgProduct = dialog.findViewById(R.id.imgProduct);
        TextView txtProductName = dialog.findViewById(R.id.txtProductName);
        TextView txtPrice = dialog.findViewById(R.id.txtPrice);
        SeekBar seekBarQuantity = dialog.findViewById(R.id.seekBarQuantity);
        TextView txtQuantity = dialog.findViewById(R.id.txtQuantity);
        RadioGroup radioGroupSize = dialog.findViewById(R.id.radioGroupSize);
        RadioGroup radioGroupColor = dialog.findViewById(R.id.radioGroupColor);
        Button btnConfirm = dialog.findViewById(R.id.btnConfirm);


        Glide.with(this).load(sanPham.getAnhSanPham()).into(imgProduct);
        txtProductName.setText(sanPham.getTenSanPham());
        txtPrice.setText(String.format(Locale.getDefault(), "%.0f VNĐ", sanPham.getGia()));


        // Khởi tạo SeekBar
        seekBarQuantity.setMax(10); // Ví dụ, tối đa 10 sản phẩm
        seekBarQuantity.setProgress(1); // Khởi tạo số lượng là 1
        txtQuantity.setText("Số lượng: " + seekBarQuantity.getProgress());

        // Lắng nghe sự kiện thay đổi SeekBar số lượng
        seekBarQuantity.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                txtQuantity.setText("Số lượng: " + progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        // Xử lý khi nhấn nút xác nhận
        btnConfirm.setOnClickListener(v -> {
            int selectedSizeId = radioGroupSize.getCheckedRadioButtonId();
            int selectedColorId = radioGroupColor.getCheckedRadioButtonId();

            if (selectedSizeId == -1 || selectedColorId == -1) {
                Toast.makeText(chitietsanpham.this, "Vui lòng chọn kích thước và màu sắc.", Toast.LENGTH_SHORT).show();
                return;
            }

            String size = ((RadioButton) dialog.findViewById(selectedSizeId)).getText().toString();
            String color = ((RadioButton) dialog.findViewById(selectedColorId)).getText().toString();
            int quantity = seekBarQuantity.getProgress();

            // Lấy ma_thuoc_tinh
            int maThuocTinh = dao.getMaThuocTinh(sanPham.getMaSanPham(), size, color);
            if (maThuocTinh <=0) {
                Toast.makeText(chitietsanpham.this, "Không tìm thấy thuộc tính sản phẩm", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                return;
            }

            // Thêm vào giỏ hàng
            boolean success = dao.addToCart(currentUserId, sanPham.getMaSanPham(), maThuocTinh, quantity, new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
            if (success) {
                Toast.makeText(chitietsanpham.this, "Đã thêm vào giỏ hàng", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(chitietsanpham.this, GioHangA.class);
                startActivity(intent);
            } else {
                Toast.makeText(chitietsanpham.this, "Không thể thêm vào giỏ hàng", Toast.LENGTH_SHORT).show();
            }

            dialog.dismiss();
        });



        dialog.show();
    }
}

