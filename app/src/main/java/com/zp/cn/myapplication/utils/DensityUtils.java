package com.zp.cn.myapplication.utils;

import android.content.Context;

/**
 * 完成dpi与px的相互转化
 */

public class DensityUtils {

    public static int dpi2px(Context ctx,int dpi){
        return (int)(ctx.getResources().getDisplayMetrics().density * dpi);
    }

    public static int px2dpi(Context ctx,int px){
        return (int)(px / ctx.getResources().getDisplayMetrics().density);
    }
}
