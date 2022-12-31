package com.example.myapplication.core;


import android.content.Context;
import android.webkit.WebView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.core.DataStore.DataStore;
import com.example.myapplication.core.Extension.Extension;
import com.example.myapplication.core.Extension.Javascript;
import com.example.myapplication.core.Extension.JsExtension;
import com.example.myapplication.core.Extension.JsInterface;
import com.example.myapplication.core.FileMGR.AssertMGR;
import com.example.myapplication.core.FileMGR.FileMGRStore;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;


public class Boot {
    //数据存储
    private DataStore dataStore;

    public DataStore getDataStore() {
        return dataStore;
    }
    //====================================

    private MainActivity activity;

    public MainActivity getActivity() {
        return activity;

    }

    private static Boot boot = null;

    public String getHomePage() {
        return activity.getResources().getString(R.string.homePage);
    }

    public String getSetting() {
        return activity.getResources().getString(R.string.settingPage);
    }

    //=======================================
    private FileMGRStore fileMGRStore;

    public FileMGRStore getFileMGRStore() {
        return fileMGRStore;
    }

    //=========================================
    private HashMap<String, Extension> extensionStore = new HashMap<>();
    private WebView webView = null;






    public  void goBack(){
        this.webView.goBack();
    }
    public  void goHome(){
        this.webView.loadUrl(getHomePage());
    }
    public  void setting() {
        this.webView.loadUrl(getSetting());
//        TODO 暂时用来测试功能
//        startExtension(this.webView.getUrl());
        //todo debuging
        webView.loadUrl("javascript: android.test();");
        try {
            AssertMGR.copyFile("classTable",
                    this.fileMGRStore.getPriFileMGR().getFile("/").getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void test() {
        String[] stringArray = activity.getResources().getStringArray(R.array.ExtensionPath);
        try {
            this.addExtension(stringArray[0]);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        //do something after loadUrl
        startExtension(url);//start the extension
    }

    public int startExtension(String url){
        int counter = 0;
        for (Extension extension:this.extensionStore.values()) {
            if (extension.check(url)){
                counter++;
                extension.startup(webView);
              }
        }
        return counter;
    }

    /**
     *
     * @param id extension的id
     * @param o extension执行的参数，由双方自行约定
     * @return 返回执行的结果，也是由上方自行约定
     */
    public Object invokeExtension(String id,Object o){
        if (this.extensionStore.get(id) != null) {
            return this.extensionStore.get(id).invoke(o);
        }else{
            return null;
        }
    }

    /**
     *
     * @param url 包含id的隐式调用
     * @return
     */
    public Object invokeExtension(@NonNull String url){
        String id;
        if (url.startsWith("JAVASCRIPT")){

            url = url.replace("JAVASCRIPT:","");
        }
        id = url.split(":")[0];

        url = url.replace(id+":","");
        if (this.extensionStore.get(id) != null) {
            return this.extensionStore.get(id).invoke(url);
        }else{
            return null;
        }
    }

    public WebView getWebView() throws Exception {
        if (this.webView == null) {
            throw new Exception();
        }
        return webView;
    }

    public Boot(MainActivity activity,@NonNull WebView view) {
        this.activity = activity;
        this.webView = view;
        boot = this;
        this.fileMGRStore = new FileMGRStore(view.getContext());
        try {
            this.getWebView().addJavascriptInterface(new JsInterface(),"android");
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.dataStore = new DataStore("__Basic__");

    }

    public static void startup(MainActivity activity,WebView view){
        if (boot == null) {
            boot = new Boot(activity,view);
        }
        if (boot.webView==null){
            boot.webView = view;
        }
        boot.test();
        //todo delete

    }

    public boolean addExtension(String path) throws Exception {
        try {
            JsExtension instance = JsExtension.getInstance(path);
            if (instance != null) {
                this.extensionStore.put(instance.getId(),instance);
                return true;
            }
        } catch (Exception e) {
            throw e;
        }
        return false;

    }

}
