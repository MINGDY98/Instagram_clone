package com.example.instagram_clone.view.menu;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.instagram_clone.controller.AdapterHome;
import com.example.instagram_clone.R;
import com.example.instagram_clone.models.DtoFeed;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MenuHome extends Fragment {

    //recyclerView의 구현
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    //firebasedatabase에서 정보가져오기
    private FirebaseDatabase database;
    private List<DtoFeed> dtoFeeds = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                                 @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        Context context = view.getContext();
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new AdapterHome(dtoFeeds,context);
        recyclerView.setAdapter(mAdapter);
        //1.화면이 로딩 ->정보를 받아온다.
        //2. 정보 -> 어댑터에 넘겨준다.
        //3. 어댑터-> 셋팅

        getFeed();
        return view;
    }

    private void getFeed(){
        database = FirebaseDatabase.getInstance();
        /*data변경이 감지될때마다 모든 데이터를 다시 recyclerview에 보여주기위해*/
        //메뉴
        database.getReference().child("feeds").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    dtoFeeds.clear();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        DtoFeed dtoFeed = snapshot.getValue(DtoFeed.class);
                        System.out.println(dtoFeed.getProfile_name());
                        dtoFeeds.add(dtoFeed);
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


