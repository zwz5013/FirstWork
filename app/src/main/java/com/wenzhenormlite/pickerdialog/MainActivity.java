package com.wenzhenormlite.pickerdialog;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private DatePickerDialog mDatePickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mDatePickerDialog= new DatePickerDialog();
        mDatePickerDialog.setSelectedDate(new int[]{2003,10,11});
        mDatePickerDialog.show(getSupportFragmentManager(),"date");
        mDatePickerDialog.setOnDatePickerClickListener(new DatePickerDialog.OnDatePickerClickListener() {

            @Override
            public void onDateChanged(int year, int monthOfYear, int dayOfMonth) {
                Toast.makeText(MainActivity.this,year+"年"+monthOfYear+"月"+dayOfMonth+"日",Toast.LENGTH_SHORT).show();
                mDatePickerDialog.dismiss();
            }
        });
    }
}
