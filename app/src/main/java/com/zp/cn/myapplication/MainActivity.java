package com.zp.cn.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zp.cn.myapplication.model.Student;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.image.ImageOptions;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ContentView(R.layout.activity_main)
public class MainActivity extends Activity {

    @ViewInject(R.id.lv)
    private ListView listView;

    @Override  // 按返回键会销毁当前Activty
    protected void onDestroy() {
        super.onDestroy();
        // 正常退出APP应用
        System.exit(0);
    }

    @Override  // 在Activty创建的时候会加载布局文件
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
//        setContentView(R.layout.activity_main);
        // 获取ListView并且填充数据 (findViewById 当前布局文件: R.layout.activity_main)去查找
//        listView = (ListView) this.findViewById(R.id.lv);
        // 下午数据会从服务器获取

        // RequestParams：配置服务器的地址 需要网络权限
        RequestParams params = new RequestParams("http://www.jxy-edu.com/AjaxServlet");
        // 配置ssl
//        params.setSslSocketFactory(...); // 设置ssl
        // 如果需要传递参数,则配置
//        params.addQueryStringParameter("wd", "xUtils");
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override  // 如果成功,返回的是基于json的字符串
            public void onSuccess(String result) {  //  string -->json 然后在赋值
//                Toast.makeText(x.app(), result, Toast.LENGTH_LONG).show();
//                Log.i("zp","result:" + result);
                // 重新把JSON解析集合
                Gson gson = new Gson();
                List<Student> stuList = (List<Student>)gson.fromJson(result, new TypeToken<List<Student>>() {
                }.getType());
                Log.i("zp","result:" + stuList);
//                listView.addView(); 必须要创建Adapter适配器在装载数据,否则直接addView会抛出异常
                listView.setAdapter(new MyAdapter(stuList));
            }

            @Override  // 请求出错,调用此方法
            public void onError(Throwable ex, boolean isOnCallback) {
                Toast.makeText(x.app(), ex.getMessage(), Toast.LENGTH_LONG).show();
            }

            @Override  // 请求取消测调用此方法
            public void onCancelled(CancelledException cex) {
                Toast.makeText(x.app(), "cancelled", Toast.LENGTH_LONG).show();
            }

            @Override  // 无论成功还是失败次方法都会被调用
            public void onFinished() {
                Toast.makeText(x.app(), "onFinished", Toast.LENGTH_LONG).show();
            }
        });
    }

    private class MyAdapter extends  BaseAdapter{
        // 此数据在创建Adapter对象时必须传入
        private List<Student> stuList = null;

        public MyAdapter(List<Student> stuList){
            this.stuList = stuList;
        }

        @Override   // 获取当前集合的总数量
        public int getCount() {
            return stuList.size();
        }

        @Override //  获取每个数据项目
        public Object getItem(int position) {
            return stuList.get(position);
        }

        @Override  // 获取数据项ID
        public long getItemId(int position) {
            Student student = stuList.get(position);
            return 0;
        }

        @Override  // getView：获取布局文件,并且加载到ListView中
        public View getView(int position, View convertView, ViewGroup parent) {
            Log.i("zp", "position:" + position + ",convertView" + convertView + ",parent:" + parent);
            View listItem = null;
            if (convertView != null) {  // 缓存中已有之前ListItem布局,因此不需要在重新获取(但是数据需要更新)
                listItem = convertView;
            } else {
                // 是在当前layout配置文件中去查找
                listItem = View.inflate(MainActivity.this, R.layout.list_item, null);
            }
            // 数据需要重新赋值
            TextView tvTel = (TextView) listItem.findViewById(R.id.txt_tel);
            TextView tvSalary = (TextView) listItem.findViewById(R.id.txt_salary);
            ImageView iv = (ImageView) listItem.findViewById(R.id.iv);
            Student student = (Student) getItem(position);
            tvTel.setText(student.getTel());
            tvSalary.setText(student.getSalary());

            ImageOptions imageOptions = new ImageOptions.Builder()
                    //设置加载过程中的图片
                    .setLoadingDrawableId(R.drawable.weather)
                    //设置加载失败后的图片
                    .setFailureDrawableId(R.drawable.weather)
                    //设置使用缓存
                    .setUseMemCache(true)
                    //设置支持gif
                    .setIgnoreGif(false)
                    .build();
            x.image().bind(iv,student.getImgUrl(),imageOptions);
            return listItem;
        }
    }
}
