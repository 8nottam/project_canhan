package com.it4_k12.btl.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import com.it4_k12.btl.Model.DanhGiaSanPham;
import com.it4_k12.btl.R;

import java.util.List;

public class DanhGiaAdapter extends ArrayAdapter<DanhGiaSanPham> {

    private Context context;
    private List<DanhGiaSanPham> danhGiaList;

    public DanhGiaAdapter(Context context, List<DanhGiaSanPham> danhGiaList) {
        super(context, 0, danhGiaList);
        this.context = context;
        this.danhGiaList = danhGiaList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_danhgia, parent, false);
        }

        DanhGiaSanPham danhGia = getItem(position);

        TextView textViewTenNguoiDanhGia = convertView.findViewById(R.id.textViewTenNguoiDung);
        RatingBar ratingBarDanhGia = convertView.findViewById(R.id.ratingBarDanhGia);
        TextView textViewNoiDung = convertView.findViewById(R.id.textViewNoiDung);
        TextView textViewNgayDanhGia = convertView.findViewById(R.id.textViewNgayDanhGia);

        // Thiết lập dữ liệu cho các View
        textViewTenNguoiDanhGia.setText(danhGia.getHoTenNguoiDanhGia());
        ratingBarDanhGia.setRating(danhGia.getDiem());
        textViewNoiDung.setText(danhGia.getBinhLuan());
        textViewNgayDanhGia.setText(danhGia.getNgayDanhGia());

        return convertView;
    }
}
