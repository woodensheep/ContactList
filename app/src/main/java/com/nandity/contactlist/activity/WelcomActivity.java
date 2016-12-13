package com.nandity.contactlist.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.nandity.contactlist.R;
import com.nandity.contactlist.view.AutoZoomInImageView;

public class WelcomActivity extends AppCompatActivity {

    private AutoZoomInImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcom);
        iv = (AutoZoomInImageView) findViewById(R.id.aiv_welcom);

        iv.post(new Runnable() {//iv即AutoZoomInImageView

            @Override
            public void run() {
                //简单方式启动放大动画
//                iv.init()
//                  .startZoomInByScaleDeltaAndDuration(0.3f, 1000, 1000);//放大增量是0.3，放大时间是1000毫秒，放大开始时间是1000毫秒以后
                //使用较为具体的方式启动放大动画
                iv.init()
                        .setScaleDelta(0.3f)//放大的系数是原来的（1 + 0.2）倍
                        .setDurationMillis(1900)//动画的执行时间为1500毫秒
                        .setOnZoomListener(new AutoZoomInImageView.OnZoomListener() {
                            @Override
                            public void onStart(View view) {
                                //放大动画开始时的回调
                            }

                            @Override
                            public void onUpdate(View view, float progress) {
                                //放大动画进行过程中的回调 progress取值范围是[0,1]
                            }

                            @Override
                            public void onEnd(View view) {
                                //放大动画结束时的回调
                                Intent intent = new Intent(WelcomActivity.this, MainActivity.class);
                                startActivity(intent);
                                WelcomActivity.this.finish();
                            }
                        })
                        .start(100);//延迟1000毫秒启动
            }
        });
    }
}
