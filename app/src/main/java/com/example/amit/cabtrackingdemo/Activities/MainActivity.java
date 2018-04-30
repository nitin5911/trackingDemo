package com.example.amit.cabtrackingdemo.Activities;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.amit.cabtrackingdemo.R;
import com.example.amit.cabtrackingdemo.Trackingstatus_inteface;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity{

   public TextView clickmap,currentlatlongtext,starttimetext,endtimetext;
   public ImageView starttimeimg,endtimeimg;
    public  EditText enteraddrs,enteraddrs2;
    public  Button trackclickmap,getlocationbtn,getcurlocationbtn,getgivenlocationbtn,getdistdurationbtn;
    public String useroriginaddress,userdestaddress,starttimestr_value,endtimestr_value;
    public  String userlatlong=null,tM;
    public  double latitude,longtitude;
    public  double originlat1,originlng1,destlat2,destlng2;
    public  LocationManager locationManager_obj;
    public  static final int REQUEST_LOCATION=1;
    public SharedPreferences.Editor latlong_preference;
    public  AlarmManager alarmManager;
    public  PendingIntent pendingIntent;
    public static MainActivity inst;
    public int mHour,mMinute;
    public  Handler track_time_handler = new Handler();
    Trackingstatus_inteface trackinginterface_obj;

    public static MainActivity instance() {
        return inst;
    }//end of constructor

    @Override
    public void onStart() {
        super.onStart();
        inst = this;
    }//end of onStart method


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        starttimetext=(TextView)findViewById(R.id.starttimeid); 
        endtimetext=(TextView)findViewById(R.id.endtimeid);
        starttimeimg=(ImageView)findViewById(R.id.starttimeiconid);
        endtimeimg=(ImageView)findViewById(R.id.endtimeiconid);
        clickmap=(TextView)findViewById(R.id.clickformapid);
        trackclickmap=(Button)findViewById(R.id.trackmapclickid);
        currentlatlongtext=(TextView)findViewById(R.id.currentlatlongtextid);
        enteraddrs=(EditText)findViewById(R.id.addrsedttextid);
        enteraddrs2=(EditText)findViewById(R.id.addrsedttextid2);
        getlocationbtn=(Button)findViewById(R.id.getlocationbtnid);
        getcurlocationbtn=(Button)findViewById(R.id.getcurlocationbtnid);
        getgivenlocationbtn=(Button)findViewById(R.id.getgivenlocationbtnid);
        getdistdurationbtn=(Button)findViewById(R.id.getdurationbtnid);
            locationManager_obj=(LocationManager)getSystemService(Context.LOCATION_SERVICE);
        alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);

        getlocationbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                useroriginaddress=enteraddrs.getText().toString();
                userdestaddress=enteraddrs2.getText().toString();
                try {
                    getLocationFromAddress(getApplicationContext(),useroriginaddress,userdestaddress);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });  //end of get location button work part

        getcurlocationbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getCurrentLocation();

            }//end of onClick method
        });  //end of get current location work part

        getgivenlocationbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                useroriginaddress=enteraddrs.getText().toString();
                userdestaddress=enteraddrs2.getText().toString();
                try {
                    getTrackLocationFromAddress(getApplicationContext(),useroriginaddress,userdestaddress);
                } catch (IOException e) {
                    e.printStackTrace();
                }            }
        }); //end of get address from given lat long work part

        clickmap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent obj=new Intent(getApplicationContext(),Googlemap.class);
                startActivity(obj);
            }
        });

        //track button Inactive bydefault
        trackclickmap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent obj=new Intent(getApplicationContext(),TrackingPath.class);
                startActivity(obj);
            }
        });

//        Intent myIntent = new Intent(getApplicationContext(),BackrunService.class);
//        myIntent.putExtra("startservicevalue","1");
//        startService(myIntent);
//        trackinginterface_obj=new MainActivity();
//        trackinginterface_obj.trackingstarttime_method();
        //start service of Tracking

//        Intent broad_intent = new Intent(getApplicationContext(), BackrunService.class);
//        boolean alarmRunning = (PendingIntent.getBroadcast(MainActivity.this, 0,broad_intent,PendingIntent.FLAG_NO_CREATE) != null);
//        if(alarmRunning == false) {
//            PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, broad_intent, 0);
//            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
//            alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime(), 1800000, pendingIntent);
//        }
//        startService(new Intent(getApplicationContext(), BackrunService.class));
        startService(new Intent(getApplicationContext(), BackrunService.class));
//        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(new Intent(getApplicationContext(), BackrunBroadcastrun.class));

    }//end of onCreate method

