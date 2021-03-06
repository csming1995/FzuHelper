package com.helper.west2ol.fzuhelper.fragment;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.DragEvent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.helper.west2ol.fzuhelper.R;
import com.helper.west2ol.fzuhelper.activity.MainContainerActivity;
import com.helper.west2ol.fzuhelper.bean.CourseBean;
import com.helper.west2ol.fzuhelper.bean.CourseBeanLab;
import com.helper.west2ol.fzuhelper.bean.FDScoreLB;
import com.helper.west2ol.fzuhelper.util.HtmlParseUtil;
import com.helper.west2ol.fzuhelper.util.HttpUtil;
import com.helper.west2ol.fzuhelper.view.MyScrollView;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lcodecore.tkrefreshlayout.header.SinaRefreshView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

import static android.R.id.empty;

/**
 * Created by Administrator on 2016/10/20.
 */

public class CourseTableFragment extends Fragment{
    private static final String TAG = "KBActivity";
    /** 第一个无内容的格子 */
    protected TextView empty;
    /** 星期一的格子 */
    protected TextView monColum;
    /** 星期二的格子 */
    protected TextView tueColum;
    /** 星期三的格子 */
    protected TextView wedColum;
    /** 星期四的格子 */
    protected TextView thrusColum;
    /** 星期五的格子 */
    protected TextView friColum;
    /** 星期六的格子 */
    protected TextView satColum;
    /** 星期日的格子 */
    protected TextView sunColum;
    /** 课程表body部分布局 */
    protected RelativeLayout course_table_layout;
    /** 屏幕宽度 **/
    protected int screenWidth;
    /** 课程格子平均宽度 **/
    protected int aveWidth;

    private int yearpre=2016;
    private int weekpre=1;
    private int xuenianpre=1;
    private View view;
    private ImageView menuIcon;
    private ImageView accountIcon;
    private DrawerLayout mDrawerLayout;
    Button menu_button_in_course_table;
    @Bind(R.id.course_table_myscrollview)
    TwinklingRefreshLayout refreshLayout;
    DrawerLayout drawer;
    @Override
    public void onCreate(Bundle savedIntenceState){
        super.onCreate(savedIntenceState);

    }
    @Override
    public View onCreateView(LayoutInflater inflater , ViewGroup container , Bundle savedIntanceState){
        View rootView = inflater.inflate(R.layout.fragment_course_table , container , false);
        view=rootView;
        ButterKnife.bind(this, rootView);
        FloatingActionButton add = (FloatingActionButton) rootView.findViewById(R.id.more_button_in_coursetable);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("CourseTable" , "onClick");
                // add onClick
            }
        });
        drawer = (DrawerLayout)getActivity().findViewById(R.id.drawer_layout);
        menu_button_in_course_table = (Button)rootView.findViewById(R.id.menu_button_in_course_table);
        menu_button_in_course_table.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.openDrawer(Gravity.LEFT);
            }
        });
        new getCourse().execute();
        initKB(rootView);
        Log.i("CourseTable", "初始化完成");
        return rootView;
    }

    private void initKB(View v){
        empty = (TextView) v.findViewById(R.id.test_empty);
        monColum = (TextView) v.findViewById(R.id.test_monday_course);
        tueColum = (TextView) v.findViewById(R.id.test_tuesday_course);
        wedColum = (TextView) v.findViewById(R.id.test_wednesday_course);
        thrusColum = (TextView) v.findViewById(R.id.test_thursday_course);
        friColum = (TextView) v.findViewById(R.id.test_friday_course);
        satColum  = (TextView) v.findViewById(R.id.test_saturday_course);
        sunColum = (TextView) v.findViewById(R.id.test_sunday_course);
        course_table_layout = (RelativeLayout) v.findViewById(R.id.test_course_rl);
        SinaRefreshView refreshView = new SinaRefreshView(getActivity());
        refreshLayout.setHeaderView(refreshView);
        refreshLayout.setOnRefreshListener(new TwinklingRefreshLayout.OnRefreshListener(){
            @Override
            public void onRefresh(final TwinklingRefreshLayout refreshLayout) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                refreshLayout.finishRefreshing();
                            }
                        });
                    }
                }).start();
            }
        });
