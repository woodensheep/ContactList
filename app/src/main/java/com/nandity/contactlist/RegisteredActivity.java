package com.nandity.contactlist;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.InjectView;

import static android.R.attr.name;


public class RegisteredActivity extends AppCompatActivity {
    private static final String TAG = "RegisteredActivity";
    @InjectView(R.id.input_name)
    EditText mInputName;
    @InjectView(R.id.input_email)
    EditText mInputEmail;
    @InjectView(R.id.input_password)
    EditText mInputPassword;
    @InjectView(R.id.btn_signup)
    AppCompatButton mBtnSignup;
    @InjectView(R.id.link_login)
    TextView mLinkLogin;
    private String mName=null;
    private String mEmail=null;
    private String mPassword=null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registered);
        ButterKnife.inject(this);
        mBtnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });

        mLinkLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });
    }
    public void signup() {
        Log.d(TAG, "Signup");

        if (!validate()) {
            onSignupFailed();
            return;
        }

        mBtnSignup.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(RegisteredActivity.this,
                R.style.transparent_dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("请稍后...");
        progressDialog.show();

        mName = mInputName.getText().toString();
        mEmail = mInputEmail.getText().toString();
        mPassword = mInputPassword.getText().toString();



        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {

                        onSignupSuccess();

                        progressDialog.dismiss();
                    }
                }, 3000);
    }


    public void onSignupSuccess() {
        mBtnSignup.setEnabled(true);
        Intent mIntent = new Intent();
        mIntent.putExtra("name",mName);
        System.out.println("onSignupSuccess"+mName);
        setResult(RESULT_OK, mIntent);
        finish();
    }

    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "注册失败", Toast.LENGTH_LONG).show();

        mBtnSignup.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String name = mInputName.getText().toString();
        String email = mInputEmail.getText().toString();
        String password = mInputPassword.getText().toString();

        if (name.isEmpty() ||  !android.util.Patterns.PHONE.matcher(name).matches()||name.length()<8) {
            mInputName.setError("输入正确的电话号码");
            valid = false;
        } else {
            mInputName.setError(null);
        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            mInputEmail.setError("输入一个有效的电子邮件地址");
            valid = false;
        } else {
            mInputEmail.setError(null);
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
