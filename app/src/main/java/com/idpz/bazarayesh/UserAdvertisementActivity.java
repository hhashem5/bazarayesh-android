package com.idpz.bazarayesh;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.gson.JsonArray;
import com.idpz.bazarayesh.Adapters.UserAdvertisementAdapter;
import com.idpz.bazarayesh.Models.AdAssignment.ResponseAssignment;
import com.idpz.bazarayesh.Models.AdBride.ResponseBride;
import com.idpz.bazarayesh.Models.AdDisscount.ResponseDiscountAds;
import com.idpz.bazarayesh.Models.AdRecruiment.ResponseRecruiment;
import com.idpz.bazarayesh.Models.AdRegCourse.ResponseRegCourse;
import com.idpz.bazarayesh.Models.AdWorkShop.ResponseWorkShop;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

import static com.idpz.bazarayesh.Utils.AppController.getAppContext;

public class UserAdvertisementActivity extends BaseActivity {

    String ad_type, memberId;

    int ex;


    RecyclerView recycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_advertisement);

       try {
           memberId = (String) getIntent().getExtras().get("memid");
       }catch (Exception e){}

        try {
            ad_type = (String) getIntent().getExtras().get("tag");
        }catch (Exception e){}

     try {
         ex= (int) getIntent().getExtras().get("ex");
     }catch (Exception e){}


        recycler = findViewById(R.id.recycle);

        getMyAds();
    }

    public void getMyAds() {

        String url = tools.baseUrl + "ads_own";
        RequestParams params = new RequestParams();
        params.put("ad_type", ad_type);
        params.put("member_id", memberId);
        params.put("ex", ex);

        params.put("api_token", tools.getSharePrf("api_token"));
        params.put("APP_KEY", "bazarayesh:barber:11731e11b");

        tools.client.post(url, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {


                if (responseString.contains("ok")) {

                    switch (ad_type) {



                        case "Bride":
                            ResponseBride responseBride = gson.fromJson(responseString, ResponseBride.class);
                            responseBride.getAds();
                            UserAdvertisementAdapter advertisementAdapter1 = new UserAdvertisementAdapter(responseBride.getAds(), R.layout.disscounts_adapter, context, 1);
                            recycler.setLayoutManager(new LinearLayoutManager(getAppContext(), LinearLayoutManager.VERTICAL, false));
                            recycler.setAdapter(advertisementAdapter1);
                            break;

                        case "Discount_ads":
                            ResponseDiscountAds responseDiscountAds = gson.fromJson(responseString, ResponseDiscountAds.class);
                            responseDiscountAds.getAds();
                            UserAdvertisementAdapter discountAdapter = new UserAdvertisementAdapter(responseDiscountAds.getAds(), R.layout.disscounts_adapter, context, 1, 1);
                            recycler.setLayoutManager(new LinearLayoutManager(getAppContext(), LinearLayoutManager.VERTICAL, false));
                            recycler.setAdapter(discountAdapter);

                            break;


                        case "Workshops":
                            ResponseWorkShop responseWorkShop = gson.fromJson(responseString, ResponseWorkShop.class);
                            responseWorkShop.getAds();
                            UserAdvertisementAdapter workshopadapter = new UserAdvertisementAdapter(responseWorkShop.getAds(), context, R.layout.workshop_adapter);
                            recycler.setLayoutManager(new LinearLayoutManager(getAppContext(), LinearLayoutManager.VERTICAL, false));
                            recycler.setAdapter(workshopadapter);

                            break;
                        case "Assignment":
                            ResponseAssignment responseAssignment = gson.fromJson(responseString, ResponseAssignment.class);
                            responseAssignment.getAds();



                            UserAdvertisementAdapter assignmentadapter = new UserAdvertisementAdapter(responseAssignment.getAds(),  R.layout.assignment_adapter,context);
                            recycler.setLayoutManager(new LinearLayoutManager(getAppContext(), LinearLayoutManager.VERTICAL, false));
                            recycler.setAdapter(assignmentadapter);

                            break;
                        case "Recruiment":
                            ResponseRecruiment responseRecruiment = gson.fromJson(responseString, ResponseRecruiment.class);
                            responseRecruiment.getAds();
                            UserAdvertisementAdapter advertisementAdapter2 = new UserAdvertisementAdapter(responseRecruiment.getAds(), context, R.layout.recruiment_adapter, 1);
                            recycler.setLayoutManager(new LinearLayoutManager(getAppContext(), LinearLayoutManager.VERTICAL, false));
                            recycler.setAdapter(advertisementAdapter2);

                            break;

                        case "Reg_Course":
                             ResponseRegCourse responseCourses=gson.fromJson(responseString,ResponseRegCourse.class);
                              responseCourses.getAds();
                            UserAdvertisementAdapter CourseAdapter = new UserAdvertisementAdapter(1,responseCourses.getAds(), context, R.layout.recruiment_adapter);
                            recycler.setLayoutManager(new LinearLayoutManager(getAppContext(), LinearLayoutManager.VERTICAL, false));
                            recycler.setAdapter(CourseAdapter);

                            break;


                    }

                }
            }
        });


    }
}
