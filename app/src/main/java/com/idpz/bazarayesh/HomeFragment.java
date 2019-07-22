package com.idpz.bazarayesh;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.idpz.bazarayesh.Adapters.MainBannerSliderAdapter;
import com.idpz.bazarayesh.Adapters.MainBannerSliderAdapter2;
import com.idpz.bazarayesh.Adapters.MainItemAdapter;
import com.idpz.bazarayesh.Models.BannerModels.ResponseBanner;
import com.idpz.bazarayesh.Models.MainItem;
import com.idpz.bazarayesh.Models.PicName;
import com.idpz.bazarayesh.Utils.Tools;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import ss.com.bannerslider.Slider;
import ss.com.bannerslider.event.OnSlideClickListener;

import static com.idpz.bazarayesh.BaseActivity.BOTTOM_TO_UP;
import static com.idpz.bazarayesh.BaseActivity.RIGHT_TO_LEFT;
import static com.idpz.bazarayesh.Utils.AppController.getAppContext;
import static maes.tech.intentanim.CustomIntent.customType;

public class HomeFragment extends BaseFragment implements IOnBackPressed {

    private Slider banner_slider;

    RecyclerView recyclerView;

    List<MainItem> items = new ArrayList<>();
    final List<PicName> picNames = new ArrayList<>();

    private boolean doubleBackToExitPressedOnce = false;

    public String page = "page1";

    int type;
    Tools tools;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View v = inflater.inflate(R.layout.home_fragment, container, false);
        // tools.state = "2";

        settoolbarText(getString(R.string.title_home), v);
        initViews(v);
//        picNames.add(new PicName(1, "jsj", "https://setare.com/files/fa/news/1397/8/21/200856_426.jpg", "memo0"));
//        picNames.add(new PicName(2, "jsj", "http://kheshtan.ir/wp-content/uploads/2019/04/kh9.jpg", "memo0"));
//
//
//        banner_slider.setAdapter(new MainSliderAdapter(picNames, getContext()));
//        banner_slider.setOnSlideClickListener(new OnSlideClickListener() {
//            @Override
//            public void onSlideClick(int position) {
//
//            }
//        });
        setMyAdapter("page1");

