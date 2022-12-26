package com.example.myapplication.core;


import android.webkit.WebChromeClient;
import android.webkit.WebView;

public class MyWebChromeClient extends WebChromeClient {
    @Override
    public void onProgressChanged(WebView view, int newProgress) {
        super.onProgressChanged(view, newProgress);
        //页面加载完后通知Boot
        if (newProgress==100){
            Boot.getBoot().refresh(view.getUrl());
        }
    }
}
