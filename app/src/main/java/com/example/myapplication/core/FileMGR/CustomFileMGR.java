package com.example.myapplication.core.FileMGR;

import android.content.Context;

import java.io.File;

/**
 * 自定义映射文件管理
 * 通过初始化是对文件进行映射，可以实现对文件的本地化获取
 */
public class CustomFileMGR extends PubFileMGR {
    private String target;

    public CustomFileMGR(String target) {
        if (!target.endsWith(File.separator)){
            target = target+ File.separator;
        }
        this.target = target;
    }

    @Override
    public String convertPath(String path) {
        return super.convertPath(this.target + path);
    }
}
