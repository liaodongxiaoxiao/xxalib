package com.ldxx.android.base.utils;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
 *
 * Created by LDXX on 2015/7/17.
 * company Ltd
 * liaodongxiaoxiao@gmail.com
 */
public class XXDBUtils {
    private XXDBUtils() {
    }

    public static void copyDBtoSDcard(Context context) {
        if(!XXSDCardUtils.isSDCardEnable()){
            return;
        }
        FileOutputStream out = null;
        FileInputStream in = null;
        try {
            //获取应用的db文件路径
            String path = context.getFilesDir().getPath().replace("files", "databases");

            File dir = new File(path);
            if (!dir.isDirectory()) {
                return;
            }

            File[] listFiles = dir.listFiles();
            //文件存储目录
            String dataPath = XXFileUtils.initFileDirUnderPackageNameInSD(context, "databases");
            Log.e("XXDBUtils",dataPath);
            File outFile ;
            for (File f : listFiles) {
                in = new FileInputStream(f);
                outFile = new File(dataPath + "/" + f.getName());
                out = new FileOutputStream(outFile);
                int length = -1;
                byte[] buf = new byte[1024];
                while ((length = in.read(buf)) != -1) {
                    out.write(buf, 0, length);
                }
                out.flush();
            }

        } catch (Exception e) {
            Log.e("XXDBUtils",e.getMessage(),e);
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
