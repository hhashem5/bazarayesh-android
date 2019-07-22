package com.idpz.bazarayesh;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import java.io.File;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static maes.tech.intentanim.CustomIntent.customType;

public class MainActivity extends BaseActivity {

    static BottomNavigationView navigation;
    private FirebaseAnalytics mFirebaseAnalytics;


    boolean flag = true;
    private File toInstall;
    private String myFileName = "BazArayesh.apk";
    private ProgressDialog progressDialog;

    private int webVersion = 0;

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

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        try {


            if (getIntent().getStringExtra("back").equals("1")) {
                navigation.setSelectedItemId(R.id.navigation_profile);
                loadFragment(new ProfileFragment());

            } else if (getIntent().getStringExtra("back").equals("3")) {
                navigation.setSelectedItemId(R.id.navigation_advertise);
                loadFragment(new AdvertiseFragment());
            } else {
                navigation.setSelectedItemId(R.id.navigation_home);
                loadFragment(new HomeFragment());
            }

        } catch (NullPointerException e) {

            navigation.setSelectedItemId(R.id.navigation_home);

            loadFragment(new HomeFragment());

        }


        if (flag) {
            tools.state = "2";
            flag = false;
        }


        DownloadApk.downloadListener(new OnDownload() {
            @Override
            public void downloadPr(String messageText) {

                progressDialog.setProgress(Integer.valueOf(messageText));
                if (messageText.equals("100"))
                    progressDialog.dismiss();
            }

            @Override
            public void OnDownloadComplete(String fileName) {

                Log.d("tag", "OnDownloadComplete: " + fileName);
                if (toInstall.exists())
                    try {
                        Uri apkUri = Uri.fromFile(toInstall);
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    } catch (Exception e) {
                        // Instruct the user to install a PDF reader here, or something
                        Log.d("tag", "OnDownloadComplete: " + e.getMessage());
                    }

            }
        });
    }

    public void initViews() {
        navigation = findViewById(R.id.navigation);


        versionCheck();
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
                    //       tools.state="3";
                    break;
                case R.id.navigation_home:
                    fragment = new HomeFragment();
                    tools.state = "2";
                    break;
                case R.id.navigation_profile:
                    fragment = new ProfileFragment();
                    //   tools.state="1";
                    break;
            }

            return loadFragment(fragment);
        }
    };


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));

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


    public void versionCheck() {

        String url = tools.baseUrl + "app_version";

        RequestParams params = new RequestParams();
        params.put("api_token", tools.getSharePrf("api_token"));
        params.put("APP_KEY", "bazarayesh:barber:11731e11b");

        tools.client.post(url, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {

                try {


                    if (Integer.valueOf(BuildConfig.VERSION_CODE) < Integer.valueOf(responseString)) {
                        AlertDialog();

                    }

                } catch (Exception e) {
                }
            }
        });


    }

    public void AlertDialog() {

        final Dialog message = new Dialog(context);
        message.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        message.setContentView(R.layout.update_details_dialog);
        message.setCancelable(true);
        message.show();
        TextView txtTitle = message.findViewById(R.id.txtTitle);
        TextView txtSubject = message.findViewById(R.id.txtSubject);


        TextView btnExit = message.findViewById(R.id.btnExit);
        TextView btnOk = message.findViewById(R.id.btnMember);
        btnOk.setVisibility(View.VISIBLE);
        btnOk.setText("باشه");
        btnExit.setText("بعدا");

        txtSubject.setText("پیام");
        txtTitle.setText("آپدیت جدید داریم.الان آپدیت میکنی؟ ");


        //txtBody.setText();
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                message.dismiss();
            }
        });

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                message.dismiss();
                updateMe();
            }
        });
        message.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

    }


    private void updateMe() {

        toInstall = new File(Environment.getExternalStorageDirectory().toString(), myFileName);
        if (toInstall.exists()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Uri apkUri = FileProvider.getUriForFile(MainActivity.this, BuildConfig.APPLICATION_ID + ".provider", toInstall);
                Intent intent = new Intent(Intent.ACTION_INSTALL_PACKAGE);
                intent.setData(apkUri);
                intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                MainActivity.this.startActivity(intent);
            } else {
                Uri apkUri = Uri.fromFile(toInstall);
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                MainActivity.this.startActivity(intent);
            }
        } else {
//            Toast.makeText(NewMainActivity.this, "نسخه جدید برنامه موجود است", Toast.LENGTH_SHORT).show();
            // execute this when the downloader must be fired
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);
                    return;

                } else {

                    progressDialog = new ProgressDialog(this);
                    progressDialog.setCancelable(true);
                    progressDialog.setMessage("درحال دریافت فایل ");
                    progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                    progressDialog.setProgress(0);
                    progressDialog.show();
                    new DownloadApk(context, toInstall).execute("http://arayesh.myzibadasht.ir/app.apk");//todo webversion instead of 35

                    //downloadTask.execute("http://idpz.ir/apk/" + webVersion + ".apk");
                }
            } else {
                progressDialog = new ProgressDialog(this);
                progressDialog.setCancelable(true);
                progressDialog.setMessage("درحال دریافت فایل ");
                progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                progressDialog.setProgress(0);
                progressDialog.show();
                new DownloadApk(context, toInstall).execute("http://arayesh.myzibadasht.ir/app.apk");//todo webversion instead of 35

            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 100) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setCancelable(true);
            progressDialog.setMessage("درحال دریافت فایل ");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressDialog.setProgress(0);
            progressDialog.show();
            new DownloadApk(context, toInstall).execute("http://arayesh.myzibadasht.ir/app.apk");//todo webversion instead of 35

        }



    }
}



