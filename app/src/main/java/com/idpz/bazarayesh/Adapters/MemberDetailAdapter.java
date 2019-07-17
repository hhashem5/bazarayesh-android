package com.idpz.bazarayesh.Adapters;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.idpz.bazarayesh.Models.MainItem;
import com.idpz.bazarayesh.R;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;


public class MemberDetailAdapter extends RecyclerView.Adapter<MemberDetailAdapter.MyViewHolder> {
    //    Typeface face = Typeface.createFromAsset(AppController.getAppContext().getAssets(),
//            "IRANSans(FaNum).ttf");
    List<MainItem> items;
    MainItem item;
    Context mContext;
    Activity activity;
    OnItemClickListener  mListener;

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_adapter, parent, false);


        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        item = items.get(position);

        // holder.image.setImageResource(item.getImgUrl());

        Glide.with(activity)
                .load(item.imgUrl)
                .into(holder.imagview);


        holder.txtContent.setText(item.getTitle());

        holder.relItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                FragmentManager fragmentManager = getSupportFragmentManager();
//                DialogFullscreenImageFragment newFragment = new DialogFullscreenImageFragment();
//
//                FragmentTransaction transaction = fragmentManager.beginTransaction();
//                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
//                transaction.add(android.R.id.content, newFragment).addToBackStack(null).commit();

                mListener.onItemClick( position, items.get(position));

            }
        });

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView txtTitle,txtContent;

        RelativeLayout relItem;


       RoundedImageView imagview;


        public MyViewHolder(View view) {
            super(view);


            txtContent = view.findViewById(R.id.txtContent);

            imagview = view.findViewById(R.id.imagview);


            relItem = view.findViewById(R.id.relItem);


        }


    }



    public MemberDetailAdapter(List<MainItem> items, Activity activity) {
        this.items = items;
        this.activity = activity;
    }

    public MemberDetailAdapter(List<MainItem> items, Activity activity, OnItemClickListener onItemClickListener) {
        this.items = items;
        this.activity = activity;
        this.mListener=onItemClickListener;
    }


    public interface OnItemClickListener {
        void onItemClick(int position, Object object);
    }

}
