package com.example.projectinrealm.events;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.projectinrealm.R;

public class myBoardcastReceiver extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {

        Intent i=new Intent(context,ViewEvents.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent=PendingIntent.getActivity(context,0,i,0);

        NotificationCompat.Builder builder= new NotificationCompat.Builder(context,"NotifyEvents")
                .setSmallIcon(R.drawable.ic_baseline_notifications_24)
                .setContentTitle("My Schedule")
                .setContentText("Alert for Events")
                .setDefaults(Notification.DEFAULT_SOUND)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent);

        NotificationManagerCompat managerCompat=NotificationManagerCompat.from(context);
        managerCompat.notify(52,builder.build());



    }
}
