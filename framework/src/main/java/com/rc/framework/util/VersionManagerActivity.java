package com.rc.framework.util;

import android.app.Activity;
import android.os.Bundle;

/**
 * Description:
 * User: Caizemingg(Email:Caizemingg@163.com)
 * Date: 2015-01-09
 * Time: 19:59
 * ModifyDescri:
 * ModifyDate:
 */
public class VersionManagerActivity extends Activity {

    private String downloadUrl
            = "http://www.hgrc.cn/files/o/attach/xupload/month_1209/201209241046018634.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new VersionManager.Builder(this).setDownloadUrl(downloadUrl).setNewVersion(2)
                .setUpdateContentText("1、更新测试\n2、换行了").build().show();
//        DowloadTask dowloadTask = new DowloadTask(this);
//        dowloadTask.execute(downloadUrl,"QQ_196.jpg");
    }

}
