package com.helper.west2ol.fzuhelper.util;

import android.content.Context;
import android.util.Log;

import com.helper.west2ol.fzuhelper.bean.CourseBean;
import com.helper.west2ol.fzuhelper.bean.CourseBeanLab;
import com.helper.west2ol.fzuhelper.bean.FDScore;
import com.helper.west2ol.fzuhelper.bean.FDScoreLB;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/10/22.
 */

public class HtmlParseUtil {
    private static final String TAG = "HtmlParseUtil";
    /*
        * 0：本专业
        * 1：课程名称
        * 2：教学大纲 授课计划
        * 4：学分
        * 5：课程类型
        * 6：考试形式
        * 7：教师名字
        * 8：周数 星期几：*-*节 地点
        * */
    private static final int COURSE_TITLE = 1;
    private static final int COURSE_JIHUA = 2;
    private static final int COURSE_SCORE = 4;
    private static final int COURSE_TYPE = 5;
    private static final int COURSE_EXAM_FORM = 6;
    private static final int COURSE_TEACHER = 7;
    private static final int COURSE_TIME = 8;

    public static boolean getCourse(Context context) {
        ArrayList<FDScore> scores = FDScoreLB.get(context).getScores();
        ArrayList<CourseBean> kcs = CourseBeanLab.get(context).getCourses();
        if(scores.size()>=1&&kcs.size()>=2||CourseBeanLab.get(context).isParse()){
            Log.i(TAG, "已经解析过");
            return true;
        }
        String result = HttpUtil.getCourseHtml();
        Document document = Jsoup.parse(result);
        Elements courseEles = document.select("tr[onmouseover=c=this.style.backgroundColor;this.style.backgroundColor='#CCFFaa']");
        Element myKb = courseEles.get(3);
        for(int i=0;i<10;i++){
            if(myKb.select("td").get(i)==null){
                Log.d("s1","null");
            }else{
                Element thing = myKb.select("td").get(i);
                String s1 = thing.text();
                if(s1==null){
                    Log.d("s1","null");
                    continue;
                }
                Log.d("s"+i,s1);
            }
        }
        //
        for (int i = 0; i < courseEles.size(); i++) {
            Element kb = courseEles.get(i);
            Element titleEle = kb.select("td").get(COURSE_TITLE);
            Log.e(TAG,"课程名称:"+titleEle.text());
            String title = titleEle.text();
            //解析学年
            String yearStr = "2017";
            String xuenianStr = "01";
            int year = Integer.parseInt(yearStr);
            int xuenian = Integer.parseInt(xuenianStr);
            //解析成绩
            FDScore fdscore = new FDScore();
            fdscore.setName(title);
            Element jihuaEle = kb.select("td").get(COURSE_JIHUA);
            String j1 = jihuaEle.text();
            Log.d("jihuaEle" ,j1);

//            Element scoreEle = kb.select("td").get(4);
//            String score = scoreEle.text();
//            fdscore.setScore(score);

//            Element jidianEle = kb.select("td").get(5);
//            String jidian = scoreEle.text();
//            fdscore.setJidian(jidian);

            Element xuefenEle = kb.select("td").get(COURSE_SCORE);
            String xuefen = xuefenEle.text();
            Log.d("xuefen","xuefen:"+xuefen);
            fdscore.setXuefen(xuefen);
            fdscore.setYear(year);
            fdscore.setXuenian(xuenian);

            //解析课程备注:
            Element noteEle=kb.select("td").get(11);
            String note=noteEle.text();
            Log.d("note","note:"+note);

            scores.add(fdscore);

            //解析老师名称：
            Element teacherEle = kb.select("td").get(COURSE_TEACHER);
            String teacher = teacherEle.text();
            Log.d("teacher",teacher);

            //解析课程类型
            Element typeEle = kb.select("td").get(COURSE_TYPE);
            String type = typeEle.text();

            //解析上课时间
            Element timeEle = kb.select("td").get(COURSE_TIME);
            String timeCou = timeEle.text();
            Log.d("timeCou","timeCou:"+timeCou);
            String[] strings = timeCou.split(" ");
            for (int j = 0; j < strings.length; j++) {
                CourseBean kc = new CourseBean();
                if (note.length()>=1){
                    kc.setKcNote(note);
                }
                kc.setKcBackgroundId(i);
                kc.setKcYear(year);
                kc.setKcXuenian(xuenian);
                kc.setKcName(title);
                kc.setKcTeacher(teacher);
                kc.setKcType(type);
                try {
                    String[] contents = strings[j].split(" ");

                    String[] week = contents[0].split("-");
                    int startWeek = Integer.parseInt(week[0]);
                    int endWeek = Integer.parseInt(week[1]);
                    kc.setKcStartWeek(startWeek);
                    kc.setKcEndWeek(endWeek);
//                    Log.i(TAG, "startweek" + startWeek + "  endweek" + endWeek);

                    int weekend = Integer.parseInt(contents[1].substring(2, 3));
                    kc.setKcWeekend(weekend);

                    if (contents[1].contains("单")) {
                        String timeStr = contents[1].substring(4, contents[1].length() - 4);
                        String[] time = timeStr.split("-");
                        int startTime = Integer.parseInt(time[0]);
                        int endTime = Integer.parseInt(time[1]);
                        kc.setKcStartTime(startTime);
                        kc.setKcEndTime(endTime);
                        kc.setKcIsDouble(false);

                    } else if (contents[1].contains("双")) {
                        String timeStr = contents[1].substring(4, contents[1].length() - 4);
                        String[] time = timeStr.split("-");
                        int startTime = Integer.parseInt(time[0]);
                        int endTime = Integer.parseInt(time[1]);
                        kc.setKcStartTime(startTime);
                        kc.setKcEndTime(endTime);
                        kc.setKcIsSingle(false);
                    } else {
                        String timeStr = contents[1].substring(4, contents[1].length() - 1);
                        String[] time = timeStr.split("-");
                        int startTime = Integer.parseInt(time[0]);
                        int endTime = Integer.parseInt(time[1]);
                        kc.setKcStartTime(startTime);
                        kc.setKcEndTime(endTime);
                    }

                    String location = contents[2];
                    kc.setKcLocation(location);
//                    String[] time=contents[1];
                    kcs.add(kc);
                } catch (Exception e) {
                    Log.i(TAG, "解析出错:"+title);
                }
            }
        }
        CourseBeanLab.get(context).setParse(true);
        Log.i(TAG,"共"+courseEles.size()+"个");
        return true;
    }

    public static boolean getBeginDate(Context context){
        String html=HttpUtil.getHtml("http://59.77.226.32/xl.asp");
        Log.i(TAG,html);
        Document document = Jsoup.parse(html);
        String now=document.select("div[style=padding:5px;border:1px black dotted]").text();
        Log.i(TAG, "now:" + now);
//        Elements monthEles=document.select("td[colspan=8]");
//        String beginText=monthEles.get(0).text();
//        String beginMonth=beginText.split("年")[1].split("月")[0];
//        Log.i(TAG, "开学月份:" + beginMonth);
//        int month=Integer.parseInt(beginMonth)-1;
        Elements dayEles=document.select("td:matches(正式上课*)");
        Element dayEle = dayEles.get(1);
        String date=document.select("table[cellspacing]").get(1).text();
        date=date.split("为正式上课")[0];
        int length=date.length();
        date=date.substring(length-5,length);
        int month=Integer.parseInt(date.split("-")[0]);
        int day=Integer.parseInt(date.split("-")[1]);
        Log.i(TAG, "month:" + month + " day" + day);
        return false;
    }
}

