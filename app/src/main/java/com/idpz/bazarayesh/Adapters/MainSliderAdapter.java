package com.idpz.bazarayesh.Adapters;

import android.content.Context;


import com.idpz.bazarayesh.Models.PicName;

import java.util.List;

import ss.com.bannerslider.adapters.SliderAdapter;
import ss.com.bannerslider.viewholder.ImageSlideViewHolder;

public class MainSliderAdapter extends SliderAdapter {
    private List<PicName> picNames;
    Context mContext;

    public MainSliderAdapter(List<PicName> picNames) {
        this.picNames = picNames;
    }

    public MainSliderAdapter(List<PicName> picNames, Context context) {
        this.mContext = context;
        this.picNames = picNames;
    }

    @Override
    public int getItemCount() {
        return picNames.size();
    }

    @Override
    public void onBindImageSlide(final int position, ImageSlideViewHolder viewHolder) {




        viewHolder.bindImageSlide(picNames.get(position).getUrl());


//        switch (position) {
//            case 0:
//                viewHolder.bindImageSlide("http://mahdasht.idpz.ir/assets/images/places/1523172846cheshme-shahdasht3.jpg");
//                break;
//            case 1:
//                viewHolder.bindImageSlide("http://mahdasht.idpz.ir/assets/images/places/1521123230k3_6718.JPG");
//                break;
//            case 2:
//                viewHolder.bindImageSlide("http://kamalshahr.idpz.ir/assets/images/villas/0938.jpg");
//                break;
//        }
    }
}