package com.nandity.contactlist.view;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.widget.DatePicker;

import java.util.Calendar;

/**
 * Created by lemon on 2016/12/14.
 */

public class DatePickerFragment extends DialogFragment implements
        DatePickerDialog.OnDateSetListener {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }
    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {
        SetInputListener listener = (SetInputListener) getActivity();
        listener.onSetInputComplete( year, month,  day);
       // Log.d("OnDateSet", "select year:"+year+";month:"+month+";day:"+day);
    }

    public interface SetInputListener
    {
        void onSetInputComplete(int year, int month, int day);
    }
}
