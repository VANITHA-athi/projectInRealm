package com.example.projectinrealm.events;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.projectinrealm.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;

public class ViewEvents extends AppCompatActivity {
    private Realm realm;
    private RecyclerView recyclerView;
    private eventAdapter adapter;
    List<eventModel> eventModelList;
    FloatingActionButton floatingActionButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_events);
        realm=Realm.getDefaultInstance();

        recyclerView=findViewById(R.id.RecyclerView);
        floatingActionButton=findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),eventData.class);
                startActivity(i);
            }
        });
        eventModelList=new ArrayList<>();
        prepareRecyclerView();}

    private void prepareRecyclerView() {
        eventModelList=realm.where(eventModel.class).findAll();
        adapter=new eventAdapter(eventModelList,this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

    }
}