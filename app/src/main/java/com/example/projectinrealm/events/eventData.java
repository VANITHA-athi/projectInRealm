package com.example.projectinrealm.events;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;
import com.example.projectinrealm.R;
import java.util.Calendar;
import java.util.UUID;

import io.realm.Realm;

public class eventData extends AppCompatActivity {
        EditText fesName,fesDate,fesTime;
        Button save ;
        Realm realm;
        Calendar c;


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
                fesName.getText().clear();
                fesDate.setText("");
                fesTime.setText("");
                setAlarm();
                Intent intent=new Intent(getApplicationContext(),service.class);
                startService(intent);

            }
        });
        fesTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v == fesTime) {
                     c = Calendar.getInstance();
                    int mHour = c.get(Calendar.HOUR_OF_DAY);
                    int mMinute = c.get(Calendar.MINUTE);

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
    private void setAlarm() {
        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        Intent i=new Intent(this,myBoardcastReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, i, 0);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,c.getTimeInMillis(),AlarmManager.INTERVAL_DAY,pendingIntent);
        Toast.makeText(this, "Alert Successful", Toast.LENGTH_SHORT).show();
    }

    private void insertData () {
        String name = fesName.getText().toString();
        String date = fesDate.getText().toString();
        String time = fesTime.getText().toString();

        if (TextUtils.isEmpty(name)) {
            fesName.setError("Enter Festival Name");
            return;
        }
        if (TextUtils.isEmpty(date)) {
            fesDate.setError("Enter Festival Date");
            return;
        }
        if (TextUtils.isEmpty(time)) {
            fesTime.setError("Enter Festival Time");
            return;
        }
        eventModel model = new eventModel();

        //model.setId();
        model.setEventName(name);
        model.setEventDate(date);
        model.setEventTime(time);


        realm.executeTransaction(mReal -> mReal.copyToRealm(model));
        finish();


    }
        @Override
        protected void onDestroy() {
            super.onDestroy();
            realm.close();
        }





}

