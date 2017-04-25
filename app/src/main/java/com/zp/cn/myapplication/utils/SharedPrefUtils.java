package com.zp.cn.myapplication.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Administrator on 2017/4/25.
 */

public class SharedPrefUtils {

    public static void setBoolean(Context context, String key, boolean value) {
        SharedPreferences sf = context.getSharedPreferences("welcome_show", Context.MODE_PRIVATE);
        sf.edit().putBoolean(key,value).commit();
    }

    public static boolean getBoolean(Context context, String key, boolean defVal) {
        SharedPreferences sf = context.getSharedPreferences("welcome_show", Context.MODE_PRIVATE);
        return sf.getBoolean(key,defVal);
    }
}
