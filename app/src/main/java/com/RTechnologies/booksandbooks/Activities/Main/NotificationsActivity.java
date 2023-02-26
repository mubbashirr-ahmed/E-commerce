package com.RTechnologies.booksandbooks.Activities.Main;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.RTechnologies.booksandbooks.Adapters.Recycler.NotificationsAdapter;
import com.RTechnologies.booksandbooks.databinding.ActivityNotificationsBinding;

public class NotificationsActivity extends AppCompatActivity {

    Context context = this;
    ActivityNotificationsBinding binding;
    NotificationsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNotificationsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



        initUI();
    }

    private void initUI() {
        clickListeners();
        setAdapter();
    }

    private void setAdapter() {
        binding.rvNotifications.setLayoutManager(new LinearLayoutManager(context));
        adapter = new NotificationsAdapter(context, null);
        binding.rvNotifications.setAdapter(adapter);
    }

    private void clickListeners() {
        binding.toolbar.setNavigationOnClickListener(view -> onBackPressed());
    }
}