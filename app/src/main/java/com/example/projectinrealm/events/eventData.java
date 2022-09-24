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

public class eventData extends AppCompatActivity {
        EditText fesName,fesDate,fesTime;
        Button save ;
        Realm realm;
    private String FestivalName,FestivalDate,FestivalTime;

    @Override
protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         realm = Realm.getDefaultInstance();
        setContentView(R.layout.activity_event_data);
        fesName = findViewById(R.id.editTextTextPersonName2);
        fesDate = findViewById(R.id.editTextTextPersonName3);
        fesTime = findViewById(R.id.editTextTextPersonName4);
        save = findViewById(R.id.save);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = fesName.getText().toString();
                String date = fesDate.getText().toString();
                String time = fesTime.getText().toString();

                if (TextUtils.isEmpty(name)) {
                    fesName.setError("Enter FestivalName");
                    return;
                } if (TextUtils.isEmpty(date)) {
                    fesDate.setError("Enter FestivalDate");
                    return;
                } if (TextUtils.isEmpty(time)) {
                    fesTime.setError("Enter FestivalTime");
                    return;
                }
                insertData(FestivalName,FestivalDate,FestivalTime);
                Toast.makeText(getApplicationContext(), "Details added", Toast.LENGTH_SHORT).show();
                fesName.setText("");
                fesDate.setText("");
                fesTime.setText("");
            }
        });

    }

    private void insertData (String FestivalName, String FestivalDate, String FestivalTime){
            eventModel model = new eventModel();
            Number id = realm.where(eventModel.class).max("id");
            long nextId;
            if (id == null) {
                nextId = 1;
            } else {
                nextId = id.intValue()+ 1;
            }
            model.setId(nextId);
            model.setEventName(FestivalName);
            model.setEventDate(FestivalDate);
            model.setEventTime(FestivalTime);
            Intent i=new Intent(getApplicationContext(),ViewEvents.class);
             startActivity(i);


            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm mReal) {
                    mReal.copyToRealm(model);

                }
            });




    }
}

