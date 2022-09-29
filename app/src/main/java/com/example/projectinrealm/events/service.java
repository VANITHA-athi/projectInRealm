package com.example.projectinrealm.events;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
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
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    public void sendBroadcast(){
        
        Intent i=new Intent(this,myBoardcastReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, i, 0);
        Toast.makeText(this, "Alert Successful", Toast.LENGTH_SHORT).show();
        Calendar calendar=Calendar.getInstance();
        createAlarm(this,pendingIntent,calendar.getTimeInMillis());


    }
    private void createAlarm(Context context,PendingIntent intent,long timeInMilli) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if(alarmManager != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, timeInMilli, intent);
                } else {
                    alarmManager.setExact(AlarmManager.RTC_WAKEUP, timeInMilli,intent);
                }
        }

    }


}
