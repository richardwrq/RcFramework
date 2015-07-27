package com.rc.framework.util;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v4.app.NotificationCompat;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Description:
 * User: Caizemingg(Email:Caizemingg@163.com)
 * Date: 2015-01-09
 * Time: 11:55
 * ModifyDescri:
 * ModifyDate:
 */
public class DownloadTask extends AsyncTask<String, Integer, Integer> {

    private NotificationManager notificationManager;
    private NotificationCompat.Builder notificationBuilder;
    private static final int NOTIFICATION_ID = 0x123456;
    private static final int NOTIFICATION_MAX = 100;
    private Context context;
    private String fileUrl;

    public DownloadTask(Context context) {
        this.context = context;
        notificationManager = (NotificationManager) context.getSystemService(
                Context.NOTIFICATION_SERVICE);
        notificationBuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(android.R.drawable.ic_menu_report_image)
                .setContentTitle("版本更新").setContentText("下载中请稍等")
                .setAutoCancel(false).setProgress(NOTIFICATION_MAX, 0, false);
    }

    /**
     * 下载的url不对
     */
    public static final int DOWNLOAD_URL_ERROR = -1;
    public static final int FILE_URL_ERROR = -2;
    public static final int CONNECTION_ERROR = -3;
    public static final int DOWNLOAD_SUCCESS = 0;

    @Override
    protected Integer doInBackground(String... params) {
        String downloadUrl = params[0];
        fileUrl = Environment.getExternalStorageDirectory() + "/" + params[1];
        File file = new File(fileUrl);

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        int totalSize;// 文件总大小
        int downloadCount = 0;// 已经下载好的大小
        int updateCount = 0;// 已经上传的文件大小
        InputStream inputStream;
        OutputStream outputStream;
        URL url;

        try {
            url = new URL(downloadUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return DOWNLOAD_URL_ERROR;
        }
        HttpURLConnection httpURLConnection = null;
        try {
            httpURLConnection = (HttpURLConnection) url
                    .openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setConnectTimeout(15 * 1000);
            httpURLConnection.setReadTimeout(15 * 1000);
            // 获取下载文件的size
            totalSize = httpURLConnection.getContentLength();
            if (httpURLConnection.getResponseCode() == 404) {
                return CONNECTION_ERROR;
            }
            inputStream = httpURLConnection.getInputStream();
            outputStream = new FileOutputStream(fileUrl, false);// 文件存在则覆盖掉
            byte buffer[] = new byte[1024];
            int readSize = 0;
            try {
                int hundredth = totalSize / 100;//百分之一
                while ((readSize = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, readSize);
                    downloadCount += readSize;// 时时获取下载到的大小
                    if (downloadCount - updateCount >= hundredth) {//多下载百分一就通知
                        publishProgress(downloadCount * 100 / totalSize);
                    }
                }
            } catch (FileNotFoundException ffe) {
                ffe.printStackTrace();
                return FILE_URL_ERROR;
            } finally {
                inputStream.close();
                outputStream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return CONNECTION_ERROR;
        }
        return DOWNLOAD_SUCCESS;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        if (notificationBuilder != null && notificationManager != null) {
            notificationBuilder.setProgress(NOTIFICATION_MAX, values[0], false);
            notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build());
        }
    }

    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);
        if (notificationBuilder != null && notificationManager != null) {

            Uri uri = Uri.fromFile(new File(fileUrl));
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(uri,
                    "application/vnd.android.package-archive");
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,
                    intent, 0);
            notificationBuilder.setAutoCancel(true)
                    .setContentText("下载中成功，请点击安装")
                    .setProgress(NOTIFICATION_MAX, NOTIFICATION_MAX, false)
                    .setContentIntent(pendingIntent);
            notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build());
        }

    }
}
