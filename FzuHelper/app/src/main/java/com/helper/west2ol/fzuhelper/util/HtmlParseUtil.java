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

    public static boolean getCourse(Context context) {
        ArrayList<FDScore> scores = FDScoreLB.get(context).getScores();
        ArrayList<CourseBean> kcs = CourseBeanLab.get(context).getCourses();
        String result = HttpUtil.getCourseHtml();
        Document document = Jsoup.parse(result);
        Elements courseEles = document.select("tr[onmouseover=c=this.style.backgroundColor;this.style.backgroundColor='#CCFFaa']");
        for (int i = 0; i < courseEles.size(); i++) {
            Element kb = courseEles.get(i);
            Element titleEle = kb.select("td").get(2);

            String title = titleEle.text();
            //解析学年
            Element yearEle = kb.select("td").get(1);
            String yearStr = yearEle.text().substring(0, 4);
            String xuenianStr = yearEle.text().substring(4, 6);
            int year = Integer.parseInt(yearStr);
            int xuenian = Integer.parseInt(xuenianStr);
            //解析成绩
            FDScore fdscore = new FDScore();
            fdscore.setName(title);
            Element jihuaEle = kb.select("td").get(3);

            Element scoreEle = kb.select("td").get(4);
            String score = scoreEle.text();
            fdscore.setScore(score);

            Element jidianEle = kb.select("td").get(5);
            String jidian = scoreEle.text();
            fdscore.setJidian(jidian);

            Element xuefenEle = kb.select("td").get(6);
            String xuefen = xuefenEle.text();
            fdscore.setXuefen(xuefen);
            fdscore.setYear(year);
            fdscore.setXuenian(xuenian);


            scores.add(fdscore);
            //解析上课时间
            Element timeEle = kb.select("td").get(10);
            String timeCou = timeEle.text();
            String[] strings = timeCou.split(" ");
            int backgroudID = i;
            for (int j = 0; j < strings.length; j++) {
                CourseBean kc = new CourseBean();
                kc.setKcYear(year);
                kc.setKcXuenian(xuenian);
                kc.setKcName(title);
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
        Log.i(TAG,"共"+courseEles.size()+"个");
        return true;
    }
}

