package com.idpz.bazarayesh.Adapters;

import android.app.Activity;
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


public class DialogItemAdapter extends RecyclerView.Adapter<DialogItemAdapter.MyViewHolder> {
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
                .inflate(R.layout.dialog_item, parent, false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        item = items.get(position);


        holder.txt.setText(item.getTitle());
        holder.img_icon.setImageResource(item.getImage());

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

        TextView txt;
        RelativeLayout relativeLayout;
        ImageView img_icon;


        public MyViewHolder(View view) {
            super(view);




            img_icon=view.findViewById(R.id.img_icon);
            txt=view.findViewById(R.id.txtTitle);
            relativeLayout = view.findViewById(R.id.relative);


        }


    }

    public DialogItemAdapter(List<MainItem> items, Activity activity, OnItemClickListener onItemClickListener) {
        this.items = items;
        this.activity = activity;
        this.mListener = onItemClickListener;
    }

    public DialogItemAdapter(List<MainItem> items, Activity activity) {
        this.items = items;
        this.activity = activity;
    }


    public interface OnItemClickListener {
        void onItemClick(int position, Object object);
    }
}
