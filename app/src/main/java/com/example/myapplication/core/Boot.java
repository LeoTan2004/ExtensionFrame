package com.example.myapplication.core;


import android.webkit.WebView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.core.DataStore.DataStore;
import com.example.myapplication.core.Extension.Extension;
import com.example.myapplication.core.Extension.JsExtension;
import com.example.myapplication.core.JsInterface.JsDataInterface;
import com.example.myapplication.core.FileMGR.AssertMGR;
import com.example.myapplication.core.FileMGR.FileMGRStore;
import com.example.myapplication.core.JsInterface.JsFileInterface;
import com.example.myapplication.core.JsInterface.JsInterface;
import com.example.myapplication.core.JsInterface.JsSettingInterface;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;


public class Boot {
    private DataStore dataStore;
    private Settings settings;

    public Settings getSettings() {
        if (settings == null) {
            settings = new Settings();
            settings.bindDataStore(new DataStore(this.getActivity(),"__SETTINGS__"));
        }
        return settings;
    }

    public DataStore getDataStore() {
        if (dataStore == null) {
            dataStore = new DataStore(this.getActivity(),"__BASIC__");
        }
        return dataStore;
    }
//====================================

    private MainActivity activity;

    public MainActivity getActivity() {
        return activity;
    }

    private static Boot boot = null;

//    使用优先级的方式，如果从setting中可以获取到则使用setting中的值，
//    否则使用默认值，也就是在value中的值
    public String getHomePage() {
        if (this.getSettings().getSettings("homePage")==null){
            return activity.getResources().getString(R.string.homePage);
        }
        return (String) this.getSettings().getSettings("homePage");
    }

    public String goSetting() {
        if (this.getSettings().getSettings("settingPage")==null){
            return activity.getResources().getString(R.string.settingPage);
        }
        return (String) this.getSettings().getSettings("settingPage");
    }

//=======================================
    private FileMGRStore fileMGRStore;

    public FileMGRStore getFileMGRStore() {
        if (fileMGRStore == null) {
            fileMGRStore = new FileMGRStore(this.getActivity());
        }
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
        this.webView.loadUrl(goSetting());
    }

//===================初始化==============================

    private void initExtension() {
        //这个是指程序初始化是的应用，无需写到外部，当然，写到外部也行。这里据情况而定
        // TODO: 2023/1/4 写到外面，有外部setting决定
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

    public static void startup(MainActivity activity,WebView view){
        if (boot == null) {
            boot = new Boot(activity,view);
        }
        if (boot.webView==null){
            boot.webView = view;
        }
        boot.initAssert();
        boot.initExtension();
    }

    public Boot(MainActivity activity,@NonNull WebView view) {
        this.activity = activity;
        this.webView = view;
        boot = this;
        this.fileMGRStore = new FileMGRStore(view.getContext());
        initJsInterface();
        this.dataStore = new DataStore(this.getActivity(),"__BASIC__");
    }

    private void initJsInterface(){
        this.addJsInterface(new JsDataInterface());
        this.addJsInterface(new JsFileInterface());
        this.addJsInterface(new JsSettingInterface());
    }

    private void initAssert(){
        try {
            AssertMGR.copyFile("classTable",
                    this.getFileMGRStore().getPriFileMGR().getFile("classTable").getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
//===================================================

    /**
     *
     * @param jsInterface 接口对象
     * @return 是否成功添加
     */
    public boolean addJsInterface(JsInterface jsInterface) {
        try {
            this.getWebView().addJavascriptInterface(jsInterface,jsInterface.getIdentity());
        } catch (Exception e) {
            return false;
        }
        return true;
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
        id = id.toLowerCase(Locale.ROOT);
        if (this.extensionStore.get(id) != null) {
            return this.extensionStore.get(id).invoke(o);
        }else{
            return null;
        }
    }

    /**
     *
     * @param url 包含id的隐式调用
     *            格式如：JsExtension.id:path
     * @return 由上面的方法决定
     */
    public Object invokeExtension(@NonNull String url){
        String id = url.split(":")[0];
        url = url.replace(id+":","");
        return invokeExtension(id,url);
    }

    public WebView getWebView() throws Exception {
        if (this.webView == null) {
            throw new Exception();
        }
        return webView;
    }

    public boolean addExtension(String path) throws Exception {
        try {
            Extension instance = JsExtension.getInstance(path);
            if (instance != null) {
                this.extensionStore.put(instance.getId(),instance);
                return true;
            }
        } catch (Exception e) {
            throw e;
        }
        return false;

    }








    // TODO: 2023/1/3 testFunction
    public void test(){
        Toast.makeText(activity, "Test", Toast.LENGTH_SHORT).show();
    }
}
