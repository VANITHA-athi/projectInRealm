package com.example.projectinrealm.events;
import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.Toast;
import androidx.core.app.NotificationCompat;
import com.example.projectinrealm.R;

public class myBoardcastReceiver extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
       String text = new String(),date = new String(),des = new String();
       Intent intent1=new Intent(context,eventAdapter.class);
        intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent1.putExtra("eventName",text);
        intent1.putExtra("eventDate",date);
        intent1.putExtra("Description",des);
        PendingIntent pendingIntent=PendingIntent.getActivity(context,0,intent1,PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationManager managerCompat= (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder= new NotificationCompat.Builder(context,"NotifyEvents");

        @SuppressLint("RemoteViewLayout")
        RemoteViews remoteViews=new RemoteViews(context.getPackageName(),R.layout.activity_event_data);
        remoteViews.setTextViewText(R.id.eventNameId,text);
        remoteViews.setTextViewText(R.id.eventDateId,date);
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
            NotificationChannel channel = new NotificationChannel(channelId,"Schedule Events",NotificationManager.IMPORTANCE_HIGH);
            channel.enableVibration(true);
            managerCompat.createNotificationChannel(channel);
            builder.setChannelId(channelId);
        }
        Notification notification = builder.build();
        managerCompat.notify(25, notification);




    }


}
