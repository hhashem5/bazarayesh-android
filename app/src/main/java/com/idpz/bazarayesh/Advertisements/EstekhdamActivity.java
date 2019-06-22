package com.idpz.bazarayesh.Advertisements;

import android.content.Intent;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.idpz.bazarayesh.Adapters.DialogItemAdapter;
import com.idpz.bazarayesh.BaseActivity;
import com.idpz.bazarayesh.Models.MainItem;
import com.idpz.bazarayesh.R;
import com.idpz.bazarayesh.SubProfileActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.idpz.bazarayesh.Utils.AppController.getAppContext;
import static maes.tech.intentanim.CustomIntent.customType;

public class EstekhdamActivity extends BaseActivity implements View.OnClickListener {

    RelativeLayout relative_expert, relative_title, relative_subject, relative_terms;

    TextView txtSubject, txtTitle, txtExpert,txtTerms;
    LinearLayout llBottomSheet;
    private BottomSheetBehavior bottomSheetBehavior;
    List<MainItem> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estekhdam);
        settoolbarText("اگهی استخدام");
        initViews();

    }

    public void initViews() {


        imgbBack.setVisibility(View.VISIBLE);
        relative_expert = findViewById(R.id.relative_expert);
        relative_subject = findViewById(R.id.relative_subject);
        relative_terms=findViewById(R.id.relative_terms);
        relative_title = findViewById(R.id.relative_title);
        txtSubject = findViewById(R.id.txtSubject);
        txtTitle = findViewById(R.id.txtTitle);
        txtExpert = findViewById(R.id.txtExpert);
        txtTerms=findViewById(R.id.txtTerms);

        relative_title.setOnClickListener(this);
        relative_subject.setOnClickListener(this);
        relative_expert.setOnClickListener(this);
        relative_terms.setOnClickListener(this);
        imgbBack.setOnClickListener(this);

        LinearLayout llBottomSheet = findViewById(R.id.linear_bottomsheet);

        bottomSheetBehavior = BottomSheetBehavior.from(llBottomSheet);

        // change the state of the bottom sheet
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
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
                initComponent("11","شرایط استخدام");
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
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                } else if (status.equals("2") || status.equals("3") || status.equals("4")
                        || status.equals("5") || status.equals("6") || status.equals("9") ||
                        status.equals("8")) {
                    txtTitle.setText(item.getTitle());
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                } else if (status.equals("10")) {
                    txtExpert.setText(item.getTitle());
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);

                }
                else if (status.equals("11")){
                    txtTerms.setText(item.getTitle());
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
                MainItem item1 = new MainItem("مو");
                MainItem item2 = new MainItem("پوست");
                MainItem item3 = new MainItem("ابرو");
                MainItem item4 = new MainItem("ناخن");
                MainItem item5 = new MainItem("مژه");
                MainItem item6 = new MainItem("چهره");


                items.add(item1);
                items.add(item2);
                items.add(item3);
                items.add(item4);
                items.add(item5);
                items.add(item6);

                break;
            case "2":
                MainItem item7 = new MainItem("اپلاسیون");
                MainItem item8 = new MainItem("تاتو");
                MainItem item9 = new MainItem("مراقبت از پوست");
                MainItem item10 = new MainItem("اصلاح صورت");


                items.add(item7);
                items.add(item8);
                items.add(item9);
                items.add(item10);

                break;

            case "3":

                MainItem item11 = new MainItem("شینیون");
                MainItem item12 = new MainItem("کراتین وصافی");
                MainItem item13 = new MainItem("کوتاهی مو");
                MainItem item14 = new MainItem("رنگ مش");
                MainItem item15 = new MainItem("بافت براشینگ");
                MainItem item16 = new MainItem("اکستنشن");


                items.add(item11);
                items.add(item12);
                items.add(item13);
                items.add(item14);
                items.add(item15);
                items.add(item16);
                break;

            case "4":

                MainItem item17 = new MainItem("میکاپ و گریم");
                MainItem item18 = new MainItem("آرایش عروس");
                items.add(item17);
                items.add(item18);
                break;

            case "5":
                MainItem item19 = new MainItem("اکستنشن کار مژه");
                MainItem item21 = new MainItem("مژه کار");
                items.add(item21);
                items.add(item19);
                break;
            case "6":
                MainItem item20 = new MainItem("ناخن کار");
                items.add(item20);
                break;


            case "8":
                MainItem item22 = new MainItem("ابرو کار");
                items.add(item22);
                break;
            case "9":
                MainItem item23 = new MainItem("اپلاسیون");
                MainItem item24 = new MainItem("تاتو");
                MainItem item25 = new MainItem("مراقبت از پوست");
                MainItem item26 = new MainItem("اصلاح صورت");

                MainItem item27 = new MainItem("ابرو کار");
                MainItem item28 = new MainItem("ناخن کار");
                MainItem item29 = new MainItem("اکستنشن کار مژه");
                MainItem item30 = new MainItem("مژه کار");
                MainItem item31 = new MainItem("میکاپ و گریم");
                MainItem item32 = new MainItem("آرایش عروس");
                items.add(item23);
                items.add(item24);
                items.add(item25);
                items.add(item26);
                items.add(item27);

                items.add(item28);
                items.add(item29);
                items.add(item30);
                items.add(item31);
                items.add(item32);

            case "10":
                MainItem item33 = new MainItem("دستیار / کارآموز");
                MainItem item34 = new MainItem("ماهر/ حرفه ای");
                MainItem item35 = new MainItem("استادکار/ فوق حرفه ای");
                items.add(item33);
                items.add(item34);
                items.add(item35);

            case "11":
                MainItem item36 = new MainItem("اجاره ماهیانه");
                MainItem item37 = new MainItem("درصدی و پورسانت");
                MainItem item38 = new MainItem("حقوق ثابت");
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

}
