package com.idpz.bazarayesh;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.google.gson.Gson;
import com.idpz.bazarayesh.Models.SingleAdRecruiment.ResponseSingleRecruiment;
import com.idpz.bazarayesh.Models.adDetails.AdDetailsModel;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import cz.msebera.android.httpclient.Header;

public class AdsDetailsActivity extends BaseActivity {

    private String id;
    private String tag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ads_details);

        Intent intent = getIntent();
        id = (String) intent.getExtras().get("id");
        tag = intent.getStringExtra("tag");

        getAddDetails();
    }

    private void getAddDetails() {
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

                    ResponseSingleRecruiment responseSingleRecruiment=gson.fromJson(responseString,ResponseSingleRecruiment.class);
                    responseSingleRecruiment.getAd();



//                    AdDetailsModel model = gson.fromJson(responseString, AdDetailsModel.class);
//                    model.getAd().getName();
                } catch (Exception e) {
                    e.getMessage();
                }
            }
        });

    }


}
