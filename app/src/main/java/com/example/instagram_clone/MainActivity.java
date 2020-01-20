package com.example.instagram_clone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.os.Bundle;
import android.view.MenuItem;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    //상태에 따른 툴바를 가져오기위한 변수
    int menuState=0; //0은 home, 1은 icon_find_small, 2는 heart 3은 profile

    // FrameLayout에 각 메뉴의 Fragment를 바꿔 줌
    private FragmentManager fragmentManager = getSupportFragmentManager();
    // 4개의 메뉴에 들어갈 Fragment들
    private MenuHome menuHome = new MenuHome();
    private MenuFind menuFind = new MenuFind();
    private MenuHeart menuHeart = new MenuHeart();
    private MenuProfile menuProfile = new MenuProfile();
    //각 화면의 Appbar의 역할을 하는 Fragment들
    private MenuHomeAppBar menuHomeAppBar = new MenuHomeAppBar();
    private MenuFindAppBar menuFindAppBar = new MenuFindAppBar();
    private MenuHeartAppBar menuHeartAppBar = new MenuHeartAppBar();
    private MenuProfileAppBar menuProfileAppBar = new MenuProfileAppBar();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        //우리는 바텀 네비게이션을 사용할것입니다
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        // Fragment부분 첫 화면 지정
        final FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frame_layout, menuHome);
        transaction.add(R.id.appbar,menuHomeAppBar);
        transaction.commit();

        //bottomnavigation부분
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                switch(item.getItemId()){
                    case R.id.navigation_home: {
                        menuState=0;
                        transaction.replace(R.id.frame_layout, menuHome);
                        transaction.replace(R.id.appbar,menuHomeAppBar);
                        transaction.commit();
                        break;
                    }
                    case R.id.navigation_finder: {
                        menuState=1;
                        transaction.replace(R.id.frame_layout, menuFind);
                        transaction.replace(R.id.appbar,menuFindAppBar);
                        transaction.commit();
                        break;
                    }
                    case R.id.navigation_heart: {
                        menuState=2;
                        transaction.replace(R.id.frame_layout, menuHeart);
                        transaction.replace(R.id.appbar,menuHeartAppBar);
                        transaction.commit();
                        break;
                    }
                    case R.id.navigation_profile: {
                        menuState=3;
                        transaction.replace(R.id.frame_layout, menuProfile);
                        transaction.replace(R.id.appbar,menuProfileAppBar);
                        transaction.commit();
                        break;
                    }
                }
                return true;
            }
        });

    }

}
