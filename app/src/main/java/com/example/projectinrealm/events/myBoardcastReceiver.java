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
        Bundle bundle=intent.getExtras();
        String text=bundle.getString("eventName");
        String date=bundle.getString("eventDate");
        String des=bundle.getString("Description");

       Intent intent1=new Intent(context,ViewEvents.class);
        intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
      //  intent1.putExtra("title",text);
       // intent1.putExtra("Date",date);
     //   intent1.putExtra("description",des);
       PendingIntent pendingIntent=PendingIntent.getActivity(context,0,intent1,PendingIntent.FLAG_IMMUTABLE);
        NotificationManager managerCompat= (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
       NotificationCompat.Builder builder= new NotificationCompat.Builder(context,"NotifyEvents");


        /*NotificationCompat.Builder builder = new NotificationCompat.Builder(context,"")
                .setSmallIcon(R.drawable.ic_baseline_notifications_24)
                .setContentTitle(text)
                .setContentText(date+"--"+des)
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        Notification notification = builder.build();
        managerCompat.notify(67,notification);*/


         RemoteViews remoteViews=new RemoteViews(context.getPackageName(),R.layout.activity_notification);
         remoteViews.setImageViewResource(R.id.icon,R.drawable.ic_baseline_notifications_24);
         remoteViews.setTextViewText(R.id.message,text);
         remoteViews.setTextViewText(R.id.date,date);
         remoteViews.setTextViewText(R.id.des,des);
         builder.setOnlyAlertOnce(true);
         builder.setSmallIcon(R.drawable.ic_baseline_notifications_24);
         builder.setDefaults(NotificationCompat.DEFAULT_SOUND);
         builder.setAutoCancel(true);
         builder.build().flags = Notification.FLAG_AUTO_CANCEL | Notification.PRIORITY_HIGH;
         builder.setContentIntent(pendingIntent);
         builder.setCustomContentView(remoteViews);

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
