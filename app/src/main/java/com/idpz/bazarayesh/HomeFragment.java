package com.idpz.bazarayesh;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.idpz.bazarayesh.Adapters.MainItemAdapter;
import com.idpz.bazarayesh.Adapters.MainSliderAdapter;
import com.idpz.bazarayesh.Models.MainItem;
import com.idpz.bazarayesh.Models.PicName;

import java.util.ArrayList;
import java.util.List;

import ss.com.bannerslider.Slider;
import ss.com.bannerslider.event.OnSlideClickListener;

@SuppressLint("ValidFragment")
public class HomeFragment extends BaseFragment {

    private Slider banner_slider;

    RecyclerView recyclerView;
    private Context context;
    List<MainItem> items = new ArrayList<>();
    final List<PicName> picNames = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View v = inflater.inflate(R.layout.home_fragment, container, false);
        settoolbarText(getString(R.string.title_home), v);
        initViews(v);
        picNames.add(new PicName(1, "jsj", "https://setare.com/files/fa/news/1397/8/21/200856_426.jpg", "memo0"));
        picNames.add(new PicName(2, "jsj", "http://kheshtan.ir/wp-content/uploads/2019/04/kh9.jpg", "memo0"));

        Slider.init(new GlideImageLoadingService(context));

        banner_slider.setAdapter(new MainSliderAdapter(picNames, context));
        banner_slider.setOnSlideClickListener(new OnSlideClickListener() {
            @Override
            public void onSlideClick(int position) {

            }
        });
        setMyAdapter("page1", v);

        return v;


    }

    public void initViews(View v) {

        banner_slider = v.findViewById(R.id.banner_slider);
        recyclerView = v.findViewById(R.id.recycle);

    }


    @SuppressLint("ValidFragment")
    public HomeFragment(Context context) {
        this.context = context;
    }

    public void addItems(String status) {

        switch (status) {
            case "page1":
                MainItem item1 = new MainItem("1", R.drawable.ic_chair, "#a83a7f", "آرایشگاه");
                MainItem item2 = new MainItem("2", R.drawable.ic_teacher, "#a492ac", "مدرس");
                MainItem item3 = new MainItem("3", R.drawable.ic_makeover, "#87bcc2", "آرایشگر");
                MainItem item4 = new MainItem("4", R.drawable.ic_salon, "#EC87AC", "آموزشگاه");


                items.add(item1);
                items.add(item2);
                items.add(item3);
                items.add(item4);

                break;

            case "page2":
                MainItem item5 = new MainItem("5", R.drawable.ic_chair, "#a83a7f", "مو");
                MainItem item6 = new MainItem("6", R.drawable.ic_teacher, "#a482ac", "پوست");
                MainItem item7 = new MainItem("7", R.drawable.ic_makeover, "#87b222", "ابرو");
                MainItem item8 = new MainItem("8", R.drawable.ic_salon, "#EC877C", "ناخن");
                MainItem item9 = new MainItem("9", R.drawable.ic_salon, "#Eb87AC", "مژه");
                MainItem item10 = new MainItem("10", R.drawable.ic_salon, "#E887AC", "چهره");


                items.add(item5);
                items.add(item6);
                items.add(item7);
                items.add(item8);
                items.add(item9);
                items.add(item10);

                break;

            case "page3":

                MainItem item11 = new MainItem("11", R.drawable.ic_chair, "#b83a7f", "شینیون");
                MainItem item12 = new MainItem("12", R.drawable.ic_teacher, "#a592ac", "کراتین وصافی");
                MainItem item13 = new MainItem("13", R.drawable.ic_makeover, "#c7bcc2", "کوتاهی مو");
                MainItem item14 = new MainItem("14", R.drawable.ic_salon, "#EC77AC", "رنگ مش");
                MainItem item15 = new MainItem("15", R.drawable.ic_salon, "#bC87AC", "بافت براشینگ");


                items.add(item11);
                items.add(item12);
                items.add(item13);
                items.add(item14);
                items.add(item15);
                break;

        }
    }


    public void setMyAdapter(final String status, final View v) {
        items = new ArrayList<>();

        addItems(status);

        MainItemAdapter itemAdapter = new MainItemAdapter(items, (Activity) context, new MainItemAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, Object object) {
                //   items = new ArrayList<>();
                imgbBack.setVisibility(View.VISIBLE);
                MainItem item = (MainItem) object;



                switch (item.getId()) {
                    case "1":
                        setMyAdapter("page2", v);

                        break;
                    case "5":
                        setMyAdapter("page3", v);

                        break;
                }




            }
        });
        switch (status) {

            case "page2":
                imgbBack.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        setMyAdapter("page1", v);
                   //     settoolbarText("خانه", v);
                        imgbBack.setVisibility(View.GONE);

                    }
                });
                break;
            case "page3":
                imgbBack.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        setMyAdapter("page2", v);


                    }
                });
                break;
        }
        recyclerView.setLayoutManager(new GridLayoutManager(context, 2));
        recyclerView.setAdapter(itemAdapter);

    }


}
