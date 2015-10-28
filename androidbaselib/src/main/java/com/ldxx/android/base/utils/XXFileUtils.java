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
     *
     * @param dir
     * @return
     */
    public static String initFileDirInSdCard(String dir) {
        //
        if (!XXSDCardUtils.isSDCardEnable() || StringUtils.isEmpty(dir)) {
            return null;
        }
        //
        String sdFile = XXSDCardUtils.getSDCardPath()+"/" + dir;
        File file = new File(sdFile);
        //
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
