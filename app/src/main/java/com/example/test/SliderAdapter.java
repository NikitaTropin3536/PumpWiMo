//package com.example.test;
//
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ListView;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//import androidx.viewpager2.widget.ViewPager2;
//
//import java.util.List;
//
//public class SliderAdapter extends RecyclerView.Adapter<SliderAdapter.SliderViewHolder> {
//
//        private TextView textView;
//
//        private List<SliderItem> sliderItems;
//
//        private ViewPager2 viewPager2;
//        public SliderAdapter(List<SliderItem> sliderItems, ViewPager2 viewPager2) {
//            this.sliderItems = sliderItems;
//            this.viewPager2 = viewPager2;
//        }
//
//        public void setText(SliderItem sliderItem) {
//            textView.setText(sliderItem.getText());
//        }
//
//        @NonNull
//        @Override
//        public SliderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//            return new SliderViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.slide_item_container, parent, false));
//        }
//
//
//        @Override
//        public void onBindViewHolder(@NonNull SliderViewHolder holder, int position) {
//
//        }
//
//
//        @Override
//        public int getItemCount() {
//            return sliderItems.size();
//        }
//
//        class SliderViewHolder extends RecyclerView.ViewHolder{
//
//        }
//    }
