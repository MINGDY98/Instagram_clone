package com.example.instagram_clone.view.menu;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.instagram_clone.controller.AdapterFeedHolder;
import com.example.instagram_clone.R;
import com.example.instagram_clone.models.DtoFeed;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MenuProfile extends Fragment {
    final static int MAX_COLUMN = 3;//한줄에 3개의 컬럼추가

    //recyclerView의 구현
    final ArrayList<String> feed_thumnails = new ArrayList();
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private GridLayoutManager mGridLayoutManager;
    private FirebaseDatabase database;//디비에서 게시물 썸네일 가져오기

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        recyclerView = view.findViewById(R.id.recycler_view_feed_folder);
        recyclerView.setHasFixedSize(true);

        mGridLayoutManager = new GridLayoutManager(getContext(),MAX_COLUMN);
        recyclerView.setLayoutManager(mGridLayoutManager);
        mAdapter = new AdapterFeedHolder(feed_thumnails);
        recyclerView.setAdapter(mAdapter);

        initProfile();

        return view;
    }

    private void initProfile(){
        database = FirebaseDatabase.getInstance();
        /*data변경이 감지될때마다 모든 데이터를 다시 recyclerview에 보여주기위해*/
        //메뉴
        database.getReference().child("feeds").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    feed_thumnails.clear();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        DtoFeed dtoFeed = snapshot.getValue(DtoFeed.class);
                        //System.out.println(dtoFeed.getFeed_picture());
                        if(dtoFeed.getFeed_picture()!=null){
                            //Log.d("fullmoon",dtoFeed.getFeed_picture());
                            feed_thumnails.add(dtoFeed.getFeed_picture());
                        }
                    }
                    mAdapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}

