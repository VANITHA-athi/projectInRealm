package com.example.projectinrealm.events;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

import io.realm.Realm;

public class UpdateEvent extends AppCompatActivity {
    private EditText upName,upDate,upTime;
    private Button upBtn,delBtn;
    private String festivalName,festivalDate,festivalTime;
    private String id;
    private String userId;
    private  int calYear,calMonthOfYear,calDayOfMonth;
    Realm realm;
    private String a;

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
        setContentView(R.layout.activity_update_event);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        realm=Realm.getDefaultInstance();
        upName=findViewById(R.id.updateName);
        upDate=findViewById(R.id.updateDate);
        upTime=findViewById(R.id.updateTime);
        upBtn=findViewById(R.id.update);
        delBtn=findViewById(R.id.delete);

        upDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v == upDate) {
                    final Calendar c = Calendar.getInstance();
                    int mYear = c.get(Calendar.YEAR);
                    int mMonth = c.get(Calendar.MONTH);
                    int mDay = c.get(Calendar.DAY_OF_MONTH);
                    DatePickerDialog datePickerDialog = new DatePickerDialog(UpdateEvent.this,
                            new DatePickerDialog.OnDateSetListener() {

                                @Override
                                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                    Calendar newDate = Calendar.getInstance();

                                    calYear = year;
                                    calMonthOfYear = monthOfYear;
                                    calDayOfMonth = dayOfMonth;

                                    newDate.set(year, monthOfYear, dayOfMonth);


                                    SimpleDateFormat formater = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);

                                    a= formater.format(newDate.getTime());

                                   // a=(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                                  //  upDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                                    timePicker();
                                }
                            }, mYear, mMonth, mDay);
                    datePickerDialog.show();
                }
            }
        });

        festivalName=getIntent().getStringExtra("eventName");
        festivalDate=getIntent().getStringExtra("eventDate");
        festivalTime=getIntent().getStringExtra("Description");
        id=getIntent().getStringExtra("id");
        userId=getIntent().getStringExtra("userId");

        upName.setText(festivalName);
        upDate.setText(festivalDate);
        upTime.setText(festivalTime);
        upBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String festivalNameS=upName.getText().toString();
                String festivalDateS=upDate.getText().toString();
                String festivalTimeS=upTime.getText().toString();

                if (TextUtils.isEmpty(festivalNameS)){
                    upName.setError("Update Event name");
                    return;
                }if (TextUtils.isEmpty(festivalDateS)){
                    upDate.setError("Update Event Date and Time");
                    return;
                }if (TextUtils.isEmpty(festivalTimeS)){
                    upTime.setError("Update Description");
                    return;
                }

                final eventModel model = realm.where(eventModel.class).equalTo("id", id).equalTo("userId",userId).findFirst();
                updateDetails(model,festivalNameS,festivalDateS,festivalTimeS,userId);
                Toast.makeText(getApplicationContext(), "Event Updated.", Toast.LENGTH_SHORT).show();
                setAlarm(festivalNameS,festivalDateS,festivalTimeS);
                finish();
                }
        });

        delBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteDetails(id);
                Toast.makeText(getApplicationContext(), "Data deleted!", Toast.LENGTH_SHORT).show();
                finish();

            }
        });
    }

    private void timePicker() {
        final Calendar c = Calendar.getInstance();
        int mHour = c.get(Calendar.HOUR_OF_DAY);
        int mMinute = c.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(UpdateEvent.this,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        Calendar newDate = Calendar.getInstance();
                        newDate.set(calYear, calMonthOfYear, calDayOfMonth, hourOfDay, minute);


                        SimpleDateFormat formater = new SimpleDateFormat("dd-MM-yyyy hh:mm a", Locale.ENGLISH);
                        upDate.setText(formater.format(newDate.getTime()));
                        //upDate.setText(a+" "+hourOfDay + ":" + minute);
                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();
    }




    private void deleteDetails(String id) {
        eventModel model=realm.where(eventModel.class).equalTo("id",id).findFirst();
        realm.executeTransaction(realm -> model.deleteFromRealm());
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }
    private String setAlarm(String name, String Date, String des) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent i = new Intent(this, myBoardcastReceiver.class);
        Bundle bundle=new Bundle();
        bundle.putString("eventName",name);
        bundle.putString("eventDate",Date);
        bundle.putString("Description",des);
        i.putExtra("eventName", name);
        i.putExtra("eventDate", Date);
        i.putExtra("Description", des);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, i, 0);
        String _date = "yyyy-MM-dd hh:mm a";
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy hh:mm a", Locale.ENGLISH);
        SimpleDateFormat format1 = new SimpleDateFormat("dd-MM-yyyy hh:mm a", Locale.ENGLISH);
        try {
            java.util.Date dt = format.parse(Date);
            assert dt != null;
            alarmManager.set(AlarmManager.RTC_WAKEUP, dt.getTime(), pendingIntent);
            Toast.makeText(this, "Alert Successful", Toast.LENGTH_SHORT).show();
            return format1.format(dt);
        } catch (ParseException e) {
            e.printStackTrace();

            Intent intent = new Intent(getApplicationContext(), ViewEvents.class);
            startActivity(intent);
            return "";

        }

    }

    private void updateDetails(eventModel model, String festivalNameS, String festivalDateS, String festivalTimeS,String userId) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                model.setEventName(festivalNameS);
                model.setEventDate(festivalDateS);
                model.setDescription(festivalTimeS);
                model.setUserId(userId);
                realm.copyToRealmOrUpdate(model);

            }
        });
    }

}