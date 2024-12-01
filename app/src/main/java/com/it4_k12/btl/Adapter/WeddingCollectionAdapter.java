package com.it4_k12.btl.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.it4_k12.btl.R;

import java.util.List;

public class WeddingCollectionAdapter extends RecyclerView.Adapter<WeddingCollectionAdapter.ViewHolder> {

    private Context context;
    private List<String> imageUrls;

    public WeddingCollectionAdapter(Context context, List<String> imageUrls) {
        this.context = context;
        this.imageUrls = imageUrls;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate layout cho từng trang trong ViewPager2, phải là match_parent cho width và height
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_gioithieu, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String imageUrl = imageUrls.get(position);

        // Sử dụng Glide để load ảnh từ URL
        Glide.with(context)
                .load(imageUrl)
                .override(Target.SIZE_ORIGINAL)  // Giữ nguyên kích thước gốc của ảnh
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return imageUrls.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }
}

