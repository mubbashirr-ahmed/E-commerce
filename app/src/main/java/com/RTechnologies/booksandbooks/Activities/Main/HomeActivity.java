package com.RTechnologies.booksandbooks.Activities.Main;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.viewpager.widget.ViewPager;

import com.RTechnologies.booksandbooks.Adapters.Tabs.HomeTabsAdapter;
import com.RTechnologies.booksandbooks.R;
import com.RTechnologies.booksandbooks.databinding.ActivityHomeBinding;
import com.RTechnologies.booksandbooks.databinding.FragmentHomeBinding;

@SuppressWarnings("deprecation")
public class HomeActivity extends AppCompatActivity {

    Context context;
    ActivityHomeBinding binding;
    FragmentHomeBinding fragmentHomeBinding;
    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_NAME = "name";
    SharedPreferences preferences;
    ActionBarDrawerToggle toggle;
    HomeTabsAdapter adapter;
    ImageView profilepic;


    String emailAddress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        preferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);

//        FirebaseUser user=firebaseAuth.getCurrentUser();
        //updateUI(user);
//        if(user!=null) {
//            GoogleSignInAccount account= GoogleSignIn.getLastSignedInAccount(this);
//            System.out.println("Email address is 1 "+emailAddress);
//            if (account != null) {
//                emailAddress = account.getEmail();
//                editor=preferences.edit();
//                editor.putString(KEY_NAME, emailAddress);
//                editor.commit();
//            }
//        }else {

        emailAddress = preferences.getString(KEY_NAME, null);
        profilepic = findViewById(R.id.iv_dp);
//        }

        System.out.println("Email address is " + emailAddress);

        initUI();
    }

    @Override
    protected void onStart() {
        super.onStart();
        initUI();
    }

    private void initUI() {
        context = HomeActivity.this;
        setNavigationDrawer();
        setViewPagerAdapter();
        setBottomNavigation();
        clickListeners();
    }

    private void clickListeners() {
        binding.cvNotification.setOnClickListener(view -> startActivity(new Intent(context, NotificationsActivity.class)));
    }

    private void setNavigationDrawer() {
        toggle = new ActionBarDrawerToggle(this, binding.drawerLayout, binding.toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        binding.drawerLayout.addDrawerListener(toggle);
        toggle.setDrawerIndicatorEnabled(false);
        Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_menu, getTheme());
        toggle.setHomeAsUpIndicator(drawable);
        toggle.syncState();
    }

    private void setViewPagerAdapter() {
        binding.viewPager.setOffscreenPageLimit(4);
        adapter = new HomeTabsAdapter(getSupportFragmentManager());
        binding.viewPager.setAdapter(adapter);
    }

    private void setBottomNavigation() {
        binding.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    binding.bottomNavigationView.getMenu().findItem(R.id.menu_home).setChecked(true);
                    binding.viewPager.setCurrentItem(0);

                } else if (position == 1) {
                    binding.bottomNavigationView.getMenu().findItem(R.id.menu_account).setChecked(true);
                    binding.viewPager.setCurrentItem(1);

                } else if (position == 2) {
                    binding.bottomNavigationView.getMenu().findItem(R.id.menu_cart).setChecked(true);
                    binding.viewPager.setCurrentItem(2);

                } else if (position == 3) {
                    binding.bottomNavigationView.getMenu().findItem(R.id.menu_settings).setChecked(true);
                    binding.viewPager.setCurrentItem(3);
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        binding.bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            if (item.getItemId() == R.id.menu_home) {
                binding.bottomNavigationView.getMenu().findItem(R.id.menu_home).setChecked(true);
                binding.viewPager.setCurrentItem(0);

            } else if (item.getItemId() == R.id.menu_account) {
                binding.bottomNavigationView.getMenu().findItem(R.id.menu_account).setChecked(true);
                binding.viewPager.setCurrentItem(1);

            } else if (item.getItemId() == R.id.menu_cart) {
                binding.bottomNavigationView.getMenu().findItem(R.id.menu_cart).setChecked(true);
                binding.viewPager.setCurrentItem(2);

            } else if (item.getItemId() == R.id.menu_settings) {
                binding.bottomNavigationView.getMenu().findItem(R.id.menu_settings).setChecked(true);
                binding.viewPager.setCurrentItem(3);
            }

            return false;
        });
    }

    @Override
    public void onBackPressed()
    {
        if (binding.viewPager.getCurrentItem() == 0) {
            super.onBackPressed();
        } else {
            binding.viewPager.setCurrentItem(binding.viewPager.getCurrentItem() - 1);
        }
    }
}