        return v;


    }

    public void initViews(View v) {

        tools = new Tools(getContext());
        banner_slider = v.findViewById(R.id.banner_slider);
        recyclerView = v.findViewById(R.id.recycle);
        Slider.init(new GlideImageLoadingService(getContext()));


     //   if (tools.isNetworkAvailable())
            getBanner();
//        else
//        {
//                    picNames.add(new PicName(1, "jsj", "https://setare.com/files/fa/news/1397/8/21/200856_426.jpg", "memo0"));
//            banner_slider.setAdapter(new MainBannerSliderAdapter2(picNames, getContext()));
//            banner_slider.setOnSlideClickListener(new OnSlideClickListener() {
//                @Override
//                public void onSlideClick(int position) {
//
//                }
//            });
//        }

    }


    public void addItems(String status) {

        switch (status) {
            case "page1":
                MainItem item1 = new MainItem("1", R.drawable.ic_chair, "#a83a7f", "آرایشگاه");
                MainItem item2 = new MainItem("2", R.drawable.ic_teacher, "#a492ac", "مدرس");
                MainItem item3 = new MainItem("3", R.drawable.ic_makeover, "#87bcc2", "آرایشگر");
                MainItem item4 = new MainItem("4", R.drawable.ic_school, "#EC87AC", "آموزشگاه");
                MainItem item0 = new MainItem("0", R.drawable.ic_salon, "#E555AC", "فروشگاه");
                items.add(item3);

                items.add(item1);
                items.add(item2);
                items.add(item4);
                items.add(item0);

                break;

            case "page2":
                MainItem item5 = new MainItem("5", R.drawable.ic_comb2, "#a83a7f", "مو");
                MainItem item6 = new MainItem("6", R.drawable.ic_skin, "#a482ac", "پوست");
                MainItem item7 = new MainItem("7", R.drawable.ic_eyebrows, "#87b222", "ابرو");
                MainItem item8 = new MainItem("8", R.drawable.nail1, "#EC877C", "ناخن");
                MainItem item9 = new MainItem("9", R.drawable.ic_eyelashes, "#Eb87AC", "مژه");
                MainItem item10 = new MainItem("10", R.drawable.ic_face, "#E887AC", "چهره");


                items.add(item5);
                items.add(item6);
                items.add(item7);
                items.add(item8);
                items.add(item9);
                items.add(item10);

                break;

            case "page3":

                MainItem item11 = new MainItem("11", R.drawable.ic_shinion, "#b83a7f", "شینیون");
                MainItem item12 = new MainItem("12", R.drawable.ic_hairstyle, "#a592ac", "کراتین وصافی");
                MainItem item13 = new MainItem("13", R.drawable.ic_haircut2, "#c7bcc2", "کوتاهی مو");
                MainItem item14 = new MainItem("14", R.drawable.ic_hairstyle4, "#EC77AC", "رنگ مش");
                MainItem item15 = new MainItem("15", R.drawable.ic_baft, "#bC87AC", "بافت براشینگ");


                items.add(item11);
                items.add(item12);
                items.add(item13);
                items.add(item14);
                items.add(item15);
                break;


            case "page3_1":


                MainItem item16 = new MainItem("16", R.drawable.ic_wax, "#b83a7f", "اپیلاسیون");
                MainItem item17 = new MainItem("17", R.drawable.ic_tatoo, "#a592ac", "تاتو");
                MainItem item18 = new MainItem("18", R.drawable.ic_skin_care, "#c7bcc2", "مراقبت از پوست");
                MainItem item19 = new MainItem("19", R.drawable.ic_skin, "#EC77AC", "اصلاح صورت");


                items.add(item16);
                items.add(item17);
                items.add(item18);
                items.add(item19);
                break;


            case "page3_2":

                MainItem item20 = new MainItem("20", R.drawable.ic_face, "#b83a7f", "مکاپ و گریم");
                MainItem item21 = new MainItem("21", R.drawable.ic_bride2, "#a592ac", "آرایش عروس");


                items.add(item20);
                items.add(item21);

                break;

            case "page3_3":

                MainItem item22 = new MainItem("22", R.drawable.ic_eyelashes, "#b83a7f", "اکستنشن کار مژه");


                items.add(item22);

                break;

            case "page3_4":

                MainItem item23 = new MainItem("23", R.drawable.nail1, "#b83a7f", "ناخن کار");


                items.add(item23);

                break;

            case "page3_5":

                MainItem item24 = new MainItem("24", R.drawable.ic_eyelashes, "#b83a7f", "ابرو کار");


                items.add(item24);

                break;


        }
    }


    public void setMyAdapter(final String status) {

        page = status;
        items = new ArrayList<>();

        addItems(status);

        MainItemAdapter itemAdapter = new MainItemAdapter(items, getActivity(), new MainItemAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, Object object) {
                //   items = new ArrayList<>();
                imgbBack.setVisibility(View.VISIBLE);
                MainItem item = (MainItem) object;


                switch (item.getId()) {


                    case "0":
                        type = 5;


                        Intent intent4 = new Intent(getAppContext(), MapsActivity.class);
                        intent4.putExtra("type", type);
                        intent4.setAction(Intent.ACTION_MAIN);
                        intent4.addCategory(Intent.CATEGORY_HOME);
                        intent4.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent4.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent4.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        customType(getContext(), BOTTOM_TO_UP);


                        startActivity(intent4);
                        customType(getContext(), RIGHT_TO_LEFT);

                        getActivity().finish();
                        break;
                    case "1":
                        type = 1;

                        Intent intent5 = new Intent(getAppContext(), MapsActivity.class);
                        intent5.putExtra("type", type);
                        intent5.setAction(Intent.ACTION_MAIN);
                        intent5.addCategory(Intent.CATEGORY_HOME);
                        intent5.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent5.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent5.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        customType(getContext(), BOTTOM_TO_UP);


                        startActivity(intent5);
                        customType(getContext(), RIGHT_TO_LEFT);

                        getActivity().finish();

                        break;

                    //modares
                    case "2":
                        type = 4;
                        setMyAdapter("page2");

                        break;

                    //arayeshgar
                    case "3":
                        type = 2;
                        setMyAdapter("page2");

                        break;

                    case "4":
                        type = 3;
                        Intent intent6 = new Intent(getAppContext(), MapsActivity.class);
                        intent6.putExtra("type", type);
                        intent6.setAction(Intent.ACTION_MAIN);
                        intent6.addCategory(Intent.CATEGORY_HOME);
                        intent6.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent6.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent6.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        customType(getContext(), BOTTOM_TO_UP);


                        startActivity(intent6);
                        customType(getContext(), RIGHT_TO_LEFT);

                        getActivity().finish();

                        break;


                    case "5":
                        setMyAdapter("page3");

                        break;


                    case "6":
                        setMyAdapter("page3_1");
                        break;

                    case "7":
                        setMyAdapter("page3_5");
                        break;


                    case "8":
                        setMyAdapter("page3_4");

                        break;

                    case "9":

                        setMyAdapter("page3_3");

                        break;

                    case "10":
                        setMyAdapter("page3_2");

                        break;


                    /////////////////////////////////////////////////////////////////

                    case "11":
                        startmActivity("service_type", 5);

                        break;


                    case "12":
                        startmActivity("service_type", 10);

                        break;
                    case "13":
                        startmActivity("service_type", 6);

                        break;
                    case "14":
                        startmActivity("service_type", 7);

                        break;
                    case "15":
                        startmActivity("service_type", 8);

                        break;
                    case "16":
                        startmActivity("service_type", 1);

                        break;
                    case "17":
                        startmActivity("service_type", 2);

                        break;
                    case "18":
                        startmActivity("service_type", 3);

                        break;
                    case "19":
                        startmActivity("service_type", 4);

                        break;
                    case "20":
                        startmActivity("service_type", 11);

                        break;
                    case "21":
                        startmActivity("service_type", 12);

                        break;
                    case "22":
                        startmActivity("service_type", 13);

                        break;
                    case "23":
                        startmActivity("service_type", 14);

                        break;
                    case "24":
                        startmActivity("service_type", 15);

                        break;


                }


            }
        });
        switch (status) {

            case "page2":
                imgbBack.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        setMyAdapter("page1");
                        //     settoolbarText("خانه", v);
                        imgbBack.setVisibility(View.GONE);

                    }
                });
                break;
            case "page3":
                imgbBack.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        setMyAdapter("page2");


                    }
                });
                break;

            case "page3_1":
                imgbBack.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        setMyAdapter("page2");


                    }
                });
                break;

            case "page3_5":
                imgbBack.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        setMyAdapter("page2");


                    }
                });
                break;

            case "page3_4":
                imgbBack.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        setMyAdapter("page2");


                    }
                });
                break;
            case "page3_3":
                imgbBack.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        setMyAdapter("page2");


                    }
                });
                break;
            case "page3_2":
                imgbBack.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        setMyAdapter("page2");


                    }
                });
                break;
        }
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerView.setAdapter(itemAdapter);

    }


    private boolean loadFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }


    @Override
    public boolean onBackPressed() {

        switch (page) {
            case "page1":
                if (doubleBackToExitPressedOnce) {
                    getActivity().finish();
                }

                doubleBackToExitPressedOnce = true;
                Toast.makeText(getContext(), "برای خروج دوباره کلید بازگشت را بزنید", Toast.LENGTH_SHORT).show();
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        doubleBackToExitPressedOnce = false;
                    }
                }, 2000);

                break;

            case "page2":
                setMyAdapter("page1");
                //     settoolbarText("خانه", v);
                imgbBack.setVisibility(View.GONE);
                break;

            case "page3":
                setMyAdapter("page2");

                break;


            case "page3_1":

                setMyAdapter("page2");


                break;

            case "page3_5":

                setMyAdapter("page2");


                break;

            case "page3_4":

                setMyAdapter("page2");


                break;
            case "page3_3":

                setMyAdapter("page2");


                break;
            case "page3_2":

                setMyAdapter("page2");


                break;
        }

        return false;
    }

    public void startmActivity(String key, int value) {
        Intent intent4 = new Intent(getAppContext(), MapsActivity.class);
        intent4.putExtra(key, value);
        intent4.putExtra("type", type);
        intent4.setAction(Intent.ACTION_MAIN);
        intent4.addCategory(Intent.CATEGORY_HOME);
        intent4.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent4.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent4.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        customType(getContext(), BOTTOM_TO_UP);


        startActivity(intent4);
        customType(getContext(), RIGHT_TO_LEFT);

        getActivity().finish();
    }


    public void getBanner() {

        String url = tools.baseUrl + "slider";

        RequestParams params = new RequestParams();
        params.put("api_token", tools.getSharePrf("api_token"));
        params.put("APP_KEY", "bazarayesh:barber:11731e11b");

        tools.client.post(url, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {


            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {

                try {

                    if (responseString.contains("ok")) {

                        ResponseBanner responseBanner = tools.gson.fromJson(responseString, ResponseBanner.class);


                        banner_slider.setAdapter(new MainBannerSliderAdapter(responseBanner.getBaners(), getContext()));
                        banner_slider.setOnSlideClickListener(new OnSlideClickListener() {
                            @Override
                            public void onSlideClick(int position) {

                            }
                        });
                    }
                } catch (Exception e) {
                    e.getMessage();
                }

            }
        });
    }
}
