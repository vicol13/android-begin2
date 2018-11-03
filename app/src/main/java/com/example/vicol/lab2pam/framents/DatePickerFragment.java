package com.example.vicol.lab2pam.framents;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;

import java.util.Calendar;

public class DatePickerFragment  extends DialogFragment {


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        // return super.onCreateDialog(savedInstanceState);


        //use curent dates as default when user will se the date picker
        final Calendar c = Calendar.getInstance();
        int year,month,day;
        year = c.get(Calendar.YEAR);
        month  = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(getActivity(), (DatePickerDialog.OnDateSetListener)getActivity() , year, month, day);

    }

    //TODO un constructor in care sa fie dinamic setate date

    public DatePickerFragment(){}
}
