package com.RTechnologies.booksandbooks.Activities.Main;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.RTechnologies.booksandbooks.Utils.Functions;
import com.RTechnologies.booksandbooks.databinding.ActivityRateOrderBinding;

public class RateOrderActivity extends AppCompatActivity {

    Context context = this;
    ActivityRateOrderBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRateOrderBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initUI();
    }

    private void initUI() {
        Functions.hideStatusBar(context);
        clickListeners();
    }

    private void clickListeners() {
        binding.cvBack.setOnClickListener(view -> onBackPressed());
    }
}