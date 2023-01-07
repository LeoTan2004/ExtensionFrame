package com.example.myapplication.core.FileMGR;

import android.content.Context;

import java.io.File;

/**
 * 程序自身的存储空间
 * convertPath一般不建议直接调用，
 * TODO 可以考虑使用接口向上层抽象，但是那是之后维护的事情，先将功能开发出来
 */

public class PriFileMGR extends FileMGR{
    private Context context;
    @Override
    public String convertPath(String path) {
        return context.getFilesDir().getPath()+ File.separator+path;
    }

    public PriFileMGR(Context context){
        this.context = context;
    }
}

