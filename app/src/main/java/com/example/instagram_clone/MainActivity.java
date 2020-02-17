package com.example.instagram_clone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageButton;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    //이메일 비밀번호 로그인 모듈 변수
    private FirebaseAuth mAuth;

    //상태에 따른 툴바를 가져오기위한 변수
    int menuState=0; //0은 home, 1은 icon_find_small, 2는 heart 3은 profile 4는 addfeed

    // FrameLayout에 각 메뉴의 Fragment를 바꿔 줌
//    private FragmentManager fragmentManager = getSupportFragmentManager();
    // 4개의 메뉴에 들어갈 Fragment들
    private MenuHome menuHome = new MenuHome();
    private MenuFind menuFind = new MenuFind();
    private MenuAddFeed menuAddFeed;
    private MenuAddFeedAppBar menuAddFeedAppBar;
    private MenuHeart menuHeart = new MenuHeart();
    private MenuProfile menuProfile = new MenuProfile();
    //각 화면의 Appbar의 역할을 하는 Fragment들
    private MenuHomeAppBar menuHomeAppBar = new MenuHomeAppBar();
    private MenuFindAppBar menuFindAppBar = new MenuFindAppBar();
    private MenuHeartAppBar menuHeartAppBar = new MenuHeartAppBar();
    private MenuProfileAppBar menuProfileAppBar = new MenuProfileAppBar();
    private ImageButton menu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        menuAddFeed = new MenuAddFeed();
        menuAddFeedAppBar = new MenuAddFeedAppBar(menuAddFeed);
        //회원가&로그인 용
        mAuth = FirebaseAuth.getInstance();
        //우리는 바텀 네비게이션을 사용할것입니다
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        // Fragment부분 첫 화면 지정
        FragmentManager fragmentManager = getSupportFragmentManager();
        final FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.frame_layout, menuHome)
                .add(R.id.frame_layout, menuFind)
                .add(R.id.frame_layout, menuAddFeed)
                .add(R.id.frame_layout, menuHeart)
                .add(R.id.frame_layout, menuProfile)
                .add(R.id.appbar,menuHomeAppBar)
                .add(R.id.appbar, menuFindAppBar)
                .add(R.id.appbar, menuAddFeedAppBar)
                .add(R.id.appbar, menuHeartAppBar)
                .add(R.id.appbar, menuProfileAppBar).commit();

        getHomeFragment();

        //bottomnavigation부분
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                switch(item.getItemId()){
                    case R.id.navigation_home: {
                        menuState=0;
                        getHomeFragment();
                        break;
                    }
                    case R.id.navigation_finder: {
                        menuState=1;
                        getFindFragment();
                        break;
                    }
                    case R.id.navigation_plus: {
                        menuState=4;
                        getPlusFragment();
                        break;
                    }
                    case R.id.navigation_heart: {
                        menuState=2;
                        getHeartFragment();
                        break;
                    }
                    case R.id.navigation_profile: {
                        menuState=3;
                        getProfileFragment();
                        break;
                    }
                }
                return true;
            }
        });

    }

    private void getHomeFragment(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        final FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.show(menuHome)
        .hide(menuFind)
        .hide(menuAddFeed)
        .hide(menuHeart)
        .hide(menuProfile)
        .show(menuHomeAppBar)
        .hide(menuFindAppBar)
        .hide(menuAddFeedAppBar)
        .hide(menuHeartAppBar)
        .hide(menuProfileAppBar).commit();
    }
    private void getFindFragment(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        final FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.hide(menuHome)
                .show(menuFind)
                .hide(menuAddFeed)
                .hide(menuHeart)
                .hide(menuProfile);
        transaction.hide(menuHomeAppBar)
                .show(menuFindAppBar)
                .hide(menuAddFeedAppBar)
                .hide(menuHeartAppBar)
                .hide(menuProfileAppBar)
                .commit();
    }
    private void getPlusFragment(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        final FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.hide(menuHome)
                .hide(menuFind)
                .show(menuAddFeed)
                .hide(menuHeart)
                .hide(menuProfile);
        transaction.hide(menuHomeAppBar)
                .hide(menuFindAppBar)
                .show(menuAddFeedAppBar)
                .hide(menuHeartAppBar)
                .hide(menuProfileAppBar)
                .commit();
    }
    private void getHeartFragment(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        final FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.hide(menuHome)
                .hide(menuFind)
                .hide(menuAddFeed)
                .show(menuHeart)
                .hide(menuProfile);
        transaction.hide(menuHomeAppBar)
                .hide(menuFindAppBar)
                .hide(menuAddFeedAppBar)
                .show(menuHeartAppBar)
                .hide(menuProfileAppBar)
                .commit();
    }

    private void getProfileFragment(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        final FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.hide(menuHome)
                .hide(menuFind)
                .hide(menuAddFeed)
                .hide(menuHeart)
                .show(menuProfile);
        transaction.hide(menuHomeAppBar)
                .hide(menuFindAppBar)
                .hide(menuAddFeedAppBar)
                .hide(menuHeartAppBar)
                .show(menuProfileAppBar)
                .commit();
    }
    //로그인 되어있으면 currentUser 변수에 유저정보 할당. 아닌경우 login 페이지로 이동!

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        if(mAuth.getCurrentUser() == null){
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
        }
    }



}
