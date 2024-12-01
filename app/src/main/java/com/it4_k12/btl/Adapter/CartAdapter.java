package com.it4_k12.btl.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.it4_k12.btl.Controller.DAO;
import com.it4_k12.btl.Model.GioHang;
import com.it4_k12.btl.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {
    private List<GioHang> cartItems;
    private final Context context;
    private final DAO dao;
    private OnCheckboxChangeListener onCheckboxChangeListener;

    public CartAdapter(List<GioHang> cartItems, Context context) {
        this.cartItems = cartItems;
        this.context = context;
        this.dao = new DAO();
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_giohang, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {

        GioHang cartItem = cartItems.get(position);
        Glide.with(context).load(cartItem.getAnhSanPham()).into(holder.imgProduct);
        holder.txtProductName.setText(cartItem.getTenSanPham());
        holder.txtPrice.setText(String.format(Locale.getDefault(), "%.0f", (float) cartItem.getGia()));
        holder.txtQuantity.setText(String.valueOf(cartItem.getSoLuong()));
        holder.txtSize.setText("Size: " + cartItem.getSize());
        holder.txtColor.setText("Màu sắc: " + cartItem.getMauSac());
        if(!cartItems.isEmpty()){
            // Set checkbox state and listener
            holder.chkSelect.setOnCheckedChangeListener(null); // Clear previous listener
            holder.chkSelect.setChecked(cartItem.isSelected());
            holder.chkSelect.setOnCheckedChangeListener((buttonView, isChecked) -> {
                cartItem.setSelected(isChecked);
                if (onCheckboxChangeListener != null) {
                    onCheckboxChangeListener.onCheckboxChanged(); // Notify the activity
                }
            });

            holder.btnIncrease.setOnClickListener(v -> updateQuantity(holder, cartItem, 1));
            holder.btnDecrease.setOnClickListener(v -> updateQuantity(holder, cartItem, -1));
        }

    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    public List<GioHang> getSelectedItems() {
        List<GioHang> selectedItems = new ArrayList<>();
        for (GioHang item : cartItems) {
            if (item.isSelected()) {
                selectedItems.add(item);
            }
        }
        return selectedItems;
    }

    public void updateItems(List<GioHang> newItems) {
        this.cartItems = newItems;
        notifyDataSetChanged();
    }

    private void updateQuantity(CartViewHolder holder, GioHang cartItem, int change) {
        int newQuantity = cartItem.getSoLuong() + change;
        if (newQuantity > 0) {
            cartItem.setSoLuong(newQuantity);
            dao.updateCartQuantity(cartItem.getMaGioHang(), newQuantity);
            holder.txtQuantity.setText(String.valueOf(newQuantity));

            // Thông báo cho activity cập nhật tổng tiền
            if (onCheckboxChangeListener != null) {
                onCheckboxChangeListener.onCheckboxChanged();
            }
        } else {
            Toast.makeText(context, "Số lượng không thể nhỏ hơn 1", Toast.LENGTH_SHORT).show();
        }
    }


    public static class CartViewHolder extends RecyclerView.ViewHolder {
        ImageView imgProduct;
        TextView txtProductName, txtPrice, txtQuantity, txtSize, txtColor;
        Button btnIncrease, btnDecrease;
        CheckBox chkSelect;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            imgProduct = itemView.findViewById(R.id.imgProduct);
            txtProductName = itemView.findViewById(R.id.txtProductName);
            txtPrice = itemView.findViewById(R.id.txtPrice);
            txtQuantity = itemView.findViewById(R.id.txtQuantity);
            txtSize = itemView.findViewById(R.id.txtSize);
            txtColor = itemView.findViewById(R.id.txtColor);
            btnIncrease = itemView.findViewById(R.id.btnIncrease);
            btnDecrease = itemView.findViewById(R.id.btnDecrease);
            chkSelect = itemView.findViewById(R.id.chkSelect);
        }
    }

    public void setOnCheckboxChangeListener(OnCheckboxChangeListener listener) {
        this.onCheckboxChangeListener = listener;
    }

    public interface OnCheckboxChangeListener {
        void onCheckboxChanged();
    }
}
