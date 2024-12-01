package com.it4_k12.btl.View.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.it4_k12.btl.Adapter.WeddingCollectionAdapter;
import com.it4_k12.btl.R;

import java.util.ArrayList;
import java.util.List;

public class fragment_thongbao extends Fragment {

    private ViewPager2 viewPager2;
    private WeddingCollectionAdapter adapter;
    private List<String> imageUrls;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_thongbao, container, false);

        // Khởi tạo ViewPager2
        viewPager2 = view.findViewById(R.id.vpWeddingCollection);

        // Thêm các URL hình ảnh váy cưới
        imageUrls = new ArrayList<>();
        imageUrls.add("https://mimosawedding.vn/wp-content/uploads/2024/07/hau-truong-chup-anh-cuoi-xoai-non-lou-hoang-37.jpg");  // URL ảnh váy cưới 1
        imageUrls.add("https://mimosawedding.vn/wp-content/uploads/2022/07/vay-cuoi-dep-15-1.jpg");  // URL ảnh váy cưới 2
        imageUrls.add("https://mimosawedding.vn/wp-content/uploads/2022/07/vay-cuoi-dep-29-1.jpg");  // URL ảnh váy cưới 3

        // Khởi tạo Adapter và thiết lập cho ViewPager2
        adapter = new WeddingCollectionAdapter(getContext(), imageUrls);
        viewPager2.setAdapter(adapter);
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);

                // Tìm view tại vị trí hiện tại
                View currentView = ((RecyclerView) viewPager2.getChildAt(0)).findViewHolderForAdapterPosition(position).itemView;

                // Lấy ImageView trong layout và đo chiều cao của nó
                ImageView imageView = currentView.findViewById(R.id.imageView);
                imageView.post(new Runnable() {
                    @Override
                    public void run() {
                        // Điều chỉnh chiều cao của ViewPager2 dựa trên chiều cao của ImageView
                        ViewGroup.LayoutParams layoutParams = viewPager2.getLayoutParams();
                        layoutParams.height = imageView.getHeight();
                        viewPager2.setLayoutParams(layoutParams);
                    }
                });
            }
        });

        return view;
    }
}
