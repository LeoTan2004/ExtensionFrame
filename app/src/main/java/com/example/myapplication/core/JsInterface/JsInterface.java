package com.example.myapplication.core.JsInterface;

import android.webkit.JavascriptInterface;

public interface JsInterface {
    String getIdentity();

    @JavascriptInterface
    void toast(String msg);
}
