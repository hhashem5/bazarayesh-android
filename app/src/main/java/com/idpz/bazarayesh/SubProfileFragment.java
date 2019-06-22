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
import com.idpz.bazarayesh.Models.UserResponse;
import com.idpz.bazarayesh.Models.VerifyResponse;
import com.idpz.bazarayesh.Utils.Tools;
import com.idpz.bazarayesh.Utils.crop.CropUtil;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.soundcloud.android.crop.Crop;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

import static com.bumptech.glide.load.resource.bitmap.TransformationUtils.rotateImage;
import static com.idpz.bazarayesh.BaseActivity.LEFT_TO_RIGHT;
import static com.idpz.bazarayesh.SubProfileActivity.*;
import static com.idpz.bazarayesh.Utils.AppController.getAppContext;
import static com.idpz.bazarayesh.Utils.Tools.gson;
import static maes.tech.intentanim.CustomIntent.customType;

public class SubProfileFragment extends BaseFragment implements View.OnClickListener, IOnBackPressed, LocationListener, GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks {
    private Handler mHandler;
    int state = 1;

    private double lat = 0.0, lng = 0.0;

    boolean imgLogoFlag = false;
    private int location_time = 0;
    public static int location_int = 1;
    private boolean doubleBackToExitPressedOnce = false;

    RelativeLayout rel12, relative, page1, page2, page3, relLogo, rel18, rel17, rel29, rel19, rel14,
            rel24, rel6, rel26, rel25, rel27, rel39, rel49, rel59, rel69, rel2, lnRelFrag, rel55, rel79;
    Button btn1, btn2, btn3;
    LinearLayout linear0, linear, tempLinear, linear2, linear3, linear5, linear6, linear7,
            linear8, linear9, linear10, linear11, linear12, linear13,
            linear14, linear15, linear16, linear18, linear17, linearRetry;
    ImageView imgbOne, imgbTwo, imgbThree, clickedImageView, clickedDrop, imgPic1, imgPic3, logoimg1;

    EditText txtName, txtBoss, txtFame, txtphone, txtaddress, txttelegram, txtinstagram;
    private BottomSheetBehavior mBehavior;
    private BottomSheetDialog mBottomSheetDialog;
    private View bottom_sheet;
    private int TAKE_PICTURE = 720;
    private int PICK_PICTURE = 970;
    private int CROP_PICTURE = 360;
    private File profilePictureDirectory;
    private Bitmap bitmap;

    View inflatedLayout, tempView, v;

    TextView txtTilte, titleimg1, txtPic1, tempTextView;
    ImageView myImageView, imgRetry;
    ImageButton imgbDropPic13, closeimg1, imgDropTemp, imgbDropPic2;

    List<View> myViews = new ArrayList<>();
    List<LinearLayout> myLinears = new ArrayList<>();


    private GoogleApiClient googleApiClient;
    private SupportMapFragment mapFragment;

    private GoogleMap googleMap;

    MapView mMapView;
    private GoogleApiClient mGoogleApiClient;


    private String bytePicMain, bytePic;
    File mainFile, file;
    Typeface irsans;

    int service_type, type;
    String title;


