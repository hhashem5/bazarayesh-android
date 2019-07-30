package com.idpz.bazarayesh;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.idpz.bazarayesh.Adapters.MembersAdapter;
import com.idpz.bazarayesh.Models.Data;
import com.idpz.bazarayesh.Models.ResponseListMember;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import cz.msebera.android.httpclient.Header;

import static com.idpz.bazarayesh.Utils.AppController.getAppContext;

public class ListShowActivty extends BaseActivity implements View.OnClickListener {
    Spinner spinner;

    RecyclerView recyclerView;

    int type;
    double lat, lng;
    MembersAdapter adapter;

    Typeface irsans;

    private Dialog internetDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_show_activty);

        try {

            type = (int) getIntent().getExtras().get("type");
            lat = (double) getIntent().getExtras().get("lat");
            lng = (double) getIntent().getExtras().get("lng");

        } catch (Exception e) {
        }


        switch (type) {

            case 1:
                settoolbarText("لیست آرایشگاه ");

                break;
            case 2:
                settoolbarText("لیست آرایشگر ");

                break;

            case 3:
                settoolbarText("لیست آموزشگاه ");

                break;

            case 4:
                settoolbarText("لیست مدرس ");

                break;
            case 5:
                settoolbarText("لیست فروشگاه ");

                break;

        }
        settoolbarText("جستجو");

        initViews();

    }

    public void initViews() {

        imgbBack.setVisibility(View.VISIBLE);
        spinner = findViewById(R.id.spinner);

        irsans = Typeface.createFromAsset(getAssets(), "fonts/iran_sans.ttf");

        recyclerView = findViewById(R.id.recycle);

        imgbBack.setOnClickListener(this);

        String[] strings = new String[]{"پربازدیدترین", "امتیاز بالا", "حروف الفبا"};
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(context, R.layout.spinner, strings);

        arrayAdapter.setDropDownViewResource(R.layout.spinner);
        spinner.setAdapter(arrayAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                switch (i) {

                    case 0:
                        getList("view");

                        break;

                    case 1:
                        getList("view");//score

                        break;

                    case 2:
                        getList("alphabet");

                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        irsans = Typeface.createFromAsset(getAssets(), "fonts/iran_sans.ttf");

        pd = new ProgressDialog(context);
        pd.setCancelable(false);
        pd.setMessage(FontUtils.typeface(irsans, getString(R.string.wait)));
        getList("all");

    }


    public void getList(final String tag) {
        pd.show();
        String url = tools.baseUrl + "barber_shop_list";
        RequestParams params = new RequestParams();

        params.put("tag", tag);
        params.put("lat", 35.706468);
        params.put("lng", 51.356125);
        params.put("type", type);


        params.put("api_token", tools.getSharePrf("api_token"));
        params.put("APP_KEY", "bazarayesh:barber:11731e11b");
        tools.client.post(url, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                pd.dismiss();

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {

                try {


                    pd.dismiss();
                    if (responseString.contains("ok")) {

                        final ResponseListMember responseListMember = gson.fromJson(responseString, ResponseListMember.class);

                        if (responseListMember.getMembers().size() == 0)
                            notFound();
                            adapter = new MembersAdapter(responseListMember.getMembers(), context, new MembersAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(int position, Object object) {
                                    Data member = (Data) object;
                                    Intent intent = new Intent(ListShowActivty.this, ShowMemberDetail.class);

                                    intent.putExtra("backtag", 1);
                                    intent.putExtra("id", member.getId());

                                    startActivity(intent);
                                }
                            });


                        recyclerView.setLayoutManager(new LinearLayoutManager(getAppContext(), LinearLayoutManager.VERTICAL, true));
                        recyclerView.setAdapter(adapter);


                    }
                } catch (Exception e) {
                }
            }
        });
    }

    @Override
    public void onBackPressed() {


        finish();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.imgbBack:

                finish();
                break;
        }
    }


    public void notFound() {
        internetDialog = new Dialog(context);
        internetDialog.requestWindowFeature(Window.FEATURE_NO_TITLE); //before
        internetDialog.setContentView(R.layout.notfound_dialog);

        internetDialog.setCancelable(false);
        internetDialog.show();
        TextView btnExit = internetDialog.findViewById(R.id.btnExit);
        internetDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

}
