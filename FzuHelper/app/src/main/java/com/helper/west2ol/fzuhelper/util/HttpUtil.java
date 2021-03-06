package com.helper.west2ol.fzuhelper.util;

import android.util.Log;

import java.io.IOException;
import java.util.List;

import okhttp3.Cookie;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/** Login
 * Http request
 * Accept:text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*
 *  /*;q=0.8
 * Accept-Encoding:gzip, deflate
 * Accept-Language:zh-CN,zh;q=0.8
 * Cache-Control:max-age=0
 * Connection:keep-alive
 * Content-Length:39
 * Content-Type:application/x-www-form-urlencoded
 * Cookie:ASPSESSIONIDAQCAAAQC=JCFOLEEBMHFIFIEDDLCNFAEA
 * Host:59.77.226.32
 * Origin:http://jwch.fzu.edu.cn
 * Referer:http://jwch.fzu.edu.cn/
 * Upgrade-Insecure-Requests:1
 * User-Agent:Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.22 Safari/537.36 SE 2.X MetaSr 1.0
*/

public class HttpUtil {
    private static final String TAG = "HttpUtil";
    public static final String LOGIN = "http://59.77.226.32/logincheck.asp";
    // parameter muser,passwwd,x=0,y=0
    public static final String LOGIN_CHK_XS = "http://59.77.226.35/loginchk_xs.aspx";
    // parameter id num       从logincheck.asp获取
    //返回 id

    /**
     * 获取资源
     */
    public static final String BASE = "http://59.77.226.35/";
    public static final String getCourse = "student/xkjg/wdxk/xkjg_list.aspx"; //课表
    public static final String getGrade = "student/jscp/TeaList.aspx"; //成绩
    public static final String getExamClassRoom = "student/jscp/TeaList.aspx"; //考场 同上

    public static final String getEmptyClassRoom = "kkgl/kbcx/kbcx_choose.aspx"; //空教室


    public static String Login(String muser , String passwwd ){
        OkHttpClient okHttpClient = new OkHttpClient.Builder().addNetworkInterceptor(new LoginInterceptor()).build();
        FormBody formBody=new FormBody.Builder().add("muser","031502344").add("passwd","zsqqq1996424").build();
        Request request=new Request.Builder()
                .url("http://59.77.226.32/logincheck.asp")
                .method("Post",null)
                .post(formBody)
                .addHeader("Referer","http://jwch.fzu.edu.cn/")
                .addHeader("Connection","keep-alive")
                .build();
        try {
            Response response=okHttpClient.newCall(request).execute();
            if(!response.message().equals("OK")){
                Log.i(TAG,"网络出错");
                return "网络出错";
            }
            List<Cookie> cookies =null;
            String result = new String(response.body().bytes());
            if(result.contains("<body bgcolor=C6DCB4><script language=javascript>alert")){
                Log.i(TAG,"密码错误");
                return "密码错误";
            }
            Log.i(TAG, "登录成功");
            return "登录成功";
        } catch (IOException e) {
            Log.i(TAG,"网络出错");
            e.printStackTrace();
            return "网络出错";
        } catch (Exception e){
            Log.i(TAG,"网络出错");
            e.printStackTrace();
            return "网络出错";
        }
    }

    public static String getCourseHtml(){
        String html=null;
        OkHttpClient okHttpClient=new OkHttpClient.Builder().build();
        Log.i(TAG, "id:" + FzuCookie.get().getId());
        Request request=new Request.Builder()
                .url("http://59.77.226.35/student/xkjg/wdxk/xkjg_list.aspx?"+"id="+FzuCookie.get().getId())
                .addHeader("Cookie",FzuCookie.get().getCookie()+"")
                .addHeader("Accept-Language","zh-CN,zh;q=0.8,en;q=0.6")
                .addHeader("Connection","keep-alive")
                .build();
        try {
            Response response = okHttpClient.newCall(request).execute();
            if(!response.message().equals("OK")){
                Log.i(TAG,"获取课表失败，message不是Ok");
                return null;
            }
            String result=new String(response.body().bytes());
            Log.i(TAG, "result" + result);
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return html;
    }

    public static String getHtml(String url) {
        OkHttpClient client = new OkHttpClient();
        Request request=new Request.Builder().url(url)
                .addHeader("Accept-Language","zh-CN,zh;q=0.8,en;q=0.6")
                .addHeader("Accept","text/html,application/xhtml+xml,application/xml;charset=utf-8;q=0.9,image/webp,*/*;q=0.8")
                .addHeader("Accept-Encoding","gzip, deflate, sdch").build();
        try {
            Response response=client.newCall(request).execute();
            if (response.isSuccessful()) {
                String result = new String(response.body().bytes(),"gb2312");
                return result;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }


}
