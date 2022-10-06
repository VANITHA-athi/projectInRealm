package com.example.projectinrealm.events;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.arch.core.executor.DefaultTaskExecutor;
import androidx.core.app.NotificationCompat;

import com.example.projectinrealm.R;

import java.util.Calendar;
import java.util.Date;

public class service extends Service {
    String name,Date,des;
    private static String TAG="service";

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        name=intent.getStringExtra("eventName");
        Date=intent.getStringExtra("eventDate");
        des=intent.getStringExtra("Description");
        Log.e(TAG,"Service is running..");
        sendBroadcast();
        return START_STICKY;
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
        NotificationManager managerCompat= (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder= new NotificationCompat.Builder(this,"NotifyEvents");
        @SuppressLint("RemoteViewLayout")
        RemoteViews remoteViews=new RemoteViews(this.getPackageName(),R.layout.activity_event_data);
        remoteViews.setTextViewText(R.id.eventNameId,name);
        remoteViews.setTextViewText(R.id.eventDateId,Date);
        remoteViews.setTextViewText(R.id.eventTimeId,des);
        builder.setSmallIcon(R.drawable.ic_baseline_notifications_24);
        builder.setOngoing(true);
        builder.setOnlyAlertOnce(true);
        builder.setDefaults(NotificationCompat.DEFAULT_SOUND);
        builder.setAutoCancel(true);
        builder.build().flags = Notification.FLAG_AUTO_CANCEL | Notification.PRIORITY_HIGH;
        builder.setContentIntent(pendingIntent);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId="channelId";
            NotificationChannel channel = new NotificationChannel(channelId,"Schedule Events", NotificationManager.IMPORTANCE_HIGH);
            channel.enableVibration(true);
            managerCompat.createNotificationChannel(channel);
            builder.setChannelId(channelId);
        }
        Notification notification = builder.build();
        managerCompat.notify(25, notification);

    }


}
