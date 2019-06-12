package com.idpz.bazarayesh;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import static com.idpz.bazarayesh.BaseActivity.LEFT_TO_RIGHT;
import static maes.tech.intentanim.CustomIntent.customType;

public class ProfileFragment extends BaseFragment implements View.OnClickListener {


    TextView beautyshop;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View v = inflater.inflate(R.layout.profile_fragment, container, false);

        initViews(v);
        beautyshop.setOnClickListener(this);
        settoolbarText(getString(R.string.title_profile), v);
        return v;


    }


    public void initViews(View v) {

        beautyshop = v.findViewById(R.id.beautyshop);

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.beautyshop:
                Intent intent = new Intent(getActivity(), SubProfileActivity.class);
                getActivity().startActivity(intent);
                customType(getContext(), LEFT_TO_RIGHT);

                getActivity().finish();

                break;

        }
    }
}