    boolean flag = true;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.subprofile_fragment, container, false);

        initviews(v);
        irsans = Typeface.createFromAsset(getActivity().getAssets(), "fonts/iran_sans.ttf");

        pd = new ProgressDialog(getActivity());
        pd.setCancelable(false);
        pd.setMessage(FontUtils.typeface(irsans, getString(R.string.wait)));
        mMapView.onCreate(savedInstanceState);

        buildGoogleApiClient();


        mMapView.onResume();
        return v;
    }


    public void initviews(View v) {

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
        txtaddress = v.findViewById(R.id.txtaddress);
        txttelegram = v.findViewById(R.id.txtaddress);
        txtinstagram = v.findViewById(R.id.txtaddress);


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

        mMapView = (MapView) v.findViewById(R.id.map);

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


        // turnOnGPS();
        //  mylocation=v.findViewById(R.id.currentlocation);
        // needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(final GoogleMap mMap) {


                googleMap = mMap;

                LatLng latLong = new LatLng(35.684209, 51.388263);

                CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(35.709561, 51.372652), 5);
                mMap.moveCamera(cameraUpdate);
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


                CameraPosition cameraPosition = new CameraPosition.Builder()
                        .target(latLong).zoom(18).build();

                mMap.animateCamera(CameraUpdateFactory
                        .newCameraPosition(cameraPosition));

                googleMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
                    @Override
                    public void onMyLocationChange(Location location) {
                        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 18);
                        if (location_time < location_int)
                            mMap.moveCamera(cameraUpdate);
                        location_time = location_time + 1;
                    }
                });


                // For showing a move to my location button
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
                googleMap.setMyLocationEnabled(true);
                googleMap.getUiSettings().setMyLocationButtonEnabled(false);


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

            }
        });


        prepareProfilePictureDirectory();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        final File photo = new File(Environment.getExternalStorageDirectory(), "/arayesh/Picture.jpg");

        try {
            if (requestCode == TAKE_PICTURE && resultCode == Activity.RESULT_OK) {

                Crop.of(Uri.fromFile(photo), Uri.fromFile(profilePictureDirectory))
                        .withMaxSize(900, 900)
                        .withAspect(16, 9)
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


//       todo                     switch (tempLinear.getId()){
//                                case R.id.linear:
//                                    membersFilesRegister(6, "", file, title);
//
//                                    break;
//                            }
//                            if (tempLinear == linear) {
//
//                                membersFilesRegister(1, "2", file, "");
//
//                            } else if (tempLinear == linear0) {
//                                membersFilesRegister(6, "", file, title);
//
//                            } else if (tempLinear == linear2) {
//                                membersFilesRegister(1, "3", file, "");
//                            } else if (tempLinear == linear3) {
//                                membersFilesRegister(1, "4", file, "");
//
//                            } else if (tempLinear == linear5) {
//                                membersFilesRegister(1, "5", file, "");
//
//                            } else if (tempLinear == linear6) {
//                                membersFilesRegister(1, "6", file, "");
//
//                            } else if (tempLinear == linear7) {
//                                membersFilesRegister(1, "7", file, "");
//
//                            } else if (tempLinear == linear8) {
//                                membersFilesRegister(1, "8", file, "");
//
//                            } else if (tempLinear == linear9) {
//                                membersFilesRegister(1, "9", file, "");
//
//                            } else if (tempLinear == linear10) {
//                                membersFilesRegister(1, "10", file, "");
//
//                            } else if (tempLinear == linear11) {
//                                membersFilesRegister(1, "11", file, "");
//
//                            } else if (tempLinear == linear12) {
//                                membersFilesRegister(1, "14", file, "");
//
//                            } else if (tempLinear == linear13) {
//                                membersFilesRegister(1, "13", file, "");
//
//                            } else if (tempLinear == linear14) {
//                                membersFilesRegister(1, "12", file, "");
//
//                            } else if (tempLinear == linear15) {
//                                membersFilesRegister(1, "4", file, "");
//
//                            } else if (tempLinear == linear16) {
//                                membersFilesRegister(3, "", file, title);
//
//                            } else if (tempLinear == linear17) {
//                                membersFilesRegister(2, "", file, title);
//
//                            } else if (tempLinear == linear18) {
//                                membersFilesRegister(4, "", file, "");
//
//                            }
//
                     }


                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bitmap1.compress(Bitmap.CompressFormat.PNG, 100, stream);
                        Glide.with(getContext())
                                .load(stream.toByteArray())
                                .into(clickedImageView);


                        for (final View view : myViews) {
                            if (view.getVerticalScrollbarPosition() == imgDropTemp.getVerticalScrollbarPosition()) {
                                imgDropTemp.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        tempLinear.removeView(view);
                                    }
                                });

                            }
                        }


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
                    if (!txtBoss.getText().toString().equals("")) {

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
                        txtBoss.setError(" نام مدیر آرایشگاه را وارد کنید ");

                        txtBoss.requestFocus();
                    }
                } else {
                    txtName.setError(" نام را وارد کنید ");

                    txtName.requestFocus();
                }
                break;

            case R.id.btn2:

                page2.setVisibility(View.GONE);
                page3.setVisibility(View.VISIBLE);
                imgbOne.setImageResource(R.drawable.step1);
                imgbTwo.setImageResource(R.drawable.step3);
                imgbThree.setImageResource(R.drawable.step22);
//          todo     if (flag) {
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
                successDialog("با موفقیت ثبت شد.");
                break;

            case R.id.closeimg1:


                txtPic1.setVisibility(View.VISIBLE);
                imgPic1.setVisibility(View.VISIBLE);
                closeimg1.setVisibility(View.GONE);
                logoimg1.setVisibility(View.GONE);
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
                AlertDialog(linear10);
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
        builder.setMessage(FontUtils.typeface(typeface, "نام خدمت مورد نظر را وارد کنید"));
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
        params.put("address", txtaddress.getText().toString());
        params.put("telegram", txttelegram.getText().toString());
        params.put("instagram", txtinstagram.getText().toString());
        params.put("lat", 37.44545);//todo lat
        params.put("lng", 51.57768);//todo lng
        try {
            params.put("logo", mainFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        params.put("api_token", tools.getSharePrf("api_token"));
        params.put("APP_KEY", "bazarayesh:barber:11731e11b");


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

                        tools.addToSharePrf("userId", response.getMember().getId().toString());
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
                } catch (Exception e) {
                }
            }
        });
    }


    public void membersFilesRegister(final int type, final String service_type, final File pic, final String title) throws FileNotFoundException {

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
                            membersFilesRegister(type, service_type, pic, title);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {

                pd.dismiss();

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

        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialog.setContentView(R.layout.payment_ok);
        dialog.setCancelable(false);
        dialog.show();
        TextView content = dialog.findViewById(R.id.content);
        Button btnOk = dialog.findViewById(R.id.btnOk);
        Button btnGo = dialog.findViewById(R.id.btnGo);
        content.setText(text);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                dialog.dismiss();
            }
        });
        btnGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });


    }
}

