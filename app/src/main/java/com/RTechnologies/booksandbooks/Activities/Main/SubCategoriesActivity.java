package com.RTechnologies.booksandbooks.Activities.Main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import com.RTechnologies.booksandbooks.Adapters.Recycler.MoreCategoriesAdapter;
import com.RTechnologies.booksandbooks.Adapters.Recycler.SubCategoriesAdapter;
import com.RTechnologies.booksandbooks.Adapters.Slider.SliderAdapter;
import com.RTechnologies.booksandbooks.Models.Category;
import com.RTechnologies.booksandbooks.Models.Item;
import com.RTechnologies.booksandbooks.databinding.ActivitySubCategoriesBinding;
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

public class SubCategoriesActivity extends AppCompatActivity {

    Context context = this;
    ActivitySubCategoriesBinding binding;
    SubCategoriesAdapter subCategoriesAdapter;
    MoreCategoriesAdapter moreCategoriesAdapter;
    SliderAdapter sliderAdapter;
    SliderAdapter sliderAdapter2;
    ArrayList<String> mSliderItems;
    ArrayList<String> mSliderItems2;
    ArrayList<Category> subCategoriesList;
    ArrayList<Item> itemsList;

    int catCheck;

    String setSubCatagory="http://192.168.18.99/api/fetchSubCatagories.php";
    String itemsUrl="http://192.168.18.99/api/fetchItems.php";
    String Fetchbanner="http://192.168.18.99/api/fetchCatagoryBanners.php";

