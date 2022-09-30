package com.example.projectinrealm.events;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;
import com.example.projectinrealm.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import io.realm.Realm;


public class eventData extends AppCompatActivity {
        EditText fesName,fesDate,fesTime;
        Button save ;
        Realm realm;
        Calendar c;
    private long id;
    private String date,time;
    String name,Date,des;

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent i=new Intent(getApplicationContext(),ViewEvents.class);
        startActivityForResult(i,0);
        finish();
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        realm = Realm.getDefaultInstance();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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

                                    date=(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                                    timePicker();

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
                fesName.getText().clear();
                fesDate.getText().clear();
                fesTime.getText().clear();
                try {
                    setAlarm();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    private void setAlarm() throws ParseException {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent i = new Intent(this, myBoardcastReceiver.class);
        i.putExtra("eventName", name);
        i.putExtra("eventDate", Date);
        i.putExtra("Description", des);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, i, 0);
        String dateAndTime = Date;
        DateFormat format = new SimpleDateFormat("d-M-yyyy  hh:mm");
        try {
            java.util.Date dt = format.parse(dateAndTime);
            alarmManager.set(AlarmManager.RTC_WAKEUP, dt.getTime(), pendingIntent);
            Toast.makeText(this, "Alert Successful", Toast.LENGTH_SHORT).show();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Intent intent = new Intent(getApplicationContext(), service.class);
        startService(intent);


    }
    public void timePicker(){
        c = Calendar.getInstance();
        int mHour = c.get(Calendar.HOUR_OF_DAY);
        int mMinute = c.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(eventData.this,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {

                        fesDate.setText(date+" "+hourOfDay + ":" + minute);

                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();

    }
    public void insertData() {
        name = fesName.getText().toString();
        Date = fesDate.getText().toString();
        des = fesTime.getText().toString();

        if (TextUtils.isEmpty(name)) {
            fesName.setError("Enter Event Name");
            return;
        }
        if (TextUtils.isEmpty(Date)) {
            fesDate.setError("Enter Event Date and Time");
            return;
        }
        if (TextUtils.isEmpty(des)) {
            fesTime.setError("Enter Description");
            return;
        }
        eventModel model = new eventModel();
        id=UUID.randomUUID().getLeastSignificantBits();
        model.setId(id);
        model.setEventName(name);
        model.setEventDate(Date);
        model.setDescription(des);

        realm.executeTransaction(mReal -> mReal.copyToRealm(model));
        finish();


    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }


}

