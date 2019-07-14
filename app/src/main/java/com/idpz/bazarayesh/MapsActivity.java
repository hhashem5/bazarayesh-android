package com.idpz.bazarayesh;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.idpz.bazarayesh.Adapters.MapsItemAdapter;
import com.idpz.bazarayesh.Models.Data;
import com.idpz.bazarayesh.Models.ResponseListMember;
import com.idpz.bazarayesh.Models.MainItem;
import com.idpz.bazarayesh.Utils.MyLocation;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cz.msebera.android.httpclient.Header;


import static com.idpz.bazarayesh.Utils.AppController.getActivity;
import static com.idpz.bazarayesh.Utils.AppController.getAppContext;
import static maes.tech.intentanim.CustomIntent.customType;

public class MapsActivity extends BaseActivity implements OnMapReadyCallback, View.OnClickListener {

    private GoogleMap mMap;

    List<MainItem> items;

    RecyclerView recycle;

    double userLat, userLng;

    int type = 0;

    int service_type = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.


        try {
            type = (int) getIntent().getExtras().get("type");
        } catch (Exception e) {
        }

        try {
            service_type = (int) getIntent().getExtras().get("service_type");
        } catch (Exception e) {
        }

        settoolbarText("نقشه");


        initViews();

        MyLocation.LocationResult locationResult = new MyLocation.LocationResult() {
            @Override
            public void gotLocation(final Location location) {
                //Got the location!
                imgbBack.setVisibility(View.VISIBLE);

                userLat = location.getLatitude();
                userLng = location.getLongitude();
                if (service_type == 0) {

                    Handler mainHandler = new Handler(Looper.getMainLooper());
                    Runnable myRunnable = new Runnable() {
                        @Override
                        public void run() {
                            //Code that uses AsyncHttpClient in your case ConsultaCaract()
                            getLists(type, "all", location.getLatitude(), location.getLongitude());


                        }
                    };
                    mainHandler.post(myRunnable);


                } else {

                    Handler mainHandler = new Handler(Looper.getMainLooper());

                    Runnable myRunnable = new Runnable() {
                        @Override
                        public void run() {
                            //Code that uses AsyncHttpClient in your case ConsultaCaract()

                            getStylishList(type, userLat, userLng, service_type, "");

                        }
                    };
                    mainHandler.post(myRunnable);
                }
            }
        };
        MyLocation myLocation = new MyLocation();
        myLocation.getLocation(this, locationResult);


    }

    public void initViews() {
        checkLocationPermission();

        imgbBack.setVisibility(View.VISIBLE);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        recycle = findViewById(R.id.recycle);

        mapFragment.getMapAsync(this);
        imgbBack.setOnClickListener(this);


        addItems();

        MapsItemAdapter mapsItemAdapter = new MapsItemAdapter(items, getActivity(), new MapsItemAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, Object object) {

                MainItem item = (MainItem) object;
                if (item.getTitle().equals("جستجو")) {

                    Intent intent = new Intent(context, ListShowActivty.class);
                    intent.putExtra("type", type);
                    intent.putExtra("lat", userLat);
                    intent.putExtra("lng", userLng);
                    customType(context, BOTTOM_TO_UP);
                    startActivity(intent);
                    customType(context, RIGHT_TO_LEFT);

                } else if (service_type == 0) {
                    item.getTag();
                    getLists(type, item.getTag(), userLat, userLng);
                } else

                    getStylishList(type, userLat, userLng, service_type, item.getTag());
            }
        });


        recycle.setLayoutManager(new LinearLayoutManager(getAppContext(), LinearLayoutManager.HORIZONTAL, true));
        recycle.setAdapter(mapsItemAdapter);


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
        LatLng tehran = new LatLng(35.684209, 51.388263);
        mMap.addMarker(new MarkerOptions().position(tehran));

        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(tehran, 16);
        googleMap.moveCamera(cameraUpdate);


    }


    public void addItems() {
        items = new ArrayList<>();

        switch (type) {


            //arayeshgah
            case 1:

                items.add(new MainItem(R.drawable.ic_employees, "استخدام", "recruiment"));
                items.add(new MainItem(R.drawable.ic_bride, "آگهی بازدید عروس", "brides"));
                items.add(new MainItem(R.drawable.ic_door, "واگذاری فضای آرایشگاهی", "assignment"));
                items.add(new MainItem(R.drawable.ic_noun_teacher, "شروع ثبتنام کارگاه آموزشی", "workshops"));
                items.add(new MainItem(R.drawable.ic_noun_facial_wash, "تخفیف خدمات آرایشکاهی", "discount_ads"));
                items.add(new MainItem(R.drawable.ic_search, "جستجو"));
                items.add(new MainItem(R.drawable.ic_all, "همه", "all"));

                break;

            //amozeshgah
            case 3:

                items.add(new MainItem(R.drawable.ic_employees, "استخدام", "recruiment"));
                items.add(new MainItem(R.drawable.ic_clipboard_with_pencil, "ثبتنام دوره های آموزشی", "reg_Course"));
                items.add(new MainItem(R.drawable.ic_noun_teacher, "شروع ثبتنام کارگاه آموزشی", "workshops"));
                items.add(new MainItem(R.drawable.ic_teaching, "تخفیف  دوره و کارگاه آموزشی", "discount_ads"));
                items.add(new MainItem(R.drawable.ic_search, "جستجو"));
                items.add(new MainItem(R.drawable.ic_all, "همه", "all"));

                break;


            //froshgah
            case 5:

                items.add(new MainItem(R.drawable.ic_employees, "استخدام", "recruiment"));
                items.add(new MainItem(R.drawable.ic_spray_bottle, "تخفیف محصولات آرایشی", "discount_ads"));
                items.add(new MainItem(R.drawable.ic_search, "جستجو"));
                items.add(new MainItem(R.drawable.ic_all, "همه", "all"));

                break;


            case 2:
                items.add(new MainItem(R.drawable.ic_employees, "استخدام", "recruiment"));
                items.add(new MainItem(R.drawable.ic_noun_teacher, "شروع ثبتنام کارگاه آموزشی", "workshop"));
                items.add(new MainItem(R.drawable.ic_noun_facial_wash, "تخفیف خدمات آرایشکاهی", "discount"));
                items.add(new MainItem(R.drawable.ic_door, "واگذاری فضای آرایشگاهی", "assignment"));

                break;

            case 4:
                items.add(new MainItem(R.drawable.ic_employees, "استخدام", "recruiment"));
                items.add(new MainItem(R.drawable.ic_clipboard_with_pencil, "ثبتنام دوره های آموزشی", "reg_Course"));
                items.add(new MainItem(R.drawable.ic_teaching, "تخفیف  دوره و کارگاه آموزشی", "discount"));
                items.add(new MainItem(R.drawable.ic_noun_teacher, "شروع ثبتنام کارگاه آموزشی", "workshop"));

                break;


        }


//        items = new ArrayList<>();
//        items.add(new MainItem(R.drawable.ic_employees, "استخدام"));
//        items.add(new MainItem(R.drawable.ic_teaching, "تخفیف 2 ویدئوهای آموزشی"));
//        items.add(new MainItem(R.drawable.ic_door, "واگذاری فضای آرایشگاهی"));
//        items.add(new MainItem(R.drawable.ic_clipboard_with_pencil, "ثبتنام دوره های آموزشی"));
//        items.add(new MainItem(R.drawable.ic_bride, "آگهی بازدید عروس"));
//        items.add(new MainItem(R.drawable.ic_facial_cream_tube, "تخفیف 3 محصولات"));
//        items.add(new MainItem(R.drawable.ic_spray_bottle, "واگذاری تجهیزات آرایشگاهی"));
//        items.add(new MainItem(R.drawable.ic_noun_facial_wash, "خدمات آرایشکاهی"));
//        items.add(new MainItem(R.drawable.ic_noun_teacher, "شروع ثبتنام خدمات آرایشگاهی"));
//        items.add(new MainItem(R.drawable.ic_search, "جستجو"));

    }

    @Override
    public void onBackPressed() {

        Intent i1 = new Intent(MapsActivity.this, MainActivity.class);
        i1.setAction(Intent.ACTION_MAIN);
        i1.addCategory(Intent.CATEGORY_HOME);
        i1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        i1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i1);
        customType(MapsActivity.this, RIGHT_TO_LEFT);
        finish();
    }


    //list amozeshgah va arayeshgah va foroshgah
    public void getLists(final int type, String filterTag, double lat, double lng) {

        String url = tools.baseUrl + "barber_shop_list";

        RequestParams params = new RequestParams();
        params.put("tag", filterTag);
        params.put("lat", lat);
        params.put("lng", lng);
        params.put("type", type);
        params.put("api_token", tools.getSharePrf("api_token"));
        params.put("APP_KEY", "bazarayesh:barber:11731e11b");

        AsyncHttpClient client = new AsyncHttpClient();

        client.post(url, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {


                try {
                    mMap.clear();

                    if (!responseString.equals("ok")) {

                        final ResponseListMember members = gson.fromJson(responseString, ResponseListMember.class);

                        for (final Data member : members.getMembers()) {

                            member.getLat();
                            member.getLng();


                            Marker marker = mMap.addMarker(new MarkerOptions()
                                    .position(new LatLng(member.getLat(), member.getLng()))
                                    .icon(bitmapDescriptorFromVector(MapsActivity.this, R.drawable.ic_location_pin)));


                            marker.setTitle(member.getFullName());

                            marker.setTag(member.getId());

                            mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                                @Override
                                public boolean onMarkerClick(Marker marker) {


                                    for (Data member2 : members.getMembers()) {
                                        if (marker.getTag() .equals( member2.getId())) {
                                            Intent intent = new Intent(MapsActivity.this, ShowMemberDetail.class);
                                          //  intent.putExtra("type",type);
                                            //intent.putExtra("service_type",service_type);
                                            intent.putExtra("id", member2.getId());
                                            startActivity(intent);
                                        }
                                    }
                                    return false;


                                }
                            });


                            //    mMap.addMarker(new MarkerOptions().position(new LatLng(member.getLat(),member.getLng())));
                        }

                    }
                } catch (Exception e) {
                    e.getMessage();
                }

            }
        });
    }


    //modares and arayeshgar
    //agar ad_filter
    public void getStylishList(final int type, double lat, double lng, final int serviceType, String ad_filter) {

        String url = tools.baseUrl + "stylist_list";

        RequestParams params = new RequestParams();
        params.put("lat", lat);
        params.put("lng", lng);
        params.put("type", type);
        if (!ad_filter.equals(""))
            params.put("ad_filter", ad_filter);

        params.put("service_type", serviceType);
        params.put("api_token", tools.getSharePrf("api_token"));
        params.put("APP_KEY", "bazarayesh:barber:11731e11b");

        AsyncHttpClient client = new AsyncHttpClient();

        client.post(url, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {


            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {

                try {
                    mMap.clear();

                    if (!responseString.equals("ok")) {

                        ResponseListMember members = gson.fromJson(responseString, ResponseListMember.class);
                        if (members.getMembers().size() != 0)


                            for (final Data member : members.getMembers()) {


                                member.getLat();
                                member.getLng();


                                Marker marker = mMap.addMarker(new MarkerOptions()
                                        .position(new LatLng(member.getLat(), member.getLng()))
                                        .icon(bitmapDescriptorFromVector(MapsActivity.this, R.drawable.ic_location_pin)));


                                marker.setTitle(member.getFullName());

                                marker.setTag(member.getId());

                                mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                                    @Override
                                    public boolean onMarkerClick(Marker marker) {

                                        Intent intent = new Intent(MapsActivity.this, ShowMemberDetail.class);

                                        intent.putExtra("id", member.getId());
                                        //intent.putExtra("type",type);
                                      //  intent.putExtra("service_type",service_type);
                                        startActivity(intent);

                                        return false;
                                    }
                                });


                                //    mMap.addMarker(new MarkerOptions().position(new LatLng(member.getLat(),member.getLng())));
                            }

                    }
                } catch (Exception e) {
                }
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(context,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        //Request location updates:
                        //   locationManager.requestLocationUpdates(provider, 400, 1, this);
                    }

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.

                }
                return;
            }

        }
    }


    public boolean checkLocationPermission() {

        if (ContextCompat.checkSelfPermission(context,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(context)
                        .setTitle("موقعیت مکانی")
                        .setMessage("برای استفاده از نقشه سیستم موقعیت مکانی خود را روشن کنید.")
                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(activity,
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        1);
                            }
                        })
                        .create()
                        .show();


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(activity,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        2);
            }
            return false;
        } else {

            return true;
        }

    }


    private BitmapDescriptor bitmapDescriptorFromVector(Context context, int vectorResId) {
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorResId);
        vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.imgbBack:
                Intent intent = new Intent(getAppContext(), MainActivity.class);
                intent.setAction(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                customType(context, BOTTOM_TO_UP);

                startActivity(intent);
                customType(context, RIGHT_TO_LEFT);

                finish();

                break;
        }
    }
}
