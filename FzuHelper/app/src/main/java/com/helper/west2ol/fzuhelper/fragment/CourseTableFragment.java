package com.helper.west2ol.fzuhelper.fragment;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.helper.west2ol.fzuhelper.R;
import com.helper.west2ol.fzuhelper.activity.MainContainerActivity;
import com.helper.west2ol.fzuhelper.bean.CourseBean;
import com.helper.west2ol.fzuhelper.bean.CourseBeanLab;

import java.util.ArrayList;

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
    private Toolbar toolbar;
    /** 课程格子平均宽度 **/
    protected int aveWidth;

    private int yearpre=2016;
    private int weekpre=1;
    private int xuenianpre=1;
    private View view;
    private ImageView menuIcon;
    private ImageView accountIcon;
    private DrawerLayout mDrawerLayout;
    private SwipeRefreshLayout swipeRefreshLayout;
    Button menu_button_in_course_table;
    DrawerLayout drawer;
    @Override
    public void onCreate(Bundle savedIntenceState){
        super.onCreate(savedIntenceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater , ViewGroup container , Bundle savedIntanceState){
        View rootView = inflater.inflate(R.layout.fragment_course_table , container , false);
        view=rootView;
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
        initKB(rootView);
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
        swipeRefreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.swipe_refreshLayout);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,
                R.color.colorRed,
                R.color.colorAccent,
                R.color.colorBackground);
        swipeRefreshLayout.setSize(SwipeRefreshLayout.DEFAULT);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
//                        refreshDate();
//                        mHandler.sendEmptyMessage(1);
                    }
                }).start();
            }
        });
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
                tx.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                tx.setLayoutParams(rp);
                course_table_layout.addView(tx);
            }
        }
    }

    public void showKB(int week,int year,int xuenian){
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
        int height = dm.heightPixels+toolbar.getHeight();

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
        //五种颜色的背景
//        int[] background = {
//                R.drawable.course_info_blue,
//                R.drawable.course_info_green,
//                R.drawable.course_info_purple,
//                R.drawable.course_info_purple_2,
//                R.drawable.course_info_darkgreen,
//                R.drawable.course_info_blue_2,
//                R.drawable.course_info_green_2,
//                R.drawable.course_info_red,
//                R.drawable.course_info_red_light,
//                R.drawable.course_info_green_3,
//                R.drawable.course_info_purple_light,
//                R.drawable.couse_info_primary,
//                R.drawable.course_info_yellow,
//                R.drawable.course_info_blue_1,
//                R.drawable.course_info_purple_light_2,
//                R.drawable.course_info_blue,
//                R.drawable.course_info_green,
//                R.drawable.course_info_purple,
//                R.drawable.course_info_purple_2,
//                R.drawable.course_info_darkgreen,
//                R.drawable.course_info_blue_2,
//                R.drawable.course_info_green_2,
//                R.drawable.course_info_red,
//                R.drawable.course_info_red_light,
//                R.drawable.course_info_green_3,
//                R.drawable.course_info_purple_light,
//                R.drawable.couse_info_primary,
//                R.drawable.course_info_yellow,
//                R.drawable.course_info_blue_1,
//                R.drawable.course_info_purple_light_2
//        };

        // 添加课程信息
        ArrayList<CourseBean> kcs = CourseBeanLab.get(getActivity().getApplicationContext()).getCourses();
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

            rlp.topMargin = 5 + (kc.getKcStartTime()- 1) * gridHeight;
            rlp.leftMargin = 1;
            // 偏移由这节课是星期几决定
            rlp.addRule(RelativeLayout.RIGHT_OF, kc.getKcWeekend());
            //字体剧中
            courseInfo.setGravity(Gravity.CENTER);
            // 设置一种背景 根据当前周数设定



            if(kc.getKcStartWeek()<=week&&kc.getKcEndWeek()>=week){
                if(kc.isKcIsSingle()&&week%2==1){
                    for(int j=kc.getKcStartTime();j<=kc.getKcEndTime();j++) {
                        mark[kc.getKcWeekend()][j][week]=1;
                    }
                    courseInfo.setBackgroundResource(R.color.colorGray);
                    courseInfo.getBackground().setAlpha(200);

                } else if(kc.isKcIsDouble() && week % 2 == 0){
                    for(int j=kc.getKcStartTime();j<=kc.getKcEndTime();j++) {
                        mark[kc.getKcWeekend()][j][week]=1;
                    }
                    courseInfo.setBackgroundResource(R.color.colorRed);
                    courseInfo.getBackground().setAlpha(200);

                }
            }


            if(kc.getKcStartWeek()>week||kc.getKcEndWeek()<week||(!kc.isKcIsDouble()&&week%2==0)||(!kc.isKcIsSingle()&&week%2==1)){
                int flag=0;
                for(int j=kc.getKcStartTime();j<=kc.getKcEndTime();j++) {
//                    if (kc.getName().equals("模拟电子技术")&&kc.getWeekend()==1) {
//                        Log.i(TAG, "从" + kc.getStartTime()+"到"+kc.getEndTime()+" mark="+mark[kc.getWeekend()][j][week]);
//                    }
                    if (mark[kc.getKcWeekend()][j][week] == 1) {
                        flag=1;
                        break;
                    }
                    mark[kc.getKcWeekend()][j][week]=1;
                }
                if (flag == 1) {
                    continue;
                }

                courseInfo.setBackgroundResource(R.color.colorAccent);
                courseInfo.getBackground().setAlpha(200);
            }
            courseInfo.setTextSize(12);
            courseInfo.setLayoutParams(rlp);
            courseInfo.setTextColor(Color.WHITE);
            //设置不透明度
            course_table_layout.addView(courseInfo);
        }
    }
    private void refreshDate(){
//        KCLB.get(getActivity()).getKcs().clear();
//        FDScoreLB.get(getActivity()).getScores().clear();
//        String Xuehao = getActivity().getSharedPreferences("userinfo", Context.MODE_PRIVATE).getString("passwd","");
//        Log.i("KBFragment", "密码" +).getSharedPreferences("userinfo", Context.MODE_PRIVATE).getString("username", "");
//        String Passwd = getActivity( Passwd);
//        Log.i("KBFragment", "学号" + UserInformation.get(getActivity()).getXuehao());
//        HtmlAnalyze.getScore(getActivity(), Xuehao, Passwd);
    }
}
