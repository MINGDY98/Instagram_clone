package com.example.instagram_clone;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
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
                if(menuAddFeed.getUri()!=null){
                    menuAddFeed.uploadFile();
                }
            }
        });
        return view;
    }
}
