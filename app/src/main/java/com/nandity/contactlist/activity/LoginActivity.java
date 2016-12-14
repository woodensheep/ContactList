package com.nandity.contactlist.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.nandity.contactlist.R;
import com.nandity.contactlist.view.AnimationButton;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class LoginActivity extends BaseActivity {
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;

    @InjectView(R.id.input_name)
    EditText mInputName;
    @InjectView(R.id.input_password)
    EditText mInputPassword;
//    @InjectView(R.id.btn_login)
//    AppCompatButton mBtnLogin;
AnimationButton mBtnLogin;
    @InjectView(R.id.link_signup)
    TextView mLinkSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.inject(this);
        final AnimationButton mBtnLogin = (AnimationButton) findViewById(R.id.btn_login);
        mBtnLogin.setText("登录"); //设置文本，不设置有默认值
        mBtnLogin.setMode(AnimationButton.Mode.Hand_Finish); //设置进度条模式，不设置有默认值Mode.Auto_Finish
        mBtnLogin.setOnAnimationButtonClickListener(new AnimationButton.OnAnimationButtonClickListener() {
            @Override
            public void onClick() {
                //stopProgress方法 仅仅在button.setMode(AnimationButton.Mode.Hand_Finish);之后才有效。

                mBtnLogin.stopProgress();
                //跳转主页面
                forward(MainActivity.class);
                finish();
            }
        });
//        mBtnLogin.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                login();
//            }
//        });

        mLinkSignup.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // 进入注册页面
                Intent intent = new Intent(getApplicationContext(), RegisteredActivity.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
            }
        });
    }

    public void login() {
        Log.d(TAG, "登录");

        if (!validate()) {
            onLoginFailed();
            return;
        }

        mBtnLogin.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,
                R.style.transparent_dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        onLoginSuccess();

                        progressDialog.dismiss();
                    }
                }, 3000);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, "onActivityResult");
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {
                mInputName.setText(data.getStringExtra("name"));
                Log.d(TAG, data.getStringExtra("name"));
//                mInputPassword.setText(data.getStringExtra("password"));
            }
        }
    }

    @Override
    public void onBackPressed() {
        // 不能返回到主页
        moveTaskToBack(true);
    }

    public void onLoginSuccess() {
        Toast.makeText(getBaseContext(), "登录成功", Toast.LENGTH_LONG).show();
        mBtnLogin.setEnabled(true);
        //跳转主页面
        forward(MainActivity.class);
        finish();
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "登录失败", Toast.LENGTH_LONG).show();

        mBtnLogin.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String name = mInputName.getText().toString();
        String password = mInputPassword.getText().toString();

        if (name.isEmpty() || !android.util.Patterns.PHONE.matcher(name).matches() || name.length() < 8) {
            mInputName.setError("输入正确的电话号码");
            valid = false;
        } else {
            mInputName.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            mInputPassword.setError("4到10个字母数字字符");
            valid = false;
        } else {
            mInputPassword.setError(null);
        }

        return valid;
    }

}
