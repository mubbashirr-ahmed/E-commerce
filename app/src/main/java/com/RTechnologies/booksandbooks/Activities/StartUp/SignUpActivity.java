package com.RTechnologies.booksandbooks.Activities.StartUp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.RTechnologies.booksandbooks.Activities.Main.Dialogbox;
import com.RTechnologies.booksandbooks.Activities.Main.HomeActivity;
import com.RTechnologies.booksandbooks.R;
import com.RTechnologies.booksandbooks.Utils.Functions;
import com.RTechnologies.booksandbooks.databinding.ActivitySignUpBinding;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@SuppressLint("ClickableViewAccessibility")
public class SignUpActivity extends AppCompatActivity implements Dialogbox.ExampleDialogListener{

    Context context;
    ActivitySignUpBinding binding;
    boolean isDrawableClicked = true, isDrawableClicked1 = true;

    EditText name, email, phone, pass, confirmpass;

    String fullName, emailAddress, password, confirmPassword;
    String authURL="http://192.168.18.99/api/authenticate.php";

    long contactNo;
    GoogleSignInClient googleSignInClient;
    FirebaseAuth firebaseAuth;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    GoogleSignInAccount account;

    private static final String SHARED_PREF_NAME="mypref";
    private static final String KEY_NAME="name";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        name = findViewById(R.id.eet_name);
        email = findViewById(R.id.eet_email);
        phone = findViewById(R.id.et_phone);
        pass = findViewById(R.id.et_pwd);
        confirmpass = findViewById(R.id.et_cnfrm_pwd);

        firebaseAuth=FirebaseAuth.getInstance();
        googleRequest();

        preferences=getSharedPreferences(SHARED_PREF_NAME , MODE_PRIVATE);



        initUI();
    }

    private void initUI() {
        context = SignUpActivity.this;
        Functions.changeStatusBarColor(context, R.color.purple_500);
        clickListeners();
    }

    private void googleRequest() {
        GoogleSignInOptions gso=new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail().build();
        googleSignInClient=GoogleSignIn.getClient(this, gso);

    }

    private void clickListeners() {
        binding.etPwd.setOnTouchListener((v, event) -> {
            final int DRAWABLE_RIGHT = 2;

            if (event.getAction() == MotionEvent.ACTION_UP) {
                if (event.getRawX() >= (binding.etPwd.getRight() - binding.etPwd.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {

                    if (!isDrawableClicked) {
                        binding.etPwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                        binding.etPwd.setCompoundDrawablesWithIntrinsicBounds(null, null, ContextCompat.getDrawable(context, R.drawable.ic_eye_disabled), null);
                        isDrawableClicked = true;

                    } else {
                        binding.etPwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                        binding.etPwd.setCompoundDrawablesWithIntrinsicBounds(null, null, ContextCompat.getDrawable(context, R.drawable.ic_eye_enabled), null);
                        isDrawableClicked = false;
                    }

                    return true;
                }
            }

            return false;
        });

        binding.etCnfrmPwd.setOnTouchListener((v, event) -> {
            final int DRAWABLE_RIGHT = 2;

            if (event.getAction() == MotionEvent.ACTION_UP) {
                if (event.getRawX() >= (binding.etCnfrmPwd.getRight() - binding.etCnfrmPwd.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {

                    if (!isDrawableClicked1) {
                        binding.etCnfrmPwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                        binding.etCnfrmPwd.setCompoundDrawablesWithIntrinsicBounds(null, null, ContextCompat.getDrawable(context, R.drawable.ic_eye_disabled), null);
                        isDrawableClicked1 = true;

                    } else {
                        binding.etCnfrmPwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                        binding.etCnfrmPwd.setCompoundDrawablesWithIntrinsicBounds(null, null, ContextCompat.getDrawable(context, R.drawable.ic_eye_enabled), null);
                        isDrawableClicked1 = false;
                    }

                    return true;
                }
            }

            return false;
        });

        binding.llSignIn.setOnClickListener(view -> startActivity(new Intent(context, SignUpActivity.class)));
        binding.btnGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    processLogin();
            }
        });

        binding.btnSigup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                fullName = name.getText().toString();
                emailAddress = email.getText().toString();
                password = pass.getText().toString();
                confirmPassword = confirmpass.getText().toString();
                contactNo = Long.parseLong(phone.getText().toString());

                if (!password.equals(confirmPassword)) {
                    Toast.makeText(SignUpActivity.this, "Password does not match!", Toast.LENGTH_SHORT).show();
                } else {
                    if (phone.getText().toString().trim().length() == 10) {
//                        if(phone.getText().toString().trim().startsWith(String.valueOf(3))){

                        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                                "+92" + contactNo, 60, TimeUnit.SECONDS,
                                SignUpActivity.this,
                                new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                                    @Override
                                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                                    }

                                    @Override
                                    public void onVerificationFailed(@NonNull FirebaseException e) {

                                        e.printStackTrace();

                                    }

                                    @Override
                                    public void onCodeSent(@NonNull String otp, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                        startActivity(new Intent(context, CodeVerificationActivity.class)
                                                .putExtra("fullName", fullName)
                                                .putExtra("emailAddress", emailAddress)
                                                .putExtra("password", password)
                                                .putExtra("contactNo", contactNo)
                                                .putExtra("otp", otp)
                                        );
                                    }
                                }
                        );




//                        }
                    } else {
                        Toast.makeText(SignUpActivity.this, "Invalid Phone Number format", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
        private void processLogin() {
        Intent sigInIntent=googleSignInClient.getSignInIntent();
        startActivityForResult(sigInIntent , 101);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 101){
            Task<GoogleSignInAccount> task= GoogleSignIn.getSignedInAccountFromIntent(data);

            try {
                 account=task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext() , "Error getting information with google." , Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
//
        AuthCredential credential= GoogleAuthProvider.getCredential(idToken , null);
//
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {

                            GoogleSignInAccount account= GoogleSignIn.getLastSignedInAccount(getApplicationContext());
                            FirebaseUser user = firebaseAuth.getCurrentUser();

                            if (user != null) {
                                emailAddress = account.getEmail();
                                fullName = user.getDisplayName();

                                authenticateEmail(emailAddress);

                            }
                            startActivity(new Intent(SignUpActivity.this, CreateAccountActivity.class)
                                    .putExtra("fullName", fullName)
                                    .putExtra("emailAddress", emailAddress)
                                    .putExtra("password" , password)
                            );
                            finish();
                        }
                        else{
                            Toast.makeText(getApplicationContext() , "Problem with Signing" , Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    private void authenticateEmail(String emailAddress) {

        StringRequest request = new StringRequest(Request.Method.POST, authURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if(response.equals("exist")){
                    editor=preferences.edit();
                    editor.putString(KEY_NAME, emailAddress);
                    editor.commit();
                    startActivity(new Intent(SignUpActivity.this , HomeActivity.class));
                    finish();
                }
                else{
                    firebaseAuthWithGoogle(account.getIdToken());
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

                return map;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(request);

    }

    @Override
    public void applyTexts(String pass) {
        password=pass;
    }
}