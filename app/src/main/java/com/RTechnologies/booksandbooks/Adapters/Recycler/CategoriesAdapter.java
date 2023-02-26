package com.RTechnologies.booksandbooks.Adapters.Recycler;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.RTechnologies.booksandbooks.Activities.Main.SubCategoriesActivity;
import com.RTechnologies.booksandbooks.Models.Category;
import com.RTechnologies.booksandbooks.R;

import java.util.ArrayList;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.VH> {

    Context context;
    ArrayList<Category> arrayList;

    public CategoriesAdapter(Context context, ArrayList<Category> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_category, parent, false);
        return new VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        int res = context.getResources().getIdentifier(arrayList.get(position).getImgURL(), "drawable", context.getPackageName());
        holder.iv_img.setImageResource(res);
        holder.tv_cat_name.setText(arrayList.get(position).getCatName());
        holder.cardView.setOnClickListener(view -> context.startActivity(new Intent(context, SubCategoriesActivity.class)
                .putExtra("catName" , arrayList.get(position).getCatName() )
        ));
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class VH extends RecyclerView.ViewHolder {

        CardView cardView;
        TextView tv_cat_name;
        ImageView iv_img;
        public VH(@NonNull View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.cardView);
            tv_cat_name = itemView.findViewById(R.id.tv_cat_name);
            iv_img = itemView.findViewById(R.id.iv_img);
        }
    }

}
