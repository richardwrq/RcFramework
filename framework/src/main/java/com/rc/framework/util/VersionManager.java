package com.rc.framework.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;

/**
 * Description:
 * User: Caizemingg(Email:Caizemingg@163.com)
 * Date: 2015-01-09
 * Time: 11:53
 * ModifyDescri:
 * ModifyDate:
 */
public class VersionManager {

    private Context context;
    private int currentVersion;

    private int newVersion;
    private String downloadUrl;
    private String updateContentText;

    private DownloadTask dowloadTask;

    public static class Builder {
        private Context context;
        /**
         * 新版本号
         */
        private int newVersion;
        /**
         * app下载地址
         */
        private String downloadUrl;
        /**
         * 跟新的内容
         */
        private String updateContentText;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder setNewVersion(int newVersion) {
            this.newVersion = newVersion;
            return this;
        }

        public Builder setDownloadUrl(String downloadUrl) {
            this.downloadUrl = downloadUrl;
            return this;
        }

        public Builder setUpdateContentText(String updateContentText) {
            this.updateContentText = updateContentText;
            return this;
        }

        public VersionManager build() {
            return new VersionManager(this);
        }
    }

    private VersionManager(Builder builder) {
        this.context = builder.context;
        this.downloadUrl = builder.downloadUrl;
        this.newVersion = builder.newVersion;
        this.updateContentText = builder.updateContentText;

        try {
            this.currentVersion = context.getPackageManager()
                    .getPackageInfo(context.getPackageName(), PackageManager.GET_CONFIGURATIONS).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        dowloadTask = new DownloadTask(context);
    }

    public void show() {
        createDialog().show();
    }

    protected AlertDialog createDialog()
    {
       return new AlertDialog.Builder(context).setTitle("更新提示")
                .setMessage(updateContentText).setPositiveButton("更新", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dowloadTask.execute(downloadUrl,"QQ_196.jpg");
                dialog.dismiss();
            }
        }).setNegativeButton("取消",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).create();
    }

}
