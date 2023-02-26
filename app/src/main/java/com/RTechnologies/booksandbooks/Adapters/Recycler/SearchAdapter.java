package com.RTechnologies.booksandbooks.Adapters.Recycler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.RTechnologies.booksandbooks.R;

import java.util.ArrayList;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.VH> {

    Context context;
    ArrayList<String> arrayList;

    public SearchAdapter(Context context, ArrayList<String> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_search, parent, false);
        return new VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {

        holder.search.setText(arrayList.get(position));
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class VH extends RecyclerView.ViewHolder {

        CardView cvReOrder, cvRateOrder;
        TextView search;


        public VH(@NonNull View itemView) {
            super(itemView);

            cvReOrder = itemView.findViewById(R.id.cv_reOrder);
            cvRateOrder = itemView.findViewById(R.id.cv_rate_order);
            search = itemView.findViewById(R.id.tv_product_names);

        }
    }

}
