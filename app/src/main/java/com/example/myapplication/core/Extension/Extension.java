package com.example.myapplication.core.Extension;

import android.webkit.WebView;

public interface Extension {
    boolean check(String url);
    void startup(WebView webView);
    Object invoke(Object o);
}
