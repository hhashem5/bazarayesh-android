package com.idpz.bazarayesh;

import android.Manifest;
import android.animation.Animator;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.idpz.bazarayesh.Models.Award;
import com.idpz.bazarayesh.Models.Course;
import com.idpz.bazarayesh.Models.FamousCustomer;
import com.idpz.bazarayesh.Models.MyMember;
import com.idpz.bazarayesh.Models.Service;
import com.idpz.bazarayesh.Models.UserResponse;
import com.idpz.bazarayesh.Models.WorkplacePic;
import com.idpz.bazarayesh.Utils.Tools;
import com.idpz.bazarayesh.Utils.crop.CropUtil;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.soundcloud.android.crop.Crop;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cz.msebera.android.httpclient.Header;

import static com.bumptech.glide.load.resource.bitmap.TransformationUtils.rotateImage;
import static com.idpz.bazarayesh.BaseActivity.LEFT_TO_RIGHT;
import static com.idpz.bazarayesh.ProfileFragment.edit1;
import static com.idpz.bazarayesh.ProfileFragment.edit2;
import static com.idpz.bazarayesh.ProfileFragment.edit3;
import static com.idpz.bazarayesh.ProfileFragment.edit4;
import static com.idpz.bazarayesh.ProfileFragment.edit5;

import static com.idpz.bazarayesh.SubProfileActivity.*;
import static com.idpz.bazarayesh.Utils.AppController.getAppContext;
import static com.idpz.bazarayesh.Utils.Tools.gson;
import static maes.tech.intentanim.CustomIntent.customType;

public class SubProfileFragment extends BaseFragment implements View.OnClickListener, IOnBackPressed, LocationListener, GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks {
    private Handler mHandler;
    int state = 1;
    Tools tools;
    String mtitle = "";

    private double lat = 0.0, lng = 0.0;

    boolean imgLogoFlag = false;
    private int location_time = 0;
    public static int location_int = 1;
    private boolean doubleBackToExitPressedOnce = false;

    RelativeLayout rel12, relative, page1, page2, page3, relLogo, rel18, rel17, rel29, rel19, rel14,
            rel24, rel6, rel26, rel25, rel27, rel39, rel49, rel59, rel69, rel2, lnRelFrag, rel55, rel79, relRoumer, rel99;
    Button btn1, btn2, btn3;
    LinearLayout linear0, linear, tempLinear, linear2, linear3, linear5, linear6, linear7,
            linear8, linear9, linear10, linear11, linear12, linear13,
            linear14, linear15, linear16, linear18, linear17, linear19, linearRetry;
    ImageView imgbOne, imgbTwo, imgbThree, clickedImageView, clickedDrop, imgPic1, imgPic3, logoimg1;

    ScrollView svL3;


    EditText txtName, txtBoss, txtFame, txtphone, txtaddress, txttelegram, txtinstagram, txtphone2;
    private BottomSheetBehavior mBehavior;
    private BottomSheetDialog mBottomSheetDialog;
    private View bottom_sheet;
    private int TAKE_PICTURE = 720;
    private int PICK_PICTURE = 970;
    private int CROP_PICTURE = 360;
    private File profilePictureDirectory;
    private Bitmap bitmap;

    View inflatedLayout, tempView, v;

    TextView txtTilte, titleimg1, txtPic1, tempTextView, name, boss, romour;
    ImageView myImageView, imgRetry;
    ImageButton imgbDropPic13, closeimg1, imgDropTemp, imgbDropPic2;

    List<View> myViews = new ArrayList<>();
    List<String> keyList = new ArrayList<>();
    List<LinearLayout> myLinears = new ArrayList<>();

    List<View> viewsList1 = new ArrayList<>();
    List<ImageButton> drops1 = new ArrayList<>();
    List<LinearLayout> linearLayouts1 = new ArrayList<>();

    List<View> viewsList = new ArrayList<>();
    List<ImageButton> drops = new ArrayList<>();
    List<LinearLayout> linearLayouts = new ArrayList<>();

    private GoogleApiClient googleApiClient;
    private SupportMapFragment mapFragment;

    private GoogleMap googleMap;

    MapView mMapView;
    private GoogleApiClient mGoogleApiClient;


    private String bytePicMain, bytePic;
    File mainFile, file;

    int service_type, type;
    String title;

    boolean edit = false;

    boolean flag = true;

    boolean memberInfoFlag = false;

    String member_id;
    private int pos = 0;

    String id, key, mtag;

