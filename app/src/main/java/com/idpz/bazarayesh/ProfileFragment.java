package com.idpz.bazarayesh;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import static com.idpz.bazarayesh.BaseActivity.LEFT_TO_RIGHT;
import static com.idpz.bazarayesh.MainActivity.navigation;
import static com.idpz.bazarayesh.Utils.AppController.getAppContext;
import static maes.tech.intentanim.CustomIntent.customType;

public class ProfileFragment extends BaseFragment implements View.OnClickListener, IOnBackPressed {

    TextView beautyshop, teacher, institude, store, hairdresser;

    ImageView check1, check2, check3, check4, check5;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // tools.state = "1";


        View v = inflater.inflate(R.layout.profile_fragment, container, false);

        settoolbarText(getString(R.string.title_profile), v);
        initViews(v);

        return v;


    }


    public void initViews(View v) {


        beautyshop = v.findViewById(R.id.beautyshop);

        teacher = v.findViewById(R.id.teacher);

        hairdresser = v.findViewById(R.id.hairdersser);

        institude = v.findViewById(R.id.institude);

        store = v.findViewById(R.id.store);

        check1 = v.findViewById(R.id.check1);
        check2 = v.findViewById(R.id.check2);
        check3 = v.findViewById(R.id.check3);
        check4 = v.findViewById(R.id.check4);
        check5 = v.findViewById(R.id.check5);

        try {

            if (!tools.getSharePrf("memberId1").equals(""))
                check1.setVisibility(View.VISIBLE);
            if (!tools.getSharePrf("memberId3").equals(""))
                check3.setVisibility(View.VISIBLE);
            if (!tools.getSharePrf("memberId2").equals(""))
                check2.setVisibility(View.VISIBLE);
            if (!tools.getSharePrf("memberId4").equals(""))
                check4.setVisibility(View.VISIBLE);
            if (!tools.getSharePrf("memberId5").equals(""))
                check5.setVisibility(View.VISIBLE);

        } catch (Exception e) {
        }
        beautyshop.setOnClickListener(this);
        teacher.setOnClickListener(this);
        store.setOnClickListener(this);
        institude.setOnClickListener(this);
        hairdresser.setOnClickListener(this);
    }

        @Override
        public void onClick (View view){

            switch (view.getId()) {
                case R.id.beautyshop:

                    Intent intent = new Intent(getActivity(), SubProfileActivity.class);
                    intent.putExtra("type", 1);
                    getActivity().startActivity(intent);
                    customType(getContext(), LEFT_TO_RIGHT);
                    getActivity().finish();
                    break;


                case R.id.teacher:

                    Intent intent2 = new Intent(getActivity(), SubProfileActivity.class);
                    intent2.putExtra("type", 4);
                    getActivity().startActivity(intent2);
                    customType(getContext(), LEFT_TO_RIGHT);
                    getActivity().finish();
                    break;

                case R.id.store:
                    Intent intent3 = new Intent(getActivity(), SubProfileActivity.class);
                    intent3.putExtra("type", 5);
                    getActivity().startActivity(intent3);
                    customType(getContext(), LEFT_TO_RIGHT);
                    getActivity().finish();
                    break;


                case R.id.hairdersser:
                    Intent intent4 = new Intent(getActivity(), SubProfileActivity.class);
                    intent4.putExtra("type", 2);
                    getActivity().startActivity(intent4);
                    customType(getContext(), LEFT_TO_RIGHT);
                    getActivity().finish();
                    break;

                case R.id.institude:
                    Intent intent5 = new Intent(getActivity(), SubProfileActivity.class);
                    intent5.putExtra("type", 3);
                    getActivity().startActivity(intent5);
                    customType(getContext(), LEFT_TO_RIGHT);
                    getActivity().finish();
                    break;


            }
        }

        private boolean loadFragment (Fragment fragment){
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
        public boolean onBackPressed () {

            if (tools.state.equals("3")) {
                Fragment fragment = new AdvertiseFragment();
                loadFragment(fragment);
                navigation.setSelectedItemId(R.id.navigation_advertise);
                tools.state = "1";


            } else if (tools.state.equals("2")) {
                Fragment fragment = new HomeFragment();
                loadFragment(fragment);
                navigation.setSelectedItemId(R.id.navigation_home);
                tools.state = "2";


            }
            return false;
        }
    }
