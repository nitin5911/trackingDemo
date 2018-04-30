package com.example.amit.cabtrackingdemo.Activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Amit on 01-01-2018.
 */

public class BackrunBroadcastrun extends BroadcastReceiver{

    @Override
    public void onReceive(Context context, Intent intent) {
        context.startService(new Intent(context, BackrunService.class));
    }//end of onReceiver method
}//end of main class
