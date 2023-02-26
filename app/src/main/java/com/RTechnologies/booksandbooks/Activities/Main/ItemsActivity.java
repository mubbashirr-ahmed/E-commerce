package com.RTechnologies.booksandbooks.Activities.Main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.os.Bundle;

import com.RTechnologies.booksandbooks.Adapters.Recycler.SubItemsAdapter;
import com.RTechnologies.booksandbooks.R;
import com.RTechnologies.booksandbooks.databinding.ActivityItemsBinding;

public class ItemsActivity extends AppCompatActivity {

    Context context = this;
    ActivityItemsBinding binding;
    SubItemsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityItemsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setAdapter();
    }

    private void setAdapter() {
        binding.rvItems.setLayoutManager(new GridLayoutManager(context, 2));
        adapter = new SubItemsAdapter(context, null);
        binding.rvItems.setAdapter(adapter);
    }
}