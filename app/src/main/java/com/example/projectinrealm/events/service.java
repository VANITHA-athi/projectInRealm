package com.example.projectinrealm.events;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.Calendar;

public class service extends Service {
    private static String TAG="service";

    @Override
    public void onCreate() {
        sendBroadcast();
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(TAG,"Service is running..");
        return service.START_STICKY;
    }

    @Override
    public void onDestroy() {
        sendBroadcast();
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    @Override
    public void onTaskRemoved(Intent rootIntent) {
        sendBroadcast();
        super.onTaskRemoved(rootIntent);
    }
    public void sendBroadcast(){
        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        Intent i=new Intent(this,myBoardcastReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, i, 0);
        Toast.makeText(this, "Alert Successful", Toast.LENGTH_SHORT).show();

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 5);
        if (alarmManager != null)
        {
            //System.currentTimeMillis() + (i * 100),
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 6000000, pendingIntent);
        }
    }


}
