package com.example.instagram_clone;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Pattern;

public class JoinActivity extends AppCompatActivity {

    // 비밀번호 정규식
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^[a-zA-Z0-9!@.#$%^&*?_~]{4,16}$");
    //이메일 비밀번호 로그인 모듈 변수
    private FirebaseAuth mAuth;
    //현재 로그인 된 유저 정보를 담을 변수
    private FirebaseUser currentUser;

    private EditText put_email;
    private EditText put_pwd;
    private Button join_button;
    private LinearLayout do_login_button;
    private String email = "";
    private String pwd = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);


        mAuth = FirebaseAuth.getInstance();
        put_email = findViewById(R.id.EditText_email);
        put_pwd = findViewById(R.id.EditText_password);
        join_button = findViewById(R.id.Button_joinButton);
        join_button.setClickable(true);
        do_login_button = findViewById(R.id.LinearLayout_doLogin);

        do_login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(JoinActivity.this,LoginActivity.class));
                finish();
            }
        });
        join_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = put_email.getText().toString();
                pwd = put_pwd.getText().toString();
                Toast.makeText(JoinActivity.this,email +"/=가입 버튼 눌리고" +"/" + pwd,Toast.LENGTH_SHORT).show();

                if(isValidEmail()&&isValidPwd()){
                    joinStart(email,pwd);
                }

            }
        });
    }

    // 이메일 유효성 검사
    private boolean isValidEmail() {
        if (email.isEmpty()) {
            // 이메일 공백
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            // 이메일 형식 불일치
            return false;
        } else {
            return true;
        }
    }

    // 비밀번호 유효성 검사
    private boolean isValidPwd() {
        if (pwd.isEmpty()) {
            // 비밀번호 공백
            return false;
        } else if (!PASSWORD_PATTERN.matcher(pwd).matches()) {
            // 비밀번호 형식 불일치
            return false;
        } else {
            return true;
        }
    }
    //가입 함수
    public void joinStart(String email,String pwd) {

        mAuth.createUserWithEmailAndPassword(email,pwd)
                .addOnCompleteListener(this,new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            Toast.makeText(JoinActivity.this, "mAuth. onComplete 함수", Toast.LENGTH_SHORT).show();
                            if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                // 이미 존재 할 때
                                Toast.makeText(JoinActivity.this, "이미존재하는 email 입니다.", Toast.LENGTH_SHORT).show();
                            }else if(task.getException() instanceof FirebaseAuthWeakPasswordException){
                                Toast.makeText(JoinActivity.this, "비밀번호 6자리 이상으로 해주세요.", Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(JoinActivity.this,"다른예외"+task.getException().getMessage() ,Toast.LENGTH_SHORT).show();
                            }
                        }
                        else{
                            Toast.makeText(JoinActivity.this,"진입",Toast.LENGTH_SHORT).show();
                            currentUser = mAuth.getCurrentUser();
                            Toast.makeText(JoinActivity.this, "가입 성공  " + "/" + currentUser.getEmail() ,Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(JoinActivity.this, MainActivity.class));
                            finish();

                        }
                    }
                });
    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }

}

/*                        if (!task.isSuccessful()) {
                            try {
                                throw task.getException();
                            } catch (FirebaseAuthUserCollisionException e) {
                                Toast.makeText(JoinActivity.this,"이미존재하는 email 입니다." ,Toast.LENGTH_SHORT).show();

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }*/