package com.zp.cn.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

public class WelcomeActivity extends Activity {

    private ViewPager viewPager;

    private LinearLayout ll;

    private List<ImageView> iList = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        viewPager = (ViewPager) this.findViewById(R.id.viewpager);
        ll = (LinearLayout) this.findViewById(R.id.ll);
        initData();
    }

    public void initData(){
        // 必须使用适配器: PagerAdapter, 此方法完成图片的加载与显示
        viewPager.setAdapter(new WelcomeViewPager());
        // 需要根据图片的数量,动态生成圆点
        for(int i=0;i<iList.size();i++){
            View view = new View(this);
            view.setBackgroundResource(R.drawable.welcome_point_gray);
            // 因为当前point的父类是LinearLayout,因此需要创建 LinearLayout.LayoutParams
            LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(40,40);
            view.setLayoutParams(param);
            if(i>0){
                param.leftMargin = 40;  // android中单位是px
            }
            ll.addView(view);
        }
    }

    private class WelcomeViewPager extends PagerAdapter {

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View)object);
        }

        private int[] ids = new int[]{R.drawable.guide_1, R.drawable.guide_2, R.drawable.guide_3, R.drawable.guide_4};
        // 把图片存储到ListView集合中
        public WelcomeViewPager() {
            iList = new ArrayList<ImageView>();
            for(int i=0;i<ids.length;i++){
                ImageView imageView = new ImageView(WelcomeActivity.this);
                imageView.setBackgroundResource(ids[i]);
                iList.add(imageView);
            }
        }

        @Override
        public int getCount() {
            return iList.size();
        }

        @Override  // 返回ViewPager要显示的ImageView
        public Object instantiateItem(ViewGroup container, int position) {
            Log.i("zp", "container:" + container + ",position:" + position);
            // 把当前要显示的图片交给viewPager
            ImageView imageView = (ImageView) iList.get(position);
            container.addView(imageView);
            return imageView;
        }

        @Override  // 判断当前要显示的View是否在集合中
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }
}
