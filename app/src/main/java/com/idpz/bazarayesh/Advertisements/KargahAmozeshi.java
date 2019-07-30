package com.idpz.bazarayesh.Advertisements;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.idpz.bazarayesh.Adapters.DialogItemAdapter;
import com.idpz.bazarayesh.BaseActivity;
import com.idpz.bazarayesh.FontUtils;
import com.idpz.bazarayesh.Models.MainItem;
import com.idpz.bazarayesh.R;
import com.idpz.bazarayesh.SubProfileActivity;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.mohamadamin.persianmaterialdatetimepicker.date.DatePickerDialog;
import com.mohamadamin.persianmaterialdatetimepicker.time.RadialPickerLayout;
import com.mohamadamin.persianmaterialdatetimepicker.time.TimePickerDialog;
import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

import static com.idpz.bazarayesh.Utils.AppController.getAppContext;
import static maes.tech.intentanim.CustomIntent.customType;

public class KargahAmozeshi extends BaseActivity implements View.OnClickListener, TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener {

    TextView txt_corse, txt_day, txt_subject, txt_degree;

    RelativeLayout relative_day, relative_corse, relative_subject;
    private BottomSheetBehavior bottomSheetBehavior;
    List<MainItem> items;
    private static final String TIMEPICKER = "TimePickerDialog", DATEPICKER = "DatePickerDialog";

    Button btn;
    EditText etDescription;

    String day;

    Typeface irsans;
    String member_id;

    String course;
    int tag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kargah_amozeshi);

        tag = (int) getIntent().getExtras().get("tag");

        settoolbarText("کارگاه آموزشی");
        initVeiws();


    }

    public void initVeiws() {

        imgbBack.setVisibility(View.VISIBLE);
        txt_corse = findViewById(R.id.txt_corse);
        txt_day = findViewById(R.id.txt_day);
        txt_subject = findViewById(R.id.txt_subject);
        txt_degree = findViewById(R.id.txt_degree);
        etDescription = findViewById(R.id.etDescription);
        btn = findViewById(R.id.btn);


        relative_day = findViewById(R.id.relative_day);
        relative_corse = findViewById(R.id.relative_corse);
        relative_subject = findViewById(R.id.relative_subject);


        relative_corse.setOnClickListener(this);
        relative_day.setOnClickListener(this);
        relative_subject.setOnClickListener(this);
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


            case R.id.relative_corse:
                initComponent("مبحث");
                break;
            case R.id.relative_day:
                PersianCalendar now3 = new PersianCalendar();
                DatePickerDialog dpd2 = DatePickerDialog.newInstance(
                        KargahAmozeshi.this,
                        now3.getPersianYear(),
                        now3.getPersianMonth(),
                        now3.getPersianDay()
                );
                dpd2.setThemeDark(false);
                //dpd2.setTypeface(fontName);
                dpd2.show(getFragmentManager(), DATEPICKER);
                break;
            case R.id.imgbBack:

                Intent i1 = new Intent(KargahAmozeshi.this, SubProfileActivity.class);
                i1.putExtra("back", "2");

                i1.setAction(Intent.ACTION_MAIN);
                i1.addCategory(Intent.CATEGORY_HOME);
                i1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                i1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i1);
                customType(KargahAmozeshi.this, LEFT_TO_RIGHT);
                finish();

                break;
            case R.id.btn:

                registerKargahAmozeshi();

                break;


        }
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        day = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
        txt_day.setText("تاریخ " + day);

    }

    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute) {

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

        DialogItemAdapter dialogItemAdapter = new DialogItemAdapter(items, KargahAmozeshi.this, new DialogItemAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, Object object) {

                MainItem item = (MainItem) object;

                txt_corse.setText(item.getTitle());
                course = item.getId();
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);


            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(getAppContext(), LinearLayoutManager.VERTICAL, false));

        recyclerView.setAdapter(dialogItemAdapter);

    }


    public void addItems() {
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


    }

    @Override
    public void onBackPressed() {
        Intent i1 = new Intent(KargahAmozeshi.this, SubProfileActivity.class);
        i1.putExtra("back", "2");

        i1.setAction(Intent.ACTION_MAIN);
        i1.addCategory(Intent.CATEGORY_HOME);
        i1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        i1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i1);
        customType(KargahAmozeshi.this, LEFT_TO_RIGHT);
        finish();
        super.onBackPressed();
    }

    public void registerKargahAmozeshi() {

        String url = tools.baseUrl + "ads_store";
        pd.show();
        RequestParams params = new RequestParams();
        params.put("member", "workshop");


        params.put("date", day);
        params.put("course", Integer.parseInt(course));
        params.put("subject", txt_subject.getText().toString());
        params.put("evidence", txt_degree.getText().toString());

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
                if (responseString.contains("200")) {


                    successDialog("آگهی شما با موفقیت ثبت شد.");

                }
                pd.dismiss();

            }
        });


    }


}
