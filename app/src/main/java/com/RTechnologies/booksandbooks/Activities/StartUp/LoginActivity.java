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
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.RTechnologies.booksandbooks.Activities.Main.HomeActivity;
import com.RTechnologies.booksandbooks.R;
import com.RTechnologies.booksandbooks.Utils.Functions;
import com.RTechnologies.booksandbooks.databinding.ActivityLoginBinding;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.firebase.auth.FirebaseAuth;

import java.util.HashMap;
import java.util.Map;

@SuppressLint("ClickableViewAccessibility")
public class LoginActivity extends AppCompatActivity {

    Context context;
    ActivityLoginBinding binding;
    boolean isDrawableClicked = true;
    EditText email;
    EditText pass;

    String emailAddress , password;
    TextView guest;

    String loginUrl="http://192.168.18.99/api/login.php";

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    private static final String SHARED_PREF_NAME="mypref";
    private static final String KEY_NAME="name";

    GoogleSignInClient googleSignInClient;
    FirebaseAuth firebaseAuth;


//    @Override
//    protected void onStart() {
//        super.onStart();
//
////        FirebaseUser user=firebaseAuth.getCurrentUser();
//        //updateUI(user);
//        if(user!=null) {
//            GoogleSignInAccount account= GoogleSignIn.getLastSignedInAccount(this);
//            System.out.println("Email address is 1 "+emailAddress);
//                if (account != null) {
//                    emailAddress = account.getEmail();
//                    editor=preferences.edit();
//                    editor.putString(KEY_NAME, emailAddress);
//                    editor.commit();
//                }
//
//            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
//        }
//        }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
//        firebaseAuth=FirebaseAuth.getInstance();
//        googleRequest();

        preferences=getSharedPreferences(SHARED_PREF_NAME , MODE_PRIVATE);

        String name=preferences.getString(KEY_NAME , null);

        if(name != null){
            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        }
        else {

            initUI();
        }


    }

//    private void googleRequest() {
//        GoogleSignInOptions gso=new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                .requestIdToken(getString(R.string.default_web_client_id))
//                .requestEmail().build();
//        googleSignInClient=GoogleSignIn.getClient(this, gso);
//
//    }


    private void initUI() {
        context = LoginActivity.this;
        Functions.changeStatusBarColor(context, R.color.purple_500);
        email=findViewById(R.id.eet_email);
        pass=findViewById(R.id.et_pwd);
        guest=findViewById(R.id.tv_login_as_guest);
        clickListeners();
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
//
//        binding.btnGoogle.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                FirebaseUser user=firebaseAuth.getCurrentUser();
//                //updateUI(user);
//                if(user!=null) {
//                    GoogleSignInAccount account= GoogleSignIn.getLastSignedInAccount(getApplicationContext());
//                    System.out.println("Email address is 1 "+emailAddress);
//                    if (account != null) {
//                        emailAddress = account.getEmail();
//                        editor=preferences.edit();
//                        editor.putString(KEY_NAME, emailAddress);
//                        editor.commit();
//                    }
//
//                    startActivity(new Intent(LoginActivity.this, HomeActivity.class));
//                }else {
//                 processLogin();
//                }
//            }
//        });


        binding.llSignUp.setOnClickListener(view -> startActivity(new Intent(context, SignUpActivity.class)));

        binding.tvForgotPwd.setOnClickListener(view -> startActivity(new Intent(context, ForgotPasswordActivity.class)));

        guest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int userId=0;
                Intent intent=new Intent(LoginActivity.this , HomeActivity.class).putExtra("userId" , userId);

                startActivity(intent);
                finish();
            }
        });

        binding.btnSigin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                emailAddress=email.getText().toString();
                password=pass.getText().toString();

                if(emailAddress.isEmpty() || password.isEmpty()){
                    Toast.makeText(context , "Fill all credentials..." , Toast.LENGTH_SHORT).show();
                }

                else{

                    StringRequest request = new StringRequest(Request.Method.POST, loginUrl, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
//
                            if (response.equals("login_failed")) {
                                Toast.makeText(getApplicationContext(), "User not found", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getApplicationContext(), "Login Successfull", Toast.LENGTH_SHORT).show();
                                editor = preferences.edit();
                                editor.putString(KEY_NAME, emailAddress);
                                editor.commit();
                                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);

                                startActivity(intent);
                                finish();
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



                            return map;
                        }
                    };

                    RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                    queue.add(request);
                }


            }
        });
    }

//    private void processLogin() {
//        Intent sigInIntent=googleSignInClient.getSignInIntent();
//        startActivityForResult(sigInIntent , 101);
//    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if(requestCode == 101){
//            Task<GoogleSignInAccount> task=GoogleSignIn.getSignedInAccountFromIntent(data);
//
//            try {
//                GoogleSignInAccount account=task.getResult(ApiException.class);
//                firebaseAuthWithGoogle(account.getIdToken());
//            } catch (ApiException e) {
//                e.printStackTrace();
//                Toast.makeText(getApplicationContext() , "Error getting information with google." , Toast.LENGTH_SHORT).show();
//            }
//        }
//    }
//
//    private void firebaseAuthWithGoogle(String idToken) {
//
//        AuthCredential credential= GoogleAuthProvider.getCredential(idToken , null);
//
//        firebaseAuth.signInWithCredential(credential)
//                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if(task.isSuccessful()){
//                            FirebaseUser user=firebaseAuth.getCurrentUser();
//                            startActivity(new Intent(LoginActivity.this , HomeActivity.class));
//                            finish();
//                        }
//                        else{
//                            Toast.makeText(getApplicationContext() , "Problem with Signing" , Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });
//
//    }
}