//    public void trackingstarttime_method() {
//            Calendar starttime_track = null, endtime_track = null, current_time = null;
//            starttimetext=findViewById(R.id.starttimeid);
//            endtimetext=findViewById(R.id.endtimeid);
//            trackclickmap=findViewById(R.id.trackmapclickid);
//            starttime_track = Calendar.getInstance();
//            endtime_track = Calendar.getInstance();
//            current_time = Calendar.getInstance();
//            starttime_track.set(Calendar.HOUR, 2);
//            starttime_track.set(Calendar.MINUTE, 20);
//            starttime_track.set(Calendar.SECOND, 00);
//            endtime_track.set(Calendar.HOUR, 2);
//            endtime_track.set(Calendar.MINUTE, 23);
//            endtime_track.set(Calendar.SECOND, 00);
//            starttimetext.setText(starttime_track.getTime().toString());
//            endtimetext.setText(endtime_track.getTime().toString());
//            if (current_time.before(endtime_track)) {
//                if (starttime_track.before(endtime_track)) {
//                    trackclickmap.setEnabled(true);
//                    Intent myIntent = new Intent(getApplicationContext(), AlarmReceiver.class);
//                    pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, myIntent, 0);
//                    alarmManager.set(AlarmManager.RTC, starttime_track.getTimeInMillis(), pendingIntent);
//                }
//            } else {
//                Toast.makeText(getApplicationContext(), "Tracking timeout", Toast.LENGTH_SHORT).show();
//                trackclickmap.setEnabled(false);
//            }
//        }//end of method
//

    public void setTrackButtonActive() {
        trackclickmap.setEnabled(true);
    }//end of setAlarmText method

    public void setTrackButtonInActive() {
        trackclickmap.setEnabled(false);
    }//end of setAlarmText method

    public void getLocationFromAddress(Context context, String useroriginaddr, String userdestaddr) throws IOException {
        if(ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION)!=PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_LOCATION);
        }else{
            Geocoder coder = new Geocoder(context, Locale.getDefault());
            List<Address> originlist_obj,destlist_obj;
           // originlist_obj=coder.getFromLocationName(useroriginaddr, 5);
            destlist_obj=coder.getFromLocationName(userdestaddr, 5);
            if(destlist_obj.size()>0){
//                originlat1=originlist_obj.get(0).getLatitude();
//                originlng1= originlist_obj.get(0).getLongitude();
                destlat2= destlist_obj.get(0).getLatitude();
                destlng2= destlist_obj.get(0).getLongitude();

                latlong_preference=getSharedPreferences("latlongpreference", Context.MODE_PRIVATE).edit();
                latlong_preference.putString("originlat", String.valueOf(originlat1));
                latlong_preference.putString("originlng", String.valueOf(originlng1));
                latlong_preference.putString("destlat", String.valueOf(destlat2));
                latlong_preference.putString("destlng", String.valueOf(destlng2));
                latlong_preference.commit();
                Toast.makeText(context, "origin and destination lat long is "+originlat1+" "+originlng1+" "+destlat2+" "+destlng2, Toast.LENGTH_SHORT).show();
            }
        }//end of if-else condition
    }//end of method

    public void getTrackLocationFromAddress(Context context, String useroriginaddr, String userdestaddr) throws IOException {
        if(ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION)!=PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_LOCATION);
        }else{
            Geocoder coder = new Geocoder(context, Locale.getDefault());
            List<Address> originlist_obj,destlist_obj;
            originlist_obj=coder.getFromLocationName(useroriginaddr, 5);
            destlist_obj=coder.getFromLocationName(userdestaddr, 5);
            if(destlist_obj.size()>0){
                originlat1=originlist_obj.get(0).getLatitude();
                originlng1= originlist_obj.get(0).getLongitude();
                destlat2= destlist_obj.get(0).getLatitude();
                destlng2= destlist_obj.get(0).getLongitude();

                latlong_preference=getSharedPreferences("latlongpreference", Context.MODE_PRIVATE).edit();
                latlong_preference.putString("originlat", String.valueOf(originlat1));
                latlong_preference.putString("originlng", String.valueOf(originlng1));
                latlong_preference.putString("destlat", String.valueOf(destlat2));
                latlong_preference.putString("destlng", String.valueOf(destlng2));
                latlong_preference.commit();
                Toast.makeText(context, "origin and destination lat long is "+originlat1+" "+originlng1+" "+destlat2+" "+destlng2, Toast.LENGTH_SHORT).show();
            }
        }//end of if-else condition
    }//end of method

    public void getCurrentLocation(){
        if(ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION)!=PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_LOCATION);
        }else{
            Location location_obj=locationManager_obj.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            if(location_obj!=null){
                originlat1=location_obj.getLatitude();
                originlng1=location_obj.getLongitude();
                currentlatlongtext.setText(originlat1+" "+originlng1);
                Toast.makeText(getApplicationContext(), "location is "+originlat1+" " +originlng1, Toast.LENGTH_SHORT).show();
            }else{
                currentlatlongtext.setText("Unable to find current location");
                Toast.makeText(getApplicationContext(), "Current location is "+originlat1+" " +originlng1, Toast.LENGTH_SHORT).show();
            }
        }//end of if-else condition
    }//end of method

    public void getAddressFromgivenLocation(Context applicationContext, double latitude, double longitude) throws IOException {
      String fulladdress=null;
        if(ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION)!=PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_LOCATION);
        }else{
            Geocoder coder = new Geocoder(applicationContext, Locale.getDefault());
            List<Address> list_obj;
            list_obj=coder.getFromLocation(latitude,longitude,1);
            if(list_obj.size()>0){
                fulladdress=list_obj.get(0).getAddressLine(0);
                currentlatlongtext.setText(fulladdress);
            }
        }//end of if-else condition
    }//end of method

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case REQUEST_LOCATION:
                getCurrentLocation();
                break;
        }//end of switch case statement

    }//end of method

}//end of main class
