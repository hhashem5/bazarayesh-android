package com.idpz.bazarayesh.Advertisements;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.idpz.bazarayesh.BaseActivity;
import com.idpz.bazarayesh.FontUtils;
import com.idpz.bazarayesh.R;
import com.idpz.bazarayesh.SubProfileActivity;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.mohamadamin.persianmaterialdatetimepicker.date.DatePickerDialog;
import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar;

import cz.msebera.android.httpclient.Header;

import static maes.tech.intentanim.CustomIntent.customType;

public class TakhfifKhadamat extends BaseActivity implements View.OnClickListener , DatePickerDialog.OnDateSetListener{

    RelativeLayout relative_since, relative_until, relative_percent;

    TextView txt_until,txt_since,txt_percent;

    int flag;

    EditText txt_relation,txt_srvices,etDescription;
    String since, until;
    String member_id;

    int tag;

    Typeface irsans;
    Button btn;
    private static final String DATEPICKER = "DatePickerDialog";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_takhfif_khadamat);

        tag = (int) getIntent().getExtras().get("tag");

        settoolbarText("تخفیف خدمات آرایشی");
        initViews();

    }

    public void initViews() {


        imgbBack.setVisibility(View.VISIBLE);


        txt_percent=findViewById(R.id.txt_percent);
        txt_since=findViewById(R.id.txt_since);
        txt_until=findViewById(R.id.txt_until);

        txt_relation = findViewById(R.id.txtRelation);
        txt_srvices=findViewById(R.id.txtServices);
        etDescription=findViewById(R.id.etDescription);

        btn=findViewById(R.id.btn);

        relative_percent = findViewById(R.id.relative_percent);
        relative_since = findViewById(R.id.relative_since);
        relative_until = findViewById(R.id.relative_until);

        relative_until.setOnClickListener(this);
        relative_since.setOnClickListener(this);
        relative_percent.setOnClickListener(this);
        imgbBack.setOnClickListener(this);
        btn.setOnClickListener(this);

        irsans = Typeface.createFromAsset(getAssets(), "fonts/iran_sans.ttf");

        pd = new ProgressDialog(context);
        pd.setCancelable(false);
        pd.setMessage(FontUtils.typeface(irsans, getString(R.string.wait)));
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.relative_percent:
                AlertDialog();
                break;

            case R.id.relative_since:
                flag = 1;
                PersianCalendar now = new PersianCalendar();
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        TakhfifKhadamat.this,
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
                        TakhfifKhadamat.this,
                        now1.getPersianYear(),
                        now1.getPersianMonth(),
                        now1.getPersianDay()
                );
                dpd1.setThemeDark(false);
                //dpd2.setTypeface(fontName);
                dpd1.show(getFragmentManager(), DATEPICKER);
                break;

            case R.id.imgbBack:
                Intent i1 = new Intent(TakhfifKhadamat.this, SubProfileActivity.class);
                i1.putExtra("back","2");

                i1.setAction(Intent.ACTION_MAIN);
                i1.addCategory(Intent.CATEGORY_HOME);
                i1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                i1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i1);
                customType(TakhfifKhadamat.this, LEFT_TO_RIGHT);
                finish();

                break;

            case R.id.btn:
                takhfifKhadamat();
                break;
        }

    }


    public void AlertDialog() {
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/IRANSans(FaNum).ttf");

        AlertDialog.Builder builder = new AlertDialog.Builder(TakhfifKhadamat.this);
        // builder.setMessage(FontUtils.typeface(typeface, "درصد تخفیف را به عدد وارد نمایید"));
        LayoutInflater li = LayoutInflater.from(getApplicationContext());
        View promptsView = li.inflate(R.layout.alert_dialog, null);
//        final EditText input = new EditText(context);
//        input.setPadding(40, 20, 40, 20);
//        input.setTypeface(typeface);
//        input.setGravity(Gravity.RIGHT);
//
//        input.setBackground(getResources().getDrawable(R.drawable.layout_corners_shape_border));

        builder.setView(promptsView);

        final EditText userInput = (EditText) promptsView.findViewById(R.id.etUserInput);

        builder.setPositiveButton(FontUtils.typeface(typeface, "تایید"), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                txt_percent.setText(" % " + userInput.getText().toString());
                dialog.dismiss();
            }
        });

        builder.setNegativeButton(FontUtils.typeface(typeface, "انصراف"), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.show();
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        switch (flag) {
            case 1:
                since = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;


                txt_since.setText("تاریخ " + since);
                break;
            case 2:
                until = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;

                txt_until.setText("تاریخ " + until);
                break;
        }
    }


    public void takhfifKhadamat() {

        String url = tools.baseUrl + "ads_store";
        pd.show();
        RequestParams params = new RequestParams();
        params.put("member", "discount_ad");


        params.put("ad_event", txt_relation.getText().toString());
        params.put("sdate", since);
        params.put("edate", until);
        params.put("affair", txt_srvices.getText().toString());
        //todo افراد params.put("specified", .getText().toString());
        params.put("discount", txt_percent.getText().toString());


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

    @Override
    public void onBackPressed() {
        Intent i1 = new Intent(TakhfifKhadamat.this, SubProfileActivity.class);
        i1.putExtra("back","2");

        i1.setAction(Intent.ACTION_MAIN);
        i1.addCategory(Intent.CATEGORY_HOME);
        i1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        i1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i1);
        customType(TakhfifKhadamat.this, LEFT_TO_RIGHT);
        finish();
        super.onBackPressed();
    }
}
