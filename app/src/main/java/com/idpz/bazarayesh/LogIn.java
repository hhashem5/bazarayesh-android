package com.idpz.bazarayesh;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
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

public class LogIn extends BaseActivity implements View.OnClickListener {

    mEditText txtMobile;

    mButton btnLogin;

    Typeface irsans;

    String mobile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);





        irsans = Typeface.createFromAsset(getAssets(), "fonts/iran_sans.ttf");

        pd = new ProgressDialog(activity);
        pd.setCancelable(false);
        pd.setMessage(FontUtils.typeface(irsans, getString(R.string.wait)));

        initViews();

        try {
            mobile = (String) getIntent().getExtras().get("mobile");

            if (mobile!=null)
                txtMobile.setText(mobile);

        } catch (Exception e) {
        }
        txtMobile.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {

                if (i == EditorInfo.IME_ACTION_GO) {

                    if (tools.isNetworkAvailable()) {
                        if (txtMobile.getText().toString().length() == 11) {
                            loginFunc();

                        } else {
                            txtMobile.setError(getString(R.string.error1));
                        }
                    } else tools.noInternet(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            if (tools.isNetworkAvailable()) {
                                loginFunc();
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

        txtMobile = findViewById(R.id.txtMobile);

        btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(this);

    }

    private void loginFunc() {

        pd.show();
        String url = tools.baseUrl + "registerWithMobile";
        RequestParams params = new RequestParams();

        params.put("mobile", txtMobile.getText().toString());


        params.put("APP_KEY", "bazarayesh:barber:11731e11b");


        tools.client.post(url, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.d(TAG, "onFailure: " + throwable);

                pd.dismiss();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                try {


                    tools.addToSharePrf("mobile", txtMobile.getText().toString());
                    Intent i1 = new Intent(getApplicationContext(), VerificationActivity.class);
                    i1.setAction(Intent.ACTION_MAIN);
                    i1.addCategory(Intent.CATEGORY_HOME);
                    i1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    i1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    i1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    overridePendingTransition(0, 0);

                    startActivity(i1);

                    customType(context, LEFT_TO_RIGHT);

                    finish();

                    pd.dismiss();
                    Log.d(TAG, "onSuccess: " + responseString);

                } catch (Exception e) {
                }
            }
        });
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.btnLogin:

                if (txtMobile.getText().toString().length() == 11) {
                    if (tools.isNetworkAvailable()) {

                        loginFunc();
///////////////////////////////////////////////////////////// bayad hazf shavad ////////////////////////////////////////////////////////
//                        tools.addToSharePrf("mobile",txtMobile.getText().toString());
//                        Intent i1 = new Intent(getApplicationContext(), VerificationActivity.class);
//                        i1.setAction(Intent.ACTION_MAIN);
//                        i1.addCategory(Intent.CATEGORY_HOME);
//                        i1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                        i1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                        i1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                        overridePendingTransition(0, 0);

//                        startActivity(i1);
//                        customType(context,"left-to-right");
//                        finish();

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

                    } else tools.noInternet(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            if (tools.isNetworkAvailable()) {

                                loginFunc();
                                tools.hideInternet();
                            } else
                                Toast.makeText(context, getString(R.string.nonet), Toast.LENGTH_SHORT).show();

                        }
                    });

                } else
                    txtMobile.setError(getString(R.string.error1));


                break;
        }


    }


}
