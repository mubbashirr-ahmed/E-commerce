package com.RTechnologies.booksandbooks.Activities.StartUp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.RTechnologies.booksandbooks.Activities.Main.Dialogbox;
import com.RTechnologies.booksandbooks.R;
import com.RTechnologies.booksandbooks.databinding.ActivityCreateAccountBinding;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("deprecation")
public class CreateAccountActivity extends AppCompatActivity implements Dialogbox.ExampleDialogListener{

    Context context;
    ActivityCreateAccountBinding binding;
    EditText country;
    EditText city;
    EditText homeAddress;
    EditText officeAddress;

    String Country , City , HomeAddress , OfficeAddress , email , fullname , contactNo;
    String password;


    String registerurl = "http://192.168.18.99/api/register.php";

    Uri photourl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreateAccountBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Intent intent=getIntent();
        email=intent.getStringExtra("emailAddress");
        password=intent.getStringExtra("password");
        fullname=intent.getStringExtra("fullName");
//        contactNo=intent.getStringExtra("contactNo");
        if(password == null) {
            Dialogbox dialogbox = new Dialogbox();
            dialogbox.show(getSupportFragmentManager(), "Dialog Box");
        }
        contactNo="0";

        System.out.println(email + password + fullname + contactNo);

        country=findViewById(R.id.eet_country);
        city=findViewById(R.id.eet_city);
        homeAddress=findViewById(R.id.et_home_address);
        officeAddress=findViewById(R.id.et_office_address);


        initUI();
    }

    private void initUI() {
        context = CreateAccountActivity.this;
        clickListeners();
    }

    private void clickListeners() {
//        binding.etHomeAddress.setOnClickListener(view -> {
//            Intent i = new Intent(context, MapsActivity.class);
//            startActivityForResult(i, 1);
//        });
//
//        binding.etOfficeAddress.setOnClickListener(view -> {
//            Intent i = new Intent(context, MapsActivity.class);
//            startActivityForResult(i, 1);
//        });

        binding.btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Country=country.getText().toString();
                City=city.getText().toString();
                HomeAddress=homeAddress.getText().toString();
                OfficeAddress=officeAddress.getText().toString();

                if(Country.isEmpty() || City.isEmpty() || HomeAddress.isEmpty() || OfficeAddress.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Fill All Credentials" , Toast.LENGTH_SHORT).show();
                }
                else{
                    AddtodB();
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
                    startActivity(new Intent(CreateAccountActivity.this, LoginActivity.class));
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

                System.out.println(password);
                map.put("emailAddress", email);
                map.put("password", password);
                map.put("fullName", fullname);
                map.put("contactNo", String.valueOf(contactNo));
                map.put("Country", Country);
                map.put("City", City);
                map.put("HomeAddress", HomeAddress);
                map.put("OfficeAddress", OfficeAddress);





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