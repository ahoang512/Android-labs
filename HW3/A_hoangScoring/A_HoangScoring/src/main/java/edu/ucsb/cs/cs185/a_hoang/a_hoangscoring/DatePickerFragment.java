package edu.ucsb.cs.cs185.a_hoang.a_hoangscoring;



import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Calendar;


public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    private DatePickerDialog d;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        // Use April 17, 2013 as default date
        int year = 2013;
        int month = 3;
        int day = 17;

        //http://stackoverflow.com/questions/14680221/android-remove-calendarview-from-datepickerdialogfragment
        /*
        d = new DatePickerDialog(getActivity(),(MainActivity)getActivity(),year,month,day);
        d.getDatePicker().setCalendarViewShown(false);
        d.setTitle("Wed, Apr 17, 2013");
        */

        d = new DatePickerDialog(getActivity(),this,year,month,day);
        d.getDatePicker().setCalendarViewShown(false);
        d.setTitle(getResources().getString(R.string.initial_date));

        //return new instance of DatePickerDialog
        return d;
    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i2, int i3) {
        MainActivity main = (MainActivity)getActivity();
        main.updateDate(i, i2, i3);
    }









/*
    public DatePickerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_date_picker, container, false);
    }


*/
}
