package com.it4_k12.btl.View.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.it4_k12.btl.Adapter.HoaDonAdapter;
import com.it4_k12.btl.Controller.DAO;
import com.it4_k12.btl.Model.HoaDon;
import com.it4_k12.btl.R;

import java.util.List;

public class fragment_hoadon extends Fragment {
    private RecyclerView recyclerView;
    private HoaDonAdapter adapter;
    private List<HoaDon> hoaDonList;
    private DAO dao;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_hoadon, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Khởi tạo RecyclerView
        recyclerView = view.findViewById(R.id.recyclerViewHoaDon);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Khởi tạo DAO
        dao = new DAO();

        // Lấy userId từ SharedPreferences
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        int userId = sharedPreferences.getInt("userId", -1);

        // Kiểm tra userId
        if (userId != -1) {
            // Lấy danh sách đơn hàng từ database
            hoaDonList = dao.getHoaDonByUserId(userId);
            adapter = new HoaDonAdapter(getContext(), hoaDonList);
            recyclerView.setAdapter(adapter);
        } else {
            // Xử lý nếu userId không hợp lệ (người dùng chưa đăng nhập)
            Toast.makeText(getContext(), "Vui lòng đăng nhập để xem đơn hàng!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        // Cập nhật danh sách đơn hàng khi fragment được quay lại
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        int userId = sharedPreferences.getInt("userId", -1);
        if (userId != -1) {
            // Lấy danh sách đơn hàng mới nhất từ cơ sở dữ liệu
            hoaDonList = dao.getHoaDonByUserId(userId);
            // Cập nhật adapter với dữ liệu mới
            if (adapter != null) {
                adapter.updateData(hoaDonList);
            }
        }
    }
}
