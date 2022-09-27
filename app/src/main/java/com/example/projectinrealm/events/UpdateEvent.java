package com.example.projectinrealm.events;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
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

import io.realm.Realm;

public class UpdateEvent extends AppCompatActivity {
    private EditText upName,upDate,upTime;
    private Button upBtn,delBtn;
    private String festivalName,festivalDate,festivalTime;
    private long id;
    Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_event);
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
                                    upDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                                }
                            }, mYear, mMonth, mDay);
                    datePickerDialog.show();
                }
            }
        });
        upTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v == upTime) {
                    final Calendar c = Calendar.getInstance();
                    int mHour = c.get(Calendar.HOUR_OF_DAY);
                    int mMinute = c.get(Calendar.MINUTE);
                    TimePickerDialog timePickerDialog = new TimePickerDialog(UpdateEvent.this,
                            new TimePickerDialog.OnTimeSetListener() {

                                @Override
                                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                    upTime.setText(hourOfDay + ":" + minute);
                                }
                            }, mHour, mMinute, false);
                    timePickerDialog.show();
                }
            }
        });

        festivalName=getIntent().getStringExtra("eventName");
        festivalDate=getIntent().getStringExtra("eventDate");
        festivalTime=getIntent().getStringExtra("eventTime");
        id=getIntent().getLongExtra("id",0);

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
                    upName.setError("Update Festival name");
                    return;
                }if (TextUtils.isEmpty(festivalDateS)){
                    upDate.setError("Update Festival Date");
                    return;
                }if (TextUtils.isEmpty(festivalTimeS)){
                    upTime.setError("Update Festival time");
                    return;
                }

                 final eventModel model = realm.where(eventModel.class).equalTo("id", id).findFirst();
                updateDetails(model,festivalNameS,festivalDateS,festivalTimeS);
                Toast.makeText(getApplicationContext(), "Event Updated.", Toast.LENGTH_SHORT).show();
                finish();
                }
        });

        delBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteDetails(id);
                Toast.makeText(getApplicationContext(), "Data deleted!", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void deleteDetails(long id) {
        eventModel model=realm.where(eventModel.class).equalTo("id",id).findFirst();

        realm.executeTransaction(realm -> model.deleteFromRealm());

        finish();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }

    private void updateDetails(eventModel model, String festivalNameS, String festivalDateS, String festivalTimeS) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                model.setEventName(festivalNameS);
                model.setEventDate(festivalDateS);
                model.setEventTime(festivalTimeS);
                realm.copyToRealmOrUpdate(model);

            }
        });
    }

}