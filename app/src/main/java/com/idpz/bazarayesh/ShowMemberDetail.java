package com.idpz.bazarayesh;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.idpz.bazarayesh.Adapters.MemberDetailAdapter;
import com.idpz.bazarayesh.Models.Award;
import com.idpz.bazarayesh.Models.FamousCustomer;
import com.idpz.bazarayesh.Models.MainItem;
import com.idpz.bazarayesh.Models.ResponseListMember;
import com.idpz.bazarayesh.Models.Service;
import com.idpz.bazarayesh.Models.WorkplacePic;
import com.idpz.bazarayesh.Models.estekhdam.ResponseMemberDetail;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

import static com.idpz.bazarayesh.Utils.AppController.getAppContext;

public class ShowMemberDetail extends BaseActivity implements View.OnClickListener {

    int id, count, backtag;


    int type = 0;
    int service_type = 0;
    double lat, lng;

    List<MainItem> itemsAward = new ArrayList<>();
    List<MainItem> itemsServices = new ArrayList<>();
    List<MainItem> itemsWorkSpace = new ArrayList<>();
    List<MainItem> itemsFamous = new ArrayList<>();

    RecyclerView recyclerfamausCustomer, recycleworkSpace, recycleservices, recycleaward, recyclerworkShop,recycleworkShop;

    ImageView imgLogo;

    TextView txtName, txtAddress, txtTelegram, txtInsta, awardTitle, workSpaceTitle, servicesTitle, workShopTitle, famousCustomerTitle;

    RelativeLayout relativeLayout6,relativeLayout2,relativeLayout3,relativeLayout4,relativeLayout5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_member_detail);

        try {
            id = (int) getIntent().getExtras().get("id");
        } catch (Exception e) {
        }




        settoolbarText("جزئیات");
        initViews();

        memberDetail();


    }

    public void initViews() {

        imgbBack.setVisibility(View.VISIBLE);

        relativeLayout6=findViewById(R.id.relative6);
        relativeLayout2=findViewById(R.id.relative2);
        relativeLayout3=findViewById(R.id.relative3);
        relativeLayout4=findViewById(R.id.relative4);
        relativeLayout5=findViewById(R.id.relative5);


        recyclerfamausCustomer = findViewById(R.id.famousCustomer);
        recycleworkSpace = findViewById(R.id.workSpace);
        recycleservices = findViewById(R.id.services);
        recycleaward = findViewById(R.id.award);
        recyclerworkShop = findViewById(R.id.workShop);

        imgLogo = findViewById(R.id.imgLogo);

        txtInsta = findViewById(R.id.txtinsta);
        txtName = findViewById(R.id.txtName);
        txtAddress = findViewById(R.id.txtAddress);

        txtTelegram = findViewById(R.id.txttelegram);


        awardTitle = findViewById(R.id.awardTitle);
        servicesTitle = findViewById(R.id.servicesTitle);
        workShopTitle = findViewById(R.id.workShopTitle);

        workSpaceTitle = findViewById(R.id.workSpaceTitle);
        famousCustomerTitle = findViewById(R.id.famousCustomerTitle);

        imgbBack.setOnClickListener(this);

    }


    public void memberDetail() {

        RequestParams params = new RequestParams();
        params.put("id", id);
        params.put("api_token", tools.getSharePrf("api_token"));
        params.put("APP_KEY", "bazarayesh:barber:11731e11b");
        String url = tools.baseUrl + "single_member";

        tools.client.post(url, params, new TextHttpResponseHandler() {

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {

                if (responseString.contains("ok")) {
                    //   ResponseListMember response=gson.fromJson(responseString,ResponseListMember.class);
                    //   response.getMembers().


                    ResponseMemberDetail response = gson.fromJson(responseString, ResponseMemberDetail.class);

                    Glide.with(activity)
                            .load("http://arayesh.myzibadasht.ir/" + response.getMember().getLogo())
                            .into(imgLogo);


                    txtName.setText(response.getMember().getFullName());

                    txtAddress.setText(response.getMember().getAddress());

                    txtInsta.setText(response.getMember().getInstagram());
                    txtTelegram.setText(response.getMember().getTelegram());
                    if (response.getMember().getAward().size() != 0) {

                        for (Award award : response.getMember().getAward()) {
                            award.getData();

                            itemsAward.add(new MainItem(award.getTitle(), "http://arayesh.myzibadasht.ir/" + award.getData(), 1));

                        }
                        setAdapter(itemsAward, recycleaward);


                    } else {
                        relativeLayout3.setVisibility(View.GONE);
                    }

                    if (response.getMember().getService().size() != 0) {
                        count = 0;
                        for (Service service : response.getMember().getService()) {

                            count++;
                            itemsServices.add(new MainItem(count + "خدمت", "http://arayesh.myzibadasht.ir/" + service.getPic(), 1));

                        }
                        setAdapter(itemsServices, recycleservices);


                    } else {
                        relativeLayout6.setVisibility(View.GONE);
                    }

                    if (response.getMember().getFamousCustomer().size() != 0) {
                        for (FamousCustomer famousCustomer : response.getMember().getFamousCustomer()) {

                            itemsFamous.add(new MainItem(famousCustomer.getTitle(), "http://arayesh.myzibadasht.ir/" + famousCustomer.getPic(), 1));

                        }

                        setAdapter(itemsFamous, recyclerfamausCustomer);


                    } else {
                        relativeLayout4.setVisibility(View.GONE);
                    }


                    if (response.getMember().getWorkplacePic().size() != 0) {
                        count = 0;
                        for (WorkplacePic workplacePic : response.getMember().getWorkplacePic()) {

                            count++;
                            itemsWorkSpace.add(new MainItem(count + " عکس ", "http://arayesh.myzibadasht.ir/" + workplacePic.getPic(), 1));

                        }
                        setAdapter(itemsWorkSpace, recycleworkSpace);


                    } else {
                        relativeLayout5.setVisibility(View.GONE);
                    }


                }
            }
        });
    }

    public void setAdapter(List<MainItem> items, RecyclerView recyclerView) {
        MemberDetailAdapter adapter = new MemberDetailAdapter(items, activity);
        recyclerView.setLayoutManager(new LinearLayoutManager(getAppContext(), LinearLayoutManager.HORIZONTAL, true));
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgbBack:

                finish();

                break;
        }
    }

    @Override
    public void onBackPressed() {

        finish();

    }
}

