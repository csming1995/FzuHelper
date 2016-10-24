package com.example.coderqiang.okhttp3test;

import android.util.Log;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by CoderQiang on 2016/10/24.
 */

public class LoginInterceptor implements Interceptor {
    private static final String TAG="LoginInterceptor";

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request=chain.request();
        Response response=chain.proceed(request);

        Log.i(TAG, "response" + response.headers());
        String cookie=response.header("Set-Cookie");
        if (cookie != null) {
            FzuCookie.get().setCookie(cookie);
        }

        String idStr=response.header("Location");
        if (idStr != null) {
            int i=0;
            int length=0;
            String temp;
            while(i<idStr.length()){
                temp=idStr.substring(i,i+2);
                Log.i(TAG,"temp:"+temp);
                if(temp.equals("id")){
                    length=i;
                    break;
                }
                i++;
            }
            FzuCookie.get().setId(idStr.substring(length));
            Log.i(TAG, "id" + FzuCookie.get().getId());
        }

        return response;
    }
}
