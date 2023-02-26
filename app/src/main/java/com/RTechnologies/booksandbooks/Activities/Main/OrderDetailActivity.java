package com.RTechnologies.booksandbooks.Activities.Main;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.RTechnologies.booksandbooks.Adapters.Slider.SliderAdapter;
import com.RTechnologies.booksandbooks.Utils.Functions;
import com.RTechnologies.booksandbooks.databinding.ActivityOrderDetailBinding;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@SuppressLint("SetTextI18n")
public class OrderDetailActivity extends AppCompatActivity {

    Context context = this;
    ActivityOrderDetailBinding binding;
    int counter;
    SliderAdapter sliderAdapter;
    ArrayList<String> mSliderItems;

    String fetchuserIdURL="http://192.168.18.99/api/fetchUserId.php";
    String insertintoCart="http://192.168.18.99/api/InsertintoCart.php";

    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_NAME = "name";
    SharedPreferences preferences;
    int itemId;
    String ItemName;
    double price;
    String discription;
    String imgUrl;
    String Email;
    int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOrderDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        preferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        Email = preferences.getString(KEY_NAME, null);

        Intent intent=getIntent();
        itemId=intent.getIntExtra("itemId" , 0);
        ItemName=intent.getStringExtra("itemName");
        price= Double.parseDouble(intent.getStringExtra("price"));
        discription=intent.getStringExtra("description");
        imgUrl=intent.getStringExtra("img");
        binding.tvCount.setText("1");


        System.out.println("ITEM ID IS "+ itemId);
        fetchUserId();

    }

    private void fetchUserId() {
        StringRequest request = new StringRequest(Request.Method.POST, fetchuserIdURL, response -> {

            try {
                JSONArray array = new JSONArray(response);

                for (int i = 0; i < array.length(); i++) {
                    JSONObject object = array.getJSONObject(i);
                    userId=object.getInt("userId");

                    System.out.println("NEW USER is "+userId);
                }
                initUI();

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

    private void initUI() {
        Functions.hideStatusBar(context);
        setItemDetails();
        clickListeners();
    }

    private void setItemDetails(){
        binding.tvPrice.setText("Rs."+price);
        binding.tvProductName.setText(ItemName);
        binding.tvProductDesc.setText(discription);
        binding.tvPPrice.setText("Rs."+price);
        binding.tvName.setText("Dr. Tehmina Karamat Ullah Khan PHD");
        binding.tvDesc.setText("Here we are going to add product reviews");
        showSlider();
    }

    private void clickListeners() {
        binding.cvBack.setOnClickListener(view -> onBackPressed());

        binding.cvAdd.setOnClickListener(view -> {

            counter++;
            binding.tvCount.setText(counter + "");
            binding.tvPPrice.setText("Rs "+(price*counter));
        });

        binding.cvRemove.setOnClickListener(view -> {
            if (counter != 1) {
                counter--;
            }

            binding.tvCount.setText(counter + "");
            binding.tvPPrice.setText("Rs "+(price*counter));
        });
        binding.cvBuyNow.setOnClickListener(view -> {
            //Fill data in cart
            InsertToCart();

        });
    }

    private void InsertToCart() {

        StringRequest request = new StringRequest(Request.Method.POST, insertintoCart, response -> {

            if(response.equals("OK")){
                Toast.makeText(context , "Added To Cart" , Toast.LENGTH_SHORT).show();
                startActivity(new Intent(OrderDetailActivity.this, HomeActivity.class));
            }
            else{
                Toast.makeText(context , "Something Went Wrong..." , Toast.LENGTH_SHORT ).show();
            }

        }, error -> {

        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("userId", String.valueOf(userId));
                map.put("itemId" , String.valueOf(itemId));
                map.put("Quantity" , binding.tvCount.getText().toString());

                return map;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(request);


    }

    private void showSlider() {
        getItemImages();
        sliderAdapter = new SliderAdapter(((Activity) context) , mSliderItems);
        binding.sliderView.setSliderAdapter(sliderAdapter);
        binding.sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM);
        binding.sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        binding.sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        binding.sliderView.setIndicatorSelectedColor(Color.WHITE);
        binding.sliderView.setIndicatorUnselectedColor(Color.GRAY);
        binding.sliderView.setScrollTimeInSec(2);
        binding.sliderView.setAutoCycle(true);
        binding.sliderView.startAutoCycle();
    }

    private void getItemImages(){
        mSliderItems = new ArrayList<>();
        //Add image links to this arraylist
        mSliderItems.add(imgUrl);
    }
}