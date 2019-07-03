package com.idpz.bazarayesh;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.idpz.bazarayesh.Utils.Tools;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import cz.msebera.android.httpclient.Header;

import static maes.tech.intentanim.CustomIntent.customType;

public class BaseActivity extends AppCompatActivity {
    public String TAG = "parisa";
    public Context context = this;
    public Activity activity = this;

    public static String LEFT_TO_RIGHT = "left-to-right";
    public static String RIGHT_TO_LEFT = "right-to-left";
    public static String BOTTOM_TO_UP = "bottom-to-up";
    public static String UP_TO_BOTTOM = "up-to-bottom";
    public static String FADEIN_TO_FADEOUT = "fadein-to-fadeout";
    public static String ROTATIONOUT_TO_ROTATIONIN = "rotateout-to-rotatein";


   public Tools tools = new Tools(context);
    public ProgressDialog pd;
    public TextView tooTitle;
    public ImageView imgbBack;
    Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        tools = new Tools(context);

    }

    public void settoolbarText(String tag) {
        tooTitle = findViewById(R.id.tooTitle);
        imgbBack = findViewById(R.id.imgbBack);
        tooTitle.setText(tag);
    }

    public void successDialog(String text) {

        final Dialog message = new Dialog(context);
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

                Intent i1 = new Intent(getApplicationContext(), SubProfileActivity.class);
                i1.setAction(Intent.ACTION_MAIN);
                i1.addCategory(Intent.CATEGORY_HOME);
                i1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                i1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                overridePendingTransition(0, 0);
                startActivity(i1);

                customType(context, RIGHT_TO_LEFT);


                finish();
                message.dismiss();
            }
        });
        message.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

    }

}

