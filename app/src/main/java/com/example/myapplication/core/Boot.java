package com.example.myapplication.core;


import android.webkit.WebView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.myapplication.core.Extension.Extension;
import com.example.myapplication.core.Extension.Javascript;
import com.example.myapplication.core.Extension.JsExtension;
import com.example.myapplication.core.FileMGR.FileMGRStore;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;


public class Boot {

    //getter&setter hasn't been built
    private static Boot boot = null;
//    private String localhost;
    private final String homePage = "https://www.baidu.com";
    private String setting = "https://www.bilibili.com/";
    //=======================================
    private FileMGRStore fileMGRStore;

    public FileMGRStore getFileMGRStore() {
        return fileMGRStore;
    }

    //=========================================
//    private ArrayList<JsExtension> jsExtensions = new ArrayList<>();
    private HashMap<String, Extension> extensionStore = new HashMap<>();
    private WebView webView = null;


    public  void goBack(){
        this.webView.goBack();
    }
    public  void goHome(){
        this.webView.loadUrl(this.homePage);
    }
    public  void setting() {
//        this.webView.loadUrl(setting);
//        TODO 暂时用来测试功能
        File file = null;
        try {
            file = fileMGRStore.getPriFileMGR().getFile("main.js");
        } catch (IOException e) {
            e.printStackTrace();
        }
        String s = file.getAbsolutePath();
        Toast.makeText(this.webView.getContext(), s, Toast.LENGTH_SHORT).show();
        try {
            this.addExtension("/data/user/0/com.example.myapplication/files");

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
        String id = url.split(":")[0];
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
                this.extensionStore.put(instance.getId(),instance);
                return true;
            }
        } catch (Exception e) {
            throw e;
        }
        return false;

    }



}
