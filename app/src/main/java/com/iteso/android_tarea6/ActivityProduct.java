package com.iteso.android_tarea6;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.iteso.android_tarea6.beans.ItemProduct;
import com.iteso.android_tarea6.tools.Constants;

public class ActivityProduct extends AppCompatActivity {

    EditText titleEditText, storeEditText, locationEditText, phoneEditText;
    Button saveButton, cancelButton;
    ImageView image;
    ItemProduct itemProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        itemProduct = getIntent().getParcelableExtra(Constants.PRODUCT);
        titleEditText = findViewById(R.id.activity_product_item_title_edit_text);
        storeEditText = findViewById(R.id.activity_product_item_store_edit_text);
        locationEditText = findViewById(R.id.activity_product_item_location_edit_text);
        phoneEditText = findViewById((R.id.activity_product_item_phone_edit_text));
        image = findViewById(R.id.item_product_image);
        saveButton = findViewById(R.id.activity_product_save_button);
        cancelButton = findViewById(R.id.activity_product_cancel_button);
        switch(itemProduct.getImage()){
            case 0:
                image.setImageResource(R.drawable.mac); break;
            case 1:
                image.setImageResource(R.drawable.alienware); break;
            case 2:
                image.setImageResource(R.drawable.refrigerator); break;
            case 3:
                image.setImageResource(R.drawable.micro); break;
            case 4:
                image.setImageResource(R.drawable.pillows); break;
        }
        titleEditText.setText(itemProduct.getTitle());
        storeEditText.setText(itemProduct.getStore().getName());
        locationEditText.setText(itemProduct.getStore().getCity().getName());
        phoneEditText.setText(itemProduct.getStore().getPhone());
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Build object to send
                //ItemProduct itemProduct = new ItemProduct();
                itemProduct.setTitle(titleEditText.getText().toString());
                itemProduct.getStore().setName(storeEditText.getText().toString());
                itemProduct.getStore().getCity().setName(locationEditText.getText().toString());
                Intent intent = new Intent();
                intent.putExtra(Constants.PRODUCT, itemProduct);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_main, menu);
        return true;
    }




}
