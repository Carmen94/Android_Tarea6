package com.iteso.android_tarea6;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.iteso.android_tarea6.beans.City;
import com.iteso.android_tarea6.beans.Store;
import com.iteso.android_tarea6.beans.User;
import com.iteso.android_tarea6.beans.tools.Constants;
import com.iteso.android_tarea6.beans.tools.ControlStore;
import com.iteso.android_tarea6.beans.tools.DataBaseHandler;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class ActivitySplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        DataBaseHandler dataBaseHandler = DataBaseHandler.getInstance(this);

        ControlStore controlStore = new ControlStore();
        ArrayList<Store> listOfStores = controlStore.getStores(dataBaseHandler);
        if(listOfStores.size()==0){
            City guadalajara = new City();
            guadalajara.setId(1);
            Store store1 = new Store();
            store1.setName("WALMART");
            store1.setCity(guadalajara);
            store1.setPhone("3331008797");
            store1.setThumbnail(0);
            store1.setLatitude(20.5);
            store1.setLongitude(45.7);

            Store store2 = new Store();
            store2.setName("Liverpool");
            store2.setCity(guadalajara);
            store2.setPhone("3104-4578");
            store2.setThumbnail(0);
            store2.setLatitude(20.5);
            store2.setLongitude(45.7);

            Store store3 = new Store();
            store3.setName("Best Buy");
            store3.setCity(guadalajara);
            store3.setPhone("557894567");
            store3.setThumbnail(0);
            store3.setLatitude(20.5);
            store3.setLongitude(45.7);
            controlStore.addStore(store1,dataBaseHandler);
            controlStore.addStore(store2,dataBaseHandler);
            controlStore.addStore(store3,dataBaseHandler);
        }
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                User user = loadSharedPreferences();
                if(!user.isLogged()){
                    Intent intent = new Intent(ActivitySplashScreen.this,ActivityLogin.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }else{
                    Intent intent = new Intent(ActivitySplashScreen.this,ActivityMain.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
                finish();
            }
        };
        Timer timer = new Timer();
        timer.schedule(task, Constants.SPLASH_SCREEN_DELAY);
    }

    private User loadSharedPreferences(){
        User user = new User();
        SharedPreferences sharedPreferences = getSharedPreferences(Constants.PACKAGE_PREFERENCES,MODE_PRIVATE);
        user.setName(sharedPreferences.getString(Constants.SHARED_PREFERENCES_USERNAME, null));
        user.setPassword(sharedPreferences.getString(Constants.SHARED_PREFERENCES_PASSWORD, null));
        user.setLogged(sharedPreferences.getBoolean(Constants.SHARED_PREFERENCES_LOGGED,false));
        return user;
    }
}
