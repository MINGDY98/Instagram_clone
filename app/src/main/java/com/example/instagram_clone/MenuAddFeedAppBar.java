package com.example.instagram_clone;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class MenuAddFeedAppBar extends Fragment {
    private TextView share_feed;//feed공유하는 텍스트뷰버튼
    private MenuAddFeed menuAddFeed;

    public MenuAddFeedAppBar(MenuAddFeed menuAddFeed1) {
        this.menuAddFeed = menuAddFeed1;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.toolbar_add_feed, container, false);
        share_feed = view.findViewById(R.id.TextView_share_feed);

            share_feed.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if((menuAddFeed.getUri()!=null)&&(menuAddFeed.getFeed_content()!=null)) {
                        menuAddFeed.uploadFile();//'공유' 누를 시 넘어감.
                    }
                    else{
                        Toast.makeText(getActivity(),"내용이 없거나 사진이 없다면 올릴 수 없습니다..",Toast.LENGTH_SHORT).show();
                    }
                }
            });

        return view;
    }
}
