package com.RTechnologies.booksandbooks.Fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.RTechnologies.booksandbooks.Activities.Main.SubCategoriesActivity;
import com.RTechnologies.booksandbooks.Adapters.Recycler.CategoriesAdapter;
import com.RTechnologies.booksandbooks.Adapters.Recycler.ItemsAdapter;
import com.RTechnologies.booksandbooks.Adapters.Recycler.SearchAdapter;
import com.RTechnologies.booksandbooks.Adapters.Slider.SliderAdapter;
import com.RTechnologies.booksandbooks.Models.Category;
import com.RTechnologies.booksandbooks.Models.Item;
import com.RTechnologies.booksandbooks.databinding.FragmentHomeBinding;
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

public class HomeFragment extends Fragment {

    Context context;
    FragmentHomeBinding binding;
    SliderAdapter sliderAdapter;
    CategoriesAdapter categoriesAdapter;
    ItemsAdapter itemsAdapter;
    SearchAdapter searchAdapter;
    ArrayList<Category> categoriesList;
    ArrayList<Item> booksList, luxuryItemsList,bigDiscountItemsList,hotProductsList;
    ArrayList<String> mSliderItems,searchitems;
    String itemsUrl = "http://192.168.1.10/api/fetchItems.php";
    String BigDiscountUrl = "http://192.168.18.99/api/fetchBigDiscountedItem.php";
    String searchUrl = "http://192.168.18.99/api/searchProduct.php";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(getLayoutInflater(), container, false);
        initUI();
        return binding.getRoot();
    }

    private void initUI() {
        context = getContext();
        initLists();
        clickListeners();
        showSlider();
        setCategoriesAdapter();
        setBooksAdapter();
        setLuxuryAdapter();
        setBigDiscountsAdapter();
        setHotProductsAdapter();
        setSearchAdapter();
    }
    private void clickListeners() {
        binding.etSearchView.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String text = charSequence.toString().trim();
                searchitems.clear();
                if (text.isEmpty()) {
                    binding.cvHistory.setVisibility(View.GONE);
                }
                else
                {
                    binding.cvHistory.setVisibility(View.VISIBLE);
                    //String searchresult = binding.etSearchView.getText().toString();
                    StringRequest request = new StringRequest(Request.Method.POST, searchUrl, response -> {
                        try {
                            JSONArray searchlist = new JSONArray(response);
                            for (int j=0;j<searchlist.length();j++)
                            {
                                JSONObject jsonObject = searchlist.getJSONObject(j);
                                String itemName = jsonObject.getString("itemName");
                                searchitems.add(itemName);
                            }
                            setSearchAdapter();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }, error -> {
                    })
                    {
                        @Nullable
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> map = new HashMap<>();
                            map.put("bookName", text);
                            return map;
                        }
                    };
                    RequestQueue queue = Volley.newRequestQueue(context);
                    queue.add(request);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        binding.tvBooksShopMore.setOnClickListener(view->{
            Intent intent = new Intent(context, SubCategoriesActivity.class);
            intent.putExtra("catName", "Books");
            startActivity(intent);
        });
        binding.tvLuxuryItemsShopMore.setOnClickListener(view->{
            Intent intent = new Intent(context, SubCategoriesActivity.class);
            intent.putExtra("catName", "Mobile Accessories");
            startActivity(intent);
        });
        binding.tvBigDiscountsShopMore.setOnClickListener(view->{
            Intent intent = new Intent(context, SubCategoriesActivity.class);
            intent.putExtra("catName", "Books");
            startActivity(intent);
        });
        binding.tvHotProductsShopMore.setOnClickListener(view->{
            Intent intent = new Intent(context, SubCategoriesActivity.class);
            intent.putExtra("catName", "Books");
            startActivity(intent);
        });
    }

    private void setSearchAdapter() {

        binding.rvHistory.setLayoutManager(new LinearLayoutManager(requireContext()));
        searchAdapter = new SearchAdapter(context, searchitems);
        binding.rvHistory.setAdapter(searchAdapter);
    }
    private void initLists() {
        mSliderItems = new ArrayList<>();
        categoriesList = new ArrayList<>();
        booksList = new ArrayList<>();
        luxuryItemsList = new ArrayList<>();
        bigDiscountItemsList = new ArrayList<>();
        hotProductsList = new ArrayList<>();
        searchitems = new ArrayList<>();
        //his = new ArrayList<>();
        setSliderItems();
        setCategoriesList();
        fetchBooksData();
        fetchLuxuryItemsData();
        fetchBigDiscountItemsData();
        fetchHotProductsData();
    }

    private void setSliderItems() {
        mSliderItems.add("item1");
        mSliderItems.add("item2");
        mSliderItems.add("item3");
        mSliderItems.add("item4");
    }

    private void setCategoriesAdapter() {
        categoriesAdapter = new CategoriesAdapter(context, categoriesList);
        binding.rvCategories.setAdapter(categoriesAdapter);
    }

    private void setBooksAdapter() {
        itemsAdapter = new ItemsAdapter(context, booksList);
        binding.rvBooks.setAdapter(itemsAdapter);
    }

    private void setLuxuryAdapter() {
        itemsAdapter = new ItemsAdapter(context, luxuryItemsList);
        binding.rvLuxuryItems.setAdapter(itemsAdapter);
    }

    private void setBigDiscountsAdapter() {
        itemsAdapter = new ItemsAdapter(context, bigDiscountItemsList);
        binding.rvBigDiscounts.setAdapter(itemsAdapter);
    }

    private void setHotProductsAdapter() {
        itemsAdapter = new ItemsAdapter(context, hotProductsList);
        binding.rvHotProducts.setAdapter(itemsAdapter);
    }

    private void showSlider() {
        sliderAdapter = new SliderAdapter(((Activity) context), mSliderItems);
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

    private void setCategoriesList() {
        categoriesList.add(new Category("catbooks", "Books"));
        categoriesList.add(new Category("catstationery", "Stationery Items"));
        categoriesList.add(new Category("catnotebooks", "Notebooks"));
        categoriesList.add(new Category("catislamicsession", "Islamic Section"));
        categoriesList.add(new Category("cattoys", "Toys"));
        categoriesList.add(new Category("catbirthdayitems", "Gift Items"));
        categoriesList.add(new Category("catnovels", "Novels/Generals"));
        categoriesList.add(new Category("catsports", "Sports Items"));
        categoriesList.add(new Category("catbirthdayitems", "Birthday Items"));
        categoriesList.add(new Category("catbooks", "Costumes"));
        categoriesList.add(new Category("catschoolbags", "School Bags"));
        categoriesList.add(new Category("catlunchbox", "Lunch Box & Bottles"));
        categoriesList.add(new Category("catmobileacc", "Mobile Accessories"));
    }

    //functions for fetching data for items
    private void fetchBooksData() {

        StringRequest request = new StringRequest(Request.Method.POST, itemsUrl, response -> {

            try {
                JSONArray array = new JSONArray(response);
                for (int i = 0; i < array.length(); i++) {
                    JSONObject object = array.getJSONObject(i);

                    int itemId = object.getInt("itemId");
                    String itemName = object.getString("itemName");
                    double price = object.getDouble("price");
                    int discountedPrice = object.getInt("discountPercentage");
                    String img = object.getString("img");
                    String description = object.getString("description");

//                        System.out.println(itemName + price + discountedPrice);
//                        System.out.println(img);
                    Item item = new Item(discountedPrice, img, itemName, price, description, itemId);
                    booksList.add(item);

                }
                itemsAdapter = new ItemsAdapter(context, booksList);
                binding.rvBooks.setAdapter(itemsAdapter);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {

        })
        {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("catagoryId", "1");

                return map;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(request);

    }

    private void fetchLuxuryItemsData() {

        StringRequest request = new StringRequest(Request.Method.POST, itemsUrl, response -> {

            try {
                JSONArray array = new JSONArray(response);

                for (int i = 0; i < array.length(); i++) {
                    JSONObject object = array.getJSONObject(i);

                    String itemName = object.getString("itemName");
                    double price = object.getDouble("price");
                    int discountedPrice = object.getInt("discountPercentage");
                    String img = object.getString("img");
                    String description = object.getString("description");
                    int itemId = object.getInt("itemId");

//                        System.out.println(itemName + price + discountedPrice);
//                        System.out.println(img);

                    Item item = new Item(discountedPrice, img, itemName, price, description, itemId);
                    luxuryItemsList.add(item);

                }
                itemsAdapter = new ItemsAdapter(context, luxuryItemsList);
                binding.rvLuxuryItems.setAdapter(itemsAdapter);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {

        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("catagoryId", "3");

                return map;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(request);

    }

    private void fetchBigDiscountItemsData() {
        StringRequest request = new StringRequest(Request.Method.POST, BigDiscountUrl, response -> {

            try {
                JSONArray array = new JSONArray(response);

                for (int i = 0; i < array.length(); i++) {
                    JSONObject object = array.getJSONObject(i);

                    String itemName = object.getString("itemName");
                    double price = object.getDouble("price");
                    int discountPercentage = object.getInt("discountPercentage");
                    String img = object.getString("img");
                    String description = object.getString("description");
                    int itemId = object.getInt("itemId");
//                        int discountPercent= (int) object.getDouble("discountPercentage");

//                        System.out.println(itemName + price + discountedPrice +"\n"+ discountPercent+"%");
//                        System.out.println(img);

                    Item item = new Item(discountPercentage, img, itemName, price, description, itemId);
                    bigDiscountItemsList.add(item);

                }
                itemsAdapter = new ItemsAdapter(context, bigDiscountItemsList);
                binding.rvBigDiscounts.setAdapter(itemsAdapter);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {

        });

        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(request);

    }

    private void fetchHotProductsData() {

        StringRequest request = new StringRequest(Request.Method.POST, itemsUrl, response -> {

            try {
                JSONArray array = new JSONArray(response);

                for (int i = 0; i < array.length(); i++) {
                    JSONObject object = array.getJSONObject(i);

                    String itemName = object.getString("itemName");
                    double price = object.getDouble("price");
                    int discountedPrice = object.getInt("discountPercentage");
                    String img = object.getString("img");
                    String description = object.getString("description");
                    int itemId = object.getInt("itemId");

//                        System.out.println(itemName + price + discountedPrice);
//                        System.out.println(img);

                    Item item = new Item(discountedPrice, img, itemName, price, description, itemId);
                    hotProductsList.add(item);

                }
                itemsAdapter = new ItemsAdapter(context, hotProductsList);
                binding.rvHotProducts.setAdapter(itemsAdapter);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {

        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("catagoryId", "4");

                return map;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(request);

    }

}