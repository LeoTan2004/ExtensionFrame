package com.example.myapplication.core.FileMGR;

import java.io.File;
import java.io.IOException;

public interface IFILE {
    String convertPath(String path);

    File getFile_s(String path,boolean isDir);

    File getFile(String path);

    File saveFile(String s, String path, boolean isAppend) throws IOException;

    File copyFile(String resource, String target) throws IOException;

    File moveFile(String resource, String target) throws IOException;

    boolean deleteFile(String resource) throws IOException;
}
