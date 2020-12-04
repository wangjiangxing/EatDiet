package com.wang.eatdiet;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Bundle;

import java.security.ProtectionDomain;

public class LoginActivity extends AppCompatActivity {

    ImageView imageView;
    TextView textView;
    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
        imageView = findViewById(R.id.imageView_login);
        textView = findViewById(R.id.textView);
        imageView.setOnTouchListener(new OnSwipeTouchListener(getApplicationContext()) {
            public void onSwipeTop() {
            }

            public void onSwipeRight() {
                if (count == 0) {
                    imageView.setImageResource(R.drawable.good_night_img);
                    textView.setText("Night");
                    count = 1;
                } else {
                    imageView.setImageResource(R.drawable.good_morning_img);
                    textView.setText("Morning");
                    count = 0;
                }
            }

            public void onSwipeLeft() {
                if (count == 0) {
                    imageView.setImageResource(R.drawable.good_night_img);
                    textView.setText("Night");
                    count = 1;
                } else {
                    imageView.setImageResource(R.drawable.good_morning_img);
                    textView.setText("Morning");
                    count = 0;
                }
            }

            public void onSwipeBottom() {
            }

        });
        signInPageOnClick();
    }
    public void  signInPageOnClick()
    {
        Button sign_up_btn=(Button)findViewById(R.id.sign_up);
        Button sign_in_btn=(Button)findViewById(R.id.sign_in);
        Button forget_password_btn=(Button)findViewById(R.id.forget_password);
        sign_up_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isSignUpSuccess())
                {
                    // TODO: 2020/12/4 传递用户数据待补充
                    Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(intent);
                }
                else
                    Toast.makeText(LoginActivity.this,"账号或密码错误",Toast.LENGTH_SHORT).show();
            }
        });
        sign_in_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //点击注册按钮跳转注册页面
                Intent intent=new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });
        forget_password_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 2020/12/4 检查忘记密码按钮点击字段待补充
                Intent intent=new Intent(LoginActivity.this,ResetPasswordActivity.class);
                startActivity(intent);

            }
        });
    }
    public boolean isSignUpSuccess()
    {
        // TODO: 2020/12/4 检查登录字段的方法待补充
        String phone=((EditText)findViewById(R.id.user_phone)).getText().toString().trim();
        String password=((EditText)findViewById(R.id.password)).getText().toString().trim();
        Log.d("LoginActivity", "isSignUpSuccess: phone="+phone);
        Log.d("LoginActivity","isSignUpSuccess: password="+password);
        if((phone.equals("123456"))&&(password.equals("123456")))
        {
            Log.d("LoginActivity","isSignUpSuccess: return true");
            return true;
        }

        else return false;
    }
}