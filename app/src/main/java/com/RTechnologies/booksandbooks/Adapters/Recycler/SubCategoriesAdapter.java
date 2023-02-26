package com.RTechnologies.booksandbooks.Adapters.Recycler;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.RTechnologies.booksandbooks.Activities.Main.OrderDetailActivity;
import com.RTechnologies.booksandbooks.Models.Category;
import com.RTechnologies.booksandbooks.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SubCategoriesAdapter extends RecyclerView.Adapter<SubCategoriesAdapter.VH> {

    Context context;
    ArrayList<Category> arrayList;

    public SubCategoriesAdapter(Context context, ArrayList<Category> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_sub_categories, parent, false);
        return new VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        holder.tv_text.setText(arrayList.get(position).getCatName());
//        holder.imageView2.setImageResource(); set the image here
        Picasso.get().load(arrayList.get(position).getImgURL()).into(holder.imageView2);
        holder.itemView.setOnClickListener(view -> context.startActivity(new Intent(context, OrderDetailActivity.class)));

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class VH extends RecyclerView.ViewHolder {

        TextView tv_text;
        ImageView imageView2;
        public VH(@NonNull View itemView) {
            super(itemView);
            tv_text = itemView.findViewById(R.id.tv_text);
            imageView2 = itemView.findViewById(R.id.imageView2);
        }
    }

}
