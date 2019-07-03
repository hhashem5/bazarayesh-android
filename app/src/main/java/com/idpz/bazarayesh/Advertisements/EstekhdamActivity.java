package com.idpz.bazarayesh.Advertisements;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.idpz.bazarayesh.Adapters.DialogItemAdapter;
import com.idpz.bazarayesh.BaseActivity;
import com.idpz.bazarayesh.FontUtils;
import com.idpz.bazarayesh.MainActivity;
import com.idpz.bazarayesh.Models.MainItem;
import com.idpz.bazarayesh.R;
import com.idpz.bazarayesh.SubProfileActivity;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cz.msebera.android.httpclient.Header;

import static com.idpz.bazarayesh.SubProfileActivity.tag;
import static com.idpz.bazarayesh.Utils.AppController.getAppContext;
import static maes.tech.intentanim.CustomIntent.customType;

public class EstekhdamActivity extends BaseActivity implements View.OnClickListener {

    RelativeLayout relative_expert, relative_title, relative_subject, relative_terms;

    TextView txtSubject, txtTitle, txtExpert, txtTerms;
    EditText etDescription;
    LinearLayout llBottomSheet;
    private BottomSheetBehavior bottomSheetBehavior;
    List<MainItem> items;
    Button btn;
    Typeface irsans;
    String member_id;

    int tag;

    int subject, title, lvl, conditions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estekhdam);
        settoolbarText("اگهی استخدام");
        initViews();
        tag = (int) getIntent().getExtras().get("tag");


    }

    public void initViews() {


        imgbBack.setVisibility(View.VISIBLE);
        relative_expert = findViewById(R.id.relative_expert);
        relative_subject = findViewById(R.id.relative_subject);
        relative_terms = findViewById(R.id.relative_terms);
        relative_title = findViewById(R.id.relative_title);
        txtSubject = findViewById(R.id.txtSubject);
        txtTitle = findViewById(R.id.txtTitle);
        txtExpert = findViewById(R.id.txtExpert);
        txtTerms = findViewById(R.id.txtTerms);

        etDescription = findViewById(R.id.etDescription);

        btn = findViewById(R.id.btn);

        relative_title.setOnClickListener(this);
        relative_subject.setOnClickListener(this);
        relative_expert.setOnClickListener(this);
        relative_terms.setOnClickListener(this);
        imgbBack.setOnClickListener(this);
        btn.setOnClickListener(this);

        LinearLayout llBottomSheet = findViewById(R.id.linear_bottomsheet);

        bottomSheetBehavior = BottomSheetBehavior.from(llBottomSheet);

        // change the state of the bottom sheet
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);

        irsans = Typeface.createFromAsset(getAssets(), "fonts/iran_sans.ttf");

        pd = new ProgressDialog(context);
        pd.setCancelable(false);
        pd.setMessage(FontUtils.typeface(irsans, getString(R.string.wait)));

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.imgbBack:

                Intent i1 = new Intent(EstekhdamActivity.this, SubProfileActivity.class);
                i1.setAction(Intent.ACTION_MAIN);
                i1.addCategory(Intent.CATEGORY_HOME);
                i1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                i1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i1);
                customType(EstekhdamActivity.this, LEFT_TO_RIGHT);
                finish();

                break;
            case R.id.relative_subject:
                initComponent("1", "موضوع تخصص");
                break;

            case R.id.relative_expert:
                initComponent("10", "سطح تخصص");
                break;
            case R.id.relative_title:
                String text = "عنوان تخصص";
                switch (txtSubject.getText().toString()) {
                    case "مو":
                        initComponent("3", text);
                        break;

                    case "پوست":
                        initComponent("2", text);
                        break;

                    case "ابرو":
                        initComponent("8", text);
                        break;

                    case "ناخن":
                        initComponent("6", text);
                        break;

                    case "مژه":
                        initComponent("5", text);
                        break;

                    case "چهره":
                        initComponent("4", text);
                        break;

                    default:
                        initComponent("9", text);


                }
                break;

            case R.id.relative_terms:
                initComponent("11", "شرایط استخدام");
                break;

            case R.id.btn:
