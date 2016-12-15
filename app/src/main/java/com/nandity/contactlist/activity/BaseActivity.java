package com.nandity.contactlist.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

/**
 * Created by 11540 on 2016/12/13.
 */

public class BaseActivity extends AppCompatActivity {
    protected Context context = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//强制为竖屏
        super.onCreate(savedInstanceState);
//        //取消状态栏
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }
    /**
     * activity跳转
     * @param activity
     */
    public void forward(Class<? extends Activity> activity) {
        Intent intent = new Intent(context, activity);
        startActivity(intent);
    }

}
