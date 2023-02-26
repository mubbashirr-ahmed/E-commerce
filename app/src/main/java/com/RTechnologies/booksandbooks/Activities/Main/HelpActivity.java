package com.RTechnologies.booksandbooks.Activities.Main;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.RTechnologies.booksandbooks.databinding.ActivityHelpBinding;

public class HelpActivity extends AppCompatActivity {

    Context context = this;
    ActivityHelpBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHelpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initUI();
    }

    private void initUI() {
        clickListeners();
    }

    private void clickListeners() {
        binding.toolbar.setOnClickListener(view -> onBackPressed());
    }
}