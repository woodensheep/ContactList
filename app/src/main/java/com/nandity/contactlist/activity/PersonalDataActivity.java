package com.nandity.contactlist.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.nandity.contactlist.R;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class PersonalDataActivity extends AppCompatActivity {
    private static final String TAG = "PersonalDataActivity";
    private static final int REQUEST_SIGNUP = 0;
    @InjectView(R.id.data_name)
    EditText mDataName;
    @InjectView(R.id.data_email)
    EditText mDataEmail;
    @InjectView(R.id.data_department)
    EditText mDataDepartment;
    @InjectView(R.id.data_birth)
    EditText mDataBirth;
    @InjectView(R.id.btn_signup)
    AppCompatButton mBtnSignup;
    @InjectView(R.id.link_login)
    TextView mLinkLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_data);
        ButterKnife.inject(this);
    }

    @OnClick(R.id.data_birth)
    public void onClick() {
        Intent intent = new Intent(getApplicationContext(), ChooseDateActivity.class);
        startActivityForResult(intent, REQUEST_SIGNUP);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, "onActivityResult");
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {
                mDataBirth.setText(data.getStringExtra("birth"));
                Log.d(TAG, data.getStringExtra("birth"));
//                mInputPassword.setText(data.getStringExtra("password"));
            }
        }
    }

}
