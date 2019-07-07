package com.idpz.bazarayesh;

import android.content.Intent;
import android.os.Bundle;

import com.google.gson.Gson;
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
        id = intent.getStringExtra("id");
        tag = intent.getStringExtra("tag");

        getAddDetails();
    }

    private void getAddDetails() {
        String url = tools.baseUrl + "single_ad";
        RequestParams params = new RequestParams();
        params.put("type", tag);
        params.put("id", id);
        tools.client.post(url, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                statusCode = statusCode;
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Gson gson = new Gson();
                try {
                    AdDetailsModel model = gson.fromJson(responseString, AdDetailsModel.class);
                    model.getAd().getName();
                } catch (Exception e) {
                    e.getMessage();
                }
            }
        });

    }
}
