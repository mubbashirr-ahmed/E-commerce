package com.RTechnologies.booksandbooks.Adapters.Recycler;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.RTechnologies.booksandbooks.Activities.Main.OrderDetailActivity;
import com.RTechnologies.booksandbooks.Activities.Main.RateOrderActivity;
import com.RTechnologies.booksandbooks.Models.Historyitem;
import com.RTechnologies.booksandbooks.R;

import java.util.ArrayList;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.VH> {

    Context context;
    ArrayList<Historyitem> arrayList;

    public HistoryAdapter(Context context, ArrayList<Historyitem> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_order_history, parent, false);
        return new VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        holder.cvReOrder.setOnClickListener(view -> context.startActivity(new Intent(context, OrderDetailActivity.class)));
        holder.cvRateOrder.setOnClickListener(view -> context.startActivity(new Intent(context, RateOrderActivity.class)));
        int itemId = arrayList.get(position).getItemId();
        String img = arrayList.get(position).getImgURL();
        String catName = arrayList.get(position).getItemName();
        String price = Double.toString(arrayList.get(position).getPrice());
        holder.itemname.setText(catName);
        holder.date.setText(arrayList.get(position).getDate());
        holder.itemprice.setText(price+"$");
        holder.itemid.setText(""+itemId);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class VH extends RecyclerView.ViewHolder {

        CardView cvReOrder, cvRateOrder;
        TextView itemname, itemprice, itemid, date;
        public VH(@NonNull View itemView) {
            super(itemView);
            itemname = itemView.findViewById(R.id.tv_product_name);
            itemprice = itemView.findViewById(R.id.tv_price);
            itemid = itemView.findViewById(R.id.tv_product_count);
            date = itemView.findViewById(R.id.tv_time);
            cvReOrder = itemView.findViewById(R.id.cv_reOrder);
            cvRateOrder = itemView.findViewById(R.id.cv_rate_order);
        }
    }

}
