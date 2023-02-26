package com.RTechnologies.booksandbooks.Adapters.Slider;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.RTechnologies.booksandbooks.R;
import com.bumptech.glide.Glide;
import com.smarteist.autoimageslider.SliderView;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class SliderAdapter extends SliderViewAdapter<SliderAdapter.SliderAdapterVH> {

    Activity context;
    List<String> mSliderItems = new ArrayList<>();

    public SliderAdapter() {
    }

    public SliderAdapter(Activity context) {
        this.context = context;
    }

    public SliderAdapter(Activity context, List<String> mSliderItems) {
        this.context = context;
        this.mSliderItems = mSliderItems;
    }

    public void setImages(List<String> sliderItems) {
        this.mSliderItems = sliderItems;
        notifyDataSetChanged();
    }

    @Override
    public SliderAdapterVH onCreateViewHolder(ViewGroup parent) {
        @SuppressLint("InflateParams") View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_slider_layout_item, null);
        return new SliderAdapterVH(inflate);
    }

    @Override
    public void onBindViewHolder(SliderAdapterVH viewHolder, int position) {
        String sliderItem = mSliderItems.get(position);
        int res = context.getResources().getIdentifier(sliderItem, "drawable", context.getPackageName());
        viewHolder.imageViewBackground.setImageResource(res);

        // Drawable draw = context.getResources().getDrawable(res);
        // viewHolder.imageViewBackground.setImageDrawable(draw);
        // Glide.with(context).load(sliderItem).into(viewHolder.imageViewBackground);
    }

    @Override
    public int getCount() {
        return mSliderItems.size();
    }

    public static class SliderAdapterVH extends ViewHolder {

        ImageView imageViewBackground;


        public SliderAdapterVH(View itemView) {
            super(itemView);

            imageViewBackground = itemView.findViewById(R.id.imageView_slider);
        }
    }
}
