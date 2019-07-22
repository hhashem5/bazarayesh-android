package com.idpz.bazarayesh;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Typeface;
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
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.CardView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.idpz.bazarayesh.Models.Member;
import com.idpz.bazarayesh.Models.ResponseUserUpdate;
import com.idpz.bazarayesh.Models.User;
import com.idpz.bazarayesh.Utils.Tools;
import com.idpz.bazarayesh.Utils.crop.CropUtil;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.makeramen.roundedimageview.RoundedImageView;
import com.soundcloud.android.crop.Crop;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import cz.msebera.android.httpclient.Header;

import static com.bumptech.glide.load.resource.bitmap.TransformationUtils.rotateImage;
import static com.idpz.bazarayesh.BaseActivity.LEFT_TO_RIGHT;
import static com.idpz.bazarayesh.MainActivity.navigation;
import static com.idpz.bazarayesh.Utils.AppController.getAppContext;
import static com.idpz.bazarayesh.Utils.Tools.gson;
import static maes.tech.intentanim.CustomIntent.customType;

public class ProfileFragment extends BaseFragment implements View.OnClickListener, IOnBackPressed {


    private BottomSheetBehavior mBehavior;
    private BottomSheetDialog mBottomSheetDialog;
    private View bottom_sheet;

    private int TAKE_PICTURE = 720;
    private int PICK_PICTURE = 970;
    private int CROP_PICTURE = 360;

    RoundedImageView imagview1, imagview2, imagview3, imagview4, imagview5;
    private Handler mHandler;
    private File profilePictureDirectory;
    private Bitmap bitmap;

    TextView txtName, txtEmail, txtMobile, phone, email, pub1, pub2, pub3, pub4, pub5;

    CardView beautyshop, teacher, institude, store, hairdresser;
    ImageView check1, check2, check3, check4, check5, myProfile_photoLikes, clickedImageView;
    Tools tools;

    TextView btnOk;

    String bytePic;

    File proPic;

    double lat, lng;

    public int ipub1 = 1, ipub2 = 1, ipub3 = 1, ipub4 = 1, ipub5 = 1;

    public static boolean edit1, edit2, edit3, edit4, edit5;

    //  RelativeLayout relBeatyshop, relHairdreser, relShop, relInstitude, relTeacher;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // tools.state = "1";


        View v = inflater.inflate(R.layout.new_profile, container, false);
        tools = new Tools(getContext());


//        settoolbarText(getString(R.string.title_profile), v);
        initViews(v);

