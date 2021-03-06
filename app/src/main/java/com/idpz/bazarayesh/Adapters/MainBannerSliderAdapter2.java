package com.idpz.bazarayesh.Adapters;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.idpz.bazarayesh.Models.BannerModels.Baner;
import com.idpz.bazarayesh.Models.PicName;
import com.idpz.bazarayesh.R;

import java.util.List;

import ss.com.bannerslider.adapters.SliderAdapter;
import ss.com.bannerslider.viewholder.ImageSlideViewHolder;

public class MainBannerSliderAdapter2 extends SliderAdapter {
    private List<PicName> banners;
    Context mContext;

    public MainBannerSliderAdapter2(List<PicName> banners) {
        this.banners = banners;
    }

    public MainBannerSliderAdapter2(List<PicName> banners, Context context) {
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
          Glide.with(mContext.getApplicationContext()).load(R.drawable.logo_gray).error(R.drawable.logo_gray).into(viewHolder.imageView);
         //   viewHolder.bindImageSlide("http://arayesh.myzibadasht.ir" + banners.get(position).getPic());
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