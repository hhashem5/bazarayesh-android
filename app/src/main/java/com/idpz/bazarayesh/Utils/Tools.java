package com.idpz.bazarayesh.Utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.google.gson.Gson;
import com.idpz.bazarayesh.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;

import static com.idpz.bazarayesh.Utils.AppController.getAppContext;

public class Tools {

    public String baseUrl = "http://arayesh.myzibadasht.ir/api/";

    public AsyncHttpClient client = new AsyncHttpClient();

    public static Gson gson = new Gson();

    Context context;
    Activity activity;
    private Dialog internetDialog;

    public Tools(Context context) {
        this.context = context;
       // activity = (Activity) context;

    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public void noInternet(View.OnClickListener listener) {
        internetDialog = new Dialog(context);
        internetDialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        internetDialog.setContentView(R.layout.no_internet_dialog);
        internetDialog.setCancelable(false);
        internetDialog.show();
        TextView btnTry = internetDialog.findViewById(R.id.btnTry);
        btnTry.setOnClickListener(listener);
        internetDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }


    public void hideInternet() {
        try {
            internetDialog.dismiss();
        } catch (Exception e) {

        }
    }

    public void addToSharePrf(String key, String value) {
        PreferenceManager.getDefaultSharedPreferences(getAppContext()).edit()
                .putString(key, value).apply();
    }


    public String getSharePrf(String key) {
        return PreferenceManager.getDefaultSharedPreferences(getAppContext()).getString(key, "");
    }
}
