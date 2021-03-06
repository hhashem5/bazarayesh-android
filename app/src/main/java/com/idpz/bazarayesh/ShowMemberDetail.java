package com.idpz.bazarayesh;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.idpz.bazarayesh.Adapters.MemberDetailAdapter;
import com.idpz.bazarayesh.Models.Award;
import com.idpz.bazarayesh.Models.Course;
import com.idpz.bazarayesh.Models.FamousCustomer;
import com.idpz.bazarayesh.Models.MainItem;
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

    int id, count, type = 0;


    static String url, tag;
    int service_type = 0;
    double lat, lng;
    Typeface irsans;
    TextView txtView;

    boolean flagEdit = false;
    String Instagram, Telegram;
    List<MainItem> itemsAward = new ArrayList<>();
    List<MainItem> itemsServices = new ArrayList<>();
    List<MainItem> itemsWorkSpace = new ArrayList<>();
    List<MainItem> itemsFamous = new ArrayList<>();
    List<MainItem> itemsCourse = new ArrayList<>();

    RecyclerView recyclerfamausCustomer, recycleworkSpace, recycleservices, recycleaward, recyclerworkShop, recycleworkShop;

    ImageView imgLogo, imgTelegram, imgInsta;

    TextView txtName, txtAddress, awardTitle, workSpaceTitle, servicesTitle, workShopTitle,
            famousCustomerTitle, txtPhone, txtPhone2, managerName, fame, title6, title7, edit,manage_ads;

    RelativeLayout relativeLayout6, relativeLayout2, relativeLayout3, relativeLayout4, relativeLayout5;
    private DialogFullscreenImageFragment newFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_member_detail);



        irsans = Typeface.createFromAsset(getAssets(), "fonts/iran_sans.ttf");

        pd = new ProgressDialog(context);
        pd.setCancelable(false);
        pd.setMessage(FontUtils.typeface(irsans, getString(R.string.wait)));
        settoolbarText("جزئیات");
        try {
            if (getIntent().getExtras().get("id") != null)
                id = (int) getIntent().getExtras().get("id");

        } catch (Exception e) {
        }

        try {

            if (getIntent().getExtras().get("type") != null) {

                type = (int) getIntent().getExtras().get("type");
                edit.setVisibility(View.VISIBLE);
                manage_ads.setVisibility(View.VISIBLE);

                flagEdit = true;

            }

        } catch (Exception e) {

        }

        try {

            if (getIntent().getExtras().get("tag") != null)
                tag = (String) getIntent().getExtras().get("tag");
        } catch (Exception e) {
        }

        initViews();





        memberDetail();


    }

    public void initViews() {

        imgbBack.setVisibility(View.VISIBLE);

        edit = findViewById(R.id.edit);
        manage_ads=findViewById(R.id.manage_ads);
        relativeLayout6 = findViewById(R.id.relative6);
        relativeLayout2 = findViewById(R.id.relative2);
        relativeLayout3 = findViewById(R.id.relative3);
        relativeLayout4 = findViewById(R.id.relative4);
        relativeLayout5 = findViewById(R.id.relative5);
        title7 = findViewById(R.id.title7);
        title6 = findViewById(R.id.title6);
        txtView=findViewById(R.id.txtView);

        managerName = findViewById(R.id.managerName);
        fame = findViewById(R.id.fame);


        txtPhone = findViewById(R.id.txtphone);
        txtPhone2 = findViewById(R.id.txtphone2);
        imgInsta = findViewById(R.id.instagram);
        imgTelegram = findViewById(R.id.telegram);

        recyclerfamausCustomer = findViewById(R.id.famousCustomer);
        recycleworkSpace = findViewById(R.id.workSpace);
        recycleservices = findViewById(R.id.services);
        recycleaward = findViewById(R.id.award);
        recyclerworkShop = findViewById(R.id.workShop);

        imgLogo = findViewById(R.id.imgLogo);

        txtName = findViewById(R.id.txtName);
        txtAddress = findViewById(R.id.txtAddress);


        awardTitle = findViewById(R.id.awardTitle);
        servicesTitle = findViewById(R.id.servicesTitle);
        workShopTitle = findViewById(R.id.workShopTitle);

        workSpaceTitle = findViewById(R.id.workSpaceTitle);
        famousCustomerTitle = findViewById(R.id.famousCustomerTitle);


        Glide.with(ShowMemberDetail.this).load(R.drawable.instagram).into(imgInsta);
        imgbBack.setOnClickListener(this);

        imgTelegram.setOnClickListener(this);
        imgInsta.setOnClickListener(this);

        edit.setOnClickListener(this);
        manage_ads.setOnClickListener(this);

        if (!flagEdit)
        getView();


    }


    public void memberDetail() {

        pd.show();
        RequestParams params = new RequestParams();
        params.put("id", id);
        params.put("api_token", tools.getSharePrf("api_token"));
        params.put("APP_KEY", "bazarayesh:barber:11731e11b");
        if (tag != null)
            params.put("tag", tag);
        String url = tools.baseUrl + "single_member";

        tools.client.post(url, params, new TextHttpResponseHandler() {

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                try {
                    pd.dismiss();
                } catch (Exception e) {

               e.getMessage(); }
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {

                if (responseString.contains("ok")) {
                    //   ResponseListMember response=gson.fromJson(responseString,ResponseListMember.class);
                    //   response.getMembers().
                    pd.dismiss();

                    try {
                        ResponseMemberDetail response = gson.fromJson(responseString, ResponseMemberDetail.class);
                        Glide.with(activity)
                                .load("http://arayesh.myzibadasht.ir/" + response.getMember().getLogo())
                                .into(imgLogo);


                        txtName.setText(response.getMember().getFullName());

                        txtAddress.setText(response.getMember().getAddress());

                        Instagram = response.getMember().getInstagram();
                        Telegram = response.getMember().getTelegram();

                        txtPhone.setText(response.getMember().getPhone1());

                        txtPhone2.setText(response.getMember().getPhone2());
                        txtView.setText(String.valueOf(response.getMember().getView()));


                        if (response.getMember().getManagerName() != null) {

                            if (!response.getMember().getManagerName().equals("0")){
                            managerName.setVisibility(View.VISIBLE);
                            title6.setVisibility(View.VISIBLE);
                            managerName.setText(response.getMember().getManagerName());}
                        }


                        if (response.getMember().getCourse().size() != 0) {

                            for (Course course : response.getMember().getCourse()) {

                                if (course.getTitle() == null)
                                    course.setTitle("");

                                itemsCourse.add(new MainItem(course.getTitle(), "http://arayesh.myzibadasht.ir/" + course.getData(), 1));

                            }
                            setAdapter(itemsCourse, recyclerworkShop);
                        } else relativeLayout5.setVisibility(View.GONE);


                        if (response.getMember().getAward().size() != 0) {

                            for (Award award : response.getMember().getAward()) {

                                if (award.getTitle() == null)
                                    award.setTitle("");

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

                                if (service.getTitle() == null)
                                    service.setTitle("");


                                itemsServices.add(new MainItem(service.getTitle(), "http://arayesh.myzibadasht.ir/" + service.getPic(), 1));

                            }
                            setAdapter(itemsServices, recycleservices);


                        } else {
                            relativeLayout6.setVisibility(View.GONE);
                        }

                        if (response.getMember().getFamousCustomer().size() != 0) {
                            for (FamousCustomer famousCustomer : response.getMember().getFamousCustomer()) {

                                if (famousCustomer.getTitle() == null)
                                    famousCustomer.setTitle("");
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

                                if (workplacePic.getTitle() == null)
                                    workplacePic.setTitle("");
                                itemsWorkSpace.add(new MainItem(workplacePic.getTitle(), "http://arayesh.myzibadasht.ir/" + workplacePic.getPic(), 1));

                            }
                            setAdapter(itemsWorkSpace, recycleworkSpace);


                        } else {
                            relativeLayout2.setVisibility(View.GONE);
                        }

                    } catch (Exception e) {
                        e.getMessage();
                    }
                }
            }
        });
    }

    public void setAdapter(List<MainItem> items, RecyclerView recyclerView) {
        MemberDetailAdapter adapter = new MemberDetailAdapter(items, activity, new MemberDetailAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, Object object) {

                MainItem item = (MainItem) object;

                url = item.imgUrl;
                FragmentManager fragmentManager = getSupportFragmentManager();
                newFragment = new DialogFullscreenImageFragment();

                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                transaction.add(android.R.id.content, newFragment).addToBackStack(null).commit();
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(getAppContext(), LinearLayoutManager.HORIZONTAL, true));
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgbBack:
             onBackPressed();

                break;

            case R.id.instagram:
                Intent instagram = new Intent(Intent.ACTION_VIEW, Uri.parse("http://instagram.com/" + Telegram));
                startActivity(instagram);
                break;

            case R.id.telegram:
                Intent telegram = new Intent(Intent.ACTION_VIEW, Uri.parse("https://telegram.me/" + Telegram));
                startActivity(telegram);
                break;

            case R.id.edit:


                Intent intent = new Intent(ShowMemberDetail.this, SubProfileActivity.class);
                intent.putExtra("type", type);
                startActivity(intent);

                break;

            case R.id.manage_ads:
                Intent intent2 = new Intent(ShowMemberDetail.this, SubProfileActivity.class);
                intent2.putExtra("back","2");
                intent2.putExtra("type", type);
                startActivity(intent2);


                break;
        }
    }


    public void getView(){
        pd.show();
        RequestParams params = new RequestParams();
        params.put("member_id",String.valueOf(id));
        params.put("api_token", tools.getSharePrf("api_token"));
        params.put("APP_KEY", "bazarayesh:barber:11731e11b");
        String url=tools.baseUrl+"member_view";
        tools.client.post(url, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {


            }
        });

    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();

    }


}

