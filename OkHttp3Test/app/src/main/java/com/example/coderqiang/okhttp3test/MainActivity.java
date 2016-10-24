package com.example.coderqiang.okhttp3test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Cookie;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.internal.http.HttpEngine;

public class MainActivity extends AppCompatActivity {
    @Bind(R.id.textView)
    TextView textView;
    private static final String TAG="MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        new Thread(new Runnable() {
            @Override
            public void run() {
                getHttp();
            }
        }).start();

    }

    private void getHttp(){
        OkHttpClient okHttpClient = new OkHttpClient.Builder().addNetworkInterceptor(new LoginInterceptor()).build();
        FormBody formBody=new FormBody.Builder().add("muser","031502344").add("passwd","zsqqq1996424").build();
        Request request=new Request.Builder().url("http://59.77.226.32/logincheck.asp").method("Post",null).post(formBody).addHeader("Referer","http://jwch.fzu.edu.cn/").build();
        try {
            Response response=okHttpClient.newCall(request).execute();
            if(!response.message().equals("OK")){
                Log.i(TAG,"网络出错");
                return;
            }
            Log.i(TAG, "Status" + response.message());
            List<Cookie> cookies =null;
            String result = new String(response.body().bytes());
            if(result.contains("密码错误")){
                Log.i(TAG,"密码错误");
                return;
            }
            Log.i(TAG, "登录成功");
            Log.i(TAG, "Cookie" + FzuCookie.get().getCookie());
        } catch (IOException e) {
            Log.i(TAG,"网络出错");
            e.printStackTrace();
        }
        request = new Request.Builder().url("http://59.77.226.35/" + "student/xkjg/wdxk/xkjg_list.aspx" + "?" + FzuCookie.get().getId()).header("Cookie", FzuCookie.get().getCookie()).build();
        try {
            Response kbResponse = okHttpClient.newCall(request).execute();
            Log.i(TAG, new String(kbResponse.body().bytes()));
        } catch (IOException e) {
            e.printStackTrace();
        }
//        Call call=okHttpClient.newCall(request);
//        call.enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                Log.i(TAG, "请求失败");
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                Log.i(TAG,"请求成功"+new String(response.body().bytes()));
//            }
//        });

    }
}
