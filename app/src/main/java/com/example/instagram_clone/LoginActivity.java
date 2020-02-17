package com.example.instagram_clone;

import android.content.Intent;
import android.os.Bundle;
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
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

public class LoginActivity extends AppCompatActivity {

    private EditText put_email;
    private EditText put_pwd;
    private Button login_button;
    private LinearLayout doJoin;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();

        put_email = findViewById(R.id.EditText_email);
        put_pwd = findViewById(R.id.EditText_password);
        login_button = findViewById(R.id.Button_login_button);
        login_button.setClickable(true);
        doJoin = findViewById(R.id.LinearLayout_do_join);

        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = put_email.getText().toString();
                String pwd = put_pwd.getText().toString();
                //로그인 성공시 -> 메인 페이지
                loginStart(email,pwd);
            }
        });

        doJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//회원가입버튼누르면 그 화면으로
                startActivity(new Intent(LoginActivity.this, JoinActivity.class));
                finish();
            }
        });
    }

    public void loginStart(String email, String password){//로그인하기
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (!task.isSuccessful()) {
                    try {
                        throw task.getException();
                    } catch (FirebaseAuthInvalidUserException e) {
                        Toast.makeText(LoginActivity.this,"존재하지 않는 email 입니다." ,Toast.LENGTH_SHORT).show();
                    } catch (FirebaseAuthInvalidCredentialsException e) {
                        Toast.makeText(LoginActivity.this,"이메일 형식이 맞지 않습니다." ,Toast.LENGTH_SHORT).show();
                    } catch (FirebaseNetworkException e) {
                        Toast.makeText(LoginActivity.this,"Firebase NetworkException" ,Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        Toast.makeText(LoginActivity.this,"Exception" ,Toast.LENGTH_SHORT).show();
                    }

                }else{
                    Toast.makeText(LoginActivity.this, "로그인 성공"  + "/" + mAuth.getCurrentUser().getEmail() ,Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish();
                }

            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        if( mAuth.getCurrentUser() != null){
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }//로그인 버튼 눌렀을 경우, 로그아웃 안했으면, 즉 로그인 되어있으면 자동으로 메인페이지로 이동시키기
        else{//로그인 기록이없다면 회원가입창으루
        }
    }

}
