package com.RTechnologies.booksandbooks.Activities.Main;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.RTechnologies.booksandbooks.R;
import com.RTechnologies.booksandbooks.databinding.ActivityChangePasswordBinding;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class ChangePasswordActivity extends AppCompatActivity {

    Context context = this;
    ActivityChangePasswordBinding binding;
    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_NAME = "name";
    SharedPreferences preferences;
    EditText oldpassword;
    EditText newpassword;
    EditText confirmpassword;
    String emailAddress;

    String oldPassword , newPassword , confirmPassword;
    String url="http://192.168.18.99/api/updateCredentials.php";

    Button update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChangePasswordBinding.inflate(getLayoutInflater());

        preferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);

        emailAddress = preferences.getString(KEY_NAME, null);
        setContentView(binding.getRoot());
        oldpassword=findViewById(R.id.et_old_password);
        newpassword=findViewById(R.id.et_new_pwd);
        confirmpassword=findViewById(R.id.et_cnfrm_pwd);
        update=findViewById(R.id.btn_change_pwd);
        initUI();
    }

    private void initUI() {
        clickListeners();
    }

    private void clickListeners() {
        binding.toolbar.setNavigationOnClickListener(view -> onBackPressed());

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                oldPassword=oldpassword.getText().toString();
                newPassword=newpassword.getText().toString();
                confirmPassword=confirmpassword.getText().toString();

                if(newPassword.equals(confirmPassword)){

                    StringRequest request=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            if(response.equals("Updated")){
                                Toast.makeText(ChangePasswordActivity.this , "Update Successfully" , Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(ChangePasswordActivity.this , HomeActivity.class));
                            }
                            else if(response.equals("Failed")){
                                Toast.makeText(ChangePasswordActivity.this , "Update Failed" , Toast.LENGTH_SHORT).show();
                            }
                            else if(response.equals("Wrong")){
                                Toast.makeText(ChangePasswordActivity.this , "Old password incorrect" , Toast.LENGTH_SHORT).show();
                            }
                            else{

                            }

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }){
                        @Nullable
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> map=new HashMap<>();
                            map.put("oldpassword" , oldPassword);
                            map.put("newpassword" , newPassword);
                            map.put("emailAddress" ,emailAddress);
                            return  map;
                        }
                    };

                    RequestQueue queue= Volley.newRequestQueue(getApplicationContext());
                    queue.add(request);
                }
                else{
                    Toast.makeText(ChangePasswordActivity.this , "Confirm Passwords Doesn't match" ,Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}