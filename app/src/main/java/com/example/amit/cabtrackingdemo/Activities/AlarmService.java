package com.example.amit.cabtrackingdemo.Activities;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.NotificationCompat;

import com.example.amit.cabtrackingdemo.R;

/**
 * Created by Amit on 29-12-2017.
 */

public class AlarmService  extends IntentService {
    private NotificationManager alarmNotificationManager;
    public AlarmService() {
        super("AlarmService");
    }
    @Override
    public void onHandleIntent(Intent intent) {
        sendNotification("Tracking start");
    }
    private void sendNotification(String msg) {
        alarmNotificationManager = (NotificationManager)this.getSystemService(Context.NOTIFICATION_SERVICE);

        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,new Intent(this, MainActivity.class), 0);

        NotificationCompat.Builder alamNotificationBuilder = (NotificationCompat.Builder) new NotificationCompat.Builder(
                this).setContentTitle("Oops Driver Track").setSmallIcon(R.mipmap.ic_launcher_round)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(msg))
                .setContentText(msg);
        alamNotificationBuilder.setAutoCancel(true);
        alamNotificationBuilder.setContentIntent(contentIntent);
        alarmNotificationManager.notify(1, alamNotificationBuilder.build());

    }

}//main class