        return v;


    }


    public void initViews(View v) {

        irsans = Typeface.createFromAsset(getActivity().getAssets(), "fonts/iran_sans.ttf");


        pd = new ProgressDialog(getActivity());
        pd.setCancelable(false);
        pd.setMessage(FontUtils.typeface(irsans, getString(R.string.wait)));


        imgbBack=v.findViewById(R.id.imgbBack);
        myProfile_photoLikes = v.findViewById(R.id.myProfile_photoLikes);
        txtName = v.findViewById(R.id.txtName);
        txtEmail = v.findViewById(R.id.txtEmail);
        txtMobile = v.findViewById(R.id.txtMobile);
        btnOk = v.findViewById(R.id.btnOk);

        pub1 = v.findViewById(R.id.pub1);
        pub2 = v.findViewById(R.id.pub2);
        pub3 = v.findViewById(R.id.pub3);
        pub4 = v.findViewById(R.id.pub4);
        pub5 = v.findViewById(R.id.pub5);

        imagview1 = v.findViewById(R.id.imagview1);
        imagview2 = v.findViewById(R.id.imagview2);
        imagview3 = v.findViewById(R.id.imagview3);
        imagview4 = v.findViewById(R.id.imagview4);
        imagview5 = v.findViewById(R.id.imagview5);

//        relBeatyshop = v.findViewById(R.id.relBeatyShop);
//        relHairdreser = v.findViewById(R.id.relHairdreser);
//        relInstitude = v.findViewById(R.id.relInstitude);
//        relTeacher = v.findViewById(R.id.relTeacher);
//        relShop = v.findViewById(R.id.relShop);


        beautyshop = v.findViewById(R.id.beautyshop);

        teacher = v.findViewById(R.id.teacher);

        hairdresser = v.findViewById(R.id.hairdersser);

        institude = v.findViewById(R.id.institude);

        store = v.findViewById(R.id.store);

        check1 = v.findViewById(R.id.check1);
        check2 = v.findViewById(R.id.check2);
        check3 = v.findViewById(R.id.check3);
        check4 = v.findViewById(R.id.check4);
        check5 = v.findViewById(R.id.check5);
        bottom_sheet = v.findViewById(R.id.bottom_sheet);
        phone = v.findViewById(R.id.phone);
        email = v.findViewById(R.id.email);

        mBehavior = BottomSheetBehavior.from(bottom_sheet);

        //disable and enable
//        if (ipub1 == 0)
//            pub1.setVisibility(View.VISIBLE);
//        else pub1.setVisibility(View.GONE);
//        if (ipub2 == 0)
//            pub2.setVisibility(View.VISIBLE);
//        else pub2.setVisibility(View.GONE);
//        if (ipub3 == 0)
//            pub3.setVisibility(View.VISIBLE);
//        else pub3.setVisibility(View.GONE);
//        if (ipub4 == 0)
//            pub4.setVisibility(View.VISIBLE);
//        else pub4.setVisibility(View.GONE);
//        if (ipub5 == 0)
//            pub5.setVisibility(View.VISIBLE);
//        else pub5.setVisibility(View.GONE);


        try {

            if (!tools.getSharePrf("memberId1").equals("")) {
                edit1 = true;
                check1.setVisibility(View.VISIBLE);
            }
            if (!tools.getSharePrf("memberId3").equals("")) {
                edit3 = true;
                check3.setVisibility(View.VISIBLE);
            }
            if (!tools.getSharePrf("memberId2").equals("")) {
                check2.setVisibility(View.VISIBLE);
                edit2 = true;
            }
            if (!tools.getSharePrf("memberId4").equals("")) {
                edit4 = true;
                check4.setVisibility(View.VISIBLE);
            }
            if (!tools.getSharePrf("memberId5").equals("")) {
                edit5 = true;
                check5.setVisibility(View.VISIBLE);
            }

        } catch (Exception e) {
        }
        beautyshop.setOnClickListener(this);
        teacher.setOnClickListener(this);
        store.setOnClickListener(this);
        institude.setOnClickListener(this);
        hairdresser.setOnClickListener(this);
        btnOk.setOnClickListener(this);
        myProfile_photoLikes.setOnClickListener(this);
        phone.setOnClickListener(this);
        email.setOnClickListener(this);
        imgbBack.setOnClickListener(this);

//        relShop.setOnClickListener(this);
//        relTeacher.setOnClickListener(this);
//        relInstitude.setOnClickListener(this);
//        relBeatyshop.setOnClickListener(this);
//
//        relHairdreser.setOnClickListener(this);


        try {
            txtMobile.setText(tools.getSharePrf("mobile"));

        } catch (Exception e) {
        }

        prepareProfilePictureDirectory();

        user_info();

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);


    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.imgbBack:
                if (tools.state.equals("3")) {
                    Fragment fragment = new AdvertiseFragment();
                    loadFragment(fragment);
                    navigation.setSelectedItemId(R.id.navigation_advertise);
                    tools.state = "1";


                } else if (tools.state.equals("2")) {
                    Fragment fragment = new HomeFragment();
                    loadFragment(fragment);
                    navigation.setSelectedItemId(R.id.navigation_home);
                    tools.state = "2";


                }
                break;
            case R.id.phone:
                Intent call = new Intent(Intent.ACTION_CALL);

                call.setData(Uri.parse("tel:" + "09123302770"));
                if (ContextCompat.checkSelfPermission(getActivity(),
                        Manifest.permission.CALL_PHONE)
                        != PackageManager.PERMISSION_GRANTED) {

                    ActivityCompat.requestPermissions(getActivity(),
                            new String[]{Manifest.permission.CALL_PHONE}, 1);

                    // MY_PERMISSIONS_REQUEST_CALL_PHONE is an
                    // app-defined int constant. The callback method gets the
                    // result of the request.
                } else {
                    //You already have permission
                    try {
                        startActivity(call);
                    } catch (SecurityException e) {
                        e.printStackTrace();
                    }
                }


                break;

            case R.id.email:
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("message/rfc822");
                i.putExtra(Intent.EXTRA_EMAIL, new String[]{"karayesh2017@gmail.com"});
                i.putExtra(Intent.EXTRA_SUBJECT, "کاربر اپلیکیشن بازآرایش");
                try {
                    startActivity(Intent.createChooser(i, "Send mail..."));
                } catch (android.content.ActivityNotFoundException ex) {
                }
                break;
            case R.id.beautyshop:

                Intent intent = new Intent(getActivity(), SubProfileActivity.class);
                intent.putExtra("type", 1);
                getActivity().startActivity(intent);
                customType(getContext(), LEFT_TO_RIGHT);
                getActivity().finish();
                break;


            case R.id.teacher:

                Intent intent2 = new Intent(getActivity(), SubProfileActivity.class);
                intent2.putExtra("type", 4);
                getActivity().startActivity(intent2);
                customType(getContext(), LEFT_TO_RIGHT);
                getActivity().finish();
                break;

            case R.id.store:
                Intent intent3 = new Intent(getActivity(), SubProfileActivity.class);
                intent3.putExtra("type", 5);
                getActivity().startActivity(intent3);
                customType(getContext(), LEFT_TO_RIGHT);
                getActivity().finish();
                break;


            case R.id.hairdersser:
                Intent intent4 = new Intent(getActivity(), SubProfileActivity.class);
                intent4.putExtra("type", 2);
                getActivity().startActivity(intent4);
                customType(getContext(), LEFT_TO_RIGHT);
                getActivity().finish();
                break;

            case R.id.institude:
                Intent intent5 = new Intent(getActivity(), SubProfileActivity.class);
                intent5.putExtra("type", 3);
                getActivity().startActivity(intent5);
                customType(getContext(), LEFT_TO_RIGHT);
                getActivity().finish();
                break;

            case R.id.myProfile_photoLikes:
                clickedImageView = myProfile_photoLikes;
                showBottomSheetDialog();

                break;

            case R.id.btnOk:
                update_member();
                break;


        }
    }

    private boolean loadFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    @Override
    public boolean onBackPressed() {

        if (tools.state.equals("3")) {
            Fragment fragment = new AdvertiseFragment();
            loadFragment(fragment);
            navigation.setSelectedItemId(R.id.navigation_advertise);
            tools.state = "1";


        } else if (tools.state.equals("2")) {
            Fragment fragment = new HomeFragment();
            loadFragment(fragment);
            navigation.setSelectedItemId(R.id.navigation_home);
            tools.state = "2";


        }
        return false;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        final File photo = new File(Environment.getExternalStorageDirectory(), "/arayesh/Picture.jpg");

        try {
            if (requestCode == TAKE_PICTURE && resultCode == Activity.RESULT_OK) {

                Crop.of(Uri.fromFile(photo), Uri.fromFile(profilePictureDirectory))
                        .withMaxSize(900, 900)
                        .withAspect(9, 9)
                        .start(getActivity(), ProfileFragment.this, CROP_PICTURE);


            }

        } catch (Exception e) {
            Log.d("pari", e.toString());
        }
        try {
            if (requestCode == PICK_PICTURE && resultCode == Activity.RESULT_OK) {
                Crop.of(data.getData(), Uri.fromFile(profilePictureDirectory))
                        .withMaxSize(900, 900)
                        .withAspect(1, 1)
                        .start(getActivity(), ProfileFragment.this, CROP_PICTURE);


            }
        } catch (Exception e) {
            e.getMessage();
        }
        if (requestCode == CROP_PICTURE && resultCode == Activity.RESULT_OK) {
            photo.delete();


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


                        bytePic = Base64.encodeToString(imageBytes, Base64.DEFAULT);
                        proPic = saveImage(getContext(), bytePic);


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
                        } catch (Exception e) {
                            e.getMessage();
                        }
                    }
                });
            }
        }).start();

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


    public void update_member() {


        pd.show();
        RequestParams params = new RequestParams();
        params.put("full_name", txtName.getText().toString());
        params.put("lat", lat);
        params.put("lng", lng);
        params.put("email", txtEmail.getText().toString());
        try {
            params.put("pic", proPic);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        params.put("id", tools.getSharePrf("user_Id"));

        params.put("api_token", tools.getSharePrf("api_token"));
        params.put("APP_KEY", "bazarayesh:barber:11731e11b");

        String url = tools.baseUrl + "user_update";

        tools.client.post(url, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

                pd.dismiss();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                pd.dismiss();

                try {

                    if (responseString.contains("ok")) {

                        ResponseUserUpdate response = gson.fromJson(responseString, ResponseUserUpdate.class);

                        tools.addToSharePrf("user_Id", response.getUser().getId().toString());
                    }
                } catch (Exception e) {
                }
            }
        });

    }


    @Override
    public void onResume() {
        super.onResume();
        if (mBottomSheetDialog != null)
            mBottomSheetDialog.dismiss();
    }

    public void user_info() {

        pd.show();
        RequestParams params = new RequestParams();
        params.put("id", tools.getSharePrf("user_Id"));
        params.put("api_token", tools.getSharePrf("api_token"));
        params.put("APP_KEY", "bazarayesh:barber:11731e11b");

        final String url = tools.baseUrl + "user_info";

        tools.client.post(url, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

                tools.noInternet(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if (tools.isNetworkAvailable()) {
                            user_info();
                            tools.hideInternet();
                        } else
                            Toast.makeText(getContext(), getString(R.string.nonet), Toast.LENGTH_SHORT).show();

                    }
                });
                pd.dismiss();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {

                try {

                    pd.dismiss();

                    User user = gson.fromJson(responseString, User.class);

                    for (Member member : user.getMember()) {

                        switch (member.getType()) {
                            case "1":
                                if (member.getActive() == 0)
                                    pub1.setText("غیرفعال");
                                else pub1.setText("فعال");
                                Glide.with(getActivity()).load("http://arayesh.myzibadasht.ir/"+member.getLogo()).error(R.drawable.bazarayesh).into(imagview1);

                                break;
                            case "2":
                                if (member.getActive() == 0)
                                    pub2.setText("غیرفعال");
                                else pub2.setText("فعال");
                                Glide.with(getActivity()).load("http://arayesh.myzibadasht.ir/"+member.getLogo()).error(R.drawable.bazarayesh).into(imagview2);

                                break;
                            case "3":
                                if (member.getActive() == 0)
                                    pub3.setText("غیرفعال");
                                else pub3.setText("فعال");
                                Glide.with(getActivity()).load("http://arayesh.myzibadasht.ir/"+member.getLogo()).error(R.drawable.bazarayesh).into(imagview3);

                                break;
                            case "4":
                                if (member.getActive() == 0)
                                    pub4.setText("غیرفعال");
                                else pub4.setText("فعال");
                                Glide.with(getActivity()).load("http://arayesh.myzibadasht.ir/"+member.getLogo()).error(R.drawable.bazarayesh).into(imagview4);

                                break;
                            case "5":
                                if (member.getActive() == 0)
                                    pub5.setText("غیرفعال");
                                else pub5.setText("فعال");
                                Glide.with(getActivity()).load("http://arayesh.myzibadasht.ir/"+member.getLogo()).error(R.drawable.bazarayesh).into(imagview5);

                                break;

                        }
                    }
                    if (user.getPic() != null)
                        Glide.with(getContext().getApplicationContext())
                                .load("http://arayesh.myzibadasht.ir" + user.getPic()).error(R.drawable.iconnopic)
                                .into(myProfile_photoLikes);

                    txtName.setText(user.getFullName().toString());
                    txtEmail.setText(user.getEmail().toString());

                } catch (Exception e) {
                }
            }
        });
    }
}
