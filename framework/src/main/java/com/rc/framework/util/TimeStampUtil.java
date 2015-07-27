package com.rc.framework.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Description: 时间戳工具类
 * Author: Caizemingg(Email:Caizemingg@163.com)
 * Date: 2015-01-09 19:50
 */
public class TimeStampUtil {

    private static final String TAG = "TimeStampUtil";
    private static SimpleDateFormat mSdf;

    static {
        mSdf = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss", Locale.getDefault());
    }

    /**
     * php时间戳转java时间戳
     *
     * @param timeStamp php的时间戳
     * @return java时间戳
     */
    public static String php2Java(String timeStamp) {
        return timeStamp + "000";
    }

    /**
     * php时间戳转java时间戳
     *
     * @param timeStamp php的时间戳
     * @return java时间戳
     */
    public static long php2Java(long timeStamp) {
        return timeStamp * 1000;
    }

    /**
     * 格式化时间戳
     *
     * @param timeStamp 时间戳
     * @return 使用默认格式yyyy/MM/dd hh:mm:ss，格式化后的字符串
     */
    public static String getDate(String timeStamp) {
        return getDate(Long.valueOf(timeStamp));
    }

    /**
     * 格式化时间戳
     *
     * @param timeStamp 时间戳
     * @return 使用默认格式yyyy/MM/dd hh:mm:ss，格式化后的字符串
     */
    public static String getDate(long timeStamp) {
        return getDate(timeStamp, mSdf);
    }

    /**
     * 格式化时间戳
     *
     * @param timeStamp 时间戳
     * @param sdf       格式
     * @return 格式化后的字符串
     */
    public static String getDate(String timeStamp, SimpleDateFormat sdf) {
        return getDate(Long.valueOf(timeStamp), sdf);
    }

    /**
     * 格式化时间戳
     *
     * @param timeStamp 时间戳
     * @param sdf       格式
     * @return 格式化后的字符串
     */
    public static String getDate(long timeStamp, SimpleDateFormat sdf) {
        return sdf.format(new Date(timeStamp));
    }

}