package com.RTechnologies.booksandbooks.Adapters.Pager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.RTechnologies.booksandbooks.Models.OnBoarding;
import com.RTechnologies.booksandbooks.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class OnBoardingAdapter extends PagerAdapter {

    ArrayList<OnBoarding> getStartedArrayList;
    Context context;

    public OnBoardingAdapter(ArrayList<OnBoarding> getStartedArrayList, Context context) {
        this.getStartedArrayList = getStartedArrayList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return getStartedArrayList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.on_boarding_item, container, false);
        TextView tvHeading = view.findViewById(R.id.tv_title);
        TextView tvText = view.findViewById(R.id.tv_desc);
        ImageView ivImg1 = view.findViewById(R.id.iv_img_1);

        tvHeading.setText(getStartedArrayList.get(position).getTitle());
        tvText.setText(getStartedArrayList.get(position).getMessage());
        Glide.with(context).load(getStartedArrayList.get(position).getImage1()).into(ivImg1);
        container.addView(view, position - 1);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
