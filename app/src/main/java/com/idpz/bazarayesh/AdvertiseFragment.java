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
import com.idpz.bazarayesh.Models.MainItem;
import com.idpz.bazarayesh.Utils.Tools;

import java.util.ArrayList;
import java.util.List;

import static com.idpz.bazarayesh.MainActivity.navigation;
import static com.idpz.bazarayesh.Utils.AppController.getAppContext;

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
                Intent intent = new Intent(getActivity(), AdsMapActivity.class);

                switch (mainItem.getTitle()) {
                    case "استخدام":
                        intent.putExtra("type", 3);//1
                        getActivity().startActivity(intent);
                        break;
                    case "کارگاه اموزشی":
                        intent.putExtra("type", 6);//2
                        getActivity().startActivity(intent);
                        break;
                    case "واگذاری فضای آرایشگاهی":
                        intent.putExtra("type", 4);//3
                        getActivity().startActivity(intent);
                        break;
                    case "دوره های آموزشی":
                        intent.putExtra("type", 5);//4
                        getActivity().startActivity(intent);
                        break;
                    case " بازدید عروس":
                        intent.putExtra("type", 7);//5
                        getActivity().startActivity(intent);
                        break;
                    case "تخفیف خدمات آرایشی":
                        intent.putExtra("type", 1);//6
                        getActivity().startActivity(intent);
                        break;
                    case "واگذاری تجهیزات آرایشگاهی":
                        intent.putExtra("type", 8);//7
                        getActivity().startActivity(intent);
                        break;
                    case "تخفیف محصولات آرایشی":
                        intent.putExtra("type", 9);//8
                        getActivity().startActivity(intent);
                        break;
                    case "تخفیف دوره آموزشی":
                        intent.putExtra("type", 2);//9
                        getActivity().startActivity(intent);
                        break;
                }
            }
        });

        recyclerView.setLayoutManager(new GridLayoutManager(getAppContext(), 3));

        recyclerView.setAdapter(mainAdvAdapter);
        return v;
    }


    public void addItems() {

        items = new ArrayList<>();
        items.add(new MainItem(R.drawable.ic_employees, "استخدام", "1"));
        items.add(new MainItem(R.drawable.ic_teaching, "کارگاه اموزشی", "2"));
        items.add(new MainItem(R.drawable.ic_door, "واگذاری فضای آرایشگاهی", "3"));
        items.add(new MainItem(R.drawable.ic_clipboard_with_pencil, "دوره های آموزشی", "4"));
        items.add(new MainItem(R.drawable.ic_bride, " بازدید عروس", "5"));
        items.add(new MainItem(R.drawable.ic_facial_cream_tube, "تخفیف خدمات آرایشی", "6"));
        items.add(new MainItem(R.drawable.ic_spray_bottle, "واگذاری تجهیزات آرایشگاهی", "7"));
        items.add(new MainItem(R.drawable.ic_noun_facial_wash, "تخفیف محصولات آرایشی", "8"));
        items.add(new MainItem(R.drawable.ic_noun_teacher, "تخفیف دوره آموزشی", "9"));

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
