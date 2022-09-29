package com.example.projectinrealm.events;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.projectinrealm.R;

public class myBoardcastReceiver extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {

        NotificationCompat.Builder builder= new NotificationCompat.Builder(context,"NotifyEvents")
                .setSmallIcon(R.drawable.ic_baseline_notifications_24)
                .setContentTitle("My Schedule")
                .setContentText("Alert for Events")
                .setDefaults(NotificationCompat.DEFAULT_SOUND)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        Intent intent1=new Intent(context,ViewEvents.class);
        TaskStackBuilder stackBuilder=TaskStackBuilder.create(context);
        stackBuilder.addParentStack(ViewEvents.class);
        PendingIntent pendingIntent=PendingIntent.getActivity(context,0,intent1,PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);

        NotificationManager managerCompat= (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        managerCompat.notify(52,builder.build());

        Toast.makeText(context, "Service Running", Toast.LENGTH_SHORT).show();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Alert for Events";
            String description = "Alert for Events";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel("NotifyEvents", name, importance);
            channel.setDescription(description);
            NotificationManager manager= (NotificationManager)context.getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
        else  restartSchedule(context);

    }

    private void restartSchedule(Context context) {
        Intent i=new Intent(context,service.class);
        context.startService(i);

    }
}
