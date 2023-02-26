package com.RTechnologies.booksandbooks.Activities.StartUp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.RTechnologies.booksandbooks.Activities.Main.HomeActivity;
import com.RTechnologies.booksandbooks.R;
import com.RTechnologies.booksandbooks.databinding.ActivityCodeVerificationBinding;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.chaos.view.PinView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class CodeVerificationActivity extends AppCompatActivity {

    Context context;
    ActivityCodeVerificationBinding binding;
    String fullName, emailAddress, password;
    String otp;
    long contactNo;
    PinView userotp;

    String registerurl = "http://192.168.18.99/api/register.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCodeVerificationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        userotp = findViewById(R.id.pinView);

        Intent intent = getIntent();
        fullName = intent.getStringExtra("fullName");
        emailAddress = intent.getStringExtra("emailAddress");
        password = intent.getStringExtra("password");
        contactNo = intent.getLongExtra("contactNo", 0);
        otp = intent.getStringExtra("otp");

        initUI();
    }

    private void initUI() {
        context = CodeVerificationActivity.this;
        clicklistner();
    }

    private void clicklistner() {
        binding.btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!otp.isEmpty()) {
                    String userOTP = Objects.requireNonNull(userotp.getText()).toString();

                    PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(
                            otp, userOTP
                    );
                    FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(CodeVerificationActivity.this, "Verified", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(CodeVerificationActivity.this, CreateAccountActivity.class).putExtra("emailAddress", emailAddress)
                                        .putExtra("password", password)
                                                        .putExtra("fullName", fullName)
                                                        .putExtra("contactNo", String.valueOf(contactNo))


                                        );

                                    } else {
                                        Toast.makeText(CodeVerificationActivity.this, "Invalid PIN...", Toast.LENGTH_SHORT).show();
                                    }
                                }

                            });
                }
            }
        });
    }

    private void AddtodB() {
        StringRequest request = new StringRequest(Request.Method.POST, registerurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                System.out.println(response);

                if (response.equals("exist")) {
                    Toast.makeText(getApplicationContext(), "User already exist.", Toast.LENGTH_SHORT).show();
                } else if (response.equals("inserted")) {
                    Toast.makeText(getApplicationContext(), "Successfull.", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(CodeVerificationActivity.this, LoginActivity.class));
                } else {
                    Toast.makeText(getApplicationContext(), "Fail.", Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
                error.printStackTrace();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("emailAddress", emailAddress);
                map.put("password", password);
                map.put("fullName", fullName);
                map.put("contactNo", String.valueOf(contactNo));


                return map;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(request);
    }


}