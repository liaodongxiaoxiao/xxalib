package com.ldxx.android.base.utils;

import android.content.Context;

import com.ldxx.utils.StringUtils;

import java.io.File;

/**
 * Created by LDXX on 2015/7/17.
 * <p/>
 * liaodongxiaoxiao@gmail.com
 */
public class XXFileUtils {
    private XXFileUtils() {

    }

    /**
     * 在SDCard上创建一个目录
     *
     * @param dir 指定目录
     * @return
     */
    public static String initFileDirInSdCard(String dir) {
        //若SDCard不可用或传入的路径为空，返回false
        if (!XXSDCardUtils.isSDCardEnable() || StringUtils.isEmpty(dir)) {
            return null;
        }
        //获取SDcard的路径
        String sdFile = XXSDCardUtils.getSDCardPath() + dir;
        File file = new File(sdFile);
        //若传入的路径在SDCard上已经存在，返回true
        if (!file.isDirectory()) {
            file.mkdirs();
        }
        return file.getPath();

    }

    public static String initFileDirByPackageNameInSD(Context context) {
        return initFileDirInSdCard(XXAppUtils.getPackageName(context));
    }

    public static String initFileDirUnderPackageNameInSD(Context context, String dir) {
        return initFileDirInSdCard(XXAppUtils.getPackageName(context) + "/" + dir);
    }

    public static String initFileDirByShortPackageNameInSD(Context context) {
        String dir = XXAppUtils.getPackageName(context);
        return initFileDirInSdCard(dir.substring(dir.lastIndexOf(".")));
    }

    public static String initFileDirUnderShortPackageNameInSD(Context context, String dir) {
        String path = XXAppUtils.getPackageName(context);
        return initFileDirInSdCard(path.substring(path.lastIndexOf(".")) + "/" + dir);
    }
}
