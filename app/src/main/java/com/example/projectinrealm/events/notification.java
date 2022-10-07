package com.example.projectinrealm.events;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.projectinrealm.R;

public class notification extends AppCompatActivity {
    TextView title,date,des;
    Button off;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        title=findViewById(R.id.message);
        date=findViewById(R.id.date);
        des=findViewById(R.id.des);
        Bundle bundle=getIntent().getExtras();
        title.setText(bundle.getString("title"));
        date.setText(bundle.getString("Date"));
        des.setText(bundle.getString("description"));



    }
}