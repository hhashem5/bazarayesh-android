package com.idpz.bazarayesh;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.idpz.bazarayesh.Models.VerifyResponse;
import com.idpz.bazarayesh.Views.mButton;
import com.idpz.bazarayesh.Views.mEditText;
import com.idpz.bazarayesh.Views.mTextView;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import cz.msebera.android.httpclient.Header;

import static maes.tech.intentanim.CustomIntent.customType;

public class VerificationActivity extends BaseActivity implements View.OnClickListener {
    mEditText txtCode;

    mButton btnVerify;

    TextView correct, resend, timer_txt, txtMobile;

    Timer timer;

    long timercount;

    int check;
    Typeface irsans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);
        irsans = Typeface.createFromAsset(getAssets(), "fonts/iran_sans.ttf");

        pd = new ProgressDialog(activity);
        pd.setCancelable(false);
        pd.setMessage(FontUtils.typeface(irsans, getString(R.string.wait)));
        initViews();
        gettime();


        txtMobile.setText(tools.getSharePrf("mobile"));

        txtCode.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {

                if (i == EditorInfo.IME_ACTION_GO) {

                    if (tools.isNetworkAvailable()) {
                        if (txtCode.getText().toString().length() == 4) {
                            verifyFunc();

                        } else {
                            Toast.makeText(context, getString(R.string.error2), Toast.LENGTH_LONG).show();
                        }
                    } else tools.noInternet(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            if (tools.isNetworkAvailable()) {
                                verifyFunc();
                                tools.hideInternet();
                            } else
                                Toast.makeText(context, getString(R.string.nonet), Toast.LENGTH_SHORT).show();

                        }
                    });
                }
                return false;
            }
        });

    }

    public void initViews() {

        txtCode = findViewById(R.id.txtCode);
        btnVerify = findViewById(R.id.btnVerify);
        timer_txt = findViewById(R.id.timer);
        correct = findViewById(R.id.correct);
        resend = findViewById(R.id.resend);
        txtMobile = findViewById(R.id.txtMobile);

        btnVerify.setOnClickListener(this);
        resend.setOnClickListener(this);
        correct.setOnClickListener(this);
    }

    public void verifyFunc() {

        pd.show();
        String url = tools.baseUrl + "checkAuthCode";

        RequestParams params = new RequestParams();
        params.put("mobile", tools.getSharePrf("mobile"));
        params.put("code", txtCode.getText().toString());
        params.put("APP_KEY", "bazarayesh:barber:11731e11b");

        tools.client.post(url, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

                pd.dismiss();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                try {

                    pd.dismiss();

                    if (responseString.contains("200"))
                    {

                        VerifyResponse response=gson.fromJson(responseString,VerifyResponse.class);

                    tools.addToSharePrf("api_token", response.getUser().getApiToken());
                    Intent i1 = new Intent(getApplicationContext(), MainActivity.class);
                    i1.setAction(Intent.ACTION_MAIN);
                    i1.addCategory(Intent.CATEGORY_HOME);
                    i1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    i1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    i1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    overridePendingTransition(0,0);
                    startActivity(i1);

                    customType(context, RIGHT_TO_LEFT);

                    finish();}
                } catch (Exception e) {
                }
            }
        });

    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btnVerify:
                if (tools.isNetworkAvailable()) {
                    if (txtCode.getText().toString().length() == 4) {
                        verifyFunc();

                    } else {
                        Toast.makeText(context, getString(R.string.error2), Toast.LENGTH_LONG).show();
                    }
                } else tools.noInternet(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if (tools.isNetworkAvailable()) {
                            verifyFunc();
                            tools.hideInternet();
                        } else
                            Toast.makeText(context, getString(R.string.nonet), Toast.LENGTH_SHORT).show();

                    }
                });
                break;

            case R.id.resend:
                if (tools.isNetworkAvailable()) {


                }


                break;

            case R.id.correct:
                Intent i1 = new Intent(getApplicationContext(), LogIn.class);
                i1.setAction(Intent.ACTION_MAIN);
                i1.addCategory(Intent.CATEGORY_HOME);
                i1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                i1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                overridePendingTransition(0, 0);

                startActivity(i1);
                customType(context, RIGHT_TO_LEFT);

                finish();

                break;

        }
    }

    void gettime() {
        timercount = 60;
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        timercount -= 1;
                        timer_txt.setText(gettimer(timercount));
                        resend.setEnabled(false);
                        resend.setTextColor(Color.GRAY);
                        if (timercount == 0) {
                            timer.cancel();
                            timer_txt.setText("00:00");
                            resend.setTextColor(Color.WHITE);
                            resend.setEnabled(true);

                            check = 1;
                        }
                    }
                });
            }
        }, 0, 1000);
    }

    public String gettimer(long timercount) {
        long secound = (timercount);
        long mint = secound / 60;
        secound %= 60;
        return String.format(Locale.ENGLISH, "%02d", mint) + " : " + String.format(Locale.ENGLISH, "%02d", secound);
    }

}
