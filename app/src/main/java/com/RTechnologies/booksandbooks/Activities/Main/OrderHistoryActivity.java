package com.RTechnologies.booksandbooks.Activities.Main;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.RTechnologies.booksandbooks.Adapters.Recycler.HistoryAdapter;
import com.RTechnologies.booksandbooks.Models.Historyitem;
import com.RTechnologies.booksandbooks.databinding.ActivityOrderHistoryBinding;
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

public class OrderHistoryActivity extends AppCompatActivity {

    Context context = this;
    ActivityOrderHistoryBinding binding;
    ArrayList<Historyitem> arrayList;
    HistoryAdapter adapter;
    static int orderId;
    int userId;
    String historyURL = "http://192.168.18.99/api/orderHistory.php";
    String fetchorderURL = "http://192.168.18.99/api/orderDetails.php";
    String fetchuserIdURL="http://192.168.18.99/api/fetchUserId.php";
    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_NAME = "name";
    String Email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOrderHistoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        SharedPreferences sharedPreferences = this.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        Email=sharedPreferences.getString(KEY_NAME , null);
        initUI();
    }
    private void initUI() {
        clickListeners();
        fetchUserId();
    }
    private void clickListeners() {
        binding.toolbar.setNavigationOnClickListener(view -> onBackPressed());
        arrayList = new ArrayList<>();
    }
    private void fetchUserId() {
        StringRequest request = new StringRequest(Request.Method.POST, fetchuserIdURL, response -> {
            try {
                JSONArray array = new JSONArray(response);

                for (int i = 0; i < array.length(); i++) {
                    JSONObject object = array.getJSONObject(i);
                    userId=object.getInt("userId");
                }
                fetchOrderId(userId);
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
    private void fetchOrderId(int UID) {
        StringRequest request = new StringRequest(Request.Method.POST , historyURL , response -> {
            try {
                JSONArray array1 = new JSONArray(response);
                for(int i =0;i<array1.length();i++)
                {
                    JSONObject object = array1.getJSONObject(i);
                    orderId = object.getInt("orderId");
                    fetchorderdetail(orderId);
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }
        } , error -> {
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map=new HashMap<>();
                map.put("userId", String.valueOf(UID));
                return map;
            }
        };
        RequestQueue queue1 = Volley.newRequestQueue(context);
        queue1.add(request);
    }
    private void fetchorderdetail(int OID){

        StringRequest newrequest = new StringRequest(Request.Method.POST, fetchorderURL, response1 -> {

            try {
                JSONArray orderObject = new JSONArray(response1);
                for (int i1 = 0; i1 < orderObject.length(); i1++)
                {
                    JSONObject newObj = orderObject.getJSONObject(i1);
                    int price = newObj.getInt("totalPrice");
                    int numofitems = newObj.getInt("quantity");
                    String itemName = newObj.getString("itemName");
                    String date = newObj.getString("orderDate");
                    arrayList.add(new Historyitem(numofitems, date, itemName, "whatever", price));
                }
                setAdapter();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, error -> Toast.makeText( context,error.toString() , Toast.LENGTH_SHORT).show())
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("orderId", String.valueOf(OID));
                return map;
            }
        };
        RequestQueue queue1 = Volley.newRequestQueue(context);
        queue1.add(newrequest);
    }
    private void setAdapter() {
        adapter = new HistoryAdapter(context, arrayList);
        binding.rvOrderHistory.setAdapter(adapter);
    }
}