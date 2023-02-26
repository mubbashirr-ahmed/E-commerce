package com.RTechnologies.booksandbooks.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.RTechnologies.booksandbooks.Activities.Main.HomeActivity;
import com.RTechnologies.booksandbooks.R;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AccountFragment extends Fragment {

    EditText name;
    Context context;
    EditText email;
    EditText city;
    EditText country;
    EditText address;
    Button update;
    int userId;

    String fetchuserIdURL = "http://192.168.18.99/api/fetchUserId.php";
    String fetchaccountDetails = "http://192.168.18.99/api/Account.php";
    String accoutURL ="http://192.168.18.99/api/Accountupdate.php";
    String eName, eCity, eCounty, eAddress;
    String Name, Email, City, Address, County;
    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_NAME = "name";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        Email = sharedPreferences.getString(KEY_NAME, null);
        name = view.findViewById(R.id.eet_name);
        email = view.findViewById(R.id.eet_email);
        city = view.findViewById(R.id.eet_city);
        country = view.findViewById(R.id.eet_country);
        address = view.findViewById(R.id.eet_address);
        update = view.findViewById(R.id.btn_save);
        context = getContext();
        fetchUserId();
        update.setOnClickListener(v-> clickListner());
        return view;
    }
    private void fetchaccountdetails(int UID) {
        StringRequest request = new StringRequest(Request.Method.POST, fetchaccountDetails, response -> {
            try {
                JSONArray array = new JSONArray(response);
                JSONObject object = array.getJSONObject(0);
                eName = object.getString("fullName");
                eCity = object.getString("city");
                eCounty = object.getString("Country");
                eAddress = object.getString("streetAddress");
                name.setText(eName);
                city.setText(eCity);
                country.setText(eCounty);
                address.setText(eAddress);
                email.setText(Email);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {

        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("userId", String.valueOf(UID));
                return map;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(request);
    }

    private void fetchUserId() {
        StringRequest request = new StringRequest(Request.Method.POST, fetchuserIdURL, response -> {

            try {
                JSONArray array = new JSONArray(response);
                JSONObject object = array.getJSONObject(0);
                userId = object.getInt("userId");
                fetchaccountdetails(userId);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {

        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("emailAddress", Email);
                return map;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(request);
    }

    private void clickListner() {
        Name = name.getText().toString();
        City = city.getText().toString();
        County = country.getText().toString();
        Address = address.getText().toString();

        if (Name.equals(eName) && City.equals(eCity) && County.contentEquals(eCounty) && Address.contentEquals(eAddress)) {
            Toast.makeText(getContext(), "Record Update as previous", Toast.LENGTH_SHORT).show();
        }
        else
        {
            StringRequest request = new StringRequest(Request.Method.POST, accoutURL, response -> {

                if (response.equals("Updated")) {
                    Toast.makeText(getContext(), "Updated", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), response, Toast.LENGTH_SHORT).show();
                }
            }, error -> {}) {
                @Nullable
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> map = new HashMap<>();
                    map.put("emailAddress", Email);
                    map.put("fullName", name.getText().toString());
                    map.put("city", city.getText().toString());
                    map.put("Country", country.getText().toString());
                    map.put("streetAddress", address.getText().toString());
                    return map;
                }
            };
            RequestQueue queue = Volley.newRequestQueue(context);
            queue.add(request);
        }
        startActivity(new Intent(context, HomeActivity.class));
    }
}