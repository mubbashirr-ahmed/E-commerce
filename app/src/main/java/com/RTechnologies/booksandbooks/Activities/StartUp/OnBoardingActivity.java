package com.RTechnologies.booksandbooks.Activities.StartUp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.RTechnologies.booksandbooks.Activities.Main.HomeActivity;
import com.RTechnologies.booksandbooks.Adapters.Pager.OnBoardingAdapter;
import com.RTechnologies.booksandbooks.Models.OnBoarding;
import com.RTechnologies.booksandbooks.R;
import com.RTechnologies.booksandbooks.databinding.ActivityOnBoardingBinding;

import java.util.ArrayList;

public class OnBoardingActivity extends AppCompatActivity {

    Context context;
    ActivityOnBoardingBinding binding;
    OnBoardingAdapter adapter;
    ArrayList<OnBoarding> arrayList = new ArrayList<>();
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    private static final String SHARED_PREF_NAME="mypref";
    private static final String KEY_NAME="name";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOnBoardingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        preferences=getSharedPreferences(SHARED_PREF_NAME , MODE_PRIVATE);

        String name=preferences.getString(KEY_NAME , null);

        if(name != null){
            Intent intent = new Intent(OnBoardingActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        }
        else {

            initUI();
        }

    }

    private void initUI() {
        context = OnBoardingActivity.this;
        addData();
        clickListeners();
    }

    private void clickListeners() {
        binding.btnLogin.setOnClickListener(view -> startActivity(new Intent(context, LoginActivity.class)));

        binding.btnCreateAccount.setOnClickListener(view -> startActivity(new Intent(context, SignUpActivity.class)));
    }

    private void addData() {
        arrayList.add(new OnBoarding(R.drawable.latest_outfits, "Latest Outfits", "Here you can find latest outfits."));

        arrayList.add(new OnBoarding(R.drawable.ic_easy_shopping, "Easy Shopping", "East shopping of brand clothing items."));

        arrayList.add(new OnBoarding(R.drawable.ic_quick_delivery, "Quick Delivery", "Book your product and get quick delivery"));

        adapter = new OnBoardingAdapter(arrayList, context);
        binding.viewPager.setAdapter(adapter);
        binding.circleIndicator.setViewPager(binding.viewPager);
    }
}