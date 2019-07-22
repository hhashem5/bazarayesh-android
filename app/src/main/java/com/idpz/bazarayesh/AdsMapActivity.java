package com.idpz.bazarayesh;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

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
import com.google.gson.Gson;
import com.idpz.bazarayesh.Adapters.MapsItemAdapter;
import com.idpz.bazarayesh.Models.AdRegCourse.ResponseRegCourse;
import com.idpz.bazarayesh.Models.MainItem;
import com.idpz.bazarayesh.Models.SingelAdBride.ResponseSingleBride;
import com.idpz.bazarayesh.Models.SingleAdAssign.ResponseSingleAssign;
import com.idpz.bazarayesh.Models.SingleAdCourse.ResponseSingleCourse;
import com.idpz.bazarayesh.Models.SingleAdDiscount.ResponseSingleDiscount;
import com.idpz.bazarayesh.Models.SingleAdWorkShop.ResponseSingleWorkShop;
import com.idpz.bazarayesh.Models.SingleAdRecruiment.ResponseSingleRecruiment;
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
    String tag = "";


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
        switch (type) {
            case 1:
                tag = "discount_ads";
                break;

            case 2:
                tag = "discount_ads";

                break;

            case 3:
                tag = "recruiments";
                break;

            case 4:
                tag = "assignments";
                break;

            case 5:
                tag = "courses";
                break;

            case 6:
                tag = "workshops";
                break;

            case 7:
                tag = "brides";
                break;

            case 8:
                tag = "assignments";
                break;

            case 9:
                tag = "discount_ads";
                break;
        }

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
                            marker.setTag(ad.getId());


                            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(ad.getLat(),ad.getLng()), 16);
                            mMap.moveCamera(cameraUpdate);


                            mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                                @Override
                                public void onInfoWindowClick(Marker marker) {

                                    for (Ad ad : members.getAds()) {
                                        if (ad.getId().equals(marker.getTag())) {

                                            getAddDetails(String.valueOf(ad.getId()), tag);
                                        }


                                    }
                                }
                            });



