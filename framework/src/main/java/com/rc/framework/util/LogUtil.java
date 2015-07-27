package com.rc.framework.util;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Description:日志工具
 * User: Caizemingg(Email:Caizemingg@163.com)
 * Date: 2014-12-23 17:47
 */
public class LogUtil {

    private static final String DEFAULT_UID = "uId is null";
    private static final String TAG_ERROR = "error";
    private static final String TAG_WARN = "warn";
    private static final String TAG_VERBOSE = "verbose";
    private static final String TAG_DEBUG = "debug";
    private static final String TAG_INFO = "info";
    public static final String SEPARATE = "---";
    private static final String END = "\n\n";

    private static SimpleDateFormat simpleDateFormat;
    private static SimpleDateFormat sdfLogTime;
    private static LogModel logModel;

    static {
        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        sdfLogTime = new SimpleDateFormat("yyyyMMddhhmmssSSS", Locale.CHINA);
        logModel = LogModel.CONSOLE;
    }

    private static String addTime(String msg) {
        return msg + SEPARATE + getTime();
    }

    /**
     * 错误日志，
     *
     * @param msg     内容
     * @param context {@link Context}
     */
    public static void e(String msg, Context context) {
        e(null, msg, null, context);
    }

    /**
     * 错误日志，
     *
     * @param tag     标签
     * @param msg     内容
     * @param uId     唯一标识
     * @param context {@link Context}
     */
    public static void e(String tag, String msg, String uId, Context context) {
        if (tag == null) {
            tag = context.getClass().getName();
        }
        if (logModel == LogModel.CONSOLE) {
            Log.e(tag, formatMsg(uId, msg));
        } else if (logModel == LogModel.DEBUG) {
            Log.e(tag, formatMsg(uId, msg));
            saveLogLocal(TAG_ERROR, tag, msg, context);
        } else if (logModel == LogModel.RELEASE) {
            saveLogLocal(TAG_ERROR, tag, msg, context);
        }
    }

    /**
     * 调试日志
     *
     * @param msg     内容
     * @param context {@link Context}
     */
    public static void d(String msg, Context context) {
        d(null, msg, null, context);
    }

    /**
     * 调试日志。
     *
     * @param tag     标签
     * @param msg     内容
     * @param uId     唯一标识
     * @param context {@link Context}
     */
    public static void d(String tag, String msg, String uId, Context context) {
        if (tag == null) {
            tag = context.getClass().getName();
        }
        if (logModel == LogModel.CONSOLE) {
            Log.d(tag, formatMsg(uId, msg));
        } else if (logModel == LogModel.DEBUG) {
            Log.d(tag, formatMsg(uId, msg));
            saveLogLocal(TAG_DEBUG, tag, msg, context);
        } else if (logModel == LogModel.RELEASE) {
            saveLogLocal(TAG_DEBUG, tag, msg, context);
        }
    }

    /**
     * 警告日志
     *
     * @param msg     内容
     * @param context {@link Context}
     */
    public static void w(String msg, Context context) {
        w(null, msg, null, context);
    }

    /**
     * 警告日志
     *
     * @param tag     标签
     * @param msg     内容
     * @param uId     唯一标识
     * @param context {@link Context}
     */
    public static void w(String tag, String msg, String uId, Context context) {
        if (tag == null) {
            tag = context.getClass().getName();
        }
        if (logModel == LogModel.CONSOLE) {
            Log.w(tag, formatMsg(uId, msg));
        } else if (logModel == LogModel.DEBUG) {
            Log.w(tag, formatMsg(uId, msg));
            saveLogLocal(TAG_WARN, tag, msg, context);
        } else if (logModel == LogModel.RELEASE) {
            saveLogLocal(TAG_WARN, tag, msg, context);
        }
    }

    /**
     * @param tag 标签
     * @param msg 内容
     * @param uId 唯一标识
     */
    public static void v(String tag, String msg, String uId) {
        if (logModel == LogModel.CONSOLE || logModel == LogModel.DEBUG) {
            Log.v(tag, formatMsg(uId, msg));
        }
    }

    /**
     * @param tag 标签
     * @param msg 内容
     * @param uId 唯一标识
     */
    public static void i(String tag, String uId, String msg) {
        if (logModel == LogModel.CONSOLE || logModel == LogModel.DEBUG) {
            Log.i(tag, formatMsg(uId, msg));
        }
    }

