package com.vincent.weixinh;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity
        extends AppCompatActivity
        implements View.OnClickListener
{

    private Button mBtnStartService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mBtnStartService = (Button) findViewById(R.id.maina_start_service);
        mBtnStartService.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
                    case R.id.maina_start_service:
                        Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
                        startActivity(intent);
                        break;

                    default:
                        break;
                }
    }
}
