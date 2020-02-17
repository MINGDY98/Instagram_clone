package com.example.instagram_clone;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class MenuProfileAppBar extends Fragment {

    private ImageButton menu;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.toolbar_profile, container, false);
        menu = view.findViewById(R.id.imageButton_menu);

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//프로필 fragment의 menu를 누를시 설정창으로
                startActivity(new Intent(getContext(),SettingActivity.class));
            }
        });

        return view;
    }


}


