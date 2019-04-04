package com.iteso.android_tarea6;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.iteso.android_tarea6.beans.ItemProduct;
import com.iteso.android_tarea6.beans.tools.Constants;

public class ActivityMain extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    private FragmentTechnology fragmentTechnology;
    private FragmentElectronics fragmentElectronics;
    private FragmentHome fragmentHome;
    private FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        floatingActionButton = findViewById(R.id.new_item);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ActivityItem.class);
                startActivityForResult(intent, Constants.INTENT_PRODUCTS_NOTIFY);
            }
        });
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = findViewById(R.id.tabs);
        //Relate tabs with view pager content
        tabLayout.setupWithViewPager(mViewPager);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode)
        {
            case Constants.INTENT_PRODUCTS_NOTIFY:
                if(resultCode == Activity.RESULT_OK){
                    if(data != null){
                        ItemProduct itemProduct = data.getParcelableExtra(Constants.PRODUCT);
                        if(itemProduct.getCategory().getName().equalsIgnoreCase(Constants.CATEGORY_TECH)){
                            fragmentTechnology.notifyDataSetChanged(itemProduct);
                        }else if(itemProduct.getCategory().getName().equalsIgnoreCase(Constants.CATEGORY_HOME)){
                            fragmentHome.notifyDataSetChanged(itemProduct);
                        }else {
                            fragmentElectronics.notifyDataSetChanged(itemProduct);
                        }
                    }
                }
                break;
            case 2:
                if(resultCode == Activity.RESULT_OK){
                    if(data != null){
                        ItemProduct itemProduct = data.getParcelableExtra(Constants.PRODUCT);
                        if(itemProduct.getCategory().getName().equalsIgnoreCase(Constants.CATEGORY_HOME)){
                            fragmentHome.notifyDataSetChanged(itemProduct);
                        }
                    }
                }
                break;
            case 3:
                if(resultCode == Activity.RESULT_OK){
                    if(data != null){
                        ItemProduct itemProduct = data.getParcelableExtra(Constants.PRODUCT);
                        if(itemProduct.getCategory().getName().equalsIgnoreCase(Constants.CATEGORY_ELECTRONICS)){
                            fragmentElectronics.notifyDataSetChanged(itemProduct);
                        }
                    }
                }
                break;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_privacy_policy) {
            openPrivacyPolicy();
            return true;
        } else if (id == R.id.action_logout){
            logOut();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private void logOut(){
        SharedPreferences sharedPreferences = getSharedPreferences(Constants.PACKAGE_PREFERENCES,MODE_PRIVATE);
        sharedPreferences.edit().clear().commit();
        Intent intent = new Intent(ActivityMain.this,ActivityLogin.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    private void openPrivacyPolicy(){
        Intent intent = new Intent(ActivityMain.this,ActivityPrivacyPolicy.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
   /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            switch (position){
                case 0:
                    if(fragmentTechnology == null){
                        fragmentTechnology = new FragmentTechnology();
                    }
                    return fragmentTechnology;
                case 1:
                    if(fragmentHome == null){
                        fragmentHome = new FragmentHome();
                    }
                    return fragmentHome;
                case 2:
                    if(fragmentElectronics == null){
                        fragmentElectronics = new FragmentElectronics();
                    }
                    return fragmentElectronics;
                default:
                    return new FragmentTechnology();
            }
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position){
                case 0:
                    return getString(R.string.categoryTech);
                case 1:
                    return getString(R.string.categoryHome);
                case 2:
                    return getString(R.string.categoryElectronics);
            }
            return null;
        }
    }
}