    List<String> ids = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.subprofile_fragment, container, false);
        tools = new Tools(getContext());


        initviews(v);

        checkLocationPermission();

        irsans = Typeface.createFromAsset(getActivity().getAssets(), "fonts/iran_sans.ttf");

        pd = new ProgressDialog(getActivity());
        pd.setCancelable(false);
        pd.setMessage(FontUtils.typeface(irsans, getString(R.string.wait)));

        mMapView.onCreate(savedInstanceState);

        buildGoogleApiClient();


        mMapView.onResume();


        if (tag == 1) {
            boss.setText("نام مدیر آرایشگاه");
            name.setText("نام آرایشگاه");
            romour.setText("شهرت مدیر آرایشگاه");

            //title = "آرایشگاه";
        } else if (tag == 2) {
            boss.setText("نام آرایشگاه محل کار");
            name.setText("نام آرایشگر");
            romour.setText("شهرت آرایشگر");
            //title = "آرایشگر";
        } else if (tag == 3) {
            boss.setText("نام مدیر آموزشگاه");
            name.setText("نام آموزشگاه");

            relRoumer.setVisibility(View.GONE);
            //romour.setText("شهرت مدیر آموزشگاه");

            //title = "آموزشگاه";


        } else if (tag == 4) {
            boss.setText("نام آرایشگاه یا آموزشگاه محل کار");
            name.setText("نام مدرس");
            // romour.setText("شهرت آرایشگر");
            // title = "مدرس";

            relRoumer.setVisibility(View.GONE);
        } else if (tag == 5) {
            //title = "فروشگاه";
            boss.setText("نام مدیر فروشگاه");
            name.setText("نام فروشگاه");
            relRoumer.setVisibility(View.GONE);

        }


        return v;
    }


    public void initviews(View v) {

        svL3 = v.findViewById(R.id.svL3);
        btn1 = v.findViewById(R.id.btn1);
        btn2 = v.findViewById(R.id.btn2);
        btn3 = v.findViewById(R.id.btn3);

        rel12 = v.findViewById(R.id.rel12);
        relLogo = v.findViewById(R.id.relLogo);

        relative = v.findViewById(R.id.relative);
        bottom_sheet = v.findViewById(R.id.bottom_sheet);

        page1 = v.findViewById(R.id.page1);
        page2 = v.findViewById(R.id.page2);
        page3 = v.findViewById(R.id.page3);

        imgbOne = v.findViewById(R.id.imgbOne);
        imgbTwo = v.findViewById(R.id.imgbTwo);
        imgbThree = v.findViewById(R.id.imgbThree);

        txtName = v.findViewById(R.id.txtName);
        txtFame = v.findViewById(R.id.txtfame);
        txtBoss = v.findViewById(R.id.txtBoss);
        txtphone = v.findViewById(R.id.txtphone);
        txtphone2 = v.findViewById(R.id.txtphone2);
        txtaddress = v.findViewById(R.id.txtaddress);
        txttelegram = v.findViewById(R.id.txttelegram);
        txtinstagram = v.findViewById(R.id.txtinstagram);


        imgPic1 = v.findViewById(R.id.imgPic1);
        imgPic3 = v.findViewById(R.id.imgPic3);

        rel18 = v.findViewById(R.id.rel18);
        rel17 = v.findViewById(R.id.rel17);

        rel29 = v.findViewById(R.id.rel29);
        rel19 = v.findViewById(R.id.rel19);
        rel14 = v.findViewById(R.id.rel14);
        rel24 = v.findViewById(R.id.rel24);
        rel26 = v.findViewById(R.id.rel26);
        rel25 = v.findViewById(R.id.rel25);
        rel27 = v.findViewById(R.id.rel27);
        rel39 = v.findViewById(R.id.rel39);
        rel49 = v.findViewById(R.id.rel49);
        rel59 = v.findViewById(R.id.rel59);
        rel69 = v.findViewById(R.id.rel69);
        rel2 = v.findViewById(R.id.rel2);
        rel6 = v.findViewById(R.id.rel6);
        lnRelFrag = v.findViewById(R.id.lnRelFrag);
        rel55 = v.findViewById(R.id.rel55);
        rel79 = v.findViewById(R.id.rel79);
        rel99 = v.findViewById(R.id.rel99);


        txtPic1 = v.findViewById(R.id.txtPic1);
        // titleimg1=v.findViewById(R.id.titleimg1);
        closeimg1 = v.findViewById(R.id.closeimg1);
        imgbDropPic2 = v.findViewById(R.id.imgbDropPic2);

        linear = v.findViewById(R.id.linear);

        logoimg1 = v.findViewById(R.id.logoimg1);

        mBehavior = BottomSheetBehavior.from(bottom_sheet);

        linear2 = v.findViewById(R.id.linear2);
        linear3 = v.findViewById(R.id.linear3);
        linear5 = v.findViewById(R.id.linear5);
        linear6 = v.findViewById(R.id.linear6);
        linear7 = v.findViewById(R.id.linear7);
        linear8 = v.findViewById(R.id.linear8);
        linear9 = v.findViewById(R.id.linear9);
        linear10 = v.findViewById(R.id.linear10);
        linear11 = v.findViewById(R.id.linear11);
        linear12 = v.findViewById(R.id.linear12);
        linear13 = v.findViewById(R.id.linear13);
        linear14 = v.findViewById(R.id.linear14);
        linear15 = v.findViewById(R.id.linear15);
        linear0 = v.findViewById(R.id.linear0);
        linear16 = v.findViewById(R.id.linear16);
        linear17 = v.findViewById(R.id.linear17);
        linear18 = v.findViewById(R.id.linear18);
        linear19 = v.findViewById(R.id.linear19);


        mMapView = (MapView) v.findViewById(R.id.map);

        //  mMapView.setClickable(true);
        romour = v.findViewById(R.id.romour);

        boss = v.findViewById(R.id.boss);

        name = v.findViewById(R.id.Name);

        relRoumer = v.findViewById(R.id.relRoumer);

        rel12.setOnClickListener(this);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        relLogo.setOnClickListener(this);
        closeimg1.setOnClickListener(this);
        rel18.setOnClickListener(this);
        rel17.setOnClickListener(this);
        rel29.setOnClickListener(this);
        rel19.setOnClickListener(this);
        rel14.setOnClickListener(this);
        rel24.setOnClickListener(this);
        rel26.setOnClickListener(this);
        rel25.setOnClickListener(this);
        rel27.setOnClickListener(this);
        rel39.setOnClickListener(this);
        rel49.setOnClickListener(this);
        rel59.setOnClickListener(this);
        rel69.setOnClickListener(this);
        imgbDropPic2.setOnClickListener(this);
        rel2.setOnClickListener(this);
        rel6.setOnClickListener(this);
        rel79.setOnClickListener(this);
        rel55.setOnClickListener(this);
        rel99.setOnClickListener(this);
//        googleMap.setOnMapClickListener(this);


        // turnOnGPS();
        //  mylocation=v.findViewById(R.id.currentlocation);
        // needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        ImageView transparent_image = v.findViewById(R.id.transparent_image);

        transparent_image.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        // Disallow ScrollView to intercept touch events.
                        svL3.requestDisallowInterceptTouchEvent(true);
                        // Disable touch on transparent view
                        return false;

                    case MotionEvent.ACTION_UP:
                        // Allow ScrollView to intercept touch events.
                        svL3.requestDisallowInterceptTouchEvent(false);
                        return true;

                    case MotionEvent.ACTION_MOVE:
                        svL3.requestDisallowInterceptTouchEvent(true);
                        return false;

                    default:
                        return true;
                }
            }
        });


        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(final GoogleMap mMap) {


                googleMap = mMap;
                LatLng latLong = new LatLng(35.684209, 51.388263);

                if (!edit) {
                    CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(35.709561, 51.372652), 5);
                    googleMap.moveCamera(cameraUpdate);
                }

//                mMap.addMarker(new MarkerOptions()
//                        .position(latLong)
//                        .draggable(true));
                mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                    @Override
                    public void onMapClick(LatLng point) {
                        googleMap.clear();
                        googleMap.animateCamera(CameraUpdateFactory.newLatLng(point));
                        googleMap.addMarker(new MarkerOptions()
                                .position(point)
                                .draggable(true));
                        lat = point.latitude;
                        lng = point.longitude;

                        lnRelFrag.setBackgroundResource(R.drawable.dashed_back_green);
                    }
                });

                if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                mMap.setMyLocationEnabled(true);

