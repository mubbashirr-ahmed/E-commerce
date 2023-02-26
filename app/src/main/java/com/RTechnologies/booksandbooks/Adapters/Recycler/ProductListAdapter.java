package com.RTechnologies.booksandbooks.Adapters.Recycler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.RTechnologies.booksandbooks.Models.Cartitem;
import com.RTechnologies.booksandbooks.R;

import java.util.ArrayList;

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.VH> {

    Context context;
    ArrayList<Cartitem> arrayList;
    double totalprice=0;

    public ProductListAdapter(Context context, ArrayList<Cartitem> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_product_list, parent, false);
        return new VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        holder.itemname.setText(arrayList.get(position).getProductName());

        holder.tv_price.setText(String.valueOf(arrayList.get(position).getPrice()));
        holder.tv_qty.setText("Qty."+String.valueOf(arrayList.get(position).getNumOfItems()));

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class VH extends RecyclerView.ViewHolder
    {
        TextView itemname,tv_price,tv_qty;

        public VH(@NonNull View itemView) {
            super(itemView);
            itemname = itemView.findViewById(R.id.textView3);
            tv_price = itemView.findViewById(R.id.tv_price);
            tv_qty = itemView.findViewById(R.id.tv_qty);
        }
    }

}
