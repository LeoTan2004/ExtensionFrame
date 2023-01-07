package com.example.myapplication.core;

import android.app.ActionBar;
import android.graphics.Bitmap;
import android.util.Log;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapplication.R;

public class MyWebViewClient extends WebViewClient {

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        Boot.getBoot().startExtension(url);
    }

    @Nullable
    @Override
    public WebResourceResponse shouldInterceptRequest(WebView view, @NonNull String url) {//URL拦截
        String localhost = Boot.getBoot().getActivity().getString(R.string.localhost);
        if (url.startsWith(localhost)){
            url = url.substring(url.indexOf(localhost)+ localhost.length());
            WebResourceResponse webResourceResponse = convertLocalResource(url);
            if (webResourceResponse != null) return webResourceResponse;
        }

        return super.shouldInterceptRequest(view, url);
    }

    @Nullable
    @Override
    public WebResourceResponse shouldInterceptRequest(WebView view, @NonNull WebResourceRequest request) {
        String url = request.getUrl().toString();
        return shouldInterceptRequest(view, url);
    }



    static int cnt = 0;

    @Nullable
    private WebResourceResponse convertLocalResource(@NonNull String url) {
        try{
            return (WebResourceResponse) Boot.getBoot().invokeExtension(url);
        }catch (Exception s) {
            Log.d("jsInjection", "shouldInterceptRequest: 文件未打开");
            s.printStackTrace();
        }
        return null;
    }

        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);//返回true代表在当前webview中打开，返回false表示打开浏览器
            return super.shouldOverrideUrlLoading(view,url);       }

}