//                if (!edit) {
//                    CameraPosition cameraPosition = new CameraPosition.Builder()
//                            .target(latLong).zoom(18).build();
//
//                    mMap.animateCamera(CameraUpdateFactory
//                            .newCameraPosition(cameraPosition));
//
//                }

                mMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
                    @Override
                    public void onMyLocationChange(Location location) {
                        if (!edit) {
                            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 18);
                            mMap.moveCamera(cameraUpdate);
                        }
                    }
                });


                // For showing a move to my location button
                if (ActivityCompat.checkSelfPermission(

                        getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(

                        getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                googleMap.setMyLocationEnabled(true);
                googleMap.getUiSettings().

                        setMyLocationButtonEnabled(false);


                mMap.getUiSettings().setMyLocationButtonEnabled(true);
                mMap.setMyLocationEnabled(true);

                try {

                    // زمانی که edit وارد بشه اجرا میشه
                    if (lat != 0) {
                        googleMap.clear();
                        googleMap.addMarker(new MarkerOptions()
                                .position(new LatLng(lat, lng))
                                .draggable(true));


                        CameraPosition cameraPosition = new CameraPosition.Builder()
                                .target(new LatLng(lat, lng)).zoom(18).build();

                        googleMap.animateCamera(CameraUpdateFactory
                                .newCameraPosition(cameraPosition));
                    }

                } catch (Exception e) {
                }
            }
        });


        prepareProfilePictureDirectory();

        getMemberInfo();

        switch (tag) {
            case 1:
                if (edit1)
                    edit = true;
                break;

            case 2:

                if (edit2)
                    edit = true;
                break;

            case 3:
                if (edit3)
                    edit = true;
                break;

            case 4:

                if (edit4)
                    edit = true;
                break;

            case 5:

                if (edit5)
                    edit = true;
                break;
        }


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        final File photo = new File(Environment.getExternalStorageDirectory(), "/arayesh/Picture.jpg");

        try {
            if (requestCode == TAKE_PICTURE && resultCode == Activity.RESULT_OK) {

                Crop.of(Uri.fromFile(photo), Uri.fromFile(profilePictureDirectory))
                        .withMaxSize(900, 900)
                        .withAspect(1, 1)
                        .start(getActivity(), SubProfileFragment.this, CROP_PICTURE);


            }

        } catch (Exception e) {
            Log.d("pari", e.toString());
        }
        try {
            if (requestCode == PICK_PICTURE && resultCode == Activity.RESULT_OK) {
                Crop.of(data.getData(), Uri.fromFile(profilePictureDirectory))
                        .withMaxSize(900, 900)
                        .withAspect(16, 9)
                        .start(getActivity(), SubProfileFragment.this, CROP_PICTURE);


            }
        } catch (Exception e) {
            e.getMessage();
        }
        if (requestCode == CROP_PICTURE && resultCode == Activity.RESULT_OK) {
            photo.delete();

            if (clickedImageView == myImageView) {

                try {


                    tempLinear.addView(tempView);
                    myViews.add(tempView);
                    myLinears.add(tempLinear);
                } catch (Exception e) {
                }


            }

            clickedImageView.post(new Runnable() {
                @Override
                public void run() {
                    try {


                        int availableBytes = (int) profilePictureDirectory.length();
                        FileInputStream inputStream = new FileInputStream(profilePictureDirectory);


                        byte[] mdata = new byte[availableBytes];
                        int totalBytesRead = 0;
                        int bytesRead = 0;
                        while (bytesRead != -1 && totalBytesRead < availableBytes) {

                            bytesRead = inputStream.read(mdata, totalBytesRead, availableBytes - totalBytesRead);

                            totalBytesRead += bytesRead;
                        }

                        inputStream.close();


                        ExifInterface ei = new ExifInterface(profilePictureDirectory.getPath());
                        int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                                ExifInterface.ORIENTATION_UNDEFINED);
                        bitmap = ImageTools.createBitmap(mdata, 512, 512);
                        bitmap = ImageTools.rotateImage(bitmap, CropUtil.getExifRotation(profilePictureDirectory));
                        Bitmap rotatedBitmap = null;
                        switch (orientation) {

                            case ExifInterface.ORIENTATION_ROTATE_90:
                                rotatedBitmap = rotateImage(bitmap, 0);
                                break;

                            case ExifInterface.ORIENTATION_ROTATE_180:
                                rotatedBitmap = rotateImage(bitmap, 180);
                                break;

                            case ExifInterface.ORIENTATION_ROTATE_270:
                                rotatedBitmap = rotateImage(bitmap, 270);
                                break;

                            case ExifInterface.ORIENTATION_NORMAL:
                            default:
                                rotatedBitmap = bitmap;
                        }


                        //           bitmap = RoundImageView.getRoundedBitmap(bitmap, profilePicture.getWidth());
                        Bitmap bitmap1 = scaleDown(rotatedBitmap, 800, true);
                        //     bitmap = RoundImageView.getRoundedBitmap(bitmap, profilePicture.getWidth());
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();

                        bitmap1.compress(Bitmap.CompressFormat.JPEG, 100, baos);

                        byte[] imageBytes = baos.toByteArray();

                        if (clickedImageView == logoimg1) {

                            logoimg1.setVisibility(View.VISIBLE);
                            txtPic1.setVisibility(View.GONE);
                            imgPic1.setVisibility(View.GONE);
                            closeimg1.setVisibility(View.VISIBLE);
                            imgLogoFlag = true;

                            bytePicMain = Base64.encodeToString(imageBytes, Base64.DEFAULT);
                            mainFile = saveImage(getAppContext(), bytePicMain);

                        } else {
                            bytePic = Base64.encodeToString(imageBytes, Base64.DEFAULT);
                            file = saveImage(getAppContext(), bytePic);

//
//                            switch (tempLinear.getId()) {
//                                case R.id.linear:
//                                    membersFilesRegister(6,,"", file, title);
//
//                                    break;
//                            }
                            //اپلاسیون
                            if (tempLinear == linear) {

                                membersFilesRegister("service", 1, "service_id", "1", file, title);

                                mtag = "service";
                                key = "service_id";

                            } else if (tempLinear == linear0) {
                                membersFilesRegister("famous_customer", 6, "famous_customer_id", "", file, title);

                                mtag = "famous_customer";
                                key = "famous_customer_id";


                                //تاتو
                            } else if (tempLinear == linear2) {
                                membersFilesRegister("service", 1, "service_id", "2", file, title);
                                mtag = "service";
                                key = "service_id";


                                // مراقبت از پوست
                            } else if (tempLinear == linear3) {
                                membersFilesRegister("service", 1, "service_id", "3", file, title);

                                mtag = "service";
                                key = "service_id";


                                //شینیون
                            } else if (tempLinear == linear5) {
                                membersFilesRegister("service", 1, "service_id", "5", file, title);

                                mtag = "service";
                                key = "service_id";


                                //کوتاهی مو
                            } else if (tempLinear == linear6) {
                                membersFilesRegister("service", 1, "service_id", "6", file, title);


                                mtag = "service";
                                key = "service_id";

                                //رنگ و مش
                            } else if (tempLinear == linear7) {
                                membersFilesRegister("service", 1, "service_id", "7", file, title);
                                mtag = "service";
                                key = "service_id";


                                //کراتین
                            } else if (tempLinear == linear8) {
                                membersFilesRegister("service", 1, "service_id", "10", file, title);

                                mtag = "service";
                                key = "service_id";

                                //اکستنشن مو
                            } else if (tempLinear == linear9) {
                                membersFilesRegister("service", 1, "service_id", "9", file, title);


                                mtag = "service";
                                key = "service_id";

                                //بافت و براشینگ
                            } else if (tempLinear == linear10) {
                                membersFilesRegister("service", 1, "service_id", "8", file, title);


                                mtag = "service";
                                key = "service_id";

                                //میکاپ وگریم عمومی
                            } else if (tempLinear == linear11) {
                                membersFilesRegister("service", 1, "service_id", "11", file, title);


                                mtag = "service";
                                key = "service_id";

                                //ابرو و میکروپیگمنتیشن
                            } else if (tempLinear == linear12) {
                                membersFilesRegister("service", 1, "service_id", "15", file, title);


                                mtag = "service";
                                key = "service_id";

                                //ناخن
                            } else if (tempLinear == linear13) {
                                membersFilesRegister("service", 1, "service_id", "14", file, title);

                                mtag = "service";
                                key = "service_id";


                                //اکستنشن مژه
                            } else if (tempLinear == linear14) {
                                membersFilesRegister("service", 1, "service_id", "13", file, title);


                                mtag = "service";
                                key = "service_id";

                                //خودآرایی
                            } else if (tempLinear == linear15) {
                                membersFilesRegister("service", 1, "service_id", "4", file, title);


                                mtag = "service";
                                key = "service_id";

                            } else if (tempLinear == linear16) {
                                membersFilesRegister("course", 3, "course_id", "", file, title);


                                mtag = "course";
                                key = "course_id";

                            } else if (tempLinear == linear17) {
                                membersFilesRegister("award", 2, "award_id", "", file, title);
                                mtag = "award";
                                key = "award_id";

                            } else if (tempLinear == linear18) {
                                membersFilesRegister("workplace_pic", 4, "workplace_pic_id", "", file, title);
                                mtag = "workplace_pic";
                                key = "workplace_pic_id";
                            }
                            //آرایش ویژه عروس
                            else if (tempLinear == linear19) {
                                membersFilesRegister("service", 1, "service_id", "12", file, title);
                                mtag = "workplace_pic";
                                key = "workplace_pic_id";
                            }

                        }


                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bitmap1.compress(Bitmap.CompressFormat.PNG, 100, stream);
                        Glide.with(getContext())
                                .load(stream.toByteArray())
                                .into(clickedImageView);


                    } catch (Exception E) {
                        E.getMessage();
                    }


                }
            });

        }
    }

    private void showBottomSheetDialog() {
        if (mBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
            mBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }

        final View view = getLayoutInflater().inflate(R.layout.select_pic, null);

        (view.findViewById(R.id.lnCamera)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                takePicture();
            }
        });


        (view.findViewById(R.id.lnGallery)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(
                        Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, PICK_PICTURE);
            }
        });

        mBottomSheetDialog = new BottomSheetDialog(getContext());
        mBottomSheetDialog.setContentView(view);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mBottomSheetDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        mBottomSheetDialog.show();
        mBottomSheetDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                mBottomSheetDialog = null;
            }
        });
    }

    void takePicture() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {

                File photo = new File(Environment.getExternalStorageDirectory(), "/arayesh/");
                photo.mkdirs();
                photo = new File(photo, "Picture.jpg");
                try {
                    photo.delete();
                    photo.createNewFile();
                } catch (IOException ignored) {
                }

                // launching camera activity
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {

                    String authority = BuildConfig.APPLICATION_ID + ".provider";

                    cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                            FileProvider.getUriForFile(getContext(), authority, photo));

                } else {

                    cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photo));
                }
                if (cameraIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                    startActivityForResult(cameraIntent, TAKE_PICTURE);
                }

            } else {

                String[] permissions = new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(permissions, TAKE_PICTURE);
                }
            }

        } else {


            File photo = new File(Environment.getExternalStorageDirectory(), "/arayesh/");
            photo.mkdirs();
            photo = new File(photo, "Picture.jpg");
            try {
                photo.delete();
                photo.createNewFile();
            } catch (IOException ignored) {
            }

            // launching camera activity

            Uri photoURI = Uri.fromFile(photo);


            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
            cameraIntent.setFlags(0);
            if (cameraIntent.resolveActivity(getContext().getPackageManager()) != null) {
                startActivityForResult(cameraIntent, TAKE_PICTURE);
            }

        }

    }


    void prepareProfilePictureDirectory() {
        mHandler = new Handler();
        new Thread(new Runnable() {
            @Override
            public void run() {
                // Perform long-running task here
                // (like audio buffering).
                // you may want to update some progress
                // bar every second, so use handler:
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        // make operation on UI - on example
                        // on progress bar.
                        try {

                            profilePictureDirectory = new File(getContext().getFilesDir(), "/ProfilePicture");
                            profilePictureDirectory.mkdirs();
                            profilePictureDirectory = new File(profilePictureDirectory, "/Picture.jpg");
                            if (!profilePictureDirectory.exists()) {
                                profilePictureDirectory.createNewFile();
                            }

                        } catch (IOException ignored) {

                            ignored.printStackTrace();
                        }
                    }
                });
            }
        }).start();

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.rel12:

                AlertDialog(linear);
                //btnSelect(myImageView);

                break;


            case R.id.relLogo:

                clickedImageView = logoimg1;

                imgDropTemp = imgbDropPic2;

                showBottomSheetDialog();

                break;


            case R.id.btn1:

                if (!txtName.getText().toString().equals("")) {

                    YoYo.with(Techniques.SlideInLeft)
                            .duration(300)
                            .repeat(0).withListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {

                            page1.setVisibility(View.GONE);
                            page2.setVisibility(View.VISIBLE);
                            imgbOne.setImageResource(R.drawable.step1);
                            imgbTwo.setImageResource(R.drawable.step32);
                            imgbThree.setImageResource(R.drawable.step2);

                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {

                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {

                        }
                    })
                            .playOn(v.findViewById(R.id.page1));

                    state = 2;


                } else {
                    txtName.setError(" نام را وارد کنید ");

                    txtName.requestFocus();
                }
                break;

            case R.id.btn2:

                if (!txtphone.getText().toString().equals("")) {
                    if (!txtphone2.getText().toString().equals("")) {
                        if (lat != 0) {

                            pd.show();
                            getMemberRegister();
                        } else {

                            tools.alertShow("موقعیت مکانی روی نقشه حتما باید انتخاب شود");
                        }

                    } else {
                        txtphone2.setError("تلفن ثابت حتما باید وارد شود");
                        txtphone2.requestFocus();
                    }
                } else {

                    txtphone.setError("شماره مبایل حتما باید وارد شود");
                    txtphone.requestFocus();
                }


