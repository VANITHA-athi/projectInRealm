package com.example.projectinrealm.events;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.projectinrealm.R;

import io.realm.Realm;

public class UpdateEvent extends AppCompatActivity {
    private EditText upName,upDate,upTime;
    private Button upBtn,delBtn;
    private String FestivalName,FestivalDate,FestivalTime;
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

        FestivalName=getIntent().getStringExtra("eventName");
        FestivalDate=getIntent().getStringExtra("eventDate");
        FestivalTime=getIntent().getStringExtra("eventTime");
        id=getIntent().getLongExtra("id",0);

        upName.setText(FestivalName);
        upDate.setText(FestivalDate);
        upTime.setText(FestivalTime);
        upBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=upName.getText().toString();
                String date=upDate.getText().toString();
                String time=upTime.getText().toString();

                if (TextUtils.isEmpty(name)){
                    upName.setError("Update Festival name");
                    return;
                }if (TextUtils.isEmpty(date)){
                    upDate.setError("Update Festival Date");
                    return;
                }if (TextUtils.isEmpty(time)){
                    upTime.setError("Update Festival time");
                }else {
                    final eventModel model = realm.where(eventModel.class).equalTo("id", id).findFirst();
                    updateDetails(model, FestivalName, FestivalDate, FestivalTime);
                }
                Toast.makeText(getApplicationContext(), "Details Updated", Toast.LENGTH_SHORT).show();
                Intent i=new Intent(getApplicationContext(),ViewEvents.class);
                startActivity(i);
                finish();
            }
        });
        delBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteDetails(id);
                Toast.makeText(getApplicationContext(), "Data deleted!", Toast.LENGTH_SHORT).show();
                Intent i=new Intent(getApplicationContext(),ViewEvents.class);
                startActivity(i);
                finish();
            }
        });
    }

    private void deleteDetails(long id) {
        eventModel model=realm.where(eventModel.class).equalTo("id",id).findFirst();

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                model.deleteFromRealm();
            }
        });
    }

    private void updateDetails(eventModel model, String FestivalName, String FestivalDate, String FestivalTime) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                model.setEventName(FestivalName);
                model.setEventDate(FestivalDate);
                model.setEventTime(FestivalTime);

            }
        });



    }
}