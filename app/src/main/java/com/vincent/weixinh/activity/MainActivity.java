package com.vincent.weixinh.activity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.tencent.bugly.beta.Beta;
import com.vincent.weixinh.Constants;
import com.vincent.weixinh.R;
import com.vincent.weixinh.utils.ShareUtil;

import net.youmi.android.AdManager;
import net.youmi.android.normal.banner.BannerManager;
import net.youmi.android.normal.banner.BannerViewListener;

import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

public class MainActivity
        extends AppCompatActivity
        implements View.OnClickListener, EasyPermissions.PermissionCallbacks, BannerViewListener
{

    private static final int RC_EXTERNAL_STORAGE_PHONE_STATE_PER = 101;
    private Button mBtnStartService;
    private Button mBtnUpdate;
    private Button mBtnShare;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getPerm();
        initAD();
    }

    private void initAD() {
        // 获取广告条
        View bannerView = BannerManager.getInstance(this)
                                       .getBannerView(this);

        // 获取要嵌入广告条的布局
        LinearLayout bannerLayout = (LinearLayout) findViewById(R.id.ll_banner);

        // 将广告条加入到布局中
        bannerLayout.addView(bannerView);
    }

    @AfterPermissionGranted(RC_EXTERNAL_STORAGE_PHONE_STATE_PER)
    private void getPerm() {
        if (!EasyPermissions.hasPermissions(this, Constants.EXTERNAL_STORAGE_PHONE_STATE_PER)) {
            // Ask for one permission
            EasyPermissions.requestPermissions(this,
                                               getString(R.string.rationale_camera),
                                               RC_EXTERNAL_STORAGE_PHONE_STATE_PER,
                                               Constants.EXTERNAL_STORAGE_PHONE_STATE_PER);
        }
        AdManager.getInstance(this)
                 .init(Constants.APPID, Constants.APPSECRET, false, true);
        mBtnStartService = (Button) findViewById(R.id.maina_start_service);
        mBtnUpdate = (Button) findViewById(R.id.maina_update);
        mBtnShare = (Button) findViewById(R.id.maina_share);
        mBtnStartService.setOnClickListener(this);
        mBtnUpdate.setOnClickListener(this);
        mBtnShare.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.maina_start_service:
                Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
                startActivity(intent);
                break;

            case R.id.maina_update:
                Beta.checkUpgrade();
                break;
            case R.id.maina_share:
                ShareUtil.shareLink("http://118.192.153.83/apk/redApk.apk" , "分享红包辅助" , "微信全自动抢红包助手:" , this);
                break;

            default:
                break;
        }
    }



    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        // (Optional) Check whether the user denied any permissions and checked "NEVER ASK AGAIN."
        // This will display a dialog directing them to enable the permission in app settings.
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this, getString(R.string.rationale_ask_again)).setTitle(
                    getString(R.string.title_settings_dialog))
                                                                                        .setPositiveButton(
                                                                                                getString(
                                                                                                        R.string.setting))
                                                                                        .setNegativeButton(
                                                                                                getString(
                                                                                                        R.string.cancel),
                                                                                                null /* click listener */)
                                                                                        .setRequestCode(
                                                                                                100)
                                                                                        .build()
                                                                                        .show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // EasyPermissions handles the request result.
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onRequestSuccess() {

    }

    @Override
    public void onSwitchBanner() {

    }

    @Override
    public void onRequestFailed() {

    }
}
