package com.RTechnologies.booksandbooks.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.RTechnologies.booksandbooks.Activities.Main.CheckoutActivity;
import com.RTechnologies.booksandbooks.Adapters.Recycler.CartAdapter;
import com.RTechnologies.booksandbooks.Models.Cartitem;
import com.RTechnologies.booksandbooks.databinding.FragmentCartBinding;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CartFragment extends Fragment {

    Context context;
    FragmentCartBinding binding;
    CartAdapter adapter;
    ArrayList<Cartitem> cartItems;


    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_NAME = "name";
    double tprice ;

    String Email;
    int userId;

    String fetchuserIdURL="http://192.168.18.99/api/fetchUserId.php";
    String fetchCartinfo="http://192.168.18.99/api/fetchcart.php";


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCartBinding.inflate(getLayoutInflater(), container, false);
        tprice=0;

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        Email=sharedPreferences.getString(KEY_NAME , null);
        initUI();

        binding.btnCheckOut.setOnClickListener(view->{
           Intent intent = new Intent(context, CheckoutActivity.class);
           intent.putExtra("com.RTechnologies.booksandbooks.Fragments.userId", userId);
           intent.putExtra("com.RTechnologies.booksandbooks.Fragments.tprice", tprice);
           startActivity(intent);
        });
        return binding.getRoot();
    }
    private void initUI() {
        context = getContext();
        fetchUserId();
    }
    private void fetchUserId() {
        StringRequest request = new StringRequest(Request.Method.POST, fetchuserIdURL, response -> {

            try {
                JSONArray array = new JSONArray(response);

                for (int i = 0; i < array.length(); i++) {
                    JSONObject object = array.getJSONObject(i);
                    userId=object.getInt("userId");
                }
                getCartItems(userId);
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
    private void getCartItems(int UID){
        cartItems = new ArrayList<>();

        StringRequest request = new StringRequest(Request.Method.POST, fetchCartinfo, response -> {
            try {
                JSONArray array = new JSONArray(response);
                for (int i = 0; i < array.length(); i++) {
                    JSONObject object = array.getJSONObject(i);
                    String itemName=object.getString("itemName");
                    double eprice=object.getDouble("price");
                    String img=object.getString("img");
                    String addDate=object.getString("addDate");
                    int quantity=object.getInt("Quantity");
                    tprice = tprice + (eprice*quantity);
                    String description=object.getString("description");
                    Cartitem cartitem=new Cartitem(itemName , img ,quantity , eprice , addDate ,description);
                    cartItems.add(cartitem);
                }
                setAdapter();

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
    private void setAdapter() {
       binding.rvCart.setLayoutManager(new LinearLayoutManager(context));
       if(cartItems.isEmpty())
       {
           binding.empty.setVisibility(View.VISIBLE);
       }
       adapter = new CartAdapter(context, cartItems);
       binding.rvCart.setAdapter(adapter);
       binding.tvTotalprice.setText(String.valueOf(tprice));
    }
}