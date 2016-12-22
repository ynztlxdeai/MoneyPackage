package com.vincent.weixinh;

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
}
