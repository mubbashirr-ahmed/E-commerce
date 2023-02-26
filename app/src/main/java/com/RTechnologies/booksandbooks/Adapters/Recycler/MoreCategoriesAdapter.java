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
import com.RTechnologies.booksandbooks.Models.Item;
import com.RTechnologies.booksandbooks.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MoreCategoriesAdapter extends RecyclerView.Adapter<MoreCategoriesAdapter.VH> {

    Context context;
    ArrayList<Item> arrayList;

    public MoreCategoriesAdapter(Context context, ArrayList<Item> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_more_categories, parent, false);
        return new VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        String discount = Integer.toString(arrayList.get(position).getDiscount());
        String img = arrayList.get(position).getImgURL();
        String catName = arrayList.get(position).getItemName();
        String price = Double.toString(arrayList.get(position).getPrice());
        String description=arrayList.get(position).getDiscription();
        holder.tv_discount.setText(discount+"%");
        Picasso.get().load(img).into(holder.iv_img);
        holder.tv_cat_name.setText(catName);
        holder.tv_price.setText(price);
        holder.itemView.setOnClickListener(view -> context.startActivity(new Intent(context, OrderDetailActivity.class)
                .putExtra("itemName", catName)
                .putExtra("img" , img)
                .putExtra("price" , price)
                .putExtra("description" , description)


        ));
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class VH extends RecyclerView.ViewHolder {
        TextView tv_discount;
        ImageView iv_img;
        TextView tv_cat_name;
        TextView tv_price;
        public VH(@NonNull View itemView) {
            super(itemView);
            tv_discount = itemView.findViewById(R.id.tv_discount);
            iv_img = itemView.findViewById(R.id.iv_img);
            tv_cat_name = itemView.findViewById(R.id.tv_cat_name);
            tv_price = itemView.findViewById(R.id.tv_price);
        }
    }

}
