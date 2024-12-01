package com.it4_k12.btl.View;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import android.widget.Button;

import android.widget.EditText;

import android.widget.RadioGroup;

import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.it4_k12.btl.Controller.DAO;
import com.it4_k12.btl.Model.User;
import com.it4_k12.btl.R;


import java.util.Calendar;

public class Register extends AppCompatActivity {
    private EditText etHoTen, etEmail, etMatKhau, etSoDienThoai, etNgaySinh, etDiaChi, etAnhDaiDien;
    private RadioGroup rgGioiTinh;
    private Button btnDangKy;
    DAO dao = new DAO();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etHoTen = findViewById(R.id.etHoTen);
        etEmail = findViewById(R.id.etEmail);
        etMatKhau = findViewById(R.id.etMatKhau);
        etSoDienThoai = findViewById(R.id.etSoDienThoai);
        etNgaySinh = findViewById(R.id.etNgaySinh);
        etDiaChi = findViewById(R.id.etDiaChi);
        etAnhDaiDien = findViewById(R.id.etAnhDaiDien);
        rgGioiTinh = findViewById(R.id.rgGioiTinh);
        btnDangKy = findViewById(R.id.btnDangKy);

        etNgaySinh.setOnClickListener(v -> showDatePicker());

        btnDangKy.setOnClickListener(v -> registerUser());
    }

    private void showDatePicker() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, year1, monthOfYear, dayOfMonth) -> {
            String selectedDate = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year1;
            etNgaySinh.setText(selectedDate);
        }, year, month, day);

        datePickerDialog.show();
    }

    private void registerUser() {
        String hoTen = etHoTen.getText().toString();
        String email = etEmail.getText().toString();
        String matKhau = etMatKhau.getText().toString();
        String soDienThoai = etSoDienThoai.getText().toString();
        String ngaySinh = etNgaySinh.getText().toString();
        String diaChi = etDiaChi.getText().toString();
        String anhDaiDien = etAnhDaiDien.getText().toString();

        String gioiTinh;
        int selectedId = rgGioiTinh.getCheckedRadioButtonId();
        if (selectedId == R.id.rbNam) {
            gioiTinh = "Nam";
        } else if (selectedId == R.id.rbNu) {
            gioiTinh = "Nữ";
        } else {
            gioiTinh = "Khác";
        }

        if (hoTen.isEmpty() || email.isEmpty() || matKhau.isEmpty() || soDienThoai.isEmpty() || ngaySinh.isEmpty() || diaChi.isEmpty() || anhDaiDien.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.setError("Email không hợp lệ");
            etEmail.requestFocus();
            return;
        }
        if (matKhau.length() < 6) {
            etMatKhau.setError("Mật khẩu phải có ít nhất 6 ký tự");
            etMatKhau.requestFocus();
            return;
        }
        if (!soDienThoai.matches("[0-9]{10}")) {
            etSoDienThoai.setError("Số điện thoại không hợp lệ");
            etSoDienThoai.requestFocus();
            return;
        }
        if (!android.util.Patterns.WEB_URL.matcher(anhDaiDien).matches()) {
            etAnhDaiDien.setError("URL ảnh đại diện không hợp lệ");
            etAnhDaiDien.requestFocus();
            return;
        }

        User user = new User(0, hoTen, email, matKhau, soDienThoai, gioiTinh, ngaySinh, diaChi, anhDaiDien, "user", null);
        DAO dao = new DAO();
        try {
            boolean isRegistered = dao.themTaiKhoan(user);
            if (isRegistered) {
                Toast.makeText(this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Register.this, Login.class);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Đăng ký thất bại", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Lỗi khi đăng ký", Toast.LENGTH_SHORT).show();
        }
    }
}
