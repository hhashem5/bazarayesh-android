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
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

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
import com.idpz.bazarayesh.Models.MainItem;
import com.idpz.bazarayesh.Models.estekhdam.Ad;
import com.idpz.bazarayesh.Models.estekhdam.AdsMapModel;
import com.idpz.bazarayesh.Utils.MyLocation;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

import static com.idpz.bazarayesh.Utils.AppController.getActivity;
import static com.idpz.bazarayesh.Utils.AppController.getAppContext;
import static maes.tech.intentanim.CustomIntent.customType;

public class AdsMapActivity extends BaseActivity implements OnMapReadyCallback, View.OnClickListener {

    private GoogleMap mMap;

    List<MainItem> items;
    String memberType = "";
    RecyclerView recycle;

    double userLat, userLng;

    int type = 0;

    int service_type = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ads_map);

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
            public void gotLocation(Location location) {
                //Got the location!

                userLat = location.getLatitude();
                userLng = location.getLongitude();
                getLists(type, memberType, location.getLatitude(), location.getLongitude());

            }
        };

        MyLocation myLocation = new MyLocation();
        myLocation.getLocation(this, locationResult);
    }

    public void initViews() {

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

                MainItem mainItem = (MainItem) object;
                memberType = mainItem.getTag();
                getLists(type, memberType, userLat, userLng);

            }
        });


        recycle.setLayoutManager(new LinearLayoutManager(getAppContext(), LinearLayoutManager.HORIZONTAL, true));
        recycle.setAdapter(mapsItemAdapter);

//        checkLocationPermission();


    }

    //list amozeshgah va arayeshgah va foroshgah
    public void getLists(final int type, String filterTag, double lat, double lng) {

        String url = tools.baseUrl + "ads_list";
        RequestParams params = new RequestParams();
        params.put("member_type", filterTag);
        params.put("lat", 35.706337);
        params.put("lng", 51.356085);
        params.put("type", type);
        params.put("api_token", tools.getSharePrf("api_token"));
        params.put("APP_KEY", "bazarayesh:barber:11731e11b");

        AsyncHttpClient client = new AsyncHttpClient();

        client.post(url, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                statusCode = statusCode;
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {

                try {
                    mMap.clear();

                    if (!responseString.equals("ok")) {

                        final AdsMapModel members = gson.fromJson(responseString, AdsMapModel.class);

                        for (final Ad ad : members.getAds()) {

                            Marker marker = mMap.addMarker(new MarkerOptions()
                                    .position(new LatLng(ad.getLat(), ad.getLng()))
                                    .icon(bitmapDescriptorFromVector(AdsMapActivity.this, R.drawable.ic_location_pin)));
                            marker.setTitle(ad.getName());

                            mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                                @Override
                                public boolean onMarkerClick(Marker marker) {
                                    Intent intent = new Intent(activity, AdsDetailsActivity.class);
                                    intent.putExtra("id", ad.getId());
                                    intent.putExtra("tag", "" + type);
                                    startActivity(intent);
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

    public void addItems() {
        items = new ArrayList<>();

        switch (type) {
            case 3:
                items.add(new MainItem(R.drawable.ic_employees, "آرایشگاه", "1"));
                items.add(new MainItem(R.drawable.ic_employees, "آرایشگر", "2"));
                items.add(new MainItem(R.drawable.ic_bride, "آموزشگاه", "3"));
                items.add(new MainItem(R.drawable.ic_bride, "فروشگاه", "5"));
                items.add(new MainItem(R.drawable.ic_bride, "مدرس", "4"));
                break;
            case 6:
                items.add(new MainItem(R.drawable.ic_employees, "آرایشگاه", "1"));
                items.add(new MainItem(R.drawable.ic_employees, "آرایشگر", "2"));
                items.add(new MainItem(R.drawable.ic_bride, "آموزشگاه", "3"));
                items.add(new MainItem(R.drawable.ic_bride, "مدرس", "4"));
                break;
            case 4:
                items.add(new MainItem(R.drawable.ic_employees, "آرایشگاه", "1"));
                items.add(new MainItem(R.drawable.ic_employees, "آرایشگر", "2"));
                break;
            case 5:
                items.add(new MainItem(R.drawable.ic_bride, "آموزشگاه", "3"));
                items.add(new MainItem(R.drawable.ic_bride, "مدرس", "4"));
                break;
            case 7:
                items.add(new MainItem(R.drawable.ic_employees, "آرایشگاه", "1"));

                break;
            case 1:
                items.add(new MainItem(R.drawable.ic_employees, "آرایشگاه", "1"));
                items.add(new MainItem(R.drawable.ic_employees, "آرایشگر", "2"));

                break;
            case 8:
                items.add(new MainItem(R.drawable.ic_bride, "آموزشگاه", "3"));
                items.add(new MainItem(R.drawable.ic_bride, "مدرس", "4"));
                break;
            case 9:
                items.add(new MainItem(R.drawable.ic_bride, "فروشگاه", "5"));

                break;
            case 2:
                items.add(new MainItem(R.drawable.ic_bride, "آموزشگاه", "3"));
                items.add(new MainItem(R.drawable.ic_bride, "مدرس", "4"));
                break;

        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng tehran = new LatLng(35.684209, 51.388263);
        mMap.addMarker(new MarkerOptions().position(tehran));

        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(tehran, 16);
        googleMap.moveCamera(cameraUpdate);

    }

    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(context,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
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
                                ActivityCompat.requestPermissions(getActivity(),
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        1);
                            }
                        })
                        .create()
                        .show();


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(getActivity(),
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

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