/*                            mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                                @Override
                                public boolean onMarkerClick(Marker marker) {

                                    for (Ad ad : members.getAds()) {
                                        if (ad.getId().equals(marker.getTag())) {
//                                        Intent intent = new Intent(activity, AdsDetailsActivity.class);
//                                        intent.putExtra("id", String.valueOf(ad.getId()));
//                                        intent.putExtra("tag",  tag);
//                                        startActivity(intent);

                                            getAddDetails(String.valueOf(ad.getId()), tag);
                                        }


                                    }
                                    return false;
                                }
                            });*/
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
                items.add(new MainItem(R.drawable.ic_mirror, "آرایشگاه", "1"));
                items.add(new MainItem(R.drawable.ic_businesswoman, "آرایشگر", "2"));
                items.add(new MainItem(R.drawable.ic_classroom, "آموزشگاه", "3"));
                items.add(new MainItem(R.drawable.ic_store, "فروشگاه", "5"));
                items.add(new MainItem(R.drawable.ic_teacher, "مدرس", "4"));
                break;
            case 6:
                items.add(new MainItem(R.drawable.ic_mirror, "آرایشگاه", "1"));
                items.add(new MainItem(R.drawable.ic_businesswoman, "آرایشگر", "2"));
                items.add(new MainItem(R.drawable.ic_classroom, "آموزشگاه", "3"));
                items.add(new MainItem(R.drawable.ic_teacher, "مدرس", "4"));
                break;
            case 4:
                items.add(new MainItem(R.drawable.ic_businesswoman, "آرایشگر", "2"));
                items.add(new MainItem(R.drawable.ic_classroom, "آموزشگاه", "3"));
                break;
            case 5:
                items.add(new MainItem(R.drawable.ic_classroom, "آموزشگاه", "3"));
                items.add(new MainItem(R.drawable.ic_teacher, "مدرس", "4"));
                break;
            case 7:
                items.add(new MainItem(R.drawable.ic_mirror, "آرایشگاه", "1"));

                break;
            case 1:
                items.add(new MainItem(R.drawable.ic_mirror, "آرایشگاه", "1"));
                items.add(new MainItem(R.drawable.ic_businesswoman, "آرایشگر", "2"));

                break;
            case 8:
                items.add(new MainItem(R.drawable.ic_classroom, "آموزشگاه", "3"));
                items.add(new MainItem(R.drawable.ic_teacher, "مدرس", "4"));
                break;
            case 9:
                items.add(new MainItem(R.drawable.ic_store, "فروشگاه", "5"));

                break;
            case 2:
                items.add(new MainItem(R.drawable.ic_classroom, "آموزشگاه", "3"));
                items.add(new MainItem(R.drawable.ic_teacher, "مدرس", "4"));
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

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        mMap.setMyLocationEnabled(true);
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
                intent.putExtra("back","3");
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

    private void getAddDetails(String id, final String tag) {
        String url = tools.baseUrl + "single_ad";
        RequestParams params = new RequestParams();
        params.put("type", tag);
        params.put("id", id);
        params.put("api_token", tools.getSharePrf("api_token"));
        params.put("APP_KEY", "bazarayesh:barber:11731e11b");
        tools.client.post(url, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                statusCode = statusCode;
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Gson gson = new Gson();
                try {

                    switch (tag) {
                        case "recruiments":

                            ResponseSingleRecruiment responseSingleRecruiment = gson.fromJson(responseString, ResponseSingleRecruiment.class);
                            responseSingleRecruiment.getAd();

                            AlertDialog("عنوان تخصص: "+responseSingleRecruiment.getAd().getExpertise(),
                                    "موضوع:" + responseSingleRecruiment.getAd().getSubject(), "سطح تخصص: " + responseSingleRecruiment.getAd().getLvl()
                                    , "شرایط استخدام: " + responseSingleRecruiment.getAd().getConditions(), responseSingleRecruiment.getAd().getDescription(),responseSingleRecruiment.getAd().getMemId());
                            break;

                        case "workshops":
                            ResponseSingleWorkShop responseSingleWorkShop = gson.fromJson(responseString, ResponseSingleWorkShop.class);
                            responseSingleWorkShop.getAd();

                            AlertDialog("موضوع کارگاه: "+responseSingleWorkShop.getAd().getSubject(), "در تاریخ: " + responseSingleWorkShop.getAd().getDate()
                                    , "مدرک: " + responseSingleWorkShop.getAd().getEvidence(), responseSingleWorkShop.getAd().getDescription(), "",responseSingleWorkShop.getAd().getMemId());
                            break;


                        case "assignments":

                            ResponseSingleAssign responseSingleAssign = gson.fromJson(responseString, ResponseSingleAssign.class);
                            responseSingleAssign.getAd();

                            AlertDialog("نوع واگذاری: " + responseSingleAssign.getAd().getType()
                                    , "مورد واگذاری: " + responseSingleAssign.getAd().getOptions() + responseSingleAssign.getAd().getDescription()
                                    , "", "", "",responseSingleAssign.getAd().getMemId());

                            break;

                        case "discount_ads":


                            ResponseSingleDiscount responseSingleDiscount=gson.fromJson(responseString,ResponseSingleDiscount.class);
                            responseSingleDiscount.getAd();
                            AlertDialog("تخفیف: "+responseSingleDiscount.getAd().getAffair(),
                                    "از تاریخ: "+responseSingleDiscount.getAd().getSdate()+" تا تاریخ: "+responseSingleDiscount.getAd().getEdate(),
                                    " به مناسبت: "+responseSingleDiscount.getAd().getAdEvent(),
                                    " درصد تخفیف: "+responseSingleDiscount.getAd().getDiscount(),
                                    responseSingleDiscount.getAd().getDescription(),responseSingleDiscount.getAd().getMemId());
                            break;

                        case "brides":

                            ResponseSingleBride responseSingleBride = gson.fromJson(responseString, ResponseSingleBride.class);
                            responseSingleBride.getAd();

                            AlertDialog("در تاریخ :" + responseSingleBride.getAd().getDate()
                                    , "از ساعت: " + responseSingleBride.getAd().getShour() + "  تا ساعت: " + responseSingleBride.getAd().getEhour()
                                    , responseSingleBride.getAd().getDescription(), "", "",responseSingleBride.getAd().getMemId());

                            break;


                        case "courses":

                            ResponseSingleCourse responseSingleCourse = gson.fromJson(responseString, ResponseSingleCourse.class);
                            responseSingleCourse.getAd();

                            AlertDialog("موضوع: " + responseSingleCourse.getAd().getTopic(), "نام دوره: " + responseSingleCourse.getAd().getCourseName()
                                    , "مدرک: " + responseSingleCourse.getAd().getEvidence(),
                                    "مدت دوره: " + responseSingleCourse.getAd().getDuration() + "از تاریخ: " + responseSingleCourse.getAd().getScourse() + " تا تاریخ: " + responseSingleCourse.getAd().getEcourse(),
                                    responseSingleCourse.getAd().getDescription(),responseSingleCourse.getAd().getMemId());
                            break;

                    }


//                    AdDetailsModel model = gson.fromJson(responseString, AdDetailsModel.class);
//                    model.getAd().getName();
                } catch (Exception e) {
                    e.getMessage();
                }
            }
        });

    }

    public void AlertDialog(String title, String subject, String text3, String text4, String text5, final int id) {

        final Dialog message = new Dialog(context);
        message.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        message.setContentView(R.layout.message_details_dialog);
        message.setCancelable(true);
        message.show();
        TextView txtTitle = message.findViewById(R.id.txtTitle);
        TextView txtSubject = message.findViewById(R.id.txtSubject);
        TextView txt3 = message.findViewById(R.id.txtlvl);
        TextView txt4 = message.findViewById(R.id.txtTerms);
        TextView txt5 = message.findViewById(R.id.txtDesc);



        TextView btnExit = message.findViewById(R.id.btnExit);
        TextView btnProfile=message.findViewById(R.id.btnMember);
        btnProfile.setVisibility(View.VISIBLE);
        if (title.equals("")) {
            txtTitle.setVisibility(View.GONE);
        }
        if (subject.equals("")) {
            txtSubject.setVisibility(View.GONE);
        }
        if (text3.equals("")) {
            txt3.setVisibility(View.GONE);
        }
        if (text4.equals("")) {
            txt4.setVisibility(View.GONE);
        }
        if (text5.equals("")) {
            txt5.setVisibility(View.GONE);
        }


        txtTitle.setText(title);
        txtSubject.setText(subject);
        txt3.setText(text3);
        txt4.setText(text4);
        txt5.setText(text5);

        //txtBody.setText();
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                message.dismiss();
            }
        });
        btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(AdsMapActivity.this,ShowMemberDetail.class);
                intent.putExtra("id",id);
                startActivity(intent);
            }
        });

        message.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

    }

    @Override
    public void onBackPressed() {

        Intent intent = new Intent(getAppContext(), MainActivity.class);
        intent.setAction(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra("back","3");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        customType(context, BOTTOM_TO_UP);

        startActivity(intent);
        customType(context, RIGHT_TO_LEFT);

        finish();
    }
}
