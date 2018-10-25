package com.yts.tsbible.ui.dialog;

import android.app.DatePickerDialog;
import android.content.Context;

import com.yts.tsbible.utills.DateFormat;

import java.util.Calendar;

public class DateSelectDialog {
    public static void start(Context context, long date, DatePickerDialog.OnDateSetListener onDateSetListener) {

        Calendar calendar = DateFormat.getCalendar(date);

        DatePickerDialog pickerDialog = new DatePickerDialog(context, onDateSetListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE));
        pickerDialog.show();
    }
}
