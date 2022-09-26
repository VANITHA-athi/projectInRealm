package com.example.projectinrealm.events;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;
import com.example.projectinrealm.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import io.realm.Realm;

public class eventData extends AppCompatActivity {
        EditText fesName,fesDate,fesTime;
        Button save ;
        Realm realm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         realm = Realm.getDefaultInstance();
        setContentView(R.layout.activity_event_data);
        fesName = findViewById(R.id.editTextTextPersonName2);
        fesDate = findViewById(R.id.editTextTextPersonName3);
        fesTime = findViewById(R.id.editTextTextPersonName4);
        save = findViewById(R.id.save);
        fesDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v == fesDate) {

                    // Get Current Date
                    final Calendar c = Calendar.getInstance();
                    int mYear = c.get(Calendar.YEAR);
                    int mMonth = c.get(Calendar.MONTH);
                    int mDay = c.get(Calendar.DAY_OF_MONTH);


                    DatePickerDialog datePickerDialog = new DatePickerDialog(eventData.this,
                            new DatePickerDialog.OnDateSetListener() {

                                @Override
                                public void onDateSet(DatePicker view, int year,
                                                      int monthOfYear, int dayOfMonth) {

                                    fesDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                                }
                            }, mYear, mMonth, mDay);
                    datePickerDialog.show();
                }
            }
        });


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                insertData();
                Toast.makeText(getApplicationContext(), "Details added", Toast.LENGTH_SHORT).show();
                fesName.setText("");
                fesDate.setText("");
                fesTime.setText("");
            }
        });
        fesTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v == fesTime) {

                    // Get Current Time
                    final Calendar c = Calendar.getInstance();
                    int mHour = c.get(Calendar.HOUR_OF_DAY);
                    int mMinute = c.get(Calendar.MINUTE);

                    // Launch Time Picker Dialog
                    TimePickerDialog timePickerDialog = new TimePickerDialog(eventData.this,
                            new TimePickerDialog.OnTimeSetListener() {

                                @Override
                                public void onTimeSet(TimePicker view, int hourOfDay,
                                                      int minute) {

                                    fesTime.setText(hourOfDay + ":" + minute);
                                }
                            }, mHour, mMinute, false);
                    timePickerDialog.show();
                }
            }
        });

    }

    private void insertData () {
        String name = fesName.getText().toString();
        String date = fesDate.getText().toString();
        String time = fesTime.getText().toString();

        if (TextUtils.isEmpty(name)) {
            fesName.setError("Enter FestivalName");
            return;
        }
        if (TextUtils.isEmpty(date)) {
            fesDate.setError("Enter FestivalDate");
            return;
        }
        if (TextUtils.isEmpty(time)) {
            fesTime.setError("Enter FestivalTime");
            return;
        }
        eventModel model = new eventModel();
        Number id = realm.where(eventModel.class).max("id");
        long nextId;
        if (id == null) {
            nextId = 1;
        } else {
            nextId = id.intValue() + 1;
        }
        model.setId(nextId);
        model.setEventName(name);
        model.setEventDate(date);
        model.setEventTime(time);
        Intent i = new Intent(getApplicationContext(), ViewEvents.class);
        startActivity(i);


        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm mReal) {
                mReal.copyToRealm(model);

            }
        });
    }
        @Override
        protected void onDestroy() {
            super.onDestroy();
            realm.close();
        }





}

