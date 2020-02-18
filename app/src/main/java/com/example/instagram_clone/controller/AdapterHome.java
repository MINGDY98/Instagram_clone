package com.example.instagram_clone.controller;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import com.example.instagram_clone.R;
import com.example.instagram_clone.models.DtoFeed;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

public class AdapterHome extends RecyclerView.Adapter<AdapterHome.MyViewHolder> {
    private List<DtoFeed> mDataset;

    static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView user_name;
        public SimpleDraweeView feed_img;
        public TextView feed_info;
        public MyViewHolder(View v) {
            super(v);
            user_name = v.findViewById(R.id.user_name);
            feed_img = v.findViewById(R.id.SimpleDraweeView_feed_img);
            feed_info = v.findViewById(R.id.TextView_feed_Info);
        }
    }

    public AdapterHome(List<DtoFeed> myDataset, Context context) {
        mDataset = myDataset;
        Fresco.initialize(context);
    }

    @Override
    public AdapterHome.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                       int viewType) {

        CardView v = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_activity, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        DtoFeed dtoFeed = mDataset.get(position);
        holder.user_name.setText(dtoFeed.getProfile_name());
        holder.feed_info.setText(dtoFeed.getFeed_contents());
        if(dtoFeed.getFeed_picture()!=null) {
            Uri uri = Uri.parse(dtoFeed.getFeed_picture());
            holder.feed_img.setImageURI(uri);
            holder.feed_img.setScaleType(SimpleDraweeView.ScaleType.FIT_XY);
        }

    }

    @Override
    public int getItemCount() {
        return mDataset == null ? 0 : mDataset.size();
    }
}
