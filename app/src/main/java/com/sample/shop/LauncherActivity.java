package com.sample.shop;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;

import com.sample.shop.app.MainActivity;

public class LauncherActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
        Intent intent = new Intent(LauncherActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
//        //延迟三秒发送请求
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                //启动主页面
//                Intent intent = new Intent(LauncherActivity.this, MainActivity.class);
//                startActivity(intent);
//                //关闭页面
//                finish();
//            }
//        }, 1000);

    }
}