    /**
     * 上传日志文件
     *
     * @param context {@link Context} {@link Context}
     */
    public static void uploadLog(final Context context) {
        File file = new File(context.getFilesDir().getAbsolutePath());
        if (file.exists()) {
            File[] files = file.listFiles(new FilenameFilter() {
                @Override
                public boolean accept(File dir, String filename) {
                    if (filename.startsWith("log-") && filename.endsWith(".txt")) {
                        if (getLogFileName(context).endsWith(filename)) {
                            return false;
                        } else {
                            return true;
                        }
                    }
                    return false;
                }
            });
//            for (final File temp : files) {
//                IPushService iPushService = new PushServiceImpl();
//                try {
//                    iPushService.uploadFile(context, "log", temp, new JsonHttpResponseHandler() {
//                        @Override
//                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
//                            super.onSuccess(statusCode, headers, response);
//                            try {
//                                if ("0".equals(response.getString("code"))) {
//                                    temp.delete();
//                                }
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    });
//                } catch (FileNotFoundException e) {
//                    e.printStackTrace();
//                }
//            }
        }
    }

    private static String getLogFileName(Context context) {
        return context.getFilesDir().getAbsolutePath() + File.separator + "log-" + getDate() + ".txt";
    }

    /**
     * 把日志保存到本地
     *
     * @param logLevel 日志等级
     * @param tag      标签
     * @param msg      内容
     * @param context  {@link Context}
     */
    private static void saveLogLocal(String logLevel, String tag, String msg, Context context) {
        File file = new File(context.getFilesDir().getAbsolutePath());
        String content = logLevel + SEPARATE + tag + SEPARATE + msg + SEPARATE + getTime() + END;
        if (!file.exists()) {
            file.mkdir();
        }
        file = new File(getLogFileName(context));
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                Log.e(tag, "无法创建本地日志文件");
            }
        }
        if (file != null) {
            FileInputStream fis = null;
            FileWriter fw = null;
            try {
                fis = new FileInputStream(file);
                int logFileSize = fis.available();
                if (logFileSize + content.length() > 2 * 1024 * 1024) {
                    File newFile;
                    newFile = new File(context.getFilesDir().getAbsolutePath());
                    if (!newFile.exists()) {
                        newFile.mkdir();
                    }
                    newFile = new File(getNewLogFileName(context));
                    if (!newFile.exists()) {
                        try {
                            newFile.createNewFile();
                        } catch (IOException e) {
                            e.printStackTrace();
                            Log.e(tag, "无法创建本地日志文件");
                        }
                    }
                    file.renameTo(newFile);
                    file.delete();

                    file = new File(context.getFilesDir().getAbsolutePath());
                    if (!file.exists()) {
                        file.mkdir();
                    }
                    file = new File(getLogFileName(context));
                    if (!file.exists()) {
                        try {
                            file.createNewFile();
                        } catch (IOException e) {
                            e.printStackTrace();
                            Log.e(tag, "无法创建本地日志文件");
                        }
                    }
                }
                fw = new FileWriter(file, true);
                fw.write(content);
                fw.flush();
            } catch (IOException e) {
                e.printStackTrace();
                Log.e(tag, "写本地日志文件出错");
            } finally {
                if (fis != null) {
                    try {
                        fis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                        Log.e(tag, "关闭读本地日志文件出错");
                    }
                }
                if (fw != null) {
                    try {
                        fw.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                        Log.e(tag, "关闭写本地日志文件出错");
                    }
                }
            }
        }
    }

    private static String getDate() {
        return simpleDateFormat.format(new Date());
    }

    private static String getTime() {
        return sdfLogTime.format(new Date());
    }

    /**
     * 日志模式：控制台，调试，发布
     */
    public enum LogModel {
        CONSOLE,//输出到控制台
        DEBUG,//输出到控制台并保存带本地
        RELEASE//保存到本地
    }

    public static void setLogModel(LogModel logModel) {
        LogUtil.logModel = logModel;
    }

    private static String formatMsg(String uid, String msg) {
        if (TextUtils.isEmpty(uid)) {
            return DEFAULT_UID + ";" + msg + SEPARATE + getTime();
        }
        return uid + ";" + msg + SEPARATE + getTime();
    }

    private static String getNewLogFileName(Context context) {
        return context.getFilesDir().getAbsolutePath() + File.separator
                + "log-" + LogUtil.getDate() + "-" + LogUtil.getTime() + ".txt";
    }
}
