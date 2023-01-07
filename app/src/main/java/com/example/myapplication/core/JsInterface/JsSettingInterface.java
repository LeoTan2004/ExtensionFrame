package com.example.myapplication.core.JsInterface;

import android.webkit.JavascriptInterface;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.core.Boot;

public class JsSettingInterface implements JsInterface{
    private static final String identity = "__SET__";
    @Override
    public String getIdentity() {
        return identity;
    }

    @JavascriptInterface
    public void toast(String msg) {
        Toast.makeText(Boot.getBoot().getActivity(), getIdentity()+":"+msg, Toast.LENGTH_SHORT).show();
    }

    @JavascriptInterface
    public String getSetting(String name){
        return (String) Boot.getBoot().getSettings().getSettings(name);
    }

    @JavascriptInterface
    public String setSetting(String name,String value){
        return (String) Boot.getBoot().getSettings().setSettings(name,value);
    }

    @JavascriptInterface
    public String getLocalhost(){
        return Boot.getBoot().getActivity().getString(R.string.localhost);
    }
}
