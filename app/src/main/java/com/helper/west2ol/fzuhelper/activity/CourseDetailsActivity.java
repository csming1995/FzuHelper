package com.helper.west2ol.fzuhelper.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.helper.west2ol.fzuhelper.R;
import com.helper.west2ol.fzuhelper.util.ActivityController;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by Maple27 on 2017/5/10.
 */

public class CourseDetailsActivity extends AppCompatActivity {
    private static final String TAG="CourseDetailsActivity";

    private final AppCompatActivity activity = this;

    private ArrayList<TextView> al_tv;

    private String title;
    private String location;
    private String teacher;
    private int startTime;
    private int endTime;
    private int startWeek;
    private int endWeek;
    private String type;
    private int backgroundId;

    private RelativeLayout rl;
    private TextView tv_title;
    private TextView tv_location;
    private TextView tv_teacher;
    private TextView tv_time;
    private TextView tv_week;
    private TextView tv_type;
    private ImageButton ib_close;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_details);
        ActivityController.addActivity(this);

        getParam();
        initViews();
        display();
        setTranslucentStatus(true);

        setListener(activity);
    }

    //获取参数
    public void getParam(){
        Bundle bundle = getIntent().getExtras();

        Intent intent = this.getIntent();
        title = bundle.getString("title");
        location = bundle.getString("location");
        teacher = bundle.getString("teacher");
        startTime = bundle.getInt("startTime");
        endTime = bundle.getInt("endTime");
        startWeek = bundle.getInt("startWeek");
        endWeek = bundle.getInt("endWeek");
        type = bundle.getString("type");
        backgroundId = bundle.getInt("backgroundId");
        Log.d("backId" , backgroundId+"");
    }

    //初始化控件
    public void initViews(){
        al_tv = new ArrayList<TextView>();
        rl = (RelativeLayout) findViewById(R.id.rl_title);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_location = (TextView) findViewById(R.id.tv_location);
        tv_teacher = (TextView) findViewById(R.id.tv_teacher);
        tv_time = (TextView) findViewById(R.id.tv_time);
        tv_week = (TextView) findViewById(R.id.tv_week);
        tv_type = (TextView) findViewById(R.id.tv_type);
        ib_close = (ImageButton) findViewById(R.id.ib_close);
        al_tv.add(tv_location);
        al_tv.add(tv_teacher);
        al_tv.add(tv_time);
        al_tv.add(tv_type);
        al_tv.add(tv_week);
    }

    //监听
    public void setListener(final AppCompatActivity activity){
        ib_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityController.removeActivity(activity);
                activity.finish();
            }
        });
    }

    //显示数据 （文字颜色无法设置）
    public void display(){
        int[] background = {
                R.drawable.course_bg1, R.drawable.course_bg2, R.drawable.course_bg3, R.drawable.course_bg4,
                R.drawable.course_bg5, R.drawable.course_bg6, R.drawable.course_bg7, R.drawable.course_bg8,
                R.drawable.course_bg9, R.drawable.course_bg10, R.drawable.course_bg11, R.drawable.course_bg12,
                R.drawable.course_bg13, R.drawable.course_bg14, R.drawable.course_bg15, R.drawable.course_bg16,
                R.drawable.course_bg1, R.drawable.course_bg2, R.drawable.course_bg3, R.drawable.course_bg4,
                R.drawable.course_bg5, R.drawable.course_bg6, R.drawable.course_bg7, R.drawable.course_bg8,
                R.drawable.course_bg9, R.drawable.course_bg10, R.drawable.course_bg11, R.drawable.course_bg12,
                R.drawable.course_bg13, R.drawable.course_bg14, R.drawable.course_bg15, R.drawable.course_bg16,
        };
        rl.setBackgroundResource(backgroundId);
        /*int color = getResources().getColor(backgroundId);
        int a = 255;
        int r = (color >> 16) & 0xff;
        int g = (color >> 8) & 0xff;
        int b = (color >> 0) & 0xff;
        color = a << 24 | r << 16 | g << 8 | b;*/
        tv_title.setText(title);
        tv_location.setText(location);
        tv_teacher.setText(teacher);
        tv_time.setText(startTime+"-"+endTime);
        tv_week.setText(startWeek+"-"+endWeek);
        tv_type.setText(type);
        /*for(int i=0;i<al_tv.size();i++){
            al_tv.get(i).setTextColor(color);
        }*/
    }

    @TargetApi(19)
    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);

    }
}
