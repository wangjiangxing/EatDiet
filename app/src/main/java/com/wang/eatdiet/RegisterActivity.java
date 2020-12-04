package com.wang.eatdiet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.wang.eatdiet.Util.Phone;
import com.google.android.material.textfield.TextInputLayout;

import org.angmarch.views.NiceSpinner;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {
    EditText register_phone,register_password,register_repassword;
    Button register_btn;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        register_btn_onClick();
    }
    private void register_btn_onClick()
    {
        // TODO: 2020/12/4 待补充注册按钮细节
        register_btn=(Button) findViewById(R.id.register_btn);
        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isRegisterSuccess()){
                    Toast.makeText(RegisterActivity.this,"注册成功",Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(RegisterActivity.this,LoginActivity.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(RegisterActivity.this,"注册失败，请检查",Toast.LENGTH_SHORT).show();
                }

            }
        });


    }
    private boolean isRegisterSuccess()//判断输入字段
    {
        String register_phone_text,register_password_text,register_repassword_text;
        register_phone=(EditText)findViewById(R.id.register_phone);
        register_password=(EditText)findViewById(R.id.register_password);
        register_repassword=(EditText)findViewById(R.id.register_repassword);
        register_phone_text=register_phone.getText().toString().trim();
        register_password_text=register_repassword.getText().toString().trim();
        register_repassword_text=register_repassword.getText().toString().trim();
        Log.d("isRegisterSuccess", "\nphone="+register_phone_text+"\npassword="
                +register_password_text+"\nrepassword="
                +register_repassword_text+"\nisphone()="+Phone.isPhone(register_phone_text));
        if((register_password_text.equals(register_repassword_text))&&Phone.isPhone(register_phone_text))
            return true;
        else
            return false;
    }

}
// TODO: 2020/12/4 加一个NiceSpinner 并显示数据