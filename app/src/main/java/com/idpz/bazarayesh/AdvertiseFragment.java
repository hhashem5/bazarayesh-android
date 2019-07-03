package com.idpz.bazarayesh;

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

import com.idpz.bazarayesh.Adapters.MainAdvAdapter;
import com.idpz.bazarayesh.Advertisements.BazdidAroosActivity;
import com.idpz.bazarayesh.Models.MainItem;
import com.idpz.bazarayesh.Utils.Tools;

import java.util.ArrayList;
import java.util.List;

import static com.idpz.bazarayesh.BaseActivity.RIGHT_TO_LEFT;
import static com.idpz.bazarayesh.MainActivity.navigation;
import static com.idpz.bazarayesh.Utils.AppController.getAppContext;
import static maes.tech.intentanim.CustomIntent.customType;

public class AdvertiseFragment extends BaseFragment implements IOnBackPressed {
    List<MainItem> items;

    MainAdvAdapter mainAdvAdapter;
    Tools tools;

    RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //tools.state = "3";

        View v = inflater.inflate(R.layout.advertise_fragment, container, false);

        settoolbarText(getString(R.string.title_advertise), v);
        tools = new Tools(getContext());

        initViews(v);

        addItems();
        mainAdvAdapter = new MainAdvAdapter(items, getActivity(), new MainAdvAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, Object object) {
                MainItem mainItem = (MainItem) object;
                switch (mainItem.getTitle()) {


                }
            }
        });

        recyclerView.setLayoutManager(new GridLayoutManager(getAppContext(), 3));

        recyclerView.setAdapter(mainAdvAdapter);
        return v;
    }


    public void addItems() {

        items = new ArrayList<>();

        items.add(new MainItem(R.drawable.ic_employees, "استخدام"));
        items.add(new MainItem(R.drawable.ic_teaching, "کارگاه اموزشی"));
        items.add(new MainItem(R.drawable.ic_door, "واگذاری فضای آرایشگاهی"));
        items.add(new MainItem(R.drawable.ic_clipboard_with_pencil, "دوره های آموزشی"));
        items.add(new MainItem(R.drawable.ic_bride, " بازدید عروس"));
        items.add(new MainItem(R.drawable.ic_facial_cream_tube, "تخفیف خدمات آرایشی"));
        items.add(new MainItem(R.drawable.ic_spray_bottle, "واگذاری تجهیزات آرایشگاهی"));
        items.add(new MainItem(R.drawable.ic_noun_facial_wash, "تخفیف محصولات آرایشی"));
        items.add(new MainItem(R.drawable.ic_noun_teacher, "تخفیف دوره آموزشی"));


    }


    public void initViews(View v) {
        recyclerView = v.findViewById(R.id.recycle);

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


        if (tools.state.equals("2")) {
            Fragment fragment = new HomeFragment();
            loadFragment(fragment);
            navigation.setSelectedItemId(R.id.navigation_home);
            tools.state = "2";

        } else if (tools.state.equals("1")) {
            Fragment fragment = new ProfileFragment();
            loadFragment(fragment);
            navigation.setSelectedItemId(R.id.navigation_profile);
            tools.state = "3";

        }

        return false;
    }
}
