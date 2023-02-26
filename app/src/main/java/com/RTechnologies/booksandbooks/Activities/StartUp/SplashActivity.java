package com.RTechnologies.booksandbooks.Activities.StartUp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import com.RTechnologies.booksandbooks.R;
import com.RTechnologies.booksandbooks.Utils.Functions;

@SuppressLint("CustomSplashScreen")
public class SplashActivity extends AppCompatActivity {

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        initUI();
    }

    private void initUI() {
        context = SplashActivity.this;
        Functions.hideSystemUI(context);

        new Handler(Looper.myLooper()).postDelayed(() -> {
            finish();
            startActivity(new Intent(context, OnBoardingActivity.class)
                    .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));
        }, 1000);
    }
}