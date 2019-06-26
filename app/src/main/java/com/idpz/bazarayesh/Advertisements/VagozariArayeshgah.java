package com.idpz.bazarayesh.Advertisements;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.idpz.bazarayesh.Adapters.DialogItemAdapter;
import com.idpz.bazarayesh.BaseActivity;
import com.idpz.bazarayesh.Models.MainItem;
import com.idpz.bazarayesh.R;
import com.idpz.bazarayesh.SubProfileActivity;

import java.util.ArrayList;
import java.util.List;

import static com.idpz.bazarayesh.Utils.AppController.getAppContext;
import static maes.tech.intentanim.CustomIntent.customType;

public class VagozariArayeshgah extends BaseActivity implements View.OnClickListener {

    RelativeLayout relative_type, relative_case;

    TextView txt_case, txt_type;

    List<MainItem> items;

    LinearLayout llBottomSheet;
    private BottomSheetBehavior bottomSheetBehavior;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vagozari_arayeshgah);

        settoolbarText("واگذاری فضای آرایشگاهی");
        initViews();

    }

    public void initViews() {

        imgbBack.setVisibility(View.VISIBLE);
        txt_case = findViewById(R.id.txt_case);
        txt_type = findViewById(R.id.txt_type);

        relative_case = findViewById(R.id.relative_case);
        relative_type = findViewById(R.id.relative_type);

        LinearLayout llBottomSheet = findViewById(R.id.linear_bottomsheet);

        bottomSheetBehavior = BottomSheetBehavior.from(llBottomSheet);

        // change the state of the bottom sheet
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);


        relative_type.setOnClickListener(this);
        relative_case.setOnClickListener(this);
        imgbBack.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.relative_case:

                if (txt_type.getText().toString().equals("فروش"))
                    initComponent("3", "مورد واگذاری");
                else
                    initComponent("1", "مورد واگذاری");


                break;

            case R.id.relative_type:

                initComponent("2", "نوع واگذاری");

                break;

            case R.id.imgbBack:
                Intent i1 = new Intent(VagozariArayeshgah.this, SubProfileActivity.class);
                i1.setAction(Intent.ACTION_MAIN);
                i1.addCategory(Intent.CATEGORY_HOME);
                i1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                i1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i1);
                customType(VagozariArayeshgah.this, LEFT_TO_RIGHT);
                finish();

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

        DialogItemAdapter dialogItemAdapter = new DialogItemAdapter(items, VagozariArayeshgah.this, new DialogItemAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, Object object) {

                MainItem item = (MainItem) object;

                if (status.equals("1") || status.equals("3")) {
                    txt_case.setText(item.getTitle());
                } else if (status.equals("2")) {
                    txt_type.setText(item.getTitle());

                }

                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);

            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(getAppContext(), LinearLayoutManager.VERTICAL, false));

        recyclerView.setAdapter(dialogItemAdapter);

    }


    public void addItems(String status) {

        switch (status) {

            case "1":


                MainItem item1 = new MainItem("کل آرایشگاه با تجهیزات");
                MainItem item2 = new MainItem("کل آرایشگاه بدون تجهیزات");
                MainItem item3 = new MainItem("یک اتاق مستقل از آرایشگاه");
                MainItem item4 = new MainItem("یک جایگاه میز و صندلی");
                MainItem item5 = new MainItem("میز ویزه خدمات ناخن");
                MainItem item6 = new MainItem("تخت اپیلاسیون");


                items.add(item1);

                items.add(item2);

                items.add(item3);

                items.add(item4);

                items.add(item5);

                items.add(item6);


                break;

            case "2":

                MainItem item7 = new MainItem("فروش");
                MainItem item8 = new MainItem("اجاره");
                items.add(item7);
                items.add(item8);

                break;


            case "3":
                MainItem item9 = new MainItem("کل آرایشگاه با تجهیزات");
                MainItem item10 = new MainItem("کل آرایشگاه بدون تجهیزات");

                items.add(item9);
                items.add(item10);

        }


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();


    }
}
