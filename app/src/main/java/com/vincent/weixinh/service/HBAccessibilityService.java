package com.vincent.weixinh.service;

import android.accessibilityservice.AccessibilityService;
import android.annotation.TargetApi;
import android.app.Notification;
import android.app.PendingIntent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * projectName: 	    WeiXinH
 * packageName:	        com.vincent.weixinh
 * className:	        HBAccessibilityService
 * author:	            Luoxiang
 * time:	            2016/12/2	11:29
 * desc:	            HBAccessibilityServiceUpdate
 */

public class HBAccessibilityService
        extends AccessibilityService

{

    private static final String TAG = "luoxiang";
    private ArrayList<AccessibilityNodeInfo> parents;
    private ArrayList<AccessibilityNodeInfo> mChatList;
    private String mClassName = "com.tencent.mm.ui.LauncherUI";
    private static final String WEI_XIN_HONG_BAO            = "[微信红包]";
    private static final String LING_QU_HONG_BAO            = "领取红包";


    /**
     * 当启动服务的时候就会被调用
     */
    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
        parents = new ArrayList<>();
        mChatList = new ArrayList<>();
    }

    /**
     * 监听窗口变化的回调
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        int eventType = event.getEventType();

        switch (eventType) {
            //当通知栏发生改变时
            case AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED:
                List<CharSequence> texts = event.getText();
                if (!texts.isEmpty()) {
                    for (CharSequence text : texts) {
                        String content = text.toString();
                        if (content.contains("[微信红包]")) {
                            //模拟打开通知栏消息，即打开微信
                            if (event.getParcelableData() != null && event.getParcelableData() instanceof Notification) {
                                Notification  notification  = (Notification) event.getParcelableData();
                                PendingIntent pendingIntent = notification.contentIntent;
                                try {
                                    pendingIntent.send();
                                    Log.e(TAG, "进入微信");
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                }
                break;
            //当窗口的状态发生改变时
            case AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED:
                mClassName = event.getClassName()
                                  .toString();
                if (mClassName.equals("com.tencent.mm.ui.LauncherUI")) {
                    //点击最后一个红包
                    getLastPacket();
                } else if (mClassName.equals(
                        "com.tencent.mm.plugin.luckymoney.ui.LuckyMoneyReceiveUI"))
                {
                    //开红包
                    //inputClick("com.tencent.mm:id/bg7");//6.3.30--6.3.31
                    //inputClick("com.tencent.mm:id/bdh");//6.3.32
                    if (inputClick("com.tencent.mm:id/bg7") || inputClick("com.tencent.mm:id/bdh")){
                        performGlobalAction(GLOBAL_ACTION_BACK);
                        break;
                    }
                    AccessibilityNodeInfo node2 = findOpenButton(getRootInActiveWindow());
                    if (node2 != null){
                        node2.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                    }else {
                        performGlobalAction(GLOBAL_ACTION_BACK);
                    }

                } else if (mClassName.equals("com.tencent.mm.plugin.luckymoney.ui.LuckyMoneyDetailUI")) {
                    //退出红包
                   // inputClick("com.tencent.mm:id/gd");
                    performGlobalAction(GLOBAL_ACTION_BACK);
                    //领取了红包以后回到主页面啦
                    if (parents.size() <= 0){
                        /*Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); //如果是服务里调用，必须加入new task标识
                        intent.addCategory(Intent.CATEGORY_HOME);
                        startActivity(intent);*/
                        performGlobalAction(GLOBAL_ACTION_HOME);
                    }

                }
                break;

            /**
             * 唯一捕获聊天信息变化的地方
             * 但是暂时还没法精准捕获
             */
            case AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED:
                /*if (mClassName.equals("com.tencent.mm.ui.LauncherUI")){
                    AccessibilityNodeInfo nodeInfo = getRootInActiveWindow();
                    if (nodeInfo != null) {
                        collctionNode(nodeInfo , "com.tencent.mm:id/a4a" );
                        performClick();
                    }
                }*/
                break;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    private void collctionNode(AccessibilityNodeInfo nodeInfo , String key ) {
        List<AccessibilityNodeInfo> list = nodeInfo.findAccessibilityNodeInfosByViewId(key);
        if (list.isEmpty()){
            return;
        }
        AccessibilityNodeInfo info = list.get(0);
        if (!mChatList.contains(info)){
            parents.add(info);
            mChatList.add(info);
        }

    }

    /**
     * 通过ID获取控件，并进行模拟点击
     * @param clickId
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    private boolean inputClick(String clickId) {
        AccessibilityNodeInfo nodeInfo = getRootInActiveWindow();
        if (nodeInfo != null) {
            List<AccessibilityNodeInfo> list = nodeInfo.findAccessibilityNodeInfosByViewId(clickId);
            for (AccessibilityNodeInfo item : list) {
                return item.performAction(AccessibilityNodeInfo.ACTION_CLICK);
            }
        }
        return false;
    }

   /* *//**
     * 获取List中最后一个红包，并进行模拟点击
     *//*
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void getLastPacket() {
        AccessibilityNodeInfo rootNode = getRootInActiveWindow();
        recycle(rootNode);
        performClick();
    }*/

    private void performClick() {
        if (parents.size() > 0) {
            AccessibilityNodeInfo accessibilityNodeInfo = parents.get(parents.size() - 1);
            accessibilityNodeInfo.performAction(AccessibilityNodeInfo.ACTION_CLICK);
            parents.remove(accessibilityNodeInfo);
        }
    }

   /* *//**
     * 回归函数遍历每一个节点，并将含有"领取红包"存进List中
     *
     * @param info
     *//*
    public void recycle(AccessibilityNodeInfo info) {
        if (info.getChildCount() == 0) {
            if (info.getText() != null) {
                if ("领取红包".equals(info.getText()
                                      .toString()))
                {
                    if (info.isClickable()) {
                        info.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                    }
                    AccessibilityNodeInfo parent = info.getParent();
                    while (parent != null) {
                        if (parent.isClickable()) {
                            if (!mOutTimes.contains(parent)){
                                parents.add(parent);
                            }
                            break;
                        }
                        parent = parent.getParent();
                    }
                }
            }
        } else {
            for (int i = 0; i < info.getChildCount(); i++) {
                if (info.getChild(i) != null) {
                    recycle(info.getChild(i));
                }
            }
        }
    }*/

    /**
     * 中断服务的回调
     */
    @Override
    public void onInterrupt() {

    }

    /**
     * 找到打开的按钮
     * @param node 节点
     * @return 打开的按钮
     */
    private AccessibilityNodeInfo findOpenButton(AccessibilityNodeInfo node) {
        if (node == null)
            return null;

        //非layout元素
        if (node.getChildCount() == 0) {
            if ("android.widget.Button".equals(node.getClassName()))
                return node;
            else
                return null;
        }

        //layout元素，遍历找button
        AccessibilityNodeInfo button;
        for (int i = 0; i < node.getChildCount(); i++) {
            button = findOpenButton(node.getChild(i));
            if (button != null)
                return button;
        }
        return null;
    }


    /**
     * 获取List中最后一个红包，并进行模拟点击
     */
    private void getLastPacket() {
        recycle(getRootInActiveWindow());
        if(parents.size()>0){
//            parents.get(parents.size() - 1).performAction(AccessibilityNodeInfo.ACTION_CLICK);
            AccessibilityNodeInfo accessibilityNodeInfo = parents.get(parents.size() - 1);
            if (!mChatList.contains(accessibilityNodeInfo)){
                accessibilityNodeInfo.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                parents.remove(accessibilityNodeInfo);
                mChatList.add(accessibilityNodeInfo);
            }
        }
    }

    /**
     * 回归函数遍历每一个节点，并将含有"领取红包"存进List中
     *
     * @param info
     */
    public void recycle(AccessibilityNodeInfo info) {
        if (info.getChildCount() == 0) {
            if (info.getText() != null) {
                if ("领取红包".equals(info.getText().toString())) {
                    if (info.isClickable()) {
                        info.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                    }
                    AccessibilityNodeInfo parent = info.getParent();
                    while (parent != null) {
                        if (parent.isClickable()) {
                            parents.add(parent);
                            break;
                        }
                        parent = parent.getParent();
                    }
                }
            }
        } else {
            for (int i = 0; i < info.getChildCount(); i++) {
                if (info.getChild(i) != null) {
                    recycle(info.getChild(i));
                }
            }
        }
    }


}
