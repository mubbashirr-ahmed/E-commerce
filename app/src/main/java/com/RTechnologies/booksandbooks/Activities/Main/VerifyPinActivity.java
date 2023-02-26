package com.RTechnologies.booksandbooks.Activities.Main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.RTechnologies.booksandbooks.R;
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

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class VerifyPinActivity extends AppCompatActivity {
    String fullName, emailAddress, password;

    String otp;
    long contactNo;
    PinView userotp;
    Button btnDone;
    String url="http://192.168.18.99/api/updateContact.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_pin);
        Intent intent = getIntent();
        emailAddress = intent.getStringExtra("emailAddress");
        contactNo = intent.getLongExtra("contactNo", 0);
        System.out.println("Cotact no is"+contactNo);
        otp = intent.getStringExtra("otp");
        userotp = findViewById(R.id.pinView);

        btnDone=findViewById(R.id.btn_done);

        btnDone.setOnClickListener(new View.OnClickListener() {
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
                                        Toast.makeText(VerifyPinActivity.this, "Verified", Toast.LENGTH_SHORT).show();
                                        UpdatetoDB();
                                        startActivity(new Intent(VerifyPinActivity.this , HomeActivity.class));

                                    } else {
                                        Toast.makeText(VerifyPinActivity.this, "Invalid PIN...", Toast.LENGTH_SHORT).show();
                                    }
                                }

                            });
                }
            }
        });
    }

    private void UpdatetoDB() {

        System.out.println(emailAddress + contactNo);

        StringRequest request=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println(response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map=new HashMap<>();
                map.put("emailAddress" , emailAddress);
                map.put("contactNo", String.valueOf(contactNo));

                return map;
            }
        };

        RequestQueue queue= Volley.newRequestQueue(getApplicationContext());
        queue.add(request);
    }
}