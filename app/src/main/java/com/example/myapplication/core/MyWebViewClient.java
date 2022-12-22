package com.example.myapplication.core;

import android.util.Log;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


public class MyWebViewClient extends WebViewClient {


    public void setLocalhost(String localhost) {
        Log.d("HHH16", "setLocalhost: "+localhost);
        Boot.getBoot().setLocalhost(localhost);

    }


    @Nullable
    @Override
    public WebResourceResponse shouldInterceptRequest(WebView view, @NonNull String url) {//URL拦截
        String localhost = Boot.getBoot().getLocalhost();
        if (url.startsWith(localhost)){
            String path = url.substring(url.indexOf(localhost)+ localhost.length(),url.length());
            try{
                WebResourceResponse webResourceResponse =
                        new WebResourceResponse(
                                "application/javascript","UTF-8",view.getContext().getAssets().open(path));
                return  webResourceResponse;
                //TODO 还没有建立相关的目录，也就是asset目录
            }catch (Exception s) {
                Log.d("jsInjection", "shouldInterceptRequest: 文件未打开");
                s.printStackTrace();
            }
        }
        return super.shouldInterceptRequest(view, url);
    }
}
