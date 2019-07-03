package com.idpz.bazarayesh.Adapters;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.idpz.bazarayesh.Models.Data;
import com.idpz.bazarayesh.Models.MainItem;
import com.idpz.bazarayesh.Models.Member;
import com.idpz.bazarayesh.R;

import java.util.List;


public class MembersAdapter extends RecyclerView.Adapter<MembersAdapter.MyViewHolder> {
    //    Typeface face = Typeface.createFromAsset(AppController.getAppContext().getAssets(),
//            "IRANSans(FaNum).ttf");
    List<Data> members;
    Activity activity;
    Data member;
    Context context;

    private OnItemClickListener mListener;

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.member_item_adapter, parent, false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        member = members.get(position);


        Glide.with(context)
                .load("http://arayesh.myzibadasht.ir/" + member.getLogo())
                .into(holder.imgLogo);


        holder.txtTitle.setText(member.getFullName());
        holder.txtAddress.setText(member.getAddress().toString());

        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mListener.onItemClick(position, members.get(position));

            }
        });

    }

    @Override
    public int getItemCount() {
        return members.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView imgLogo;
        TextView txtTitle, txtAddress;
        RelativeLayout relativeLayout;


        public MyViewHolder(View view) {
            super(view);


            txtTitle = view.findViewById(R.id.txtTitle);
            txtAddress = view.findViewById(R.id.txtAddress);

            imgLogo = view.findViewById(R.id.imgLogo);

            relativeLayout = view.findViewById(R.id.relative);


        }


    }

    public MembersAdapter(List<Data> members, Context context, OnItemClickListener onItemClickListener) {
        this.members = members;
        this.context = context;
        this.mListener = onItemClickListener;
    }



    public interface OnItemClickListener {
        void onItemClick(int position, Object object);
    }
}
