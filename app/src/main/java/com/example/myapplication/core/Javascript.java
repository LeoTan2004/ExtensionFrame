package com.example.myapplication.core;

import androidx.annotation.NonNull;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.StringJoiner;

class Javascript{
    private File file;
    private String path;

    public String getPath() {
        return path;
    }

    public Javascript(@NonNull File file) throws Exception {
        this.file = file;

        //todo create a relative path for webview,!!check!!
        //这里要和文件管理相联系起来，相当于新建一个私有的映射文件管理器
        //统一使用一个web文件管理映射，但是如果写在webviewClient里面会增加不灵活性
        //建议写在Boot里面，但是这样Boot里面的公共资源就太多了
    }

    public File getFile() {
        return file;
    }
}