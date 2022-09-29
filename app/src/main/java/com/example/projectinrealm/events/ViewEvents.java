package com.example.projectinrealm.events;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import com.example.projectinrealm.Helper.sharedPreference;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.projectinrealm.MainActivity;
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
    sharedPreference preference;
    SwipeRefreshLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_events);
        realm=Realm.getDefaultInstance();
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        layout=findViewById(R.id.swiperefresh);
        layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                prepareRecyclerView();
                layout.setRefreshing(false);

            }
        });
       preference=new sharedPreference(ViewEvents.this);
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
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.main_menu,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
                preference.setLogin(false);
                preference.logout();
                finish();
        }
        return super.onOptionsItemSelected(item);
    }




}