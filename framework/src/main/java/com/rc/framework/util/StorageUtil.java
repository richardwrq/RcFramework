package com.rc.framework.util;

import android.os.Environment;
import android.os.StatFs;

import java.io.File;

/**
 * Description:  存储工具类
 * Author: Caizemingg(Email:Caizemingg@163.com)
 * Date: 2015-06-15 10:06
 */
public class StorageUtil {
    static final int ERROR = -1;

    /**
     * 外部存储是否可用
     *
     * @return ture：可用，false：不可用
     */
    static public boolean externalMemoryAvailable() {
        return Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
    }

    /**
     * 获取手机内部可用空间大小
     *
     * @return 手机内部可用空间大小，单位：byte
     */
    static public long getAvailableInternalMemorySize() {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long availableBlocks = stat.getAvailableBlocks();

        return availableBlocks * blockSize;
    }

    /**
     * 获取手机内部空间大小
     *
     * @return 手机内部空间的总大小，单位：byte
     */
    static public long getTotalInternalMemorySize() {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long totalBlocks = stat.getBlockCount();

        return totalBlocks * blockSize;
    }

    /**
     * 获取手机外部可用空间大小
     *
     * @return 手机外部可用空间大小
     */
    static public long getAvailableExternalMemorySize() {
        if (externalMemoryAvailable()) {
            File path = Environment.getExternalStorageDirectory();
            StatFs stat = new StatFs(path.getPath());
            long blockSize = stat.getBlockSize();
            long availableBlocks = stat.getAvailableBlocks();
            return availableBlocks * blockSize;
        } else {
            return ERROR;
        }
    }

    /**
     * 获取手机外部空间大小
     *
     * @return 手机外部空间大小
     */
    static public long getTotalExternalMemorySize() {
        if (externalMemoryAvailable()) {
            File path = Environment.getExternalStorageDirectory();
            StatFs stat = new StatFs(path.getPath());
            long blockSize = stat.getBlockSize();
            long totalBlocks = stat.getBlockCount();
            return totalBlocks * blockSize;
        } else {
            return ERROR;
        }
    }

    /**
     * 把存储空间的大小转化为字符串
     *
     * @param size 存储空间的大小
     * @return 存储空间的大小，单位：KB或者MB
     */
    static public String formatSize(long size) {
        String suffix = null;

        if (size >= 1024) {
            suffix = "KB";
            size /= 1024;
            if (size >= 1024) {
                suffix = "MB";
                size /= 1024;
            }
        }

        StringBuilder resultBuffer = new StringBuilder(Long.toString(size));
        int commaOffset = resultBuffer.length() - 3;
        while (commaOffset > 0) {
            resultBuffer.insert(commaOffset, ',');
            commaOffset -= 3;
        }

        if (suffix != null)
            resultBuffer.append(suffix);

        return resultBuffer.toString();
    }

    /**
     * 获取sd的路径
     *
     * @return sd的路径
     */
    public static String getExternalStorageDirectory() {
        return Environment.getExternalStorageDirectory().toString(); // 获取根目录
    }

    /**
     * 获取sd的路径
     *
     * @return sd的路径
     */
    public static String getInternalStorageDirectory() {
        return Environment.getDownloadCacheDirectory().toString(); // 获取根目录
    }

}
