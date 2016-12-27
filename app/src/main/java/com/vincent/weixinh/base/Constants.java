package com.vincent.weixinh.base;

import android.Manifest;

/**
 * projectName: 	    MoneyPackage
 * packageName:	        com.vincent.weixinh
 * className:	        Constants
 * author:	            Luoxiang
 * time:	            2016/12/22	8:43
 * desc:	            TODO
 *
 * svnVersion:	        $Rev
 * upDateAuthor:	    Vincent
 * upDate:	            2016/12/22
 * upDateDesc:	        TODO
 */

public interface Constants  {
    String[] EXTERNAL_STORAGE_PHONE_STATE_PER = {Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_PHONE_STATE,
                    Manifest.permission.READ_EXTERNAL_STORAGE
    };

    String APPID = "8703780aaeae50c8";

    String APPSECRET = "4ff533e9a435e393";

    String TENCENT_APPID = "1b8292003b";
    String TENCENT_KEY = "26e64f3a-db7a-4af9-a098-4bd8d6ced2b2";
}
