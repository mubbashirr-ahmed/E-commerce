package com.RTechnologies.booksandbooks.Adapters.Recycler;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.RTechnologies.booksandbooks.Activities.Main.OrderDetailActivity;
import com.RTechnologies.booksandbooks.Models.Cartitem;
import com.RTechnologies.booksandbooks.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.VH> {

    Context context;
    ArrayList<Cartitem> arrayList;

    public CartAdapter(Context context, ArrayList<Cartitem> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_cart, parent, false);
        return new VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        String productName = arrayList.get(position).getProductName();
        String productCount = Integer.toString(arrayList.get(position).getNumOfItems());
        String price = Double.toString(arrayList.get(position).getPrice());
        String date = arrayList.get(position).getDate();
        String img=arrayList.get(position).getImgURL();
        String description=arrayList.get(position).getDiscription();

        holder.tv_product_name.setText(productName);
        holder.tv_product_count.setText(productCount);
        Picasso.get().load(img).into(holder.iv_img);
        holder.tv_price.setText(price);
        holder.tv_date.setText(date);

        holder.cardView.setOnClickListener(view -> context.startActivity(new Intent(context, OrderDetailActivity.class)
                .putExtra("itemName", productName)
                .putExtra("img" , img)
                .putExtra("price" , price)
                .putExtra("description" , description)

        ));


        //holder.cvCancel.setOnClickListener(view -> context.startActivity(new Intent(context, OrderDetailActivity.class)));
        holder.cvCancel.setOnClickListener(view->{
            arrayList.remove(position);
            Toast.makeText(context, "OK", Toast.LENGTH_SHORT).show();
        });

        holder.cvModify.setOnClickListener(view -> context.startActivity(new Intent(context, OrderDetailActivity.class)));
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class VH extends RecyclerView.ViewHolder {

        CardView cardView, cvCancel, cvModify;
        ImageView iv_img;
        TextView tv_product_name;
        TextView tv_product_count;
        TextView tv_price;
        TextView tv_date;

        public VH(@NonNull View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.cardView);
            cvCancel = itemView.findViewById(R.id.cv_cancel);
            cvModify = itemView.findViewById(R.id.cv_modify);
            iv_img = itemView.findViewById(R.id.iv_img);
            tv_product_name = itemView.findViewById(R.id.tv_product_name);
            tv_product_count = itemView.findViewById(R.id.tv_product_count);
            tv_price = itemView.findViewById(R.id.tv_price);
            tv_date = itemView.findViewById(R.id.tv_date);
        }
    }

}
