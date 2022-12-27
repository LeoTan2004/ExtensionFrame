package com.example.myapplication.core.FileMGR;

import android.content.Context;

/**
 * 程序自身的存储空间
 * convertPath一般不建议直接调用，
 * TODO 可以考虑使用接口向上层抽象，但是那是之后维护的事情，先将功能开发出来
 */

public class PriFileMGR extends FileMGR{
    private Context context;
    @Override
    public String convertPath(String path) {
        return context.getFilesDir().getPath()+"/"+path;
    }

    public PriFileMGR(Context context){
        this.context = context;
    }
}

/**
 * 本地公共资源获取方法
 * 整个系统资源都可以获取
 */
class PubFileMGR extends FileMGR{

    @Override
    public String convertPath(String path) {
        return path;
    }
}

/**
 * 自定义映射文件管理
 * 通过初始化是对文件进行映射，可以实现对文件的本地化获取
 */
class CustomFileMGR extends PriFileMGR{
    private String target;
    public CustomFileMGR(Context context,String target) {
        super(context);
        this.target = target;
    }

    @Override
    public String convertPath(String path) {
         return super.convertPath(this.target+path);
    }
}
