package com.RTechnologies.booksandbooks.Activities.StartUp;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.RTechnologies.booksandbooks.databinding.ActivityForgotPasswordBinding;

public class ForgotPasswordActivity extends AppCompatActivity {

    Context context;
    ActivityForgotPasswordBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityForgotPasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initUI();
    }

    private void initUI() {
        context = ForgotPasswordActivity.this;
        clickListeners();
    }

    private void clickListeners() {
        binding.cvBack.setOnClickListener(view -> onBackPressed());
    }
}