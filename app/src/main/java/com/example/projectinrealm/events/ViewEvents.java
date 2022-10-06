package com.example.projectinrealm.events;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;

import com.example.projectinrealm.Helper.logRegModel;
import com.example.projectinrealm.Helper.sharedPreference;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.projectinrealm.MainActivity;
import com.example.projectinrealm.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmResults;

public class ViewEvents extends AppCompatActivity {
    private Realm realm;
    private RecyclerView recyclerView;
    private eventAdapter adapter;
    List<eventModel> eventModelList;
    FloatingActionButton floatingActionButton;
    sharedPreference preference;
    SwipeRefreshLayout layout;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_events);
        realm = Realm.getDefaultInstance();
        preference = new sharedPreference(ViewEvents.this);
        userId = preference.getUserID();

        layout = findViewById(R.id.swiperefresh);
        layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                prepareRecyclerView();
                layout.setRefreshing(false);

            }
        });
        recyclerView = findViewById(R.id.RecyclerView);
        floatingActionButton = findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), eventData.class);
                startActivity(i);
            }
        });
        eventModelList = new ArrayList<>();
        prepareRecyclerView();
    }

    private void prepareRecyclerView() {

        Log.e("userid", userId);
        eventModelList = (List<eventModel>) realm.where(eventModel.class).equalTo("userId", userId).findAll();
        preference.setUserLogin(false);
        adapter = new eventAdapter(eventModelList, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
                preference.setLogin(false);
                preference.logout();
                preference.setUserID("");
              //  Realm realm = Realm.getDefaultInstance();
              //  realm.executeTransaction(realm1 -> realm1.deleteAll());
                finish();
        }
        return super.onOptionsItemSelected(item);
    }


}