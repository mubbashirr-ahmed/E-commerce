package com.RTechnologies.booksandbooks.Adapters.Tabs;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.RTechnologies.booksandbooks.Fragments.AccountFragment;
import com.RTechnologies.booksandbooks.Fragments.CartFragment;
import com.RTechnologies.booksandbooks.Fragments.HomeFragment;
import com.RTechnologies.booksandbooks.Fragments.SettingsFragment;

@SuppressWarnings("deprecation")
public class HomeTabsAdapter extends FragmentPagerAdapter {

    public HomeTabsAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int i) {

        switch (i) {
            case 0:
                return new HomeFragment();
            case 1:
                return new AccountFragment();
            case 2:
                return new CartFragment();
            case 3:
                return new SettingsFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "home";
            case 1:
                return "account";
            case 2:
                return "cart";
            case 3:
                return "settings";
            default:
                return null;
        }
    }
}
