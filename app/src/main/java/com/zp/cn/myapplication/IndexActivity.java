package com.zp.cn.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.zp.cn.myapplication.utils.SharedPrefUtils;

import java.util.Timer;
import java.util.TimerTask;

public class IndexActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 在anroid中网络的访问必须在子线程中完成
        new Timer(true).schedule(new TimerTask() {
            @Override
            public void run() {
                // 此页面通常用来加载服务器数据、判断是否有新版本、检查用户是否关闭权限
                System.out.println("----------------schedule---------------");
                // 判断引导页是否已经显示
                if (SharedPrefUtils.getBoolean(IndexActivity.this, "isShow", false)) {
                    // true 则说明引导页已经显示.直接跳转主页面.
                    startActivity(new Intent(IndexActivity.this, MainActivity.class));

                } else {
                    startActivity(new Intent(IndexActivity.this, WelcomeActivity.class));
                }
//                finish(); // 销毁当前Activity
            }
        }, 2000);
    }
}
