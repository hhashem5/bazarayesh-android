package com.idpz.bazarayesh;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.idpz.bazarayesh.Utils.Tools;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import cz.msebera.android.httpclient.Header;

public class BaseActivity extends AppCompatActivity {
    public String TAG = "parisa";
    public Context context = this;
    public Activity activity = this;

    public static String LEFT_TO_RIGHT = "left-to-right";
    public static String RIGHT_TO_LEFT = "right-to-left";
    public static String BOTTOM_TO_UP = "bottom-to-up";
    public static String UP_TO_BOTTOM = "up-to-bottom";
    public static String FADEIN_TO_FADEOUT = "fadein-to-fadeout";
    public static String ROTATIONOUT_TO_ROTATIONIN ="rotateout-to-rotatein";


    Tools tools=new Tools(context);
    String response;

    ProgressDialog pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        tools = new Tools(context);


    }


}

