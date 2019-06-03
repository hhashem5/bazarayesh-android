package com.idpz.bazarayesh;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class BaseFragment extends Fragment {

    TextView tooTitle;
    ImageView imgbBack;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public void settoolbarText(String tag, View v) {
        tooTitle = v.findViewById(R.id.tooTitle);
        imgbBack =v.findViewById(R.id.imgbBack);


        tooTitle.setText(tag);

    }


}
