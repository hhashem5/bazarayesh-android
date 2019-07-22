package com.idpz.bazarayesh;


import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import ss.com.bannerslider.ImageLoadingService;

public class GlideImageLoadingService implements ImageLoadingService {
    public Context context;

    public GlideImageLoadingService(Context context) {
        this.context = context;
    }

    @Override
    public void loadImage(String url, ImageView imageView) {
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        try {
            Glide.with(context.getApplicationContext()).load(url).error(R.drawable.iconnopic).into(imageView);
        } catch (Exception e) {
        }
    }

    @Override
    public void loadImage(int resource, ImageView imageView) {
        Glide.with(context.getApplicationContext()).load(resource).error(R.drawable.iconnopic).into(imageView);
    }

    @Override
    public void loadImage(String url, int placeHolder, int errorDrawable, ImageView imageView) {
        Glide.with(context.getApplicationContext()).load(url).placeholder(R.drawable.iconnopic).error(R.drawable.iconnopic).thumbnail(0.5f).into(imageView);
    }
}