package com.idpz.bazarayesh;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.idpz.bazarayesh.Adapters.MembersAdapter;
import com.idpz.bazarayesh.Models.Data;
import com.idpz.bazarayesh.Models.ResponseListMember;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import cz.msebera.android.httpclient.Header;

import static com.idpz.bazarayesh.Utils.AppController.getAppContext;

public class ListShowActivty extends BaseActivity implements View.OnClickListener {
    MaterialSpinner spinner;

    RecyclerView recyclerView;

    int type;
    double lat, lng;
    MembersAdapter adapter;

    Typeface irsans;


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
        spinner = (MaterialSpinner) findViewById(R.id.spinner);

        recyclerView = findViewById(R.id.recycle);

        imgbBack.setOnClickListener(this);


        spinner.setItems("پربازدیدترین", "امتیاز بالا", "حروف الفبا");
        spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {

                Snackbar.make(view, "Clicked " + item, Snackbar.LENGTH_LONG).show();

                switch (item) {

                    case "پربازدیدترین":
                        getList("view");

                        break;

                    case "امتیاز بالا":
                        getList("view");//score

                        break;

                    case "حروف الفبا":
                        getList("alphabet");

                        break;
                }
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
                tools.noInternet(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        pd.dismiss();
                        getList(tag);
                        tools.hideInternet();

                    }
                });
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {

                try {


                    pd.dismiss();
                    if (responseString.contains("ok")) {

                        final ResponseListMember responseListMember = gson.fromJson(responseString, ResponseListMember.class);


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
}
