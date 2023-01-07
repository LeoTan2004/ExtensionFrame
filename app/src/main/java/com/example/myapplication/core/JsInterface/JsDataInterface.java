package com.example.myapplication.core.JsInterface;

import android.webkit.JavascriptInterface;
import android.widget.Toast;

import com.example.myapplication.core.Boot;
import com.example.myapplication.core.Extension.JsExtension;

public class JsDataInterface implements JsInterface{
    private static final String identity = "__DATA__";

    @Override
    public String getIdentity() {
        return identity;
    }

    @JavascriptInterface
    public void toast(String msg) {
        Toast.makeText(Boot.getBoot().getActivity(), getIdentity().replace("_","")+":"+msg, Toast.LENGTH_SHORT).show();
    }

//    数据读取===============

    @JavascriptInterface
    public int getInt(String key){
        return JsExtension.store.getSharedPreferences().getInt(key,0);
    }

    @JavascriptInterface
    public String getString(String key){
        return JsExtension.store.getSharedPreferences().getString(key,null);
    }

    @JavascriptInterface
    public float getFloat(String key){
        return JsExtension.store.getSharedPreferences().getFloat(key,0);
    }


//    数据存储===============

    @JavascriptInterface
    public void remove(String key){
        JsExtension.store.getEditor().remove(key);
    }

    @JavascriptInterface
    public void putInt(String key,int value){
        JsExtension.store.getEditor().putInt(key, value);
        JsExtension.store.getEditor().commit();
    }

    @JavascriptInterface
    public void putString(String key,String value){
        JsExtension.store.getEditor().putString(key, value);
        JsExtension.store.getEditor().commit();
    }

    @JavascriptInterface
    public void putFloat(String key,float value){
        JsExtension.store.getEditor().putFloat(key, value);
        JsExtension.store.getEditor().commit();
    }


}
