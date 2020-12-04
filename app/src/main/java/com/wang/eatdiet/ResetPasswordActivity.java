package com.wang.eatdiet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mob.MobSDK;
import com.wang.eatdiet.Util.Phone;

import cn.smssdk.*;

public class ResetPasswordActivity extends AppCompatActivity implements View.OnClickListener{
    String APPKEY="31a8d828cff5d";//用来配置验证码
    String APPSECRT="415b5fa0ffcd6b9f554a8722ce67ab49";
    private Button reset_code_btn,reset_submit_btn;
    private EditText reset_phone,reset_password,
            reset_repassword,reset_code;
    //private String reset_phone_text,reset_password_text,
       //     reset_repassword_text,reset_code_text;
    private int i=30;//验证码的计时器
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        String[] mPermissionList= new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.CALL_PHONE, Manifest.permission.READ_LOGS, Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.SET_DEBUG_APP, Manifest.permission.SYSTEM_ALERT_WINDOW, Manifest.permission.GET_ACCOUNTS,
                Manifest.permission.WRITE_APN_SETTINGS};
        ActivityCompat.requestPermissions(ResetPasswordActivity.this, mPermissionList, 123);
        initView();
        MobSDK.init(this);
        EventHandler eventHandler=new EventHandler(){
            @Override
            public void afterEvent(int i, int i1, Object o) {
                Message message=new Message();
                message.arg1=i;
                message.arg2=i1;
                message.obj=o;
                handler.sendMessage(message);
            }
        };
        SMSSDK.registerEventHandler(eventHandler);
    }
    private void initView()
    {
        reset_password=(EditText)findViewById(R.id.reset_password);
        //reset_password_text=reset_password.getText().toString().trim();
        reset_phone=(EditText)findViewById(R.id.reset_phone);
        //reset_phone_text=reset_phone.getText().toString().trim();
        reset_code=(EditText)findViewById(R.id.reset_code);
        //reset_code_text=reset_code.getText().toString().trim();
        reset_repassword=(EditText)findViewById(R.id.reset_repassword);
        //reset_repassword_text=reset_repassword.getText().toString().trim();
        reset_code_btn=(Button)findViewById(R.id.reset_code_btn);
        reset_code_btn.setOnClickListener(this);
        reset_submit_btn=(Button)findViewById(R.id.reset_btn);
        reset_submit_btn.setOnClickListener(this);
    }
    private boolean verifyInput()
    {
        if(Phone.isPhone(reset_phone.getText().toString().trim()))//判断是不是电话号码
            if(reset_password.getText().toString().trim().equals(reset_repassword.getText().toString().trim()))//判断两次密码是否输入一致
                return true;
        return false;
    }
    Handler handler=new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            if (msg.what == -9) {
                reset_code_btn.setText("重新发送(" + i + ")");
            } else if (msg.what == -8) {
                reset_code_btn.setText("获取验证码");
                reset_code_btn.setClickable(true);
                i = 30;
            } else {
                int i = msg.arg1;
                int i1 = msg.arg2;
                Object o = msg.obj;
                if (i1 == SMSSDK.RESULT_COMPLETE) {
                    // 短信注册成功后，返回ResetPasswordActivity,然后提示
                    if (i == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                        Log.d("resetPassword", "handleMessage: success");
                        Toast.makeText(ResetPasswordActivity.this, "提交验证码成功", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(ResetPasswordActivity.this, LoginActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("phone", reset_phone.getText().toString().trim());
                        intent.putExtras(bundle);
                        startActivity(intent);

                    } else if (i == SMSSDK.EVENT_GET_VOICE_VERIFICATION_CODE) {
                        Toast.makeText(ResetPasswordActivity.this, "正在获取验证码", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    };
    @Override
   public void onClick(View view){
       switch (view.getId()){

           case R.id.reset_code_btn://发送验证码
               Log.d("ResetPassword", "点击了发送验证码");
               Log.d("ResetPassword", "phone="+reset_phone.getText().toString().trim());
               if(!Phone.isPhone(reset_phone.getText().toString().trim()))
               {
                   Log.d("ResetPassword", "phone="+reset_phone.getText().toString().trim());
                   Toast.makeText(ResetPasswordActivity.this,"手机号输入不合法",Toast.LENGTH_SHORT);
                   return;
               }
               //用sdk发短信验证
               SMSSDK.getVerificationCode("86",reset_phone.getText().toString().trim());
               reset_code_btn.setClickable(false);
               new Thread(new Runnable() {
                   @Override
                   public void run() {
                       for(;i>0;i--) {
                           handler.sendEmptyMessage(-9);
                           if (i <= 0)
                               break;
                           try {
                               Thread.sleep(1000);
                           } catch (InterruptedException e) {
                               e.printStackTrace();
                           }
                       }
                       handler.sendEmptyMessage(-8);
                   }

               }).start();
               break;
           case R.id.reset_btn:
               if(verifyInput()){
                   SMSSDK.submitVerificationCode("86",reset_phone.getText().toString(),reset_code.getText().toString());
                   break;
               }
               else{
                   Log.d("ResetPassword", "phone="+reset_phone.getText().toString().trim());
                   Log.d("ResetPassword", "password="+reset_password.getText().toString().trim());
                   Log.d("ResetPassword", "phone="+reset_phone.getText().toString().trim());
                   Log.d("ResetPassword", "phone="+reset_phone.getText().toString().trim());
                   Toast.makeText(ResetPasswordActivity.this,
                           "输入有误",Toast.LENGTH_SHORT);
                   break;
               }
       }
   }
   //@Override加上会有：方法不会覆盖或实现超类型的方法
    protected void onDestory()
   {
       //反注册回调监听接口
       SMSSDK.unregisterAllEventHandler();
       super.onDestroy();
   }
}