    String subCatagoryImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent=getIntent();
        String catName=intent.getStringExtra("catName");
        binding = ActivitySubCategoriesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        itemsList=new ArrayList<>();
        subCategoriesList=new ArrayList<>();
        switch (catName) {
            case "Books":
                catCheck = 1;
                break;
            case "Stationery Items":
                catCheck = 2;
                break;
            case "Notebooks":
                catCheck = 6;
                break;
            case "Islamic Section":
                catCheck = 7;
                break;
            case "Toys":
                catCheck = 8;
                break;
            case "Gift Items":
                catCheck = 9;
                break;
            case "Novels/Generals":
                catCheck = 10;
                break;
            case "Sports Items":
                catCheck = 11;
                break;
            case "Birthday items":
                catCheck = 12;
                break;
            case "Costumes":
                catCheck = 13;
                break;
            case "School Bags":
                catCheck = 14;
                break;
            case "Lunch Box & Bottles":
                catCheck = 15;
                break;
            case "Mobile Accessories":
                catCheck = 16;
                break;
        }
        initUI();
    }

    private void initUI() {
        clickListeners();
        setSubcategories();
        setSubCategoriesAdapter();
        fetchItems();
        setMoreCategoriesAdapter();
        setSlider1Items();
        setSlider2Items();
    }

    private void clickListeners() {
        binding.toolbar.setOnClickListener(view -> onBackPressed());
    }

    private void setSubcategories(){
        StringRequest request=new StringRequest(Request.Method.POST, setSubCatagory, response -> {
            try {
                JSONArray array = new JSONArray(response);

                for (int i = 0; i < array.length(); i++) {
                    JSONObject object = array.getJSONObject(i);

                   String subCatagoryName=object.getString("subCatagoryName");
                    subCatagoryImg=object.getString("subCatagoryImg");

                   Category category=new Category(subCatagoryImg , subCatagoryName);
                   subCategoriesList.add(category);
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }

        }, error -> {

        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map=new HashMap<>();
                map.put("catagoryId" , String.valueOf(catCheck));

                return  map;
            }
        };

        RequestQueue queue= Volley.newRequestQueue(context);
        queue.add(request);


    }

    private void setSubCategoriesAdapter() {
        binding.rvSubCategories.setLayoutManager(new GridLayoutManager(context, 2));
        subCategoriesAdapter = new SubCategoriesAdapter(context, subCategoriesList);
        binding.rvSubCategories.setAdapter(subCategoriesAdapter);
    }

    private void fetchItems(){
        //store items in the itemsList

        StringRequest request=new StringRequest(Request.Method.POST, itemsUrl, response -> {


            try {
                JSONArray array = new JSONArray(response);

                for (int i = 0; i < array.length(); i++) {
                    JSONObject object = array.getJSONObject(i);

                    String itemName=object.getString("itemName");
                    double price=object.getDouble("price");
                    int discountedPrice= object.getInt("discountPercentage");
                    String img=object.getString("img");
                    String description=object.getString("description");
                    int itemId=object.getInt("itemId");

//
                    Item item=new Item(discountedPrice , img , itemName , price ,description, itemId);
                    itemsList.add(item);

                }

                moreCategoriesAdapter = new MoreCategoriesAdapter(context, itemsList);
                binding.rvMoreCategories.setAdapter(moreCategoriesAdapter);


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {

        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map=new HashMap<>();
                map.put("catagoryId" , String.valueOf(catCheck));

                return map;
            }
        };

        RequestQueue queue= Volley.newRequestQueue(context);
        queue.add(request);


    }

    private void setMoreCategoriesAdapter() {
        binding.rvMoreCategories.setLayoutManager(new GridLayoutManager(context, 2));
        moreCategoriesAdapter = new MoreCategoriesAdapter(context, itemsList);
        binding.rvMoreCategories.setAdapter(moreCategoriesAdapter);
    }

    private void setSlider1Items(){

        mSliderItems=new ArrayList<>();

        StringRequest request=new StringRequest(Request.Method.POST, Fetchbanner, response -> {

            try {
                JSONArray array = new JSONArray(response);

                for (int i = 0; i < array.length(); i++) {
                    JSONObject object = array.getJSONObject(i);
                    String bannerUrl=object.getString("bannerImg");
                    mSliderItems.add(bannerUrl);

                }
                showSlider1();

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {

        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map=new HashMap<>();
                map.put("catagoryId" , String.valueOf(catCheck));

                return map;
            }
        };

        RequestQueue queue= Volley.newRequestQueue(context);
        queue.add(request);
//

    }

    private void showSlider1() {
        sliderAdapter = new SliderAdapter(((Activity) context) , mSliderItems);
        binding.sliderView1.setSliderAdapter(sliderAdapter);
        binding.sliderView1.setIndicatorAnimation(IndicatorAnimationType.WORM);
        binding.sliderView1.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        binding.sliderView1.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        binding.sliderView1.setIndicatorSelectedColor(Color.WHITE);
        binding.sliderView1.setIndicatorUnselectedColor(Color.GRAY);
        binding.sliderView1.setScrollTimeInSec(2);
        binding.sliderView1.setAutoCycle(true);
        binding.sliderView1.startAutoCycle();
    }

    private void setSlider2Items(){
        mSliderItems2=new ArrayList<>();


        StringRequest request=new StringRequest(Request.Method.POST, Fetchbanner, response -> {

            try {
                JSONArray array = new JSONArray(response);

                for (int i = 0; i < array.length(); i++) {
                    JSONObject object = array.getJSONObject(i);

                    String bannerUrl=object.getString("bannerImg");
                    mSliderItems2.add(bannerUrl);
                    System.out.println(bannerUrl);
                }
                showSlider2();

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {

        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map=new HashMap<>();
                map.put("catagoryId" , String.valueOf(catCheck));

                return map;
            }
        };

        RequestQueue queue= Volley.newRequestQueue(context);
        queue.add(request);

    }

    private void showSlider2() {
        sliderAdapter2 = new SliderAdapter(((Activity) context) , mSliderItems2);
        binding.sliderView2.setSliderAdapter(sliderAdapter2);
        binding.sliderView2.setIndicatorAnimation(IndicatorAnimationType.WORM);
        binding.sliderView2.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        binding.sliderView2.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        binding.sliderView2.setIndicatorSelectedColor(Color.WHITE);
        binding.sliderView2.setIndicatorUnselectedColor(Color.GRAY);
        binding.sliderView2.setScrollTimeInSec(2);
        binding.sliderView2.setAutoCycle(true);
        binding.sliderView2.startAutoCycle();
    }
}