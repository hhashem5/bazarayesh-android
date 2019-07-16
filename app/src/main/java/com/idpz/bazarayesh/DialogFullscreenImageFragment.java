package com.idpz.instacity.Tourism.New;

import android.app.Dialog;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.bumptech.glide.Glide;
import com.idpz.instacity.R;
import com.idpz.instacity.Tools;
import com.jsibbold.zoomage.ZoomageView;

public class DialogFullscreenImageFragment extends DialogFragment {

    public CallbackResult callbackResult;

    ZoomageView zoomageView;
    Object obj;

    public void setOnCallbackResult(final CallbackResult callbackResult) {
        this.callbackResult = callbackResult;
    }

    private int request_code = 0;
    private View root_view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        root_view = inflater.inflate(R.layout.fullscreen_image_fragment, container, false);

        zoomageView = root_view.findViewById(R.id.zoomageview);

        Glide.with(getContext()).load(Tools.url).into(zoomageView);

        //        toolbarText = root_view.findViewById(R.id.tooTitle);
//        imgbBack = root_view.findViewById(R.id.imgbBack);
//        toolbarText.setText("نظرات ثبت شده");
//        imgbBack.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dismiss();
//            }
//        });

        return root_view;
    }

//    private void sendDataResult() {
//        Event event = new Event();
//        event.url=
//        event.email = tv_email.getText().toString();
//        event.name = et_name.getText().toString();
//        event.location = et_location.getText().toString();
//        event.from = spn_from_date.getText().toString() + " (" + spn_from_time.getText().toString() + ")";
//        event.to = spn_to_date.getText().toString() + " (" + spn_to_time.getText().toString() + ")";
//        event.is_allday = cb_allday.isChecked();
//        event.timezone = spn_timezone.getSelectedItem().toString();
//
//        if (callbackResult != null) {
//            callbackResult.sendResult(request_code, event);
//        }
//    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    public void setRequestCode(int request_code) {
        this.request_code = request_code;
    }

    public interface CallbackResult {
        void sendResult(int requestCode, Object obj);
    }

    @Override
    public void onResume() {
        super.onResume();
        //lock screen to portrait
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR);
    }

    @Override
    public void onPause() {
        super.onPause();
        //set rotation to sensor dependent
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR);
    }


    //    @Override
//    public void on() {
//        super.onDestroy();
//        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//
//
//    }
}