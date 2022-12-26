package com.example.myapplication.core;


import android.webkit.WebView;

import androidx.annotation.NonNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;


public class Boot {

    //getter&setter hasn't been built
    private static Boot boot = null;
//    private String localhost;
    private final String homePage = "https://www.baidu.com";
    private String setting = "https://www.bilibili.com/";
    //=======================================
    //TODO 文件管理部分，可以考虑向上抽象
    private FileMGRStore fileMGRStore;

    public FileMGRStore getFileMGRStore() {
        return fileMGRStore;
    }

    //=========================================
    private ArrayList<JsExtension> jsExtensions = new ArrayList<>();
    private WebView webView = null;


    public  void goBack(){
        this.webView.goBack();
    }
    public  void goHome(){
        this.webView.loadUrl(this.homePage);
    }
    public  void setting(){
//        this.webView.loadUrl(setting);
//        TODO 暂时用来测试功能
//        fileMGRStore.getPriFileMGR().getFile("main.js");

    }

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

    public WebView getWebView() throws Exception {
        if (this.webView == null) {
            throw new Exception();
        }
        return webView;
    }

    public Boot(@NonNull WebView view) {
        this.webView = view;
        boot = this;
        this.fileMGRStore = new FileMGRStore(view.getContext());
    }

    public static void startup(WebView view){
        if (boot == null) {
            boot = new Boot(view);
        }
        if (boot.webView==null){
            boot.webView = view;
        }

    }

    public boolean addExtension(String path) throws Exception {
        try {
            JsExtension instance = JsExtension.getInstance(path);
            if (instance != null) {
                jsExtensions.add(instance);
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw e;
        }
        return false;

    }



}
