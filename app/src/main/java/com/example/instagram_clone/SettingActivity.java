package com.example.instagram_clone;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class SettingActivity extends AppCompatActivity {
    private TextView log_out;
    private ImageButton back_arrow;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        log_out = findViewById(R.id.TextView_logout);
        back_arrow = findViewById(R.id.ImageButton_back_to_profile);
        log_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//로그아웃 버튼 누를시, 로그인 창으로
                startActivity(new Intent(SettingActivity.this, LoginActivity.class));
                FirebaseAuth.getInstance().signOut();
                finish();
            }
        });

        back_arrow.setOnClickListener(new View.OnClickListener() {//뒤로가기버튼 누를시 현재화면 종료
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
