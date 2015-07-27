package com.rc.framework.util;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Description: 文件工具类
 * 1)官方建议使用File.separator来作为文件目录的分隔符，而不建议直接使用“/”，因为在不同机子上“/”有可能被解释为不同的字符;
 * 2)读写应用目录下的私有文件必须使用openFileOutput和openFileInput方法；
 * 3)openFileOutput是在raw里编译过的，FileOutputStream是任何文件都可以
 * 4)在SDCard中创建与删除文件权限 <uses-permissio android:name="android.permission.MOUNT_UNMOUNT_FILESYSTEMS" />
 * 5)往SDCard写入数据权限 <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
 * Author: Caizemingg(Email:Caizemingg@163.com)
 * Date: 2015-01-09 19:50
 */
public class FileUtil {

    public static final String TAG = "FileUtil";

    /**
     * 新建文件
     *
     * @param path 文件的绝对路径
     * @return 新建的文件，新建失败返回null
     */
    private static File create(String path) {
        String folderPath;
        String fileName;
        int index = TextUtils.lastIndexOf(path, File.separator.toCharArray()[0]);
        folderPath = TextUtils.substring(path, 0, index);
        fileName = TextUtils.substring(path, index, path.length());
        return create(folderPath, fileName);
    }

    /**
     * 新建文件
     *
     * @param folderPath 文件夹的绝对路径
     * @param fileName   文件名文件名   文件名文件名
     * @return 新建的文件，新建失败返回null
     */
    private static File create(String folderPath, String fileName) {
        File temp = new File(folderPath);
        if (!temp.exists()) {
            temp.mkdirs();//创建文件夹
        }
        temp = new File(folderPath + File.separator + fileName);
        if (!temp.exists()) {
            try {
                temp.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                temp = null;
                Log.e(TAG, "创建新文件失败！");
            }
        }
        return temp;
    }

    /**
     * 把字符串写到指定文件中
     *
     * @param file    被写入的文件
     * @param content 要写入的内容 要写入的内容
     */
    private static void write(File file, String content) {
        FileWriter fw = null;
        try {
            fw = new FileWriter(file);
            fw.write(content);
            fw.flush();
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG, "写文件的时候出错！");
        } finally {
            if (fw != null) {
                try {
                    fw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e(TAG, "关闭写文件的时候出错！");
                }
            }
        }
    }

    /**
     * 把字节数组写到指定文件中
     *
     * @param file    被写入的文件
     * @param content 要写入的内容 要写入的内容
     */
    private static void write(File file, byte[] content) {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            fos.write(content);
            fos.flush();
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG, "写文件的时候出错！");
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e(TAG, "关闭写文件的时候出错！");
                }
            }
        }
    }

    /**
     * 把字符串写到应用私有目录的指定文件中
     *
     * @param context
     * @param fileName 文件名文件名
     * @param content  要写入的内容
     */
    private static void writePrivate(Context context, String fileName, String content) {
        writePrivate(context, fileName, content.getBytes());
    }

    /**
     * 把字节数组写到应用私有目录的指定文件中
     *
     * @param context
     * @param fileName 文件名文件名
     * @param content  要写入的内容
     */
    private static void writePrivate(Context context, String fileName, byte[] content) {
        FileOutputStream fos = null;
        try {
            fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            fos.write(content);
            fos.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Log.e(TAG, "文件不存在！");
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG, "写文件的时候出错！");
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e(TAG, "关闭写文件的时候出错！");
                }
            }
        }
    }

    /**
     * 从指定文件中读字符串
     *
     * @param file 要读的文件
     * @return 从文件中读出来的字符串，失败返回null
     */
    private static String read(File file) {
        String content = "";
        FileReader fr = null;
        try {
            fr = new FileReader(file);
            int charTemp;
            charTemp = fr.read();
            while (charTemp != -1) {
                content += (char) charTemp;
                charTemp = fr.read();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            content = null;
            Log.e(TAG, "文件不存在！");
        } catch (IOException e) {
            e.printStackTrace();
            content = null;
            Log.e(TAG, "读文件的时候出错！");
        } finally {
            if (fr != null) {
                try {
                    fr.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e(TAG, "关闭读文件的时候出错！");
                }
            }
        }
        return content;
    }

    /**
     * 从指定文件中读字节数组
     *
     * @param file 要读的文件
     * @return 从文件中读出来的字节数组，失败返回null
     */
    private static byte[] readBytes(File file) {
        FileInputStream fis = null;
        byte[] buffer;
        try {
            fis = new FileInputStream(file);
            int length = fis.available();
            buffer = new byte[length];
            fis.read(buffer);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            buffer = null;
            Log.e(TAG, "文件不存在！");
        } catch (IOException e) {
            e.printStackTrace();
            buffer = null;
            Log.e(TAG, "读文件的时候出错！");
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e(TAG, "关闭读文件的时候出错！");
                }
            }
        }
        return buffer;
    }

    /**
     * 从应用私有目录下的指定文件中读字符串
     *
     * @param context
     * @param fileName 文件名文件名
     * @return 从文件中读出来的字符串，失败返回null
     */
    private static String readPrivate(Context context, String fileName) {
        return new String(readBytesPrivate(context, fileName));
    }

    /**
     * 从应用私有目录下的指定文件中读字节数组
     *
     * @param context
     * @param fileName 文件名文件名
     * @return 从文件中读出来的字节数组，失败返回null
     */
    private static byte[] readBytesPrivate(Context context, String fileName) {
        FileInputStream fis = null;
        byte[] buffer;
        try {
            fis = context.openFileInput(fileName);
            int length = fis.available();
            buffer = new byte[length];
            fis.read(buffer);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            buffer = null;
            Log.e(TAG, "文件不存在！");
        } catch (IOException e) {
            e.printStackTrace();
            buffer = null;
            Log.e(TAG, "读文件的时候出错！");
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e(TAG, "关闭读文件的时候出错！");
                }
            }
        }
        return buffer;
    }
}
