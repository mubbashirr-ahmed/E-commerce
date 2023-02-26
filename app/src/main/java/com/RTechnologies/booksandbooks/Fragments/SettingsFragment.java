package com.RTechnologies.booksandbooks.Fragments;

import android.app.ActionBar;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.RTechnologies.booksandbooks.Activities.Main.ChangeNumberActivity;
import com.RTechnologies.booksandbooks.Activities.Main.ChangePasswordActivity;
import com.RTechnologies.booksandbooks.Activities.Main.HelpActivity;
import com.RTechnologies.booksandbooks.Activities.Main.OrderHistoryActivity;
import com.RTechnologies.booksandbooks.Activities.StartUp.LoginActivity;
import com.RTechnologies.booksandbooks.databinding.FragmentSettingsBinding;
import com.RTechnologies.booksandbooks.databinding.LogoutDialogBinding;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;

public class SettingsFragment extends Fragment {

    Context context;
    FragmentSettingsBinding binding;

    String emailAddress;
    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_NAME = "name";
    SharedPreferences.Editor editor;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSettingsBinding.inflate(getLayoutInflater(), container, false);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        emailAddress=sharedPreferences.getString(KEY_NAME , null);
        System.out.println("EA ="+emailAddress);
         editor = sharedPreferences.edit();

        initUI();

        return binding.getRoot();
    }

    private void initUI() {
        context = getContext();
        clickListeners();
    }

    private void clickListeners() {
        binding.llHelp.setOnClickListener(view -> startActivity(new Intent(context, HelpActivity.class)));

        binding.llChangePassword.setOnClickListener(view -> startActivity(new Intent(context, ChangePasswordActivity.class)));

        binding.llChangeNumber.setOnClickListener(view -> startActivity(new Intent(context , ChangeNumberActivity.class)));

        binding.llOrderHistory.setOnClickListener(view -> startActivity(new Intent(context, OrderHistoryActivity.class)));

        binding.llLogout.setOnClickListener(view -> showLogoutDialog());
    }

    private void showLogoutDialog() {
        Dialog dialog = new Dialog(context);
        LogoutDialogBinding binding = LogoutDialogBinding.inflate(LayoutInflater.from(getContext()));
        dialog.setContentView(binding.getRoot());
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setLayout(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

        binding.cvClose.setOnClickListener(view -> dialog.dismiss());
        binding.btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                editor.clear();
                editor.commit();
                startActivity(new Intent(context , LoginActivity.class));

            }
        });
    }
}