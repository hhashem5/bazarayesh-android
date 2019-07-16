package com.idpz.bazarayesh;

import android.content.Context;

import com.idpz.bazarayesh.Models.BannerModels.Baner;
import com.idpz.bazarayesh.Models.PicName;

import java.util.List;

import ss.com.bannerslider.adapters.SliderAdapter;
import ss.com.bannerslider.viewholder.ImageSlideViewHolder;

public class MainBannerSliderAdapter extends SliderAdapter {
    private List<Baner> banners;
    Context mContext;

    public MainBannerSliderAdapter(List<Baner> banners) {
        this.banners = banners;
    }

    public MainBannerSliderAdapter(List<Baner> banners, Context context) {
        this.mContext = context;
        this.banners = banners;
    }

    @Override
    public int getItemCount() {
        return banners.size();
    }

    @Override
    public void onBindImageSlide(final int position, ImageSlideViewHolder viewHolder) {


        try {
            viewHolder.bindImageSlide("http://arayesh.myzibadasht.ir" + banners.get(position).getPic());
        } catch (Exception e) {
            e.getMessage();

        }


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