//        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,
//                R.color.colorRed,
//                R.color.colorAccent,
//                R.color.colorBackground);
//        swipeRefreshLayout.setSize(SwipeRefreshLayout.DEFAULT);
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        //屏幕宽度
        int width = dm.widthPixels;
        //平均宽度
        int aveWidth = width / 7;
        //第一个空白格子设置为25宽
        this.screenWidth = width;
        this.aveWidth = aveWidth;

        int height = dm.heightPixels;
        int gridHeight = height / 12;
        for(int i = 1; i <= 12; i ++) {
            //i为行,j为列
            for (int j = 2; j <= 8; j++) {
                TextView tx = new TextView(getActivity().getApplicationContext());
                tx.setId((i - 1) * 8 + j);
                //除了最后一列，都使用course_text_view_bg背景（最后一列没有右边框）
                //相对布局参数
                RelativeLayout.LayoutParams rp = new RelativeLayout.LayoutParams(aveWidth * 33 / 32 + 1, gridHeight);
                //文字对齐方式
                tx.setGravity(Gravity.CENTER);
                if (j == 1) {
                    tx.setText(i+"");
                    rp.width = aveWidth * 3 / 4;
                    //设置他们的相对位置
                    if (i == 1)
                        rp.addRule(RelativeLayout.BELOW, empty.getId());
                    else
                        rp.addRule(RelativeLayout.BELOW, (i - 1) * 8);
                }
                tx.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                tx.setLayoutParams(rp);
                course_table_layout.addView(tx);
            }
        }
    }

    public void showKB(int week, int year, int xuenian){
        course_table_layout.removeAllViews();
        initKB(view);
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        //屏幕宽度
        int width = dm.widthPixels;
        //平均宽度
        int aveWidth = width / 7;
        //第一个空白格子设置为25宽
        screenWidth = width;
        aveWidth = aveWidth;
        int height = dm.heightPixels;

        int gridHeight = height / 12;
        //设置课表界面
        //动态生成12 * maxCourseNum个textview
        for(int i = 1; i <= 12; i ++){
            //i为行,j为列
            for(int j = 2; j <= 8; j ++){
                TextView tx = new TextView(getActivity().getApplicationContext());
                tx.setId((i - 1) * 8  + j);
                //除了最后一列，都使用course_text_view_bg背景（最后一列没有右边框）
//                if(j < 8)
//                    tx.setBackgroundDrawable(this.
//                            getResources().getDrawable(R.drawable.course_text_view_bg));
//                else
//                    tx.setBackgroundDrawable(this.
//                            getResources().getDrawable(R.drawable.course_table_last_colum));
                //相对布局参数
                RelativeLayout.LayoutParams rp = new RelativeLayout.LayoutParams(aveWidth * 33 / 32 + 1, gridHeight);
                //文字对齐方式
                tx.setGravity(Gravity.CENTER);
                //如果是第一列，需要设置课的序号（1 到 12）
                rp.addRule(RelativeLayout.RIGHT_OF, (i - 1) * 8  + j - 1);
                rp.addRule(RelativeLayout.ALIGN_TOP, (i - 1) * 8  + j - 1);
                tx.setText("");
                tx.setLayoutParams(rp);
                course_table_layout.addView(tx);
            }
        }
        //课表颜色背景
        int[] background = {
                R.drawable.course_bg1,
                R.drawable.course_bg2,
                R.drawable.course_bg3,
                R.drawable.course_bg4,
                R.drawable.course_bg5,
                R.drawable.course_bg6,
                R.drawable.course_bg7,
                R.drawable.course_bg8,
                R.drawable.course_bg9,
                R.drawable.course_bg10,
                R.drawable.course_bg11,
                R.drawable.course_bg12,
                R.drawable.course_bg13,
                R.drawable.course_bg14,
                R.drawable.course_bg15,
                R.drawable.course_bg16,
                R.drawable.course_bg1,
                R.drawable.course_bg2,
                R.drawable.course_bg3,
                R.drawable.course_bg4,
                R.drawable.course_bg5,
                R.drawable.course_bg6,
                R.drawable.course_bg7,
                R.drawable.course_bg8,
                R.drawable.course_bg9,
                R.drawable.course_bg10,
                R.drawable.course_bg11,
                R.drawable.course_bg12,
                R.drawable.course_bg13,
                R.drawable.course_bg14,
                R.drawable.course_bg15,
                R.drawable.course_bg16,
        };

        // 添加课程信息
        ArrayList<CourseBean> kcs = CourseBeanLab.get(getActivity().getApplicationContext()).getCourses();
        Log.i(TAG, "课程数" + kcs.size());
        int[][][] mark=new int[8][13][26];
        for(int j=0;j<8;j++) {
            for(int k=0;k<13;k++) {
                for(int l=0;l<26;l++) {
                    mark[j][k][l]=0;
                }
            }
        }

        for (int i=0;i<kcs.size();i++) {
            CourseBean kc=CourseBeanLab.get(getActivity().getApplicationContext()).getCourses().get(i);
            if(kc.getKcXuenian() !=xuenian||kc.getKcYear()!=year){
                continue;
            }
            if(kc.getKcStartWeek()<=week&&kc.getKcEndWeek()>=week){
                if(kc.isKcIsSingle()&&week%2==1){
                    for(int j=kc.getKcStartTime();j<=kc.getKcEndTime();j++) {
                        mark[kc.getKcWeekend()][j][week]=1;
                    }
                } else if(kc.isKcIsDouble() && week % 2 == 0){
                    for(int j=kc.getKcStartTime();j<=kc.getKcEndTime();j++) {
                        mark[kc.getKcWeekend()][j][week]=1;
                    }
                }
            }
        }

        for (int i=0;i<kcs.size();i++) {

            CourseBean kc=CourseBeanLab.get(getActivity()).getCourses().get(i);
            if(kc.getKcXuenian() !=xuenian||kc.getKcYear()!=year){
                continue;
            }
            TextView courseInfo = new TextView(getActivity());
            String name=kc.getKcName();
            String location=kc.getKcLocation();
            if(name.length()>=13){
                name = name.substring(0, 11);
                name += "...";
            }
            courseInfo.setText(name+"\n\n"+kc.getKcLocation());
            //该textview的高度根据其节数的跨度来设置
            int timecount = Math.abs(kc.getKcEndTime()-kc.getKcStartTime()+1) ;
            RelativeLayout.LayoutParams rlp = new RelativeLayout.LayoutParams(aveWidth * 31 / 32, (gridHeight - 5) * timecount );
            //textview的位置由课程开始节数和上课的时间（day of week）确定

            rlp.topMargin = 5 + (kc.getKcStartTime()- 1) * gridHeight+40;
            rlp.leftMargin = 1;
            // 偏移由这节课是星期几决定
            rlp.addRule(RelativeLayout.RIGHT_OF, kc.getKcWeekend());
            //字体居中
            courseInfo.setGravity(Gravity.CENTER);
            // 设置一种背景 根据当前周数设定



            if(kc.getKcStartWeek()<=week&&kc.getKcEndWeek()>=week){
                if(kc.isKcIsSingle()&&week%2==1){
                    for(int j=kc.getKcStartTime();j<=kc.getKcEndTime();j++) {
                        mark[kc.getKcWeekend()][j][week]=1;
                    }
                    courseInfo.setBackgroundResource(background[kc.getKcBackgroundId()]);
                    courseInfo.getBackground().setAlpha(200);

                } else if(kc.isKcIsDouble() && week % 2 == 0){
                    for(int j=kc.getKcStartTime();j<=kc.getKcEndTime();j++) {
                        mark[kc.getKcWeekend()][j][week]=1;
                    }
                    courseInfo.setBackgroundResource(background[kc.getKcBackgroundId()]);
                    courseInfo.getBackground().setAlpha(200);
                }

            }

            courseInfo.setTextColor(Color.WHITE);
            if(kc.getKcStartWeek()>week||kc.getKcEndWeek()<week||(!kc.isKcIsDouble()&&week%2==0)||(!kc.isKcIsSingle()&&week%2==1)){
                int flag=0;
                for(int j=kc.getKcStartTime();j<=kc.getKcEndTime();j++) {
                    if (mark[kc.getKcWeekend()][j][week] == 1) {
                        flag=1;
                        break;
                    }
                    mark[kc.getKcWeekend()][j][week]=1;
                }
                if (flag == 1) {
                    continue;
                }

                courseInfo.setBackgroundResource(R.drawable.course_bg0);
                courseInfo.getBackground().setAlpha(200);
                courseInfo.setTextColor(Color.GRAY);
            }
            courseInfo.setTextSize(12);
            courseInfo.setLayoutParams(rlp);

            //设置不透明度
            course_table_layout.addView(courseInfo);
        }
    }

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    showKB(weekpre,yearpre,xuenianpre);
//                    swipeRefreshLayout.setRefreshing(false);
                    Toast.makeText(getActivity(), "刷新成功", Toast.LENGTH_SHORT).show();
                    //swipeRefreshLayout.setEnabled(false);
                    break;
                default:
                    break;
            }
        }
    };

    private void refreshDate(){
        CourseBeanLab.get(getActivity()).getCourses().clear();
        FDScoreLB.get(getActivity()).getScores().clear();
//        KCLB.get(getActivity()).getKcs().clear();
//        FDScoreLB.get(getActivity()).getScores().clear();
//        String Xuehao = getActivity().getSharedPreferences("userinfo", Context.MODE_PRIVATE).getString("passwd","");
//        Log.i("KBFragment", "密码" +).getSharedPreferences("userinfo", Context.MODE_PRIVATE).getString("username", "");
//        String Passwd = getActivity( Passwd);
//        Log.i("KBFragment", "学号" + UserInformation.get(getActivity()).getXuehao());
//        HtmlAnalyze.getScore(getActivity(), Xuehao, Passwd);
        HtmlParseUtil.getCourse(getActivity().getApplicationContext());
    }

    private class getCourse extends AsyncTask<Void,Void,Void> {

        @Override
        protected Void doInBackground(Void... params) {
            HtmlParseUtil.getCourse(getActivity());
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
                showKB(2, 2017, 1);
                Log.i(TAG, "显示课表");

        }
    }
}
