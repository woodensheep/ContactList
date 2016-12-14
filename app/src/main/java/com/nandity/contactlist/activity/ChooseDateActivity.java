package com.nandity.contactlist.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.nandity.contactlist.R;

import java.util.Calendar;

public class ChooseDateActivity extends AppCompatActivity {
    private int year;
    private int month;
    private int day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_date);
        DatePicker datePicker = (DatePicker)
                findViewById(R.id.datePicker);

        // 获取当前的年、月、日、小时、分钟
        Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);
        // 初始化DatePicker组件，初始化时指定监听器
        datePicker.init(year, month, day, new DatePicker.OnDateChangedListener() {

            @Override
            public void onDateChanged(DatePicker arg0, int year
                    , int month, int day) {
                ChooseDateActivity.this.year = year;
                ChooseDateActivity.this.month = month;
                ChooseDateActivity.this.day = day;
                // 显示当前日期、时间
                showDate(year, month, day);
                Toast.makeText(ChooseDateActivity.this, "您选择的日期：" + year + "年  "
                        + month + "月  " + day + "日", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // 定义在EditText中显示当前日期、时间的方法
    private void showDate(int year, int month
            , int day) {
     String birth=year + "年" + (month + 1) + "月" + day + "日  ";

        Intent mIntent = new Intent();
        mIntent.putExtra("birth", birth);
        System.out.println("onSignupSuccess" + birth);
        setResult(RESULT_OK, mIntent);
        finish();
    }
}
