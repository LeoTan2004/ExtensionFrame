package com.example.myapplication.core.FileMGR;

import android.content.Context;

import com.example.myapplication.core.Boot;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class AssertMGR{
    public static File copyFile(String resource,String target) throws IOException {
        copyAssertDirToData(Boot.getBoot().getActivity(),resource,target);
        return new File(target);
    }

    private static void copyAssertDirToData(Context context, String assetDir, String dir) {
        String[] files;
        try {
            files = context.getResources().getAssets().list(assetDir);
        } catch (IOException e1) {
            return;
        }
        File mWorkingPath = new File(dir);
        if (!mWorkingPath.exists()) {
            mWorkingPath.mkdirs();
        }
        for (int i = 0; i < files.length; i++) {
            try {
                String fileName = files[i];
                if (!fileName.contains(".")) {
                    if (0 == assetDir.length()) {
                        copyAssertDirToData(context, fileName, dir + fileName + "/");
                    } else {
                        copyAssertDirToData(context, assetDir + "/" + fileName, dir +"/"+fileName);
                    }
                    continue;
                }
                File outFile = new File(mWorkingPath, fileName);
                if (outFile.exists()) {
                    outFile.delete();
                }
                InputStream in = null;
                if (0 != assetDir.length()){
                    in = context.getAssets().open(assetDir + "/" + fileName);
                }else{
                    in = context.getAssets().open(fileName);
                }
                OutputStream out = new FileOutputStream(outFile);
                byte[] buf = new byte[1024];
                int len;
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
                in.close();
                out.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
