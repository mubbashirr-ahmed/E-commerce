package com.RTechnologies.booksandbooks.Adapters.Recycler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.RTechnologies.booksandbooks.Models.Category;
import com.RTechnologies.booksandbooks.R;

import java.util.ArrayList;

public class SubItemsAdapter extends RecyclerView.Adapter<SubItemsAdapter.VH> {

    Context context;
    ArrayList<Category> arrayList;

    public SubItemsAdapter(Context context, ArrayList<Category> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_sub_items, parent, false);
        return new VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public static class VH extends RecyclerView.ViewHolder {

        public VH(@NonNull View itemView) {
            super(itemView);
        }
    }

}
