package com.example.instagram_clone.controller;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;

import com.example.instagram_clone.R;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

public class AdapterFeedHolder extends RecyclerView.Adapter<AdapterFeedHolder.MyViewHolder> {
    private List<String> mDataset;

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public SimpleDraweeView simpleDraweeView_single_img;
        public MyViewHolder(View v) {
            super(v);
            simpleDraweeView_single_img=v.findViewById(R.id.SimpleDraweeView_single_img);
        }
    }

    public AdapterFeedHolder(List<String> myDataset) {
        mDataset = myDataset;
    }

    @Override
    public AdapterFeedHolder.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                             int viewType) {
         View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_feed_thumnails, parent, false);

        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.simpleDraweeView_single_img.setImageURI(Uri.parse(mDataset.get(position)));
        holder.simpleDraweeView_single_img.setScaleType(SimpleDraweeView.ScaleType.FIT_XY);
    }

    @Override
    public int getItemCount() {
        return mDataset == null ? 0 : mDataset.size();
    }
}

