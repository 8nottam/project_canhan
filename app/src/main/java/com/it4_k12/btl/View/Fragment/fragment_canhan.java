package com.it4_k12.btl.View.Fragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.it4_k12.btl.Controller.DAO;
import com.it4_k12.btl.Model.User;
import com.it4_k12.btl.R;
import com.it4_k12.btl.View.Login;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;

public class fragment_canhan extends Fragment {

    private ImageView imageViewProfilePic;
    private ImageView imageViewEdit;
    private TextView txtHoTen, txtEmail, txtSoDienThoai, txtGioiTinh, txtNgaySinh, txtDiaChi, txtNgayTao;
    private DAO dao;
    private User currentUser;
    private Button btndangxuat;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_canhan, container, false);

        dao = new DAO();

        imageViewProfilePic = view.findViewById(R.id.imageViewProfilePic);
        imageViewEdit = view.findViewById(R.id.imageViewEdit);
        txtHoTen = view.findViewById(R.id.txtHoTen);
        txtEmail = view.findViewById(R.id.txtEmail);
        txtSoDienThoai = view.findViewById(R.id.txtSoDienThoai);
        txtGioiTinh = view.findViewById(R.id.txtGioiTinh);
        txtNgaySinh = view.findViewById(R.id.txtNgaySinh);
        txtDiaChi = view.findViewById(R.id.txtDiaChi);
        txtNgayTao = view.findViewById(R.id.txtNgayTao);
        btndangxuat = view.findViewById(R.id.btndangxuat);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        int userId = sharedPreferences.getInt("userId", -1);

        if (userId != -1) {
            currentUser = dao.getUserById(userId);
            updateUI();
        }

        imageViewEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEditDialog();
            }
        });

        btndangxuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Login.class);
                startActivity(intent);
            }
        });

        return view;
    }

    private void updateUI() {
        if (currentUser != null) {
            txtHoTen.setText(currentUser.getHoTen());
            txtEmail.setText("Email: " + currentUser.getEmail());
            txtSoDienThoai.setText("Phone: " + currentUser.getSoDienThoai());
            txtGioiTinh.setText("Giới tính: " + currentUser.getGioiTinh());
            txtNgaySinh.setText("Ngày sinh: " + currentUser.getNgaySinh());
            txtDiaChi.setText("Địa chỉ: " + currentUser.getDiaChi());
            txtNgayTao.setText("Ngày tạo tài khoản: " + currentUser.getNgayTao());

            // Load profile picture
            if (currentUser.getAnhDaiDien() != null) {
                loadProfilePicture(currentUser.getAnhDaiDien());
            }
        }
    }

    private void loadProfilePicture(String imageUrl) {
        new Thread(() -> {
            try {
                URL url = new URL(imageUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                Bitmap bitmap = BitmapFactory.decodeStream(input);
                getActivity().runOnUiThread(() -> imageViewProfilePic.setImageBitmap(bitmap));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void showEditDialog() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View dialogView = inflater.inflate(R.layout.dialog_edit_user, null);

        final EditText editHoTen = dialogView.findViewById(R.id.editHoTen);
        final EditText editSoDienThoai = dialogView.findViewById(R.id.editSoDienThoai);
        final RadioGroup radioGroupGioiTinh = dialogView.findViewById(R.id.radioGroupGioiTinh);
        final EditText editNgaySinh = dialogView.findViewById(R.id.editNgaySinh);
        final EditText editDiaChi = dialogView.findViewById(R.id.editDiaChi);
        final EditText editAnhDaiDien = dialogView.findViewById(R.id.editAnhDaiDien);

        // Cài đặt dữ liệu cho các trường thông tin
        if (currentUser != null) {
            editHoTen.setText(currentUser.getHoTen());
            editSoDienThoai.setText(currentUser.getSoDienThoai());

            // Set giới tính
            switch (currentUser.getGioiTinh()) {
                case "Nam":
                    radioGroupGioiTinh.check(R.id.radioNam);
                    break;
                case "Nữ":
                    radioGroupGioiTinh.check(R.id.radioNu);
                    break;
                default:
                    radioGroupGioiTinh.check(R.id.radioKhac);
                    break;
            }

            editNgaySinh.setText(currentUser.getNgaySinh());
            editDiaChi.setText(currentUser.getDiaChi());
            editAnhDaiDien.setText(currentUser.getAnhDaiDien());
        }

        // Xử lý sự kiện chọn ngày sinh
        editNgaySinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Hiển thị DatePickerDialog
                final Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
                                editNgaySinh.setText(selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear);
                            }
                        }, year, month, day);
                datePickerDialog.show();
            }
        });

        // Tạo và hiển thị dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Chỉnh sửa thông tin");
        builder.setView(dialogView);

        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Lấy giới tính từ RadioGroup
                String gioiTinh = "";
                int selectedGenderId = radioGroupGioiTinh.getCheckedRadioButtonId();
                if (selectedGenderId == R.id.radioNam) {
                    gioiTinh = "Nam";
                } else if (selectedGenderId == R.id.radioNu) {
                    gioiTinh = "Nữ";
                } else {
                    gioiTinh = "Khác";
                }

                final User updatedUser = new User(
                        currentUser.getMaUser(),
                        editHoTen.getText().toString(),
                        currentUser.getEmail(),
                        currentUser.getMatKhau(), // Bỏ qua mật khẩu
                        editSoDienThoai.getText().toString(),
                        gioiTinh,
                        editNgaySinh.getText().toString(),
                        editDiaChi.getText().toString(),
                        editAnhDaiDien.getText().toString(),
                        currentUser.getVaiTro(),
                        currentUser.getAnhDaiDien()
                );

                new AlertDialog.Builder(getContext())
                        .setTitle("Confirm")
                        .setMessage("Are you sure you want to update your information?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                boolean isUpdated = dao.updateUser(updatedUser);
                                if (isUpdated) {
                                    Toast.makeText(getContext(), "Profile updated successfully", Toast.LENGTH_SHORT).show();
                                    currentUser = updatedUser;
                                    updateUI();
                                } else {
                                    Toast.makeText(getContext(), "Failed to update profile", Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
            }
        });

        builder.setNegativeButton("Cancel", null);
        builder.show();
    }

}
