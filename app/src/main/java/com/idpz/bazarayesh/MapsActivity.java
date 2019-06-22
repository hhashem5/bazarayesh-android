package com.idpz.bazarayesh;

import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.idpz.bazarayesh.Adapters.MainItemAdapter;
import com.idpz.bazarayesh.Adapters.MapsItemAdapter;
import com.idpz.bazarayesh.Models.MainItem;

import java.util.ArrayList;
import java.util.List;

import static com.idpz.bazarayesh.BaseActivity.LEFT_TO_RIGHT;
import static com.idpz.bazarayesh.BaseActivity.RIGHT_TO_LEFT;
import static com.idpz.bazarayesh.Utils.AppController.getActivity;
import static com.idpz.bazarayesh.Utils.AppController.getAppContext;
import static maes.tech.intentanim.CustomIntent.customType;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    List<MainItem> items;

    RecyclerView recycle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.

        initViews();
        MapsItemAdapter mapsItemAdapter = new MapsItemAdapter(items, getActivity(), new MapsItemAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, Object object) {


            }
        });


        recycle.setLayoutManager(new LinearLayoutManager(getAppContext(), LinearLayoutManager.HORIZONTAL, true));
        recycle.setAdapter(mapsItemAdapter);

    }

    public void initViews() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        recycle = findViewById(R.id.recycle);

        mapFragment.getMapAsync(this);
        addItwms();

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

    }


    public void addItwms() {
        items = new ArrayList<>();
        items.add(new MainItem(R.drawable.ic_employees, "استخدام"));
        items.add(new MainItem(R.drawable.ic_teaching, "تخفیف 2 ویدئوهای آموزشی"));
        items.add(new MainItem(R.drawable.ic_door, "واگذاری فضای آرایشگاهی"));
        items.add(new MainItem(R.drawable.ic_clipboard_with_pencil, "ثبتنام دوره های آموزشی"));
        items.add(new MainItem(R.drawable.ic_bride, "آگهی بازدید عروس"));
        items.add(new MainItem(R.drawable.ic_facial_cream_tube, "تخفیف 3 محصولات"));
        items.add(new MainItem(R.drawable.ic_spray_bottle, "واگذاری تجهیزات آرایشگاهی"));
        items.add(new MainItem(R.drawable.ic_noun_facial_wash, "خدمات آرایشکاهی"));
        items.add(new MainItem(R.drawable.ic_noun_teacher, "شروع ثبتنام خدمات آرایشگاهی"));
        items.add(new MainItem(R.drawable.ic_search, "جستجو"));
    }

    @Override
    public void onBackPressed() {

        Intent i1 = new Intent( MapsActivity.this, MainActivity.class);
        i1.setAction(Intent.ACTION_MAIN);
        i1.addCategory(Intent.CATEGORY_HOME);
        i1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        i1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i1);
        customType(MapsActivity.this,RIGHT_TO_LEFT);
        finish();
    }


}
