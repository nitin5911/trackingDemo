package com.example.amit.cabtrackingdemo.Activities;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.NotificationCompat;

import com.example.amit.cabtrackingdemo.R;

import java.util.Calendar;

/**
 * Created by Amit on 30-12-2017.
 */

public class BackrunService extends Service {
    Handler service_handler;
    public  AlarmManager alarmManager;
    public  PendingIntent pendingIntent;
    private NotificationManager alarmNotificationManager;

    private Runnable runnable_obj=new Runnable() {
        @Override
        public void run() {
            trackbackground_method();
        }
    };
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
    }//end of sendNotification work

    private void trackbackground_method() {
        Calendar starttime_track = null, endtime_track = null, current_time = null;
        starttime_track = Calendar.getInstance();
        endtime_track = Calendar.getInstance();
        current_time = Calendar.getInstance();
        starttime_track.set(Calendar.HOUR, 10);
        starttime_track.set(Calendar.MINUTE,50);
        starttime_track.set(Calendar.SECOND,00);
        endtime_track.set(Calendar.HOUR,10);
        endtime_track.set(Calendar.MINUTE,53);
        endtime_track.set(Calendar.SECOND,00);
        if (current_time.getTime().before(endtime_track.getTime())) {
            if (starttime_track.before(current_time)) {
                MainActivity inst = MainActivity.instance();
                inst.setTrackButtonActive();
            }
        } else {
            MainActivity inst = MainActivity.instance();
            inst.setTrackButtonInActive();
        }
        service_handler.postDelayed(runnable_obj,5000);
    }//end of method

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }//end of onBind method

    @Override
    public void onCreate() {
        super.onCreate();
        service_handler=new Handler();
        trackbackground_method();
        sendNotification("Tracking start");
    }//end of onCreate method

    @Override
    public int onStartCommand(Intent intent,int flags,int startId) {
        onTaskRemoved(intent);
        service_handler=new Handler();
        trackbackground_method();
        return START_STICKY;
    }//end of onStartCommand method


    //for lower version we will use this method
    @Override
    public void onTaskRemoved(Intent rootIntent) {
        Intent restartServiceintent=new Intent(getApplicationContext(),this.getClass());
        restartServiceintent.setPackage(getPackageName());
        startService(restartServiceintent);
        super.onTaskRemoved(rootIntent);
    }//end of onTastRemoved method


}//end of BackrunService class
