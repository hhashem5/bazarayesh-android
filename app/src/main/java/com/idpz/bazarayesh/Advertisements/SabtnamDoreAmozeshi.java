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
import com.mohamadamin.persianmaterialdatetimepicker.date.DatePickerDialog;
import com.mohamadamin.persianmaterialdatetimepicker.time.RadialPickerLayout;
import com.mohamadamin.persianmaterialdatetimepicker.time.TimePickerDialog;
import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar;

import java.util.ArrayList;
import java.util.List;

import static com.idpz.bazarayesh.Utils.AppController.getAppContext;
import static maes.tech.intentanim.CustomIntent.customType;

public class SabtnamDoreAmozeshi extends BaseActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener {


    TextView txt_until, txt_since, txt_subject;

    RelativeLayout relative_since, relative_subject, relative_until;

    private static final String DATEPICKER = "DatePickerDialog";

    LinearLayout llBottomSheet;
    private BottomSheetBehavior bottomSheetBehavior;

    List<MainItem> items;

    int flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sabtnam_dore_amozeshi);

        settoolbarText("ثبتنام دوره آموزشی");
        initViews();

    }

    public void initViews() {

        imgbBack.setVisibility(View.VISIBLE);

        txt_since = findViewById(R.id.txt_since);
        txt_subject = findViewById(R.id.txt_subject);
        txt_until = findViewById(R.id.txt_until);

        relative_since = findViewById(R.id.relative_since);
        relative_until = findViewById(R.id.relative_until);
        relative_subject = findViewById(R.id.relative_subject);

        relative_subject.setOnClickListener(this);
        relative_until.setOnClickListener(this);
        relative_since.setOnClickListener(this);

        imgbBack.setOnClickListener(this);

        LinearLayout llBottomSheet = findViewById(R.id.linear_bottomsheet);

        bottomSheetBehavior = BottomSheetBehavior.from(llBottomSheet);

        // change the state of the bottom sheet
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {


            case R.id.relative_since:

                flag = 1;
                PersianCalendar now = new PersianCalendar();
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        SabtnamDoreAmozeshi.this,
                        now.getPersianYear(),
                        now.getPersianMonth(),
                        now.getPersianDay()
                );
                dpd.setThemeDark(false);
                //dpd2.setTypeface(fontName);
                dpd.show(getFragmentManager(), DATEPICKER);
                break;


            case R.id.relative_until:

                flag = 2;
                PersianCalendar now1 = new PersianCalendar();
                DatePickerDialog dpd1 = DatePickerDialog.newInstance(
                        SabtnamDoreAmozeshi.this,
                        now1.getPersianYear(),
                        now1.getPersianMonth(),
                        now1.getPersianDay()
                );
                dpd1.setThemeDark(false);
                //dpd2.setTypeface(fontName);
                dpd1.show(getFragmentManager(), DATEPICKER);
                break;


            case R.id.relative_subject:
                initComponent("مبحث");
                break;


            case R.id.imgbBack:

                Intent i1 = new Intent(SabtnamDoreAmozeshi.this, SubProfileActivity.class);
                i1.setAction(Intent.ACTION_MAIN);
                i1.addCategory(Intent.CATEGORY_HOME);
                i1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                i1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i1);
                customType(SabtnamDoreAmozeshi.this, LEFT_TO_RIGHT);
                finish();

                break;

        }


    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date = "تاریخ " + dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
        switch (flag) {
            case 1:
                txt_since.setText(date);
                break;
            case 2:

                txt_until.setText(date);
                break;
        }
    }

    private void initComponent(String title) {

        // get the bottom sheet view
        try {
            LinearLayout llBottomSheet = findViewById(R.id.linear_bottomsheet);


            ImageButton imgclose = findViewById(R.id.imgbClose);
            TextView txtTitle = findViewById(R.id.too_title);


            setAdapter();

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


    public void setAdapter() {

        items = new ArrayList<>();

        addItems();

        RecyclerView recyclerView = findViewById(R.id.recycle);

        DialogItemAdapter dialogItemAdapter = new DialogItemAdapter(items, SabtnamDoreAmozeshi.this, new DialogItemAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, Object object) {

                MainItem item = (MainItem) object;

                txt_subject.setText(item.getTitle());
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);

            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(getAppContext(), LinearLayoutManager.VERTICAL, false));

        recyclerView.setAdapter(dialogItemAdapter);

    }


    public void addItems() {
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
    }
    @Override
    public void onBackPressed() {
        Intent i1 = new Intent(SabtnamDoreAmozeshi.this, SubProfileActivity.class);
        i1.setAction(Intent.ACTION_MAIN);
        i1.addCategory(Intent.CATEGORY_HOME);
        i1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        i1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i1);
        customType(SabtnamDoreAmozeshi.this, LEFT_TO_RIGHT);
        finish();
        super.onBackPressed();
    }
}
