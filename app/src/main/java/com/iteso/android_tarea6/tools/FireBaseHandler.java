package com.iteso.android_tarea6.tools;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.iteso.android_tarea6.beans.Category;
import com.iteso.android_tarea6.beans.Store;

import java.util.ArrayList;

public class FireBaseHandler {

    private static FireBaseHandler databaseHandler;
    private static DatabaseReference databaseReference;
    private DatabaseReference storesReference;
    private ArrayList<Store> listOfStores;
    private FireBaseHandler(){

    }

    public static FireBaseHandler getInstance(){
        if(databaseHandler==null){
            databaseHandler = new FireBaseHandler();
            databaseReference = FirebaseDatabase.getInstance().getReference();
        }
        return databaseHandler;
    }

    public ArrayList<Category> getCategories(){
        final ArrayList<Category> listOfCategories = new ArrayList<>();
        DatabaseReference categoriesReference = databaseReference.child("Category");
        categoriesReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Category value = snapshot.getValue(Category.class);
                    Log.d("FIREBASE", "Value is: " + value);
                    listOfCategories.add(value);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        return listOfCategories;
    }

    private ArrayList<Store> getStoresFromFireBase(){
        final ArrayList<Store> listOfStores = new ArrayList<>();
        storesReference = databaseReference.child("Store");
        storesReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Store value = snapshot.getValue(Store.class);
                    Log.d("FIREBASE", "Value is: " + value);
                    listOfStores.add(value);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        return listOfStores;
    }

    public ArrayList<Store> getStores(){
        if(listOfStores==null){
            return getStoresFromFireBase();
        }else {
            return listOfStores;
        }
    }

    public Store getStoreById(int id){
        final ArrayList<Store> listOfStores = getStores();
        for(Store store : listOfStores){
            if(store.getId()==id){
                return store;
            }
        }
        return null;
    }

    public void createStore(Store store){
        String storeId = databaseReference.child("Store").push().getKey();
        DatabaseReference storesReference = databaseReference.child("Store");
        storesReference.child(storeId).setValue(store);
    }

}