//                for (MainItem item : items) {
//
//                    if (item.getTitle().equals(txtSubject.getText().toString())) {
//                        subject = Integer.valueOf(item.getId());
//                    }
//
//                    if (item.getTitle().equals(txtTitle.getText().toString())) {
//                        title = Integer.valueOf(item.getId());
//                    }
//                    if (item.getTitle().equals(txtExpert.getText().toString())) {
//                        lvl = Integer.valueOf(item.getId());
//                    }
//                    if (item.getTitle().equals(txtTerms.getText().toString())) {
//                        conditions = Integer.valueOf(item.getId());
//                    }
//
//
//                }
                registerEstekhdam(subject, title, lvl, conditions);
                break;
        }
    }


    private void initComponent(String status, String title) {

        // get the bottom sheet view
        try {
            LinearLayout llBottomSheet = findViewById(R.id.linear_bottomsheet);


            ImageButton imgclose = findViewById(R.id.imgbClose);
            TextView txtTitle = findViewById(R.id.too_title);


            setAdapter(status);

            txtTitle.setText(title);
            imgclose.setVisibility(View.VISIBLE);
            imgclose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);

                }
            });
            // init the bottom sheet behavior
            bottomSheetBehavior = BottomSheetBehavior.from(llBottomSheet);

            // change the state of the bottom sheet
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);

            // set callback for changes
            bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {

                @Override
                public void onStateChanged(@NonNull View bottomSheet, int newState) {
                    //    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

                }

                @Override
                public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                    //      bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

                }
            });


        } catch (Exception e) {
        }
    }


    public void setAdapter(final String status) {

        items = new ArrayList<>();

        addItems(status);

        RecyclerView recyclerView = findViewById(R.id.recycle);

        DialogItemAdapter dialogItemAdapter = new DialogItemAdapter(items, EstekhdamActivity.this, new DialogItemAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, Object object) {

                MainItem item = (MainItem) object;

                if (status.equals("1")) {
                    txtSubject.setText(item.getTitle());
                    subject= Integer.parseInt(item.getId());
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                } else if (status.equals("2") || status.equals("3") || status.equals("4")
                        || status.equals("5") || status.equals("6") || status.equals("9") ||
                        status.equals("8")) {
                    txtTitle.setText(item.getTitle());
                    title= Integer.parseInt(item.getId());
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                } else if (status.equals("10")) {
                    txtExpert.setText(item.getTitle());
                    lvl= Integer.parseInt(item.getId());

                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);

                } else if (status.equals("11")) {
                    txtTerms.setText(item.getTitle());

                    conditions= Integer.parseInt(item.getId());

                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);

                }


            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(getAppContext(), LinearLayoutManager.VERTICAL, false));

        recyclerView.setAdapter(dialogItemAdapter);

    }

    public void addItems(String status) {

        switch (status) {

            case "1":
                MainItem item1 = new MainItem("مو", "2");
                MainItem item2 = new MainItem("پوست", "1");
                MainItem item3 = new MainItem("ابرو", "3");
                MainItem item4 = new MainItem("ناخن", "4");
                MainItem item5 = new MainItem("مژه", "5");
                MainItem item6 = new MainItem("چهره", "6");


                items.add(item1);
                items.add(item2);
                items.add(item3);
                items.add(item4);
                items.add(item5);
                items.add(item6);
                break;

            case "2":
                MainItem item7 = new MainItem("اپلاسیون", "1");
                MainItem item8 = new MainItem("تاتو", "2");
                MainItem item9 = new MainItem("مراقبت از پوست", "3");
                MainItem item10 = new MainItem("اصلاح صورت", "4");


                items.add(item7);
                items.add(item8);
                items.add(item9);
                items.add(item10);
                break;

            case "3":

                MainItem item11 = new MainItem("شینیون", "5");
                MainItem item12 = new MainItem("کراتین وصافی", "10");
                MainItem item13 = new MainItem("کوتاهی مو", "6");
                MainItem item14 = new MainItem("رنگ مش", "7");
                MainItem item15 = new MainItem("بافت براشینگ", "8");
                MainItem item16 = new MainItem("اکستنشن", "9");


                items.add(item11);
                items.add(item12);
                items.add(item13);
                items.add(item14);
                items.add(item15);
                items.add(item16);
                break;

            case "4":

                MainItem item17 = new MainItem("میکاپ و گریم", "11");
                MainItem item18 = new MainItem("آرایش عروس", "12");
                items.add(item17);
                items.add(item18);
                break;

            case "5":
                MainItem item19 = new MainItem("اکستنشن کار مژه", "13");
                // MainItem item21 = new MainItem("مژه کار");
                // items.add(item21);
                items.add(item19);
                break;

            case "6":
                MainItem item20 = new MainItem("ناخن کار", "14");
                items.add(item20);
                break;

            case "8":
                MainItem item22 = new MainItem("ابرو کار", "15");
                items.add(item22);
                break;

            case "9":
                MainItem item23 = new MainItem("اپلاسیون", "11");
                MainItem item24 = new MainItem("تاتو", "2");
                MainItem item25 = new MainItem("مراقبت از پوست", "3");
                MainItem item26 = new MainItem("اصلاح صورت");

                MainItem item27 = new MainItem("ابرو کار", "15");
                MainItem item28 = new MainItem("ناخن کار", "14");
                MainItem item29 = new MainItem("اکستنشن کار مژه", "13");
                //  MainItem item30 = new MainItem("مژه کار");
                MainItem item31 = new MainItem("میکاپ و گریم", "11");
                MainItem item32 = new MainItem("آرایش عروس", "12");
                items.add(item23);
                items.add(item24);
                items.add(item25);
                items.add(item26);
                items.add(item27);
                items.add(item28);
                items.add(item29);
                //items.add(item30);
                items.add(item31);
                items.add(item32);
                break;

            case "10":
                MainItem item33 = new MainItem("دستیار / کارآموز", "1");
                MainItem item34 = new MainItem("ماهر/ حرفه ای", "2");
                MainItem item35 = new MainItem("استادکار/ فوق حرفه ای", "3");
                items.add(item33);
                items.add(item34);
                items.add(item35);
                break;

            case "11":
                MainItem item36 = new MainItem("اجاره ماهیانه", "1");
                MainItem item37 = new MainItem("درصدی و پورسانت", "2");
                MainItem item38 = new MainItem("حقوق ثابت", "3");
                items.add(item36);
                items.add(item37);
                items.add(item38);
                break;


        }
    }


    @Override
    public void onBackPressed() {
        Intent i1 = new Intent(EstekhdamActivity.this, SubProfileActivity.class);
        i1.setAction(Intent.ACTION_MAIN);
        i1.addCategory(Intent.CATEGORY_HOME);
        i1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        i1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i1);
        customType(EstekhdamActivity.this, LEFT_TO_RIGHT);
        finish();
        super.onBackPressed();
    }


    public void registerEstekhdam(int subject, int expertise, int lvl, int conditions) {

        String url = tools.baseUrl + "ads_store";
        pd.show();
        RequestParams params = new RequestParams();
        params.put("member", "recruiment");



        params.put("subject", subject);
        params.put("expertise", expertise);
        params.put("lvl", lvl);
        params.put("conditions", conditions);
        params.put("description", etDescription.getText().toString());


        switch (tag) {
            case 1:
                member_id = tools.getSharePrf("memberId1");
                //    params.put("id", );

                break;
            case 2:

                member_id = tools.getSharePrf("memberId2");
                // params.put("id", tools.getSharePrf("memberId2"));

                break;
            case 3:
                member_id = tools.getSharePrf("memberId3");

                //  params.put("id", tools.getSharePrf("memberId3"));

                break;
            case 4:
                member_id = tools.getSharePrf("memberId4");

                // params.put("id", tools.getSharePrf("memberId4"));

                break;
            case 5:
                member_id = tools.getSharePrf("memberId5");

                // params.put("id", tools.getSharePrf("memberId5"));

                break;
        }
        params.put("mem_id", member_id);

        params.put("api_token", tools.getSharePrf("api_token"));
        params.put("APP_KEY", "bazarayesh:barber:11731e11b");
        tools.client.post(url, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

                pd.dismiss();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {


                if (responseString.contains("200")){
                    successDialog("آگهی شما با موفقیت ثبت شد.");

                }
                pd.dismiss();


            }
        });


    }

}
