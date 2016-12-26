package com.vincent.weixinh.utils;

import android.app.KeyguardManager;
import android.content.Context;
import android.os.PowerManager;
/**
 * packageName:	    com.vincent.weixinh.utils
 * className:	    PowerUtil
 * author:	        Luoxiang
 * time:	        2016/12/26	14:40 
 * desc:	        TODO
 * 
 * svnVersion:      
 * upDateAuthor:    Vincent
 * upDate:          2016/12/26
 * upDateDesc:      TODO
 */


public class PowerUtil {
    private PowerManager.WakeLock        wakeLock;
    private KeyguardManager.KeyguardLock keyguardLock;

    public PowerUtil(Context context) {
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        wakeLock = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP,
                                  "HongbaoWakelock");
        KeyguardManager km = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);
        keyguardLock = km.newKeyguardLock("HongbaoKeyguardLock");
    }

    private void acquire() {
        wakeLock.acquire(1800000);
        keyguardLock.disableKeyguard();
    }

    private void release() {
        if (wakeLock.isHeld()) {
            wakeLock.release();
            keyguardLock.reenableKeyguard();
        }
    }

    public void handleWakeLock(boolean isWake) {
        if (isWake) {
            this.acquire();
        } else {
            this.release();
        }
    }
}
