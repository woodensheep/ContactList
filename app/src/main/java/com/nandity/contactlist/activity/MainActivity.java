package com.nandity.contactlist.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import com.nandity.contactlist.R;
import com.nandity.contactlist.view.AnimationButton;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final AnimationButton button = (AnimationButton) findViewById(R.id.button);
// button.setTextSizeTouch(25); //设置按下时字体的大小，不设置有默认值
// button.setStrokeProgress(10); //设置进度条的厚度，不设置有默认值
// button.setColorBase(Color.GREEN); //设置整体基色，不设置有默认值
// button.setColorBack(Color.GRAY); //设置进度条的背景色，不设置有默认值
// button.setStroke(3); //设置边框的厚度，不设置有默认值
// button.setStrokeText(0); //设置文本的厚度，不设置有默认值
// button.setTextSize(30); //设置文本的字体大小，不设置有默认值
// button.setRound(30); //设置圆角，不设置有默认值
        button.setText("登录"); //设置文本，不设置有默认值
        button.setMode(AnimationButton.Mode.Hand_Finish); //设置进度条模式，不设置有默认值Mode.Auto_Finish
        button.setOnAnimationButtonClickListener(new AnimationButton.OnAnimationButtonClickListener() {
            @Override
            public void onClick() {
                //stopProgress方法 仅仅在button.setMode(AnimationButton.Mode.Hand_Finish);之后才有效。
                button.stopProgress();
                Intent i= new Intent(MainActivity.this,PersonalDataActivity.class);
                startActivity(i);
            }
        });

    }
}
