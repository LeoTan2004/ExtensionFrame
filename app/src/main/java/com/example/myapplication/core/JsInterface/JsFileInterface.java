package com.example.myapplication.core.JsInterface;

import android.webkit.JavascriptInterface;
import android.widget.Toast;

import com.example.myapplication.core.Boot;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.CharBuffer;

public class JsFileInterface implements JsInterface{
    private final static String identity = "__FILE__";

    // TODO: 2023/1/3 增加文件管理接口
    @JavascriptInterface
    public String readFile(String path){
        File f = Boot.getBoot().getFileMGRStore().getPubFileMGR().getFile(path);
        if (f.canRead()&&f.exists()){
            try {
                Readable r = new InputStreamReader(new FileInputStream(f));
                CharBuffer charBuffer = CharBuffer.allocate(2<<16);
                r.read(charBuffer);
                return charBuffer.toString();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @JavascriptInterface
    public boolean newFile(String path){
        File file = Boot.getBoot().getFileMGRStore().getPubFileMGR().getFile_s(path,false);
        if (file == null) {
            return false;
        }else{
            return true;
        }
    }

    @JavascriptInterface
    public boolean copyFile(String resource,String target){
        try {
            Boot.getBoot().getFileMGRStore().getPubFileMGR().copyFile(resource, target);
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    @JavascriptInterface
    public boolean moveFile(String resource,String target){
        try {
            Boot.getBoot().getFileMGRStore().getPubFileMGR().moveFile(resource, target);
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    @JavascriptInterface
    public boolean deleteFile(String target){
        try {
            Boot.getBoot().getFileMGRStore().getPubFileMGR().deleteFile(target);
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    @JavascriptInterface
    public boolean writeFile(String target,String data){
        try {
            Boot.getBoot().getFileMGRStore().getPubFileMGR().saveFile(data,target,false);
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    @Override
    public String getIdentity() {
        return identity;
    }

    @JavascriptInterface
    public void toast(String msg) {
        Toast.makeText(Boot.getBoot().getActivity(), getIdentity()+":"+msg, Toast.LENGTH_SHORT).show();
    }
}
