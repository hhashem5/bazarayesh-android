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
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.idpz.bazarayesh.Models.User;
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

    private Handler mHandler;
    private File profilePictureDirectory;
    private Bitmap bitmap;

    TextView beautyshop, teacher, institude, store, hairdresser, txtName, txtEmail;

    ImageView check1, check2, check3, check4, check5, myProfile_photoLikes, clickedImageView;
    Tools tools;

    Button btnOk;

    String bytePic;

    File proPic;

    double lat, lng;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // tools.state = "1";


        View v = inflater.inflate(R.layout.profile_fragment, container, false);
        tools = new Tools(getContext());


        settoolbarText(getString(R.string.title_profile), v);
        initViews(v);

        return v;


    }


    public void initViews(View v) {

        irsans = Typeface.createFromAsset(getActivity().getAssets(), "fonts/iran_sans.ttf");

        pd = new ProgressDialog(getActivity());
        pd.setCancelable(false);
        pd.setMessage(FontUtils.typeface(irsans, getString(R.string.wait)));


        myProfile_photoLikes = v.findViewById(R.id.myProfile_photoLikes);
        txtName = v.findViewById(R.id.txtName);
        txtEmail = v.findViewById(R.id.txtEmail);
        btnOk = v.findViewById(R.id.btnOk);

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

        mBehavior = BottomSheetBehavior.from(bottom_sheet);

        try {

            if (!tools.getSharePrf("memberId1").equals(""))
                check1.setVisibility(View.VISIBLE);
            if (!tools.getSharePrf("memberId3").equals(""))
                check3.setVisibility(View.VISIBLE);
            if (!tools.getSharePrf("memberId2").equals(""))
                check2.setVisibility(View.VISIBLE);
            if (!tools.getSharePrf("memberId4").equals(""))
                check4.setVisibility(View.VISIBLE);
            if (!tools.getSharePrf("memberId5").equals(""))
                check5.setVisibility(View.VISIBLE);

        } catch (Exception e) {
        }
        beautyshop.setOnClickListener(this);
        teacher.setOnClickListener(this);
        store.setOnClickListener(this);
        institude.setOnClickListener(this);
        hairdresser.setOnClickListener(this);
        btnOk.setOnClickListener(this);
        myProfile_photoLikes.setOnClickListener(this);

        prepareProfilePictureDirectory();

        user_info();
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
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
                        .withAspect(16, 9)
                        .start(getActivity(), ProfileFragment.this, CROP_PICTURE);


            }

        } catch (Exception e) {
            Log.d("pari", e.toString());
        }
        try {
            if (requestCode == PICK_PICTURE && resultCode == Activity.RESULT_OK) {
                Crop.of(data.getData(), Uri.fromFile(profilePictureDirectory))
                        .withMaxSize(900, 900)
                        .withAspect(16, 9)
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

        String url = tools.baseUrl + "user_info";

        tools.client.post(url, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

                pd.dismiss();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {

                try {

                    pd.dismiss();

                    User user = gson.fromJson(responseString, User.class);

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
