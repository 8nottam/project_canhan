package com.it4_k12.btl.View.Fragment;

import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SearchView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.it4_k12.btl.Adapter.SanPhamAdapterAdmin;
import com.it4_k12.btl.Controller.DAO;
import com.it4_k12.btl.Model.SanPham;
import com.it4_k12.btl.R;
import java.util.ArrayList;
import java.util.List;

public class fragment_sanpham_admin extends Fragment {

    private RecyclerView recyclerViewSanPham;
    private SanPhamAdapterAdmin sanPhamAdapterAdmin;
    private SearchView searchView;
    private DAO dao;
    private List<SanPham> danhSachSanPham;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sanpham_admin, container, false);

        // Ánh xạ các view
        recyclerViewSanPham = view.findViewById(R.id.recyclerViewSanPham);
        searchView = view.findViewById(R.id.searchView);
        Button btnAddProduct = view.findViewById(R.id.btnAddProduct);
        Button btnDeleteProduct = view.findViewById(R.id.btnDeleteProduct);

        // Khởi tạo DAO
        dao = new DAO();
        // Lấy danh sách sản phẩm từ cơ sở dữ liệu
        danhSachSanPham = dao.getAllSanPham();

        // Khởi tạo adapter và set cho RecyclerView
        sanPhamAdapterAdmin = new SanPhamAdapterAdmin(getContext(), danhSachSanPham, this::showEditDialog);
        recyclerViewSanPham.setAdapter(sanPhamAdapterAdmin);
        recyclerViewSanPham.setLayoutManager(new LinearLayoutManager(getContext())); // Hiển thị dạng một cột

        // Cài đặt tìm kiếm sản phẩm
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                sanPhamAdapterAdmin.filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                sanPhamAdapterAdmin.filter(newText);
                return false;
            }
        });


        // Cài đặt nút thêm sản phẩm
        btnAddProduct.setOnClickListener(v -> showAddDialog());

        // Cài đặt nút xóa sản phẩm
        btnDeleteProduct.setOnClickListener(v -> deleteSelectedProducts());

        return view;
    }

    // Phương thức hiển thị dialog để thêm sản phẩm
    private void showAddDialog() {
        // Tạo AlertDialog.Builder
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Thêm sản phẩm");



        // Nạp layout từ XML
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_add_product, null);
        builder.setView(dialogView);

        // Ánh xạ các view trong dialog
        EditText edtTenSanPham = dialogView.findViewById(R.id.edtTenSanPham);
        RadioGroup radioGroupLoai = dialogView.findViewById(R.id.radioGroupLoai);
        RadioButton rbAoCuoi = dialogView.findViewById(R.id.rbAoCuoi);
        RadioButton rbVest = dialogView.findViewById(R.id.rbVest);
        SeekBar seekBarSoLuong = dialogView.findViewById(R.id.seekBarSoLuong);
        TextView txtSoLuong = dialogView.findViewById(R.id.txtSoLuong);
        Button btnDecrease = dialogView.findViewById(R.id.btnDecrease);
        Button btnIncrease = dialogView.findViewById(R.id.btnIncrease);
        EditText edtAnhSanPham = dialogView.findViewById(R.id.edtAnhSanPham);
        EditText edtGia = dialogView.findViewById(R.id.edtGia);
        EditText edtMoTa = dialogView.findViewById(R.id.edtMoTa);

        // Cài đặt SeekBar và nút tăng giảm số lượng
        seekBarSoLuong.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int soLuong = Math.max(1, progress);
                txtSoLuong.setText("Số lượng: " + soLuong);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        // Xử lý nút giảm số lượng
        btnDecrease.setOnClickListener(v -> {
            int currentValue = seekBarSoLuong.getProgress();
            if (currentValue > 1) {
                seekBarSoLuong.setProgress(currentValue - 1);
            }
        });

        // Xử lý nút tăng số lượng
        btnIncrease.setOnClickListener(v -> {
            int currentValue = seekBarSoLuong.getProgress();
            if (currentValue < 300) {
                seekBarSoLuong.setProgress(currentValue + 1);
            }
        });

        // Nút thêm sản phẩm
        builder.setPositiveButton("Thêm", (dialog, which) -> {
            int soLuong = seekBarSoLuong.getProgress();
            String tenSanPham = edtTenSanPham.getText().toString();
            String loaiSanPham = rbAoCuoi.isChecked() ? "Đồ cưới" : "Phụ kiện";
            String anhSanPham = edtAnhSanPham.getText().toString();
            double gia = Double.parseDouble(edtGia.getText().toString());
            String moTa = edtMoTa.getText().toString();

            // Thêm sản phẩm vào database
            if (validateProductInputs(edtTenSanPham, edtGia, seekBarSoLuong)) {
                SanPham sanPham = new SanPham(0,
                        tenSanPham,
                        loaiSanPham,
                        anhSanPham,
                        gia,
                        soLuong,
                        moTa,
                        "2024-09-22"
                );
                dao.addSanPham(sanPham);
                danhSachSanPham.add(sanPham);
                sanPhamAdapterAdmin.notifyDataSetChanged();
            }
        });

        builder.setNegativeButton("Hủy", (dialog, which) -> dialog.dismiss());
        AlertDialog dialog = builder.create();

        // Thiết lập background cho dialog
        dialog.setOnShowListener(dialogInterface -> {
            Window window = dialog.getWindow();
            if (window != null) {
                window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT)); // Đặt nền trong suốt
                window.setDimAmount(0f); // Tắt dim (bóng mờ)
            }
        });

        dialog.show();
    }


    // Phương thức hiển thị dialog để sửa sản phẩm
    private void showEditDialog(SanPham sanPham) {
        // Tạo AlertDialog.Builder
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Sửa sản phẩm");

        // Nạp layout từ XML
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_add_product, null);
        builder.setView(dialogView);

        // Ánh xạ các view trong dialog
        EditText edtTenSanPham = dialogView.findViewById(R.id.edtTenSanPham);
        RadioGroup radioGroupLoai = dialogView.findViewById(R.id.radioGroupLoai);
        RadioButton rbAoCuoi = dialogView.findViewById(R.id.rbAoCuoi);
        RadioButton rbVest = dialogView.findViewById(R.id.rbVest);
        SeekBar seekBarSoLuong = dialogView.findViewById(R.id.seekBarSoLuong);
        TextView txtSoLuong = dialogView.findViewById(R.id.txtSoLuong);
        Button btnDecrease = dialogView.findViewById(R.id.btnDecrease);
        Button btnIncrease = dialogView.findViewById(R.id.btnIncrease);
        EditText edtAnhSanPham = dialogView.findViewById(R.id.edtAnhSanPham);
        EditText edtGia = dialogView.findViewById(R.id.edtGia);
        EditText edtMoTa = dialogView.findViewById(R.id.edtMoTa);

        // Gán dữ liệu sản phẩm hiện tại vào các view
        edtTenSanPham.setText(sanPham.getTenSanPham());
        edtAnhSanPham.setText(sanPham.getAnhSanPham());
        edtGia.setText(String.valueOf(sanPham.getGia()));
        edtMoTa.setText(sanPham.getMoTa());

        // Đặt giá trị cho RadioButton (loại sản phẩm)
        if (sanPham.getLoai().equals("Đồ cưới")) {
            rbAoCuoi.setChecked(true);
        } else {
            rbVest.setChecked(true);
        }

        // Đặt giá trị cho SeekBar (số lượng)
        seekBarSoLuong.setProgress(sanPham.getSoLuong());
        txtSoLuong.setText("Số lượng: " + sanPham.getSoLuong());

        // Cài đặt SeekBar và nút tăng giảm số lượng
        seekBarSoLuong.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int soLuong = Math.max(1, progress);
                txtSoLuong.setText("Số lượng: " + soLuong);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        // Xử lý nút giảm số lượng
        btnDecrease.setOnClickListener(v -> {
            int currentValue = seekBarSoLuong.getProgress();
            if (currentValue > 1) {
                seekBarSoLuong.setProgress(currentValue - 1);
            }
        });

        // Xử lý nút tăng số lượng
        btnIncrease.setOnClickListener(v -> {
            int currentValue = seekBarSoLuong.getProgress();
            if (currentValue < 300) {
                seekBarSoLuong.setProgress(currentValue + 1);
            }
        });

        // Nút lưu thay đổi sản phẩm
        builder.setPositiveButton("Lưu", (dialog, which) -> {
            // Lấy các giá trị mới từ dialog
            String tenSanPham = edtTenSanPham.getText().toString();
            String loaiSanPham = rbAoCuoi.isChecked() ? "Đồ cưới" : "Phụ kiện";
            String anhSanPham = edtAnhSanPham.getText().toString();
            double gia = Double.parseDouble(edtGia.getText().toString());
            int soLuong = seekBarSoLuong.getProgress();
            String moTa = edtMoTa.getText().toString();

            // Cập nhật thông tin sản phẩm
            if (validateProductInputs(edtTenSanPham, edtGia, seekBarSoLuong)) {
                sanPham.setTenSanPham(tenSanPham);
                sanPham.setLoai(loaiSanPham);
                sanPham.setAnhSanPham(anhSanPham);
                sanPham.setGia(gia);
                sanPham.setSoLuong(soLuong);
                sanPham.setMoTa(moTa);

                // Cập nhật sản phẩm trong cơ sở dữ liệu
                dao.updateSanPham(sanPham);

                // Cập nhật lại RecyclerView
                sanPhamAdapterAdmin.notifyDataSetChanged();
            }
        });

        builder.setNegativeButton("Hủy", (dialog, which) -> dialog.dismiss());
        AlertDialog dialog = builder.create();

        // Thiết lập background cho dialog
        dialog.setOnShowListener(dialogInterface -> {
            Window window = dialog.getWindow();
            if (window != null) {
                window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT)); // Đặt nền trong suốt
                window.setDimAmount(0f); // Tắt dim (bóng mờ)
            }
        });

        dialog.show();
    }


    // Phương thức kiểm tra đầu vào của sản phẩm
    // Phương thức kiểm tra đầu vào của sản phẩm
    private boolean validateProductInputs(EditText edtTenSanPham, EditText edtGia, SeekBar seekBarSoLuong) {
        // Kiểm tra tên sản phẩm
        if (edtTenSanPham.getText().toString().trim().isEmpty()) {
            edtTenSanPham.setError("Tên sản phẩm không được để trống");
            return false;
        }

        // Kiểm tra giá sản phẩm
        try {
            double gia = Double.parseDouble(edtGia.getText().toString().trim());
            if (gia <= 0) {  // Giá phải lớn hơn 0
                edtGia.setError("Giá sản phẩm phải lớn hơn 0");
                return false;
            }
        } catch (NumberFormatException e) {
            edtGia.setError("Giá sản phẩm không hợp lệ");
            return false;
        }

        // Kiểm tra số lượng từ SeekBar
        int soLuong = seekBarSoLuong.getProgress();
        if (soLuong <= 0) {
            Toast.makeText(getContext(), "Số lượng phải lớn hơn 0", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }



    // Phương thức xóa sản phẩm đã chọn
    private void deleteSelectedProducts() {
        List<SanPham> sanPhamToRemove = new ArrayList<>();

        // Thêm sản phẩm được chọn vào danh sách để xóa
        for (SanPham sanPham : danhSachSanPham) {
            if (sanPham.isSelected()) {
                sanPhamToRemove.add(sanPham);  // Thêm vào danh sách xóa
            }
        }

        // Xóa sản phẩm khỏi cơ sở dữ liệu và danh sách
        for (SanPham sanPham : sanPhamToRemove) {
            boolean isDeleted = dao.deleteSanPham(sanPham.getMaSanPham()); // Gọi phương thức xóa từ DAO
            if (isDeleted) {
                danhSachSanPham.remove(sanPham); // Cập nhật danh sách sản phẩm sau khi xóa thành công
            } else {
                // Nếu không xóa được, thông báo lỗi
                Toast.makeText(getContext(), "Không thể xóa sản phẩm: " + sanPham.getTenSanPham(), Toast.LENGTH_SHORT).show();
            }
        }

        // Cập nhật lại giao diện RecyclerView
        sanPhamAdapterAdmin.notifyDataSetChanged();
    }


}
