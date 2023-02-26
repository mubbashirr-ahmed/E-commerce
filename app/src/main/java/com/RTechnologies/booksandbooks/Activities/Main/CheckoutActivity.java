package com.RTechnologies.booksandbooks.Activities.Main;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.RTechnologies.booksandbooks.Adapters.Recycler.ProductListAdapter;
import com.RTechnologies.booksandbooks.Models.Cartitem;
import com.RTechnologies.booksandbooks.R;
import com.RTechnologies.booksandbooks.databinding.ActivityCheckoutBinding;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CheckoutActivity extends AppCompatActivity {

    Context context = this;
    ActivityCheckoutBinding binding;
    ProductListAdapter adapter;
    EditText  address, et_contact, et_email;
    TextView name, finalprice;
    Button send;
    private String Email,Name , Address ,contact;
    int userId;
    double peritemprice = 0;
    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_NAME = "name";
    SharedPreferences preferences;
    Dialogox db;
    String fetchCartinfo="http://192.168.18.99/api/fetchcart.php";
    String generateOrderID = "http://192.168.18.99/api/generateOrder1st.php";
    String itemnameURL = "http://192.168.18.99/api/fetchItemId.php";
    String clearCart = "http://192.168.18.99/api/clearCart.php";
    String fetchAccountUrl ="http://192.168.18.99/api/Account.php";
    String saveOrderURL ="http://192.168.18.99/api/savingOrder.php";
    static int numitem, orderid, ItemId;
    double tprice;
    ArrayList<String> itemnames;
    ArrayList<Cartitem> cartItems;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCheckoutBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Intent intent = getIntent();
        userId = intent.getIntExtra("com.RTechnologies.booksandbooks.Fragments.userId", 0);
        tprice = intent.getDoubleExtra("com.RTechnologies.booksandbooks.Fragments.tprice", 0);
        findviews();
        db = new Dialogox();
        getCartItems();
        binding.tvFinalPrice.setText(String.valueOf(tprice));
        binding.tvTotalPrice.setText(String.valueOf(tprice));

        if(binding.cbPrice.callOnClick())
        {
            tprice = tprice+139;
            binding.tvShippingFee.setText("139");
            binding.tvFinalPrice2.setText(String.valueOf(tprice));
        }
        else
        {
            binding.tvFinalPrice2.setText(String.valueOf(tprice));
            binding.tvShippingFee.setText("0");
        }
        fetchuserDetails();
        send.setOnClickListener(view -> {
            fetchOrderId();
            uploadtoAPI();
            db.show(getSupportFragmentManager(), "Confirmation");
            finish();
        });
    }
    private void clearCart() {
        StringRequest request = new StringRequest(Request.Method.POST, clearCart, response -> {
            Toast.makeText(context, "Thanks", Toast.LENGTH_SHORT).show();
        }, error -> {})
        {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("userId", String.valueOf(userId));
                return map;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(request);
    }
    private void uploadtoAPI() {
        for (int i = 0; i<itemnames.size();i++)
        {
            String IN = itemnames.get(i);
            getItemID(IN);
        }
        itemnames.clear();
        clearCart();
    }
    private void saveOrder(int IID) {
        String thisname = name.getText().toString().trim();
        String thisaddress = address.getText().toString().trim();
        String thiscontact = et_contact.getText().toString().trim();
        StringRequest request = new StringRequest(Request.Method.POST, saveOrderURL, response -> {

        }, error -> {})
        {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("orderId", String.valueOf(orderid));
                map.put("itemId", String.valueOf(IID));
                map.put("quantity", String.valueOf(3));
                map.put("fullName", thisname);
                map.put("address", thisaddress);
                map.put("contactNumber", thiscontact);
                map.put("totalPrice", String.valueOf(90));
                return map;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(request);
    }
    private void getItemID(String IN) {
        StringRequest request = new StringRequest(Request.Method.POST, itemnameURL, response -> {
            try {
                JSONArray array = new JSONArray(response);
                JSONObject jsonObject = array.getJSONObject(0);
                ItemId = jsonObject.getInt("itemId");
                saveOrder(ItemId);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        },error -> {})
        {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("itemName", IN);
                return map;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(request);
    }
    private void fetchuserDetails() {
        StringRequest request = new StringRequest(Request.Method.POST, fetchAccountUrl, response -> {

            try {
                JSONArray array = new JSONArray(response);

                for (int i = 0; i < array.length(); i++)
                {
                    JSONObject object = array.getJSONObject(i);
                    Name = object.getString("fullName");
                    Address = object.getString("streetAddress");
                    contact = object.getString("contactNo");
                    name.setText(Name);
                    et_email.setText(Email);
                    address.setText(Address);
                    et_contact.setText(contact);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {

        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("userId", String.valueOf(userId));
                return map;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(request);
    }
    private void fetchOrderId() {
        StringRequest request = new StringRequest(Request.Method.POST, generateOrderID, response -> {
            try {
                JSONArray jsonArray = new JSONArray(response);
                for(int i =0;i<jsonArray.length();i++)
                {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    orderid  = jsonObject.getInt("orderId");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("userId", String.valueOf(userId));
                map.put("numOfItems", String.valueOf(numitem));
                return map;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(request);
    }
    private void getCartItems() {
        cartItems = new ArrayList<>();
        StringRequest request = new StringRequest(Request.Method.POST, fetchCartinfo, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONArray array = new JSONArray(response);
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);
                        String itemName = object.getString("itemName");
                        itemnames.add(itemName);
                        double price = object.getDouble("price");
                        peritemprice = peritemprice + price;
                        String img = object.getString("img");
                        String addDate = object.getString("addDate");
                        int quantity = object.getInt("Quantity");
                        String description = object.getString("description");
                        Cartitem cartitem = new Cartitem(itemName, img, quantity, price, addDate, description);
                        cartItems.add(cartitem);
                    }
                    setAdapter();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("userId", String.valueOf(userId));
                return map;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(request);
    }
    private void findviews() {
        preferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        Email = preferences.getString(KEY_NAME, null);
        itemnames = new ArrayList<>();
        name = findViewById(R.id.tv_name);
        address = findViewById(R.id.eet_address);
        et_contact = findViewById(R.id.et_phone);
        finalprice = findViewById(R.id.tv_final_price_2);
        send = findViewById(R.id.btn_send);
        et_email = findViewById(R.id.eet_email);

    }
    private void setAdapter() {
        binding.rvItemsList.setLayoutManager(new LinearLayoutManager(context));
        numitem = cartItems.size();
        binding.tvFinalPrice.setText(String.valueOf(peritemprice));
        adapter = new ProductListAdapter(context, cartItems);
        binding.rvItemsList.setAdapter(adapter);
    }

}