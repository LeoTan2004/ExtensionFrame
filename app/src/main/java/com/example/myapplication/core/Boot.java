package com.example.myapplication.core;

import android.util.Log;
import android.view.View;
import android.webkit.WebView;

import java.io.IOException;
import java.util.ArrayList;


public class Boot {

    //getter&setter hasn't been built
    private String homePage = "https://www.baidu.com";
    private String setting = "https://www.bilibili.com/";
    static {
        Log.d("Boot", "static initializer: ");
    }

    public String getLocalhost() {
        return localhost;
    }

    public void setLocalhost(String localhost) {
        this.localhost = localhost;
    }

    private String localhost;

    public  void goBack(){
        this.webView.goBack();
    }

    public  void goHome(){
        Log.d("37HHH", "goHome: "+homePage);
        this.webView.loadUrl(this.homePage);
    }

    public  void setting(){
        this.webView.loadUrl(setting);
    }

    private static Boot boot = null;
    private WebView webView = null;
    private ArrayList<JsExtension> jsExtensions = new ArrayList<>();

    public static Boot getBoot() {
        if (boot==null){
            boot = new Boot();
        }
        return boot;
    }


    private Boot(){
        boot = this;
    }
    //页面被加载完后调用，传来的url位当前的url
    public void refresh(String url){
        //TODO do something after loadUrl
        startExtension(url);//start the extension

    }

    public int startExtension(String url){
        int counter = 0;
        for (JsExtension js:jsExtensions) {
            if (js.check(url)){
                counter++;
                js.startup(webView);
              }
        }
        return counter;
    }

    public WebView getWebView() {
        return webView;
    }

    public Boot(WebView view) {
        this.webView = view;
        boot = this;
    }

    public static void startup(WebView view){
        if (boot == null) {
            boot = new Boot(view);
        }
        if (boot.webView==null){
            boot.webView = view;
        }

    }

    public boolean addExtension(String path)  {
        try {
            JsExtension instance = JsExtension.getInstance(path);
            if (instance != null) {
                jsExtensions.add(instance);
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;

    }
}
