package com.zp.cn.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.zp.cn.myapplication.model.Student;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    private ListView listView;

    @Override  // 在Activty创建的时候会加载布局文件
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 获取ListView并且填充数据
        listView = (ListView) findViewById(R.id.lv);
        // 下午数据会从服务器获取

        // final 则说明在JVM 常量池,
        final List<Student> stuList = new ArrayList<Student>();
        for (int i = 0; i < 20; i++) {
            stuList.add(new Student("https://ss1.bdstatic.com/5eN1bjq8AAUYm2zgoY3K/r/www/cache/holiday/habo/res/doodle/6.png",
                    "1831234567" + i, "工资:10000"));
        }
        // listView.addView(); 必须要创建Adapter适配器在装载数据,否则直接addView会抛出异常
        listView.setAdapter(new BaseAdapter() {
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
                // 是在当前layout配置文件中去查找

                return null;
            }
        });
    }

    public void goUrl(View view) {
        EditText txt = (EditText) findViewById(R.id.edit_url);
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(txt.getText().toString()));
        // 访问网络需要权限
        startActivity(intent);
    }
}
