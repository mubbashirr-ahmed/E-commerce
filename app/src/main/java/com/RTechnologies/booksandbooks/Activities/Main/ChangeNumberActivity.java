
package com.RTechnologies.booksandbooks.Activities.Main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class ChangeNumberActivity extends AppCompatActivity {

    EditText email;
    EditText pass;
    EditText phonenumber;
    Button changeNumber;

    String emailAddress , password ;
            Long phnNum;

    String url="http://192.168.18.99/api/checkEmailpass.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_number);

        email=findViewById(R.id.eet_email);
        pass=findViewById(R.id.et_pwd);
        phonenumber=findViewById(R.id.et_new_number);
        changeNumber=findViewById(R.id.btn_change_number);

        changeNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                emailAddress=email.getText().toString();
                password=pass.getText().toString();
                phnNum= Long.parseLong(phonenumber.getText().toString());

                if(emailAddress.isEmpty() || password.isEmpty() || phnNum== null){
                    Toast.makeText(ChangeNumberActivity.this , "" , Toast.LENGTH_SHORT).show();
                }
                else{
                    checkCredentials();
                }
            }
        });
    }

    private void checkCredentials() {

        StringRequest request=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println(response);
                if(response.equals("ok")){

                    SendOtp();

                }
                else{
                    Toast.makeText(getApplicationContext(), "Error" , Toast.LENGTH_SHORT).show();
                }
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
                map.put("password" , password);

                return map;
            }
        };

        RequestQueue queue= Volley.newRequestQueue(getApplicationContext());
        queue.add(request);

    }

    private void SendOtp() {

        if (phonenumber.getText().toString().trim().length() == 10) {
//                        if(phone.getText().toString().trim().startsWith(String.valueOf(3))){

            PhoneAuthProvider.getInstance().verifyPhoneNumber(
                    "+92" + phnNum, 60, TimeUnit.SECONDS,
                    ChangeNumberActivity.this,
                    new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                        @Override
                        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                            Toast.makeText(ChangeNumberActivity.this , "Code Sent to "+phnNum , Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onVerificationFailed(@NonNull FirebaseException e) {

                            e.printStackTrace();

                        }

                        @Override
                        public void onCodeSent(@NonNull String otp, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                            startActivity(new Intent(getApplicationContext(), VerifyPinActivity.class)

                                    .putExtra("emailAddress", emailAddress)
                                    .putExtra("contactNo", phnNum)
                                    .putExtra("otp", otp)
                            );
                        }
                    }
            );

        }
    }


}