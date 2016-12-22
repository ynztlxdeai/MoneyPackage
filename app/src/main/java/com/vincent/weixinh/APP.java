package com.vincent.weixinh;

import android.app.Application;

import com.tencent.bugly.Bugly;

/**
 * projectName: 	    MoneyPackage
 * packageName:	        com.vincent.weixinh
 * className:	        APP
 * author:	            Luoxiang
 * time:	            2016/12/22	10:13
 * desc:	            TODO
 *
 * svnVersion:	        $Rev
 * upDateAuthor:	    Vincent
 * upDate:	            2016/12/22
 * upDateDesc:	        TODO
 */

public class APP extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Bugly.init(this, Constants.TENCENT_APPID, false);
    }
}
