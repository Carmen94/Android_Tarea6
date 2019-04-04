package com.iteso.android_tarea6;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.iteso.android_tarea6.beans.Category;
import com.iteso.android_tarea6.beans.ItemProduct;
import com.iteso.android_tarea6.beans.Store;
import com.iteso.android_tarea6.tools.Constants;
import com.iteso.android_tarea6.tools.ControlCategory;
import com.iteso.android_tarea6.tools.ControlItemProduct;
import com.iteso.android_tarea6.tools.ControlStore;
import com.iteso.android_tarea6.tools.DataBaseHandler;


import java.util.ArrayList;

public class ActivityItem extends AppCompatActivity {
    protected Store storeSelected; //Store selected in spinner
    protected Category categorySelected; //Category selected in spinner
    protected int imageSelected;
    protected Spinner stores;
    protected Spinner categories;
    protected Spinner images;
    protected EditText id;
    protected EditText title;
    protected EditText description;
    private Button saveButton;
    protected ArrayAdapter<Store> storesAdapter;
    protected ArrayAdapter<Category> categoriesAdapter;
    protected ArrayAdapter<String> imagesAdapter;
    protected DataBaseHandler dh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);
        title = findViewById(R.id.title_product);
        stores = findViewById(R.id.stores_spinner);
        categories = findViewById(R.id.categories_spinner);
        images = findViewById(R.id.images_spinner);

        storeSelected = null;
        categorySelected = null;
        imageSelected = -1;
        saveButton = findViewById(R.id.Activity_item_save);

        dh = DataBaseHandler.getInstance(this);
        ControlStore storeControl = new ControlStore();
        ControlCategory categoryControl = new ControlCategory();
        ArrayList<Store> storesList = storeControl.getStores(dh);
        ArrayList<Category> categoriesList = categoryControl.getCategories(dh);
        storesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, storesList);
        stores.setAdapter(storesAdapter);
        categoriesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categoriesList);
        categories.setAdapter(categoriesAdapter);

        String[] imagesArray =getResources().getStringArray(R.array.images);
        imagesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, imagesArray);
        images.setAdapter(imagesAdapter);
        stores.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                storeSelected = storesAdapter.getItem(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
        categories.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                categorySelected = categoriesAdapter.getItem(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
        images.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                imageSelected = position;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ItemProduct itemProduct = new ItemProduct();
                itemProduct.setTitle(title.getText().toString().trim());
//                itemProduct.setDescription(description.getText().toString().trim());
                itemProduct.setStore(storeSelected);
                itemProduct.setCategory(categorySelected);
                itemProduct.setImage(imageSelected);
                ControlItemProduct itemProductControl = new ControlItemProduct(v.getContext());
                itemProductControl.addItemProduct(itemProduct, dh);
                Intent intent = new Intent();
                intent.putExtra(Constants.PRODUCT, itemProduct);
                finish();
            }
        });
    }
}
