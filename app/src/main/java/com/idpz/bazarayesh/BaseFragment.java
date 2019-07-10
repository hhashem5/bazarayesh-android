package com.idpz.bazarayesh;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.idpz.bazarayesh.Utils.Tools;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import static com.idpz.bazarayesh.Utils.AppController.getAppContext;

public class BaseFragment extends Fragment {

    TextView tooTitle;
    ImageView imgbBack;



    ProgressDialog pd;
    Typeface irsans;


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

    public static Bitmap scaleDown(Bitmap realImage, float maxImageSize, boolean filter) {

        float ratio = Math.min(
                (float) maxImageSize / realImage.getWidth(),
                (float) maxImageSize / realImage.getHeight());
        int width = Math.round((float) ratio * realImage.getWidth());
        int height = Math.round((float) ratio * realImage.getHeight());

        Bitmap newBitmap = Bitmap.createScaledBitmap(realImage, width,
                height, filter);
        return newBitmap;
    }

    public static File saveImage(final Context context, final String imageData) {
        final byte[] imgBytesData = android.util.Base64.decode(imageData,
                android.util.Base64.DEFAULT);

        File file = null;
        try {
            file = File.createTempFile("image", null, context.getCacheDir());
        } catch (IOException e) {
            e.printStackTrace();
        }
        final FileOutputStream fileOutputStream;
        try {
            fileOutputStream = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }

        final BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(
                fileOutputStream);
        try {
            bufferedOutputStream.write(imgBytesData);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                bufferedOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }

}
