package com.idpz.bazarayesh.Advertisements;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.idpz.bazarayesh.BaseActivity;
import com.idpz.bazarayesh.FontUtils;
import com.idpz.bazarayesh.MainActivity;
import com.idpz.bazarayesh.R;
import com.idpz.bazarayesh.SubProfileActivity;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.mohamadamin.persianmaterialdatetimepicker.date.DatePickerDialog;
import com.mohamadamin.persianmaterialdatetimepicker.time.RadialPickerLayout;
import com.mohamadamin.persianmaterialdatetimepicker.time.TimePickerDialog;
import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import cz.msebera.android.httpclient.Header;

import static com.idpz.bazarayesh.Utils.AppController.getActivity;
import static com.idpz.bazarayesh.Utils.AppController.getAppContext;


import static maes.tech.intentanim.CustomIntent.customType;

public class BazdidAroosActivity extends BaseActivity implements View.OnClickListener, TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener {

    RelativeLayout relative_since, relative_until, relative_date;
    TextView txtSince, txtUntil, txtDate;
    EditText etDescription;
    private static final String TIMEPICKER = "TimePickerDialog", DATEPICKER = "DatePickerDialog";

    int flag,tag;
    String member_id,mdate,shour,ehour;
    Button btn;
    Typeface irsans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bazdid_aroos);

        settoolbarText("آگهی بازدید عروس");

        initViews();
        tag = (int) getIntent().getExtras().get("tag");

    }

    public void initViews() {

        relative_since = findViewById(R.id.relative_since);
        relative_date = findViewById(R.id.relative_date);
        relative_until = findViewById(R.id.relative_until);
        txtSince = findViewById(R.id.txtSince);
        txtDate = findViewById(R.id.txtDate);
        etDescription=findViewById(R.id.etDescription);
        btn=findViewById(R.id.btn);

        txtUntil = findViewById(R.id.txtUntil);
        imgbBack.setVisibility(View.VISIBLE);


        imgbBack.setOnClickListener(this);
        relative_since.setOnClickListener(this);
        relative_until.setOnClickListener(this);
        relative_date.setOnClickListener(this);
        btn.setOnClickListener(this);

        irsans = Typeface.createFromAsset(getAssets(), "fonts/iran_sans.ttf");

        pd = new ProgressDialog(context);
        pd.setCancelable(false);
        pd.setMessage(FontUtils.typeface(irsans, getString(R.string.wait)));
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.imgbBack:

                Intent i1 = new Intent(BazdidAroosActivity.this, SubProfileActivity.class);
                i1.setAction(Intent.ACTION_MAIN);
                i1.addCategory(Intent.CATEGORY_HOME);
                i1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                i1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i1);
                customType(BazdidAroosActivity.this, LEFT_TO_RIGHT);
                finish();

                break;

            case R.id.btn:

                registerBazdidAroos();

                break;

            case R.id.relative_since:
                PersianCalendar now = new PersianCalendar();
                TimePickerDialog tpd = TimePickerDialog.newInstance(
                        BazdidAroosActivity.this,
                        now.get(PersianCalendar.HOUR_OF_DAY),
                        now.get(PersianCalendar.MINUTE),
                        true
                );
                tpd.setThemeDark(true);
                //tpd.setTypeface(fontName);
                tpd.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialogInterface) {
                        //   Log.d(TIMEPICKER, "Dialog was cancelled");
                    }
                });
                tpd.show(getFragmentManager(), TIMEPICKER);

                flag = 1;
                break;

            case R.id.relative_until:

                PersianCalendar now2 = new PersianCalendar();
                TimePickerDialog tpd2 = TimePickerDialog.newInstance(
                        BazdidAroosActivity.this,
                        now2.get(PersianCalendar.HOUR_OF_DAY),
                        now2.get(PersianCalendar.MINUTE),
                        true
                );
                tpd2.setThemeDark(true);
                //tpd.setTypeface(fontName);
                tpd2.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialogInterface) {
                        //   Log.d(TIMEPICKER, "Dialog was cancelled");
                    }
                });
                tpd2.show(getFragmentManager(), TIMEPICKER);

                flag = 2;


                break;

            case R.id.relative_date:
                PersianCalendar now3 = new PersianCalendar();
                DatePickerDialog dpd2 = DatePickerDialog.newInstance(
                        BazdidAroosActivity.this,
                        now3.getPersianYear(),
                        now3.getPersianMonth(),
                        now3.getPersianDay()
                );
                dpd2.setThemeDark(false);
                //dpd2.setTypeface(fontName);
                dpd2.show(getFragmentManager(), DATEPICKER);
                break;

        }
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {

        mdate =dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
        txtDate.setText( "تاریخ " + mdate);


    }

    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute) {

        String hourString = hourOfDay < 10 ? "0" + hourOfDay : "" + hourOfDay;
        String minuteString = minute < 10 ? "0" + minute : "" + minute;
        switch (flag) {
            case 1:
                shour=  hourString + ":" + minuteString;

                txtSince.setText("از ساعت " +shour);


                break;

            case 2:
                ehour= hourString + ":" + minuteString;

                txtUntil.setText("تا ساعت " + ehour);



                break;
        }


    }

    @Override
    public void onBackPressed() {
        Intent i1 = new Intent(BazdidAroosActivity.this, SubProfileActivity.class);
        i1.setAction(Intent.ACTION_MAIN);
        i1.addCategory(Intent.CATEGORY_HOME);
        i1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        i1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i1);
        customType(BazdidAroosActivity.this, LEFT_TO_RIGHT);
        finish();
        super.onBackPressed();
    }
    public void registerBazdidAroos() {

        String url = tools.baseUrl + "ads_store";
        pd.show();
        RequestParams params = new RequestParams();
        params.put("member", "bride");



        params.put("date", mdate);
        params.put("shour", shour);
        params.put("ehour", ehour);
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
