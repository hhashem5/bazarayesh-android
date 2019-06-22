package com.idpz.bazarayesh.Advertisements;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
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
import com.mohamadamin.persianmaterialdatetimepicker.date.DatePickerDialog;
import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar;

import java.util.ArrayList;
import java.util.List;

import static com.idpz.bazarayesh.Utils.AppController.getAppContext;
import static maes.tech.intentanim.CustomIntent.customType;

public class TakhfifAmozeshi extends BaseActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener {

    RelativeLayout relative_since, relative_until, relative_percent;

    TextView txt_since, txt_until, txt_percent;

    int flag;

    LinearLayout llBottomSheet;
    private BottomSheetBehavior bottomSheetBehavior;

    List<MainItem> items;
    View inflatedLayout;
    private static final String DATEPICKER = "DatePickerDialog";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_takhfif_amozeshi);

        settoolbarText("تخفیف دوره وکارگاه آموزشی");
        initViews();

    }

    public void initViews() {

        imgbBack.setVisibility(View.VISIBLE);
        txt_percent = findViewById(R.id.txt_percent);
        txt_since = findViewById(R.id.txt_since);
        txt_until = findViewById(R.id.txt_until);

        relative_since = findViewById(R.id.relative_since);
        relative_percent = findViewById(R.id.relative_percent);
        relative_until = findViewById(R.id.relative_until);


        relative_until.setOnClickListener(this);
        relative_percent.setOnClickListener(this);
        relative_since.setOnClickListener(this);

        imgbBack.setOnClickListener(this);



    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.relative_since:
                flag = 1;
                PersianCalendar now = new PersianCalendar();
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        TakhfifAmozeshi.this,
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
                        TakhfifAmozeshi.this,
                        now1.getPersianYear(),
                        now1.getPersianMonth(),
                        now1.getPersianDay()
                );
                dpd1.setThemeDark(false);
                //dpd2.setTypeface(fontName);
                dpd1.show(getFragmentManager(), DATEPICKER);
                break;


            case R.id.relative_percent:
                AlertDialog();
                break;

            case R.id.imgbBack:

                Intent i1 = new Intent(TakhfifAmozeshi.this, SubProfileActivity.class);
                i1.setAction(Intent.ACTION_MAIN);
                i1.addCategory(Intent.CATEGORY_HOME);
                i1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                i1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i1);
                customType(TakhfifAmozeshi.this, LEFT_TO_RIGHT);
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


    public void AlertDialog() {
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/IRANSans(FaNum).ttf");

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
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
                txt_percent.setText(" % " +userInput.getText().toString());
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
    public void onBackPressed() {
        Intent i1 = new Intent(TakhfifAmozeshi.this, SubProfileActivity.class);
        i1.setAction(Intent.ACTION_MAIN);
        i1.addCategory(Intent.CATEGORY_HOME);
        i1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        i1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i1);
        customType(TakhfifAmozeshi.this, LEFT_TO_RIGHT);
        finish();
        super.onBackPressed();
    }

}
