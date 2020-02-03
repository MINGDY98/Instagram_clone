package com.example.instagram_clone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    //이메일 비밀번호 로그인 모듈 변수
    private FirebaseAuth mAuth;
    //현재 로그인 된 유저 정보를 담을 변수
    private FirebaseUser currentUser;

    //상태에 따른 툴바를 가져오기위한 변수
    int menuState=0; //0은 home, 1은 icon_find_small, 2는 heart 3은 profile 4는 addfeed

    // FrameLayout에 각 메뉴의 Fragment를 바꿔 줌
    private FragmentManager fragmentManager = getSupportFragmentManager();
    // 4개의 메뉴에 들어갈 Fragment들
    private MenuHome menuHome = new MenuHome();
    private MenuFind menuFind = new MenuFind();
    private MenuAddFeed menuAddFeed = new MenuAddFeed();
    private MenuHeart menuHeart = new MenuHeart();
    private MenuProfile menuProfile = new MenuProfile();
    //각 화면의 Appbar의 역할을 하는 Fragment들
    private MenuHomeAppBar menuHomeAppBar = new MenuHomeAppBar();
    private MenuFindAppBar menuFindAppBar = new MenuFindAppBar();
    private MenuAddFeedAppBar menuAddFeedBar = new MenuAddFeedAppBar();
    private MenuHeartAppBar menuHeartAppBar = new MenuHeartAppBar();
    private MenuProfileAppBar menuProfileAppBar = new MenuProfileAppBar();
    private ImageButton menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);



        //회원가&로그인 용
        mAuth = FirebaseAuth.getInstance();
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
                    case R.id.navigation_plus: {
                        menuState=4;
                        transaction.replace(R.id.frame_layout, menuAddFeed);
                        transaction.replace(R.id.appbar,menuAddFeedBar);
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

    //로그인 되어있으면 currentUser 변수에 유저정보 할당. 아닌경우 login 페이지로 이동!

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        currentUser = mAuth.getCurrentUser();
        if(currentUser == null){
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
        }
    }



}