// todo agar   khastim back bezane va dige request nazane
//                if (flag) {//&& !memberInfoFlag
//                    pd.show();
//                    getMemberRegister();
//                } else {
//                    YoYo.with(Techniques.SlideInLeft)
//                            .duration(300)
//                            .repeat(0).withListener(new Animator.AnimatorListener() {
//                        @Override
//                        public void onAnimationStart(Animator animation) {
//                            page2.setVisibility(View.GONE);
//                            page3.setVisibility(View.VISIBLE);
//                            imgbOne.setImageResource(R.drawable.step1);
//                            imgbTwo.setImageResource(R.drawable.step3);
//                            imgbThree.setImageResource(R.drawable.step22);
//                        }
//                        @Override
//                        public void onAnimationEnd(Animator animation) {
//                        }
//                        @Override
//                        public void onAnimationCancel(Animator animation) {
//                        }
//                        @Override
//                        public void onAnimationRepeat(Animator animation) {
//                        }
//                    }).playOn(v.findViewById(R.id.page2));
//                    state = 3;
//                }
                break;

            case R.id.btn3:

                successDialog("پروفایل شما با موفقیت به روز رسانی شد.");

                break;

            case R.id.closeimg1:


                txtPic1.setVisibility(View.VISIBLE);
                imgPic1.setVisibility(View.VISIBLE);
                closeimg1.setVisibility(View.GONE);
                logoimg1.setVisibility(View.GONE);

                //
                // deletePic();
                break;

            case R.id.rel18:

                AlertDialog(linear2);
                break;

            case R.id.rel17:

                AlertDialog(linear3);
                break;

            case R.id.rel29:
                AlertDialog(linear5);
                break;

            case R.id.rel19:
                AlertDialog(linear6);

                break;

            case R.id.rel14:
                AlertDialog(linear7);
                break;

            case R.id.rel24:
                AlertDialog(linear8);
                break;

            case R.id.rel26:
                AlertDialog(linear9);
                break;

            case R.id.rel39:
                AlertDialog(linear12);
                break;

            case R.id.rel27:
                AlertDialog(linear11);
                break;
            case R.id.rel49:
                AlertDialog(linear13);
                break;
            case R.id.rel59:
                AlertDialog(linear14);
                break;
            case R.id.rel69:
                AlertDialog(linear15);
                break;

            case R.id.rel2:
                AlertDialog(linear0);
                break;

            case R.id.rel6:
                AlertDialog(linear16);
                break;
            case R.id.rel55:
                AlertDialog(linear18);
                break;

            case R.id.rel79:
                AlertDialog(linear17);
                break;

            case R.id.rel25:
                AlertDialog(linear10);

                break;

            case R.id.rel99:
                AlertDialog(linear19);

        }
    }

    private void btnSelect(ImageView imageView, ImageButton imageButton, LinearLayout linear) {


        clickedImageView = imageView;

        imgDropTemp = imageButton;

        tempLinear = linear;

        showBottomSheetDialog();


    }

    public void AlertDialog(final LinearLayout linear) {
        Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(), "fonts/IRANSans(FaNum).ttf");

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage(FontUtils.typeface(typeface, "عنوان را وارد کنید"));
        final EditText input = new EditText(getContext());
        input.setPadding(40, 20, 40, 20);
        input.setTypeface(typeface);
        input.setGravity(Gravity.RIGHT);

        builder.setView(input);
        builder.setPositiveButton(FontUtils.typeface(typeface, "تایید"), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                inflatedLayout = getLayoutInflater().inflate(R.layout.my_image_layout, null);

                txtTilte = inflatedLayout.findViewById(R.id.title);
                myImageView = inflatedLayout.findViewById(R.id.img);
                imgbDropPic13 = inflatedLayout.findViewById(R.id.imgbDropPic1);
                imgRetry = inflatedLayout.findViewById(R.id.retry);
                linearRetry = inflatedLayout.findViewById(R.id.linearRetry);

                tempView = inflatedLayout;
                btnSelect(myImageView, imgbDropPic13, linear);
                txtTilte.setText(input.getText().toString());
                tempTextView = txtTilte;


                viewsList.add(inflatedLayout);
                drops.add(imgbDropPic13);
                linearLayouts.add(linear);

                title = input.getText().toString();
                dialog.dismiss();
            }
        });

        builder.setNegativeButton(FontUtils.typeface(typeface, "انصراف"), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.show();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mBottomSheetDialog != null)
            mBottomSheetDialog.dismiss();
    }


    @Override
    public boolean onBackPressed() {

        switch (state) {

            case 1:


                if (doubleBackToExitPressedOnce) {
                    Intent i1 = new Intent(getContext(), MainActivity.class);
                    i1.putExtra("back", "1");
                    i1.setAction(Intent.ACTION_MAIN);

                    i1.addCategory(Intent.CATEGORY_HOME);
                    i1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    i1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    i1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    getActivity().startActivity(i1);
                    customType(getContext(), LEFT_TO_RIGHT);
                    getActivity().finish();
                }

                doubleBackToExitPressedOnce = true;
                Toast.makeText(getContext(), "برای خروج دوباره کلید بازگشت را بزنید", Toast.LENGTH_SHORT).show();
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        doubleBackToExitPressedOnce = false;
                    }
                }, 2000);

                break;
            case 2:

                YoYo.with(Techniques.SlideInLeft)
                        .duration(300)
                        .repeat(0).withListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                        page1.setVisibility(View.VISIBLE);
                        page2.setVisibility(View.GONE);
                        imgbOne.setImageResource(R.drawable.step12);
                        imgbTwo.setImageResource(R.drawable.step3);
                        imgbThree.setImageResource(R.drawable.step2);

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {

                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                }).playOn(v.findViewById(R.id.page2));

                state = 1;
                break;

            case 3:

                YoYo.with(Techniques.SlideInLeft)
                        .duration(300)
                        .repeat(0).withListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                        page2.setVisibility(View.VISIBLE);
                        page3.setVisibility(View.GONE);
                        imgbOne.setImageResource(R.drawable.step1);
                        imgbTwo.setImageResource(R.drawable.step32);
                        imgbThree.setImageResource(R.drawable.step2);

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {

                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                }).playOn(v.findViewById(R.id.page2));

                state = 2;
                break;

        }

        return false;
    }

    public void getMemberRegister() {

        RequestParams params = new RequestParams();

        params.put("type", tag);
        params.put("full_name", txtName.getText().toString());
        params.put("manager_name", txtBoss.getText().toString());
        params.put("phone1", txtphone.getText().toString());
        if (txtphone2.getText().toString() != null)
            params.put("phone2", txtphone2.getText().toString());


        params.put("address", txtaddress.getText().toString());
        params.put("telegram", txttelegram.getText().toString());
        params.put("instagram", txtinstagram.getText().toString());
        params.put("lat", lat);//todo lat
        params.put("lng", lng);//todo lng
        try {
            params.put("logo", mainFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        params.put("api_token", tools.getSharePrf("api_token"));
        params.put("APP_KEY", "bazarayesh:barber:11731e11b");


        if (!tools.getSharePrf("memberId1").equals("") || !tools.getSharePrf("memberId2").equals("") || !tools.getSharePrf("memberId3").equals("") || !tools.getSharePrf("memberId4").equals("") || !tools.getSharePrf("memberId5").equals("")) {
            switch (tag) {

                case 1:
                    params.put("member_id", tools.getSharePrf("memberId1"));

                    params.put("tag", "edit");
                    break;
                case 2:
                    params.put("member_id", tools.getSharePrf("memberId2"));
                    params.put("tag", "edit");

                    break;
                case 3:
                    params.put("member_id", tools.getSharePrf("memberId3"));
                    params.put("tag", "edit");

                    break;
                case 4:
                    params.put("member_id", tools.getSharePrf("memberId4"));
                    params.put("tag", "edit");

                    break;
                case 5:
                    params.put("member_id", tools.getSharePrf("memberId5"));
                    params.put("tag", "edit");

                    break;
            }
        }
        String url = tools.baseUrl + "membersRegister";


        tools.client.post(url, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                pd.dismiss();

                Toast.makeText(getContext(), "ثبت اطلاعات انجام نشد لطفا بعدا دوباره امتحان کنید", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                try {

                    pd.dismiss();

                    if (responseString.contains("\"code\":200")) {

                        flag = false;
                        UserResponse response = gson.fromJson(responseString, UserResponse.class);

                        switch (tag) {
                            case 1:
                                tools.addToSharePrf("memberId1", response.getMember().getId().toString());

                                break;
                            case 2:
                                tools.addToSharePrf("memberId2", response.getMember().getId().toString());

                                break;

                            case 3:
                                tools.addToSharePrf("memberId3", response.getMember().getId().toString());

                                break;
                            case 4:
                                tools.addToSharePrf("memberId4", response.getMember().getId().toString());

                                break;
                            case 5:

                                tools.addToSharePrf("memberId5", response.getMember().getId().toString());

                                break;
                        }

                        tools.addToSharePrf("userId", response.getMember().getId().toString());

                    }
                } catch (Exception e) {
                    Log.e("parisa", e.toString());
                }


                YoYo.with(Techniques.SlideInLeft)
                        .duration(300)
                        .repeat(0).withListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                        page2.setVisibility(View.GONE);
                        page3.setVisibility(View.VISIBLE);
                        imgbOne.setImageResource(R.drawable.step1);
                        imgbTwo.setImageResource(R.drawable.step3);
                        imgbThree.setImageResource(R.drawable.step22);

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {

                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                }).playOn(v.findViewById(R.id.page2));

                state = 3;
            }
        });
    }


    public void membersFilesRegister(final String tag, final int type, final String key, final String service_type, final File pic, final String title) throws FileNotFoundException {

        RequestParams params = new RequestParams();
        pd.show();

        linearRetry.setVisibility(View.GONE);


        params.put("id", tools.getSharePrf("userId"));
        params.put("api_token", tools.getSharePrf("api_token"));
        params.put("APP_KEY", "bazarayesh:barber:11731e11b");
        params.put("data", pic);
        params.put("type", type);
        if (!service_type.equals(""))
            params.put("service_type", service_type);
        if (!title.equals(""))
            params.put("title", title);

        String url = tools.baseUrl + "membersFilesRegister";

        tools.client.post(url, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                pd.dismiss();
//                Glide.with(getContext())
//                        .load(R.drawable.retry)
//                        .into(clickedImageView);
//                clickedImageView.setBackgroundColor(getResources().getColor(R.color.grey_60,null));

                imgDropTemp.setVisibility(View.GONE);

                tempTextView.setVisibility(View.GONE);

                linearRetry.setVisibility(View.VISIBLE);
                imgRetry.setImageResource(R.drawable.retry);
                linearRetry.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            membersFilesRegister(tag, type, key, service_type, pic, title);
                            imgDropTemp.setVisibility(View.VISIBLE);

                            tempTextView.setVisibility(View.VISIBLE);


                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {

                pd.dismiss();


                try {
                    JSONObject jsonObject = new JSONObject(responseString);
                    id = jsonObject.get("id").toString();
                    ids.add(id);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                linear.setTag(id);
                imgbDropPic13.setTag(id);
                inflatedLayout.setTag(id);

                viewsList1.add(inflatedLayout);
                drops1.add(imgbDropPic13);
                linearLayouts.add(linear);


                for (int i = 0; i < drops1.size(); i++) {
                    final int finalI = i;
                    for (int j = 0; j < drops1.size(); j++) {
                        final int finalJ = j;
                        imgbDropPic13.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (drops1.get(finalJ).getTag() == viewsList1.get(finalJ).getTag()) {
                                    viewsList1.get(finalJ).setVisibility(View.GONE);
                                    deletePic(mtag, key, ids.get(finalI), inflatedLayout, tools.getSharePrf("userId"), linear);
                                }
                            }
                        });
                    }
                }


            }
        });

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        int permissionCheck = ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_FINE_LOCATION);
        if (ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) getContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions((Activity) getContext(),
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);

                // MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(35.709561, 51.372652), 18);
        googleMap.moveCamera(cameraUpdate);
        googleMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
            @Override
            public void onMyLocationChange(Location location) {
                CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 18);
                googleMap.moveCamera(cameraUpdate);
            }
        });
    }


    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        try {

            LocationServices.FusedLocationApi.removeLocationUpdates(
                    mGoogleApiClient, this);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    protected synchronized void buildGoogleApiClient() {

        mGoogleApiClient = new GoogleApiClient.Builder(getContext())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API).build();
    }


    public void successDialog(String text) {

        final Dialog message = new Dialog(getContext());
        message.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        message.setContentView(R.layout.success_dialog);
        message.setCancelable(true);
        message.show();
        TextView txtTitle = message.findViewById(R.id.txtTitle);
        TextView txtBody = message.findViewById(R.id.txtBody);
        TextView btnExit = message.findViewById(R.id.btnExit);
        txtTitle.setText("پیام");
        txtBody.setText(text);
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i1 = new Intent(getContext(), MainActivity.class);
                i1.putExtra("back", "1");
                i1.setAction(Intent.ACTION_MAIN);
                i1.addCategory(Intent.CATEGORY_HOME);
                i1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                i1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i1);

                customType(getContext(), RIGHT_TO_LEFT);


                getActivity().finish();
                message.dismiss();
            }
        });
        message.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

    }


    public void getMemberInfo() {

        String url = tools.baseUrl + "member_info";

        RequestParams params = new RequestParams();
        switch (tag) {
            case 1:
                member_id = tools.getSharePrf("memberId1");
                //    params.put("id", );

                break;
            case 2:

                member_id = tools.getSharePrf("memberId2");
                // params.put("id", tools.getSharePrf("memberId2"));

                break;
            case 3:
                member_id = tools.getSharePrf("memberId3");

                //  params.put("id", tools.getSharePrf("memberId3"));

                break;
            case 4:
                member_id = tools.getSharePrf("memberId4");

                // params.put("id", tools.getSharePrf("memberId4"));

                break;
            case 5:
                member_id = tools.getSharePrf("memberId5");

                // params.put("id", tools.getSharePrf("memberId5"));

                break;
        }
        try {
            params.put("id", member_id);
        } catch (Exception e) {
        }

        params.put("api_token", tools.getSharePrf("api_token"));
        params.put("APP_KEY", "bazarayesh:barber:11731e11b");

        tools.client.post(url, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                tools.noInternet(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if (tools.isNetworkAvailable()) {
                            getMemberInfo();
                            tools.hideInternet();
                        } else
                            Toast.makeText(getContext(), getString(R.string.nonet), Toast.LENGTH_SHORT).show();

                    }
                });
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {

                try {

                    // memberInfoFlag=true;

                    MyMember response = gson.fromJson(responseString, MyMember.class);

//
                    txtName.setText(response.getData().getFullName());

                    txtBoss.setText(response.getData().getManagerName());

                    txtphone.setText(response.getData().getPhone1());
                    txtaddress.setText(response.getData().getAddress());
                    txtinstagram.setText(response.getData().getInstagram());
                    txttelegram.setText(response.getData().getTelegram());
                    txtphone2.setText(response.getData().getPhone2());

                    Glide.with(getContext())
                            .load("http://arayesh.myzibadasht.ir/" + response.getData().getLogo())
                            .into(logoimg1);


                    for (final Award award : response.getData().getAward()) {


                        setImage("http://arayesh.myzibadasht.ir" + award.getData(), linear17, "award", "award_id", award.getId().toString(), award.getTitle(), member_id);

                        //todo public bayad hazf beshe
                    }
                    for (Service service : response.getData().getService()) {


                        switch (service.getServiceType()) {

                            case 1:

//todo arayesh vize aros
                                //   setImage("http://arayesh.myzibadasht.ir/" + service.getPic(), linear17);

                                //اپلاسیون
                                tempLinear = linear;

                                if (service.getTitle() != null)
                                    mtitle = service.getTitle();
                                else mtitle = "";

                                setImage("http://arayesh.myzibadasht.ir/" + service.getPic(), linear, "service", "service_id", service.getId().toString(), mtitle, member_id);


                                break;

                            case 2:
                                //تاتو
                                tempLinear = linear2;
                                if (service.getTitle() != null)
                                    mtitle = service.getTitle();
                                else mtitle = "";

                                setImage("http://arayesh.myzibadasht.ir/" + service.getPic(), linear2, "service", "service_id", service.getId().toString(), mtitle, member_id);

                                break;
                            case 3:

                                tempLinear = linear3;
                                if (service.getTitle() != null)
                                    mtitle = service.getTitle();
                                else mtitle = "";

                                setImage("http://arayesh.myzibadasht.ir/" + service.getPic(), linear3, "service", "service_id", service.getId().toString(), mtitle, member_id);

                                break;
                            case 4:
                                tempLinear = linear15;
                                if (service.getTitle() != null)
                                    mtitle = service.getTitle();
                                else mtitle = "";

                                setImage("http://arayesh.myzibadasht.ir/" + service.getPic(), linear15, "service", "service_id", service.getId().toString(), mtitle, member_id);

                                break;

                            case 5:
                                tempLinear = linear5;
                                if (service.getTitle() != null)
                                    mtitle = service.getTitle();
                                else mtitle = "";

                                setImage("http://arayesh.myzibadasht.ir/" + service.getPic(), linear5, "service", "service_id", service.getId().toString(), mtitle, member_id);

                                break;

                            case 6:
                                tempLinear = linear6;
                                if (service.getTitle() != null)
                                    mtitle = service.getTitle();
                                else mtitle = "";

                                setImage("http://arayesh.myzibadasht.ir/" + service.getPic(), linear6, "service", "service_id", service.getId().toString(), mtitle, member_id);

                                break;

                            case 7:
                                tempLinear = linear7;
                                if (service.getTitle() != null)
                                    mtitle = service.getTitle();
                                else mtitle = "";

                                setImage("http://arayesh.myzibadasht.ir/" + service.getPic(), linear7, "service", "service_id", service.getId().toString(), mtitle, member_id);

                                break;
                            case 8:
                                tempLinear = linear10;
                                if (service.getTitle() != null)
                                    mtitle = service.getTitle();
                                else mtitle = "";

                                setImage("http://arayesh.myzibadasht.ir/" + service.getPic(), linear10, "service", "service_id", service.getId().toString(), mtitle, member_id);

                                break;
                            case 9:
                                tempLinear = linear9;
                                if (service.getTitle() != null)
                                    mtitle = service.getTitle();
                                else mtitle = "";

                                setImage("http://arayesh.myzibadasht.ir/" + service.getPic(), linear9, "service", "service_id", service.getId().toString(), mtitle, member_id);

                                break;
                            case 10:
                                tempLinear = linear8;
                                if (service.getTitle() != null)
                                    mtitle = service.getTitle();
                                else mtitle = "";
                                setImage("http://arayesh.myzibadasht.ir/" + service.getPic(), linear8, "service", "service_id", service.getId().toString(), mtitle, member_id);

                                break;
                            case 11:
                                tempLinear = linear11;
                                if (service.getTitle() != null)
                                    mtitle = service.getTitle();
                                else mtitle = "";

                                setImage("http://arayesh.myzibadasht.ir/" + service.getPic(), linear11, "service", "service_id", service.getId().toString(), mtitle, member_id);

                                break;
                            case 12:
                                tempLinear = linear19;

                                if (service.getTitle() != null)
                                    mtitle = service.getTitle();
                                else mtitle = "";

                                setImage("http://arayesh.myzibadasht.ir/" + service.getPic(), linear19, "service", "service_id", service.getId().toString(), mtitle, member_id);

                                break;
                            case 13:
                                tempLinear = linear14;

                                if (service.getTitle() != null)
                                    mtitle = service.getTitle();
                                else mtitle = "";

                                setImage("http://arayesh.myzibadasht.ir/" + service.getPic(), linear14, "service", "service_id", service.getId().toString(), mtitle, member_id);

                                break;
                            case 14:
                                tempLinear = linear13;
                                if (service.getTitle() != null)
                                    mtitle = service.getTitle();
                                else mtitle = "";

                                setImage("http://arayesh.myzibadasht.ir/" + service.getPic(), linear13, "service", "service_id", service.getId().toString(), mtitle, member_id);

                                break;
                            case 15:
                                tempLinear = linear12;
                                if (service.getTitle() != null)
                                    mtitle = service.getTitle();
                                else mtitle = "";

                                setImage("http://arayesh.myzibadasht.ir/" + service.getPic(), linear12, "service", "service_id", service.getId().toString(), mtitle, member_id);

                                break;


                        }


                    }

                    for (Course course : response.getData().getCourse()) {
                        if (course.getTitle() != null)
                            mtitle = course.getTitle();
                        else mtitle = "";

                        setImage("http://arayesh.myzibadasht.ir/" + course.getData(), linear16, "course", "course_id", course.getId().toString(), mtitle, member_id);

                    }

                    for (WorkplacePic workplacePic : response.getData().getWorkplacePic()) {
                        if (workplacePic.getTitle() != null)
                            mtitle = workplacePic.getTitle();
                        else mtitle = "";


                        setImage("http://arayesh.myzibadasht.ir/" + workplacePic.getPic(), linear18, "workplace_pic", "workplace_pic_id", workplacePic.getId().toString(), mtitle, member_id);

                    }

                    for (FamousCustomer famousCustomer : response.getData().getFamousCustomer()) {
                        if (famousCustomer.getTitle() != null)
                            mtitle = famousCustomer.getTitle();
                        else mtitle = "";


                        setImage("http://arayesh.myzibadasht.ir/" + famousCustomer.getPic(), linear0, "famous_customer", "famous_customer_id", famousCustomer.getId().toString(), mtitle, member_id);


                    }
                    lat = response.getData().getLat();
                    lng = response.getData().getLng();

                    googleMap.clear();
                    googleMap.addMarker(new MarkerOptions()
                            .position(new LatLng(response.getData().getLat(), response.getData().getLng()))
                            .draggable(true));


                    CameraPosition cameraPosition = new CameraPosition.Builder()
                            .target(new LatLng(response.getData().getLat(), response.getData().getLng())).zoom(18).build();

                    googleMap.animateCamera(CameraUpdateFactory
                            .newCameraPosition(cameraPosition));


                } catch (Exception e) {
                    Log.d("error", e.toString());
                }


            }
        });
    }

    public void setImage(String url, final LinearLayout linear, final String tag, final String key, final String id, String title, final String member_id) {

        inflatedLayout = getLayoutInflater().inflate(R.layout.my_image_layout, null);

        txtTilte = inflatedLayout.findViewById(R.id.title);
        myImageView = inflatedLayout.findViewById(R.id.img);
        imgbDropPic13 = inflatedLayout.findViewById(R.id.imgbDropPic1);
        imgRetry = inflatedLayout.findViewById(R.id.retry);
        linearRetry = inflatedLayout.findViewById(R.id.linearRetry);

        linear.setTag(id);
        imgbDropPic13.setTag(id);
        inflatedLayout.setTag(id);

        viewsList.add(inflatedLayout);
        drops.add(imgbDropPic13);
        linearLayouts.add(linear);


        if (title != null)
            txtTilte.setText(title);

        Glide.with(getContext())
                .load(url)
                .into(myImageView);

        linear.addView(inflatedLayout);

        for (int i = 0; i < drops.size(); i++) {
            final int finalI = i;
            for (int j = 0; j < drops.size(); j++) {
                final int finalJ = j;
                imgbDropPic13.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (drops.get(finalJ).getTag() == viewsList.get(finalJ).getTag()) {
                            viewsList.get(finalJ).setVisibility(View.GONE);
                            deletePic(tag, key, id, inflatedLayout, member_id, linear);
                        }
                    }
                });
            }
        }


    }


    public void deletePic(String tag, String key, String id, final View v, String member_id, final LinearLayout linear) {

        String url = tools.baseUrl + "member_picDel";
        RequestParams params = new RequestParams();
        params.put("tag", tag);
        params.put(key, id);
        params.put("api_token", tools.getSharePrf("api_token"));
        params.put("APP_KEY", "bazarayesh:barber:11731e11b");

        params.put("member_id", member_id);

        tools.client.post(url, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {

                if (responseString.contains("ok")) {
                    linear.removeView(v);

                }
            }
        });

    }


    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(getContext())
                        .setTitle("موقعیت مکانی")
                        .setMessage("برای مشخص کردن موقعیت مکانی بروی نقشه باید سیستم موقعیت مکانی روشن باشد")
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


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(getContext(),
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
}

