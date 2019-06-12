package com.idpz.bazarayesh;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.firebase.analytics.FirebaseAnalytics;

import ss.com.bannerslider.Slider;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static maes.tech.intentanim.CustomIntent.customType;

public class MainActivity extends BaseActivity {

    BottomNavigationView navigation;
    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (tools.getSharePrf("api_token") == "") {
            Intent i1 = new Intent(getApplicationContext(), LogIn.class);
            i1.setAction(Intent.ACTION_MAIN);
            i1.addCategory(Intent.CATEGORY_HOME);
            i1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            i1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            overridePendingTransition(0, 0);

            startActivity(i1);

            customType(context, LEFT_TO_RIGHT);

            finish();
        }
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        initViews();

        navigation.setSelectedItemId(R.id.navigation_home);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        try {


            if (getIntent().getStringExtra("back").equals("1")) {
                loadFragment(new ProfileFragment());

            } else loadFragment(new HomeFragment(context));
        } catch (NullPointerException e) {


            loadFragment(new HomeFragment(context));
        }

    }

    public void initViews() {
        navigation = findViewById(R.id.navigation);


    }

    private boolean loadFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        Fragment fragment = null;

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_advertise:
                    fragment = new AdvertiseFragment();
                    break;
                case R.id.navigation_home:
                    fragment = new HomeFragment(context);
                    break;
                case R.id.navigation_profile:
                    fragment = new ProfileFragment();
                    break;
            }

            return loadFragment(fragment);
        }
    };


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));

    }

}
