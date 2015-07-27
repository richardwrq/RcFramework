package com.rc.framework.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.util.Log;

/**
 * Description:  应用信息工具类
 * Author: Caizemingg(Email:Caizemingg@163.com)
 * Date: 2015-06-15 10:06
 */
public class AppInfUtil {

    private static final String TAG = "";

    public static String getAppVersion(Context context) {
        PackageManager packageManager = context.getPackageManager();
        PackageInfo packageInfo = null;
        try {
            packageInfo = packageManager.getPackageInfo(context.getPackageName(), PackageManager.GET_ACTIVITIES);
        } catch (NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Log.e(TAG, "找不到改包名:" + context.getPackageName());
        }

        return packageInfo.versionName;
    }
}
