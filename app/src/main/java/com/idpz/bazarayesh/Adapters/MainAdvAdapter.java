package com.idpz.bazarayesh.Adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.idpz.bazarayesh.Models.MainItem;
import com.idpz.bazarayesh.R;

import java.util.List;


public class MainAdvAdapter extends RecyclerView.Adapter<MainAdvAdapter.MyViewHolder> {
    //    Typeface face = Typeface.createFromAsset(AppController.getAppContext().getAssets(),
//            "IRANSans(FaNum).ttf");
    List<MainItem> items;
    MainItem item;
    Activity activity;


    private OnItemClickListener mListener;

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.main_adv_adapter, parent, false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        item = items.get(position);

        holder.imgLogo.setImageResource(item.getImage());
        holder.txt.setText(item.getTitle());

        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mListener.onItemClick(position, items.get(position));


            }
        });

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView imgLogo;
        TextView txt;
        RelativeLayout relativeLayout;


        public MyViewHolder(View view) {
            super(view);


            txt = view.findViewById(R.id.txt);

            imgLogo = view.findViewById(R.id.imgLogo);

            relativeLayout = view.findViewById(R.id.relative);


        }


    }

    public MainAdvAdapter(List<MainItem> items, Activity activity, OnItemClickListener onItemClickListener) {
        this.items = items;
        this.activity = activity;
        this.mListener = onItemClickListener;
    }

    public MainAdvAdapter(List<MainItem> items, Activity activity) {
        this.items = items;
        this.activity = activity;
    }


    public interface OnItemClickListener {
        void onItemClick(int position, Object object);
    }
}
