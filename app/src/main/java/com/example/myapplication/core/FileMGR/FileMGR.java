package com.example.myapplication.core.FileMGR;

import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.myapplication.core.Boot;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public abstract class FileMGR implements  IFILE {

    /**
     * @param path 文件名，也可以是路径,如果不存在，默认为其常见文件夹
     * @return 返回文件句柄
     */
    @Override
    public File getFile(String path) {

        path = convertPath(path);
        File file  =  new File(path);
        return file;
    }

    public File getFile_s(String path,boolean isDir) {
        File file = this.getFile(path);
        if (!file.exists()) {
            if (isDir){
                file.mkdirs();
            }else{
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    Toast.makeText(Boot.getBoot().getActivity(), "权限不足", Toast.LENGTH_SHORT).show();
                    return null;
                }
            }
        }
        if (file.exists()){
            return file;
        }
        return null;
    }

    /**
     *
     * @param s 内容
     * @param path 保存路径
     * @param isAppend 是追加还是覆盖
     * @return 返回文件句柄
     */
    @Override
    public File saveFile(String s, String path, boolean isAppend) throws IOException {
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


    @Override
    public File copyFile(String resource, String target) throws IOException {
        resource = convertPath(resource);
        target = convertPath(target);
        Files.copy(Paths.get(resource),Paths.get(target));
        return new File(target);
    }

    @Override
    public File moveFile(String resource, String target) throws IOException {
        resource = convertPath(resource);
        target = convertPath(target);
        Files.move(Paths.get(resource),Paths.get(target));
        return new File(target);
    }

    @Override
    public boolean deleteFile(String resource) throws IOException {
        resource = convertPath(resource);
        Files.delete(Paths.get(resource));
        File file = new File(resource);
        return !file.exists();//is successful
    }
}
