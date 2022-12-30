package com.example.myapplication.core.FileMGR;

/**
 * 本地公共资源获取方法
 * 整个系统资源都可以获取
 */
public class PubFileMGR extends FileMGR {

    @Override
    public String convertPath(String path) {
        return path;
    }
}
