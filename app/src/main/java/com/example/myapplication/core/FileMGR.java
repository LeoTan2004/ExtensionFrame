package com.example.myapplication.core;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public abstract class FileMGR {

    public abstract String convertPath(String path);
    /**
     * @param path 文件名，也可以是路径
     * @return 返回文件句柄
     */
    public File getFile(String path){
        return new File(path);
    }

    /**
     *
     * @param s 内容
     * @param path 保存路径
     * @param isAppend 是追加还是覆盖
     * @return 返回文件句柄
     */
    public File saveFile(String s,String path,boolean isAppend) throws IOException {
        path = convertPath(path);
        StandardOpenOption standardOpenOption;
        if (isAppend){
            standardOpenOption = StandardOpenOption.APPEND;
        }else {
            standardOpenOption = StandardOpenOption.WRITE;
        }
        File file = new File(path);
        //todo 可能会出想文件占用的问题
        Path path1 = Paths.get(file.getPath());
        try(BufferedWriter writer = Files.newBufferedWriter(path1,StandardCharsets.UTF_8, standardOpenOption)){
            writer.write(s);
        }
        return file;
    }

    public File copyFile(String resource,String target) throws IOException {
        resource = convertPath(resource);
        target = convertPath(target);
        Files.copy(Paths.get(resource),Paths.get(target));
        return new File(target);
    }

    public File moveFile(String resource,String target) throws IOException {
        resource = convertPath(resource);
        target = convertPath(target);
        Files.move(Paths.get(resource),Paths.get(target));
        return new File(target);
    }

    public boolean deleteFile(String resource) throws IOException {
        resource = convertPath(resource);
        Files.delete(Paths.get(resource));
        File file = new File(resource);
        return !file.exists();//is successful
    }
}
