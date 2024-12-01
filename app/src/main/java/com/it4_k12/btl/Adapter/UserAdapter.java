package com.it4_k12.btl.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.it4_k12.btl.Controller.DAO;
import com.it4_k12.btl.Model.User;
import com.it4_k12.btl.R;

import java.util.ArrayList;
import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {
    private List<User> userList;
    private Context context;
    private DAO dao;

    public UserAdapter(Context context, List<User> userList) {
        this.context = context;
        this.userList = userList;
        this.dao = new DAO();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_user, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        User user = userList.get(position);
        holder.hoTenTextView.setText(user.getHoTen());
        holder.emailTextView.setText(user.getEmail());
        Glide.with(context).load(user.getAnhDaiDien()).into(holder.anhDaiDienImageView);

        // Xử lý sự kiện khi nhấn nút "Xóa tài khoản"
        holder.xoaButton.setOnClickListener(v -> showDeleteConfirmationDialog(user.getMaUser(), position));

        // Xử lý sự kiện khi nhấn nút "Đổi mật khẩu"
        holder.doimatkhauButton.setOnClickListener(v -> showChangePasswordDialog(user.getMaUser()));
    }


    private void showDeleteConfirmationDialog(int maUser, int position) {
        new AlertDialog.Builder(context)
                .setTitle("Xóa tài khoản")
                .setMessage("Bạn có chắc chắn muốn xóa tài khoản này không?")
                .setPositiveButton("Có", (dialog, which) -> {
                    if (dao.deleteUser(maUser)) {
                        Toast.makeText(context, "Xóa tài khoản thành công", Toast.LENGTH_SHORT).show();
                        // Xóa tài khoản khỏi danh sách và thông báo adapter cập nhật
                        userList.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, userList.size());
                    } else {
                        Toast.makeText(context, "Xóa tài khoản thất bại", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Không", null)
                .show();
    }


    private void showChangePasswordDialog(int maUser) {
        // Tạo dialog để đổi mật khẩu
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_change_password, null);
        EditText newPasswordEditText = dialogView.findViewById(R.id.newPasswordEditText);
        Button confirmButton = dialogView.findViewById(R.id.confirmButton);
        builder.setView(dialogView);
        AlertDialog dialog = builder.create();

        confirmButton.setOnClickListener(v -> {
            String newPassword = newPasswordEditText.getText().toString();
            if (dao.updatePassword(maUser, newPassword)) {
                Toast.makeText(context, "Đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Đổi mật khẩu thất bại", Toast.LENGTH_SHORT).show();
            }
            dialog.dismiss();
        });

        dialog.show();
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView hoTenTextView;
        TextView emailTextView;
        ImageView anhDaiDienImageView;
        Button xoaButton;
        Button doimatkhauButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            hoTenTextView = itemView.findViewById(R.id.hoTenTextView);
            emailTextView = itemView.findViewById(R.id.emailTextView);
            anhDaiDienImageView = itemView.findViewById(R.id.anhDaiDienImageView);
            xoaButton = itemView.findViewById(R.id.xoaButton);
            doimatkhauButton = itemView.findViewById(R.id.doimatkhauButton);
        }
    }
}

