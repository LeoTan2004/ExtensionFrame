package com.example.myapplication.core;

import android.util.Log;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.FileInputStream;


public class MyWebViewClient extends WebViewClient {

    private String localhost;
    public void setLocalhost(String localhost) {
        Log.d("HHH16", "setLocalhost: "+localhost);
        this.localhost = localhost;
    }


    @Nullable
    @Override
    public WebResourceResponse shouldInterceptRequest(WebView view, @NonNull String url) {//URL拦截
        if (url.startsWith(localhost)){
            url = url.substring(url.indexOf(localhost)+ localhost.length(), url.length());
            WebResourceResponse webResourceResponse = convertLocalResource(url);
            if (webResourceResponse != null) return webResourceResponse;
        }

        return super.shouldInterceptRequest(view, url);
    }

    @Nullable
    private WebResourceResponse convertLocalResource(@NonNull String url) {
        FileMGR fileMGR;
        if ((fileMGR = Boot.getBoot().getFileMGRStore().getFileMGR("Web"))==null){
            fileMGR = Boot.getBoot().getFileMGRStore().getPriFileMGR();
        }
        try{
            WebResourceResponse webResourceResponse =
                    new WebResourceResponse(
                            "application/javascript","UTF-8",
                            new FileInputStream(Boot.getBoot().getFileMGRStore().
                                    getPriFileMGR().getFile(url)));
            return webResourceResponse;
            //TODO 还没有建立相关的目录，也就是asset目录
        }catch (Exception s) {
            Log.d("jsInjection", "shouldInterceptRequest: 文件未打开");
            s.printStackTrace();
        }
        return null;
    }
}
