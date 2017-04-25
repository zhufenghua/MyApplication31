package com.zp.cn.myapplication.utils;

import android.app.Application;
import android.util.Log;

import com.zp.cn.myapplication.BuildConfig;

import org.xutils.x;

/**
 * 此处用来对项目启动时的初始化操作
 */

public class MyApplication extends Application {
    @Override  // 当application创建的时候此方法被调用
    public void onCreate() {
        super.onCreate();
        // 1: 初始化配置, 2：获取远程的数据  3：对框架进行初始化
        Log.i("zp","MyApplication onCreate -->:" +  this.getResources().getDisplayMetrics().density);
        x.Ext.init(this);
        x.Ext.setDebug(BuildConfig.DEBUG); // 是否输出debug日志, 开启debug会影响性能.

    }
}
