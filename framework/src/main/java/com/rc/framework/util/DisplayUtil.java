package com.rc.framework.util;

import android.content.Context;

/**
 * Description: ui单位转换工具类
 * Author: Caizemingg(Email:Caizemingg@163.com)
 * Date: 2015-01-09 19:50
 */
public class DisplayUtil {

    private static float mDensity;
    private static float mScaleDensity;
    private static boolean isInit;
    private static final float NOT_INIT_DENSITY = -1;

    static {
        isInit = false;
        mDensity = NOT_INIT_DENSITY;
        mScaleDensity = NOT_INIT_DENSITY;
    }

    /**
     * 初始化设备的屏幕密度
     *
     * @param context
     */
    public static void initDensity(Context context) {
        mDensity = context.getResources().getDisplayMetrics().density;
        mScaleDensity = context.getResources().getDisplayMetrics().scaledDensity;
        isInit = true;
    }

    /**
     * 检查是否已经初始化，必须初始化后才能调用其他方法
     */
    private static void checkInit() {
        if (!isInit) {
            throw new IllegalStateException("请先初始化！");
        }
    }

    /**
     * px转dp
     *
     * @param pxValue
     * @return
     */
    public static int px2dp(float pxValue) {
        checkInit();

        return (int) (pxValue / mDensity + 0.5f);
    }

    /**
     * dp转px
     *
     * @param dpValue
     * @return
     */
    public static int dp2px(float dpValue) {
        checkInit();

        return (int) (dpValue * mDensity + 0.5f);
    }

    /**
     * px转sp
     *
     * @param pxValue
     * @return
     */
    public static int px2sp(float pxValue) {
        checkInit();

        return (int) (pxValue / mScaleDensity + 0.5f);
    }

    /**
     * sp转px
     *
     * @param spValue
     * @return
     */
    public static int sp2px(Context context, float spValue) {
        checkInit();

        return (int) (spValue * mScaleDensity + 0.5f);
    }
}