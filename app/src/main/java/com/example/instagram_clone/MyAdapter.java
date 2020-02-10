package com.example.instagram_clone;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private List<DtoFeed> mDataset;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView user_name;
        public SimpleDraweeView feed_img;
        public TextView feed_info;
        public MyViewHolder(View v) {
            super(v);
            user_name = v.findViewById(R.id.user_name);
            feed_img = v.findViewById(R.id.ImageView_feed_img);
            feed_info = v.findViewById(R.id.TextView_feed_Info);

        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter(List<DtoFeed> myDataset, Context context) {
        mDataset = myDataset;
        Fresco.initialize(context);
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        // create a new view
        CardView v = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_activity, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        DtoFeed dtoFeed = mDataset.get(position);
        holder.user_name.setText(dtoFeed.getProfile_name());
        holder.feed_info.setText(dtoFeed.getFeed_contents());
        Uri uri = Uri.parse(dtoFeed.getFeed_picture());
        Log.d("fullmoon",uri.toString());
        holder.feed_img.setImageURI(uri);
//        Glide.with(holder.itemView.getContext()).load(dtoFeed.getFeed_picture()).into(holder.feed_img);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset == null ? 0 : mDataset.size();
    }
}
