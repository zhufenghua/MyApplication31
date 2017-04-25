package com.zp.cn.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.zp.cn.myapplication.utils.DensityUtils;
import com.zp.cn.myapplication.utils.SharedPrefUtils;

import java.util.ArrayList;
import java.util.List;

public class WelcomeActivity extends Activity {

    private ViewPager viewPager;

    private LinearLayout ll;

    private List<View> iList = null;

    private View pointRed = null;

    private Button btnStart = null;

    private int pointMoveWidth = 0 ;

    @Override  // 此方法在获取和丢失焦点都会执行
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        pointMoveWidth = ll.getChildAt(1).getLeft() - ll.getChildAt(0).getLeft();
//        Log.i("zp","pointMoveWidth" + pointMoveWidth);
        // 获取灰点之间的间距
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        Log.i("zp","onCreate:" + this.getClass() +  "正在创建,不可见");
        viewPager = (ViewPager) this.findViewById(R.id.viewpager);
        ll = (LinearLayout) this.findViewById(R.id.ll);
        pointRed =  (View)this.findViewById(R.id.point_red);
        btnStart =  (Button)this.findViewById(R.id.btn_start);
        initData();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("zp","onStart:" + this.getClass() +  "可见但是不能获取焦点");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("zp","onResume:" + "可见能获取焦点,说明当前Activty能够被用户交互");

    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("zp","onPause:" + this.getClass() +  "可见但是不能获取焦点");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("zp","onStop:" + this.getClass() +  "不可见,但是并没有销毁.....");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("zp","onDestroy:" + this.getClass() +  "已经销毁,且释放资源.....");
    }

    public void initData() {
        // 必须使用适配器: PagerAdapter, 此方法完成图片的加载与显示
        viewPager.setAdapter(new WelcomeViewPager());
        // 需要根据图片的数量,动态生成圆点
        for (int i = 0; i < iList.size(); i++) {
            View view = new View(this);
            view.setBackgroundResource(R.drawable.welcome_point_gray);
            int px = DensityUtils.dpi2px(this, 20);
            // 因为当前point的父类是LinearLayout,因此需要创建 LinearLayout.LayoutParams
            LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(px, px);
            view.setLayoutParams(param);
            if (i > 0) {
                param.leftMargin = px;  // android中单位是px
            }
            ll.addView(view);
        }
        // 3: 如果需要让红点随着手势移动,则需要监听ViewPager的切换事件
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Log.i("zp", "onPageScrolled: position " + position + ",positionOffset" + positionOffset + ",positionOffsetPixels" + positionOffsetPixels);
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams)pointRed.getLayoutParams();
                layoutParams.leftMargin = (int)(positionOffset * pointMoveWidth) + (position * pointMoveWidth);
                pointRed.setLayoutParams(layoutParams);
                Log.i("zp","layoutParams.leftMargin:" + layoutParams.leftMargin);
            }

            @Override  // 当前被选择的页面
            public void onPageSelected(int position) {
                Log.i("zp", "onPageSelected:" + position);
//                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams)point_red.getLayoutParams();
//                layoutParams.leftMargin = position * pointMoveWidth;
//                point_red.setLayoutParams(layoutParams);
//                Log.i("zp","layoutParams.leftMargin:" + layoutParams.leftMargin);
            }

            @Override  // 当前滑动状态: 0: 未滑动  1：正在滑动  2： 正在切换
            public void onPageScrollStateChanged(int state) {
                Log.i("zp", "onPageScrollStateChanged:" + state);
            }
        });
    }

    public void goMainActivty(View view){
        Log.i("zp","App的唯一名称:" + this.getPackageName());
        Intent intent = new Intent(this, MainActivity.class);
        // 创建一个新栈空间来存储MainActivity,并且清除之前栈空间
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        // 标记引导页已经显示
        SharedPrefUtils.setBoolean(this,"isShow",true);
        startActivity(intent);
    }


    private class WelcomeViewPager extends PagerAdapter {

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        private int[] ids = new int[]{R.drawable.guide_1, R.drawable.guide_2, R.drawable.guide_3};

        // 把图片存储到ListView集合中
        public WelcomeViewPager() {
            iList = new ArrayList<View>();
            for (int i = 0; i < ids.length; i++) {
                ImageView imageView = new ImageView(WelcomeActivity.this);
                imageView.setBackgroundResource(ids[i]);
                iList.add(imageView);
            }
//            this.findViewById(); 在当前xml布局文件中查找
            View btnStartView = View.inflate(WelcomeActivity.this, R.layout.activity_start, null);
            iList.add(btnStartView);
        }

        @Override
        public int getCount() {
            return iList.size();
        }

        @Override  // 返回ViewPager要显示的ImageView
        public Object instantiateItem(ViewGroup container, int position) {
            Log.i("zp", "container:" + container + ",position:" + position);
            // 把当前要显示的图片交给viewPager
            View view = (View) iList.get(position);
            container.addView(view);
            return view;
        }

        @Override  // 判断当前要显示的View是否在集合中
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }
}
