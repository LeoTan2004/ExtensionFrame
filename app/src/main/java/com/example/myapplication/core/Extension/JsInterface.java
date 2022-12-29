package com.example.myapplication.core.Extension;

import android.webkit.JavascriptInterface;
import android.widget.Toast;

import com.example.myapplication.core.Boot;

public class JsInterface {
    @JavascriptInterface
    public void test() throws Exception {
        Toast.makeText(Boot.getBoot().getWebView().getContext(), "Js调用了test", Toast.LENGTH_SHORT).show();
    }

    @JavascriptInterface
    public void goHome(){
        Boot.getBoot().goHome();
    }

    @JavascriptInterface
    public void goBack(){
        Boot.getBoot().goBack();
    }
}
