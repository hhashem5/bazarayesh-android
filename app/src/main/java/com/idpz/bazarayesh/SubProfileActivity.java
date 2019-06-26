package com.idpz.bazarayesh;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.idpz.bazarayesh.Adapters.ViewPagerAdapter;

import java.util.List;

import static com.idpz.bazarayesh.Utils.AppController.getActivity;

public class SubProfileActivity extends BaseActivity {
    TabLayout tabLayout;
    ViewPager viewPager;

    public static int tag;

    String title = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_profile);

        settoolbarText(getString(R.string.title_profile));

        initViews();


        Intent intent = getIntent();

        try {
            tag = (int) intent.getExtras().get("type");


            if (intent.getExtras().get("type").equals(1)) {
                title = "آرایشگاه";
            } else if (intent.getExtras().get("type").equals(2)) {
                title = "آرایشگر";
            } else if (intent.getExtras().get("type").equals(3)) {
                title = "آموزشگاه";
            } else if (intent.getExtras().get("type").equals(4)) {
                title = "مدرس";
            } else if (intent.getExtras().get("type").equals(5)) {
                title = "فروشگاه";
            }
        } catch (Exception e) {
        }

        tabLayout.addTab(tabLayout.newTab().setText(" پروفایل " + title));
        tabLayout.addTab(tabLayout.newTab().setText("مدیریت آگهی ها"));

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);


        //   tabLayout.setTabGravity(TabLayout.MODE_FIXED);

        final ViewPagerAdapter adapter = new ViewPagerAdapter(this, getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    public void initViews() {

        tabLayout = findViewById(R.id.tabLayout);

        viewPager = findViewById(R.id.viewPager);
    }

    @Override
    public void onBackPressed() {


        List<Fragment> fragmentList = getSupportFragmentManager().getFragments();
        if (fragmentList != null) {
            //TODO: Perform your logic to pass back press here
            for (Fragment fragment : fragmentList) {
                if (fragment instanceof IOnBackPressed) {
                    ((IOnBackPressed) fragment).onBackPressed();
                }
            }
        }


    }

}

