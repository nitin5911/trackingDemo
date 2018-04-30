package com.example.amit.cabtrackingdemo.Activities;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.amit.cabtrackingdemo.POJO_Classes.AllmapdataPOJO1;
import com.example.amit.cabtrackingdemo.R;
import com.example.amit.cabtrackingdemo.Retrofit_files.RetrofitInstancefile;
import com.example.amit.cabtrackingdemo.Retrofit_files.RetrofitintrfcFile;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Googlemap extends AppCompatActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks,
        View.OnClickListener, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    SharedPreferences latlong_preference;
    SharedPreferences.Editor mainlatlong_preference;
    double originlat;
    double originlng;
    double destlat;
    double destlng;
    String destlocation, originlocation, API_KEY_VALUE = null;
    ArrayList<String> duration_value = new ArrayList<>();
    ArrayList<String> distance_value = new ArrayList<>();
    RetrofitintrfcFile retrofitinterfacefile_obj;
    TextView durationtext, distancetext;
    ImageButton navigationbtn, curlocationfocusbtn;
    GoogleMap mGoogleMap_obj;
    LocationManager locationManager_obj;
    static final int REQUEST_LOCATION = 1;
    GoogleApiClient mGoogleApiClient_obj;
    LocationRequest mLocationRequest;
    Polyline polyline;
    UiSettings uisettings_obj;
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_googlemap);
        distancetext = (TextView) findViewById(R.id.distanceid);
        durationtext = (TextView) findViewById(R.id.durationid);
        navigationbtn = (ImageButton) findViewById(R.id.navigationbtnid);
        curlocationfocusbtn = (ImageButton) findViewById(R.id.focusmarkertnid);

        //SharePreference work
        mainlatlong_preference = getSharedPreferences("latlongpreference", Context.MODE_PRIVATE).edit();
        latlong_preference = getSharedPreferences("latlongpreference", Context.MODE_PRIVATE);
        originlat = Double.parseDouble(latlong_preference.getString("originlat", "null"));
        originlng = Double.parseDouble(latlong_preference.getString("originlng", "null"));
        destlat = Double.parseDouble(latlong_preference.getString("destlat", "null"));
        destlng = Double.parseDouble(latlong_preference.getString("destlng", "null"));
        originlocation = originlat + " " + originlng;
        destlocation = destlat + "," + destlng;
        API_KEY_VALUE = getString(R.string.google_maps_key);
        //Retrofit file work
        retrofitinterfacefile_obj = RetrofitInstancefile.retrofit_method().create(RetrofitintrfcFile.class);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        navigationbtn.setOnClickListener(this);
        curlocationfocusbtn.setOnClickListener(this);
    }//end of onCreate method

//    private void googleAPIdist_durtndata_method() {
//        Call<DistancenddurationPOJO1> logincall_obj = retrofitinterfacefile_obj.distdurtninterface_method(originlocation, destlocation, API_KEY_VALUE);
//        logincall_obj.enqueue(new Callback<DistancenddurationPOJO1>() {
//            @Override
//            public void onResponse(Call<DistancenddurationPOJO1> call, Response<DistancenddurationPOJO1> response) {
//                if (response.body() != null) {
//                    for (int dis = 0; dis < response.body().getRows().size(); dis++) {
//                        for (int dispos = 0; dispos < response.body().getRows().get(dis).getElements().size(); dispos++) {
//                            distance_value = response.body().getRows().get(dis).getElements().get(dispos).getDistance().getText();
//                            duration_value = response.body().getRows().get(dis).getElements().get(dispos).getDuration().getText();
//                        }
//                    }//end of for loop
//                    Toast.makeText(Googlemap.this, "distance and duration API hit done " + distance_value + duration_value, Toast.LENGTH_SHORT).show();
//                    distancetext.setText(distance_value);
//                    durationtext.setText(duration_value);
//                }//end of outer if condition
//            }//end of onResponse method
//
//            @Override
//            public void onFailure(Call<DistancenddurationPOJO1> call, Throwable t) {
//                Toast.makeText(Googlemap.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
//            }//end of onFailure method
//        });
//    }//end of distance duration data method

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient_obj = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient_obj.connect();
    }//end of buildGoogleApiClient method

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap_obj = googleMap;
        mGoogleMap_obj.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        //Google map UI setting work
        uisettings_obj = mGoogleMap_obj.getUiSettings();
        uisettings_obj.setZoomControlsEnabled(true);
        uisettings_obj.setAllGesturesEnabled(true);
        uisettings_obj.setCompassEnabled(true);
        uisettings_obj.setMapToolbarEnabled(false);
        uisettings_obj.setMyLocationButtonEnabled(false);
        uisettings_obj.isCompassEnabled();
        uisettings_obj.setIndoorLevelPickerEnabled(true);

        //Initialize Google Play Services
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                //Location Permission already granted
                buildGoogleApiClient();
                markeroperations_method();
                allgoogleapidata_method();
                mGoogleMap_obj.setMyLocationEnabled(true);
            } else {
                //Request Location Permission
                checkLocationPermission();
            }
        }
    }//end of onMapReady method

    //  Here give the permission to access the map location
    private void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                new AlertDialog.Builder(this)
                        .setTitle("Location Permission Needed")
                        .setMessage("This app needs the Location permission, please accept to use location functionality")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                ActivityCompat.requestPermissions(Googlemap.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_LOCATION);
                            }
                        })
                        .create()
                        .show();
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_LOCATION);
            }
        }
    }//end of checkLocationPermission method

    public void allgoogleapidata_method() {
        Call<AllmapdataPOJO1> alldatacall_obj = retrofitinterfacefile_obj.allapidataintrfc_method(originlocation, destlocation, API_KEY_VALUE, true);
        alldatacall_obj.enqueue(new Callback<AllmapdataPOJO1>() {
            @Override
            public void onResponse(Call<AllmapdataPOJO1> call, Response<AllmapdataPOJO1> response) {
                if (response.body() != null) {
                    //Distance and duration Arraylist work
                    for (int dis = 0; dis < response.body().getRoutes().size(); dis++) {
                        for (int dispos = 0; dispos < response.body().getRoutes().get(dis).getLegs().size(); dispos++) {
                            distance_value.add(response.body().getRoutes().get(dis).getLegs().get(dispos).getDistance().getText());
                            duration_value.add(response.body().getRoutes().get(dis).getLegs().get(dispos).getDuration().getText());
                        }
                    }//end of for loop

                    if (duration_value != null && duration_value.size() != 0 && distance_value != null && distance_value.size() != 0) {
                        distancetext.setText(distance_value.get(0));
                        durationtext.setText(duration_value.get(0));
                    }

                    //  Multiple/alter routes work
                    //outer main roads work
                    for (int routpos = 1; routpos < response.body().getRoutes().size(); routpos++) {
                        List<LatLng> route_list = decodePoly(response.body().getRoutes().get(routpos).getOverviewPolyline().getPoints());
                        polyline = mGoogleMap_obj.addPolyline(new PolylineOptions().addAll(route_list).width(22).color(Color.parseColor("#FFD4D1D1")).geodesic(true));
                        //small routes work
                        for (int smallrtpos = 1; smallrtpos < response.body().getRoutes().get(routpos).getLegs().size(); smallrtpos++) {
                            for (int stepspos = 1; stepspos < response.body().getRoutes().get(routpos).getLegs().get(smallrtpos).getSteps().size(); stepspos++) {
                                List<LatLng> smallroute_list = decodePoly(response.body().getRoutes().get(routpos).getLegs().get(smallrtpos).getSteps().get(stepspos).getPolyline().getPoints());
                                polyline = mGoogleMap_obj.addPolyline(new PolylineOptions().addAll(smallroute_list).width(22).color(Color.parseColor("#FFD4D1D1")).geodesic(true));
                            }//end of steps pos for loop
                        }//end of small routes pos for loop
                    }//end of for loop
                    //fast route work
                    if (duration_value != null && duration_value.size() != 0) {
                        List<LatLng> fast_routelist1, fast_routelist2;
                        fast_routelist1 = decodePoly(response.body().getRoutes().get(0).getOverviewPolyline().getPoints());
                        fast_routelist2 = decodePoly(response.body().getRoutes().get(0).getLegs().get(0).getSteps().get(0).getPolyline().getPoints());
                        polyline = mGoogleMap_obj.addPolyline(new PolylineOptions().addAll(fast_routelist1).width(25).color(Color.parseColor("#05b1fb")).geodesic(true));
                        polyline = mGoogleMap_obj.addPolyline(new PolylineOptions().addAll(fast_routelist2).width(25).color(Color.parseColor("#05b1fb")).geodesic(true));
                    }//end of if
                }//end of outer if condition
            }//end of onResponse method

            @Override
            public void onFailure(Call<AllmapdataPOJO1> call, Throwable t) {
                Toast.makeText(Googlemap.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }//end of onFailure method
        });
    }//end of all google api data method

    private List<LatLng> decodePoly(String encoded) {
        List<LatLng> poly = new ArrayList<>();
        int index = 0, len = encoded.length();
        int lat = 0, lng = 0;

        while (index < len) {
            int b, shift = 0, result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;
            shift = 0;
            result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;
            LatLng p = new LatLng((((double) lat / 1E5)),
                    (((double) lng / 1E5)));
            poly.add(p);
        }
        return poly;
    }//end of decodePoly method


    public void markeroperations_method() {
        //Marker options initialize
        MarkerOptions destmarkerOption_obj = new MarkerOptions();
        //marker icon set
        destmarkerOption_obj.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
        //Add markers
        mGoogleMap_obj.addMarker(destmarkerOption_obj.position(new LatLng(destlat, destlng)).title("Destination").snippet("Reach here"));
        //Move camera on origin location
        mGoogleMap_obj.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(originlat, originlng)));
        mGoogleMap_obj.animateCamera(CameraUpdateFactory.zoomTo(12));
    }//end of marker operations method

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient_obj, mLocationRequest, this);
        }
    }//end of onConnected method

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        getCurrentLocation();
        mGoogleApiClient_obj.connect();
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient_obj, mLocationRequest, this);
        }
        allgoogleapidata_method();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mGoogleMap_obj.setMyLocationEnabled(true);
    }//end of onLocationChanged method

    @Override
    public void onPause() {
        super.onPause();
        //stop location updates when Activity is no longer active
        if (mGoogleApiClient_obj != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient_obj, this);
        }
    }//end of onPause method

    @Override
    protected void onResume() {
        super.onResume();
        if (mGoogleApiClient_obj != null) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient_obj, mLocationRequest, this);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.navigationbtnid:
                navigationstart_method();
                break;
            case R.id.focusmarkertnid:
                focuscurrentlocation_method();
                break;
        }//end of switch case
    }//end of onClick method

    private void focuscurrentlocation_method() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mGoogleMap_obj.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(originlat, originlng)));
        mGoogleMap_obj.animateCamera(CameraUpdateFactory.zoomTo(16));
        mGoogleMap_obj.setMyLocationEnabled(true);
    }//end of focus current location method

    private void navigationstart_method() {
        mGoogleMap_obj.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(originlat, originlng)));
        mGoogleMap_obj.animateCamera(CameraUpdateFactory.zoomTo(20));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
           return;
        }
        mGoogleMap_obj.setMyLocationEnabled(true);
    }//end of navigation start method

    public void getCurrentLocation(){
        if(ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION)!=PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_LOCATION);
        }else{
//            Location location_obj = null;
//            // getting GPS status
//            boolean isGPSEnabled = locationManager_obj.isProviderEnabled(LocationManager.GPS_PROVIDER);
//            // getting network status
//            boolean isNetworkEnabled = locationManager_obj.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
//            if (!isGPSEnabled && !isNetworkEnabled) {
//                //No network available
//                Toast.makeText(this, "problem to find location", Toast.LENGTH_SHORT).show();
//            }else{
//                //When network available
//                if (isGPSEnabled) {
//                    if (location_obj == null) {
//                        locationManager_obj = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
//                        if (locationManager_obj != null) {
//                            location_obj = locationManager_obj.getLastKnownLocation(LocationManager.GPS_PROVIDER);
//                            if(location_obj!=null){
//                                originlat=location_obj.getLatitude();
//                                originlng=location_obj.getLongitude();
//                                originlocation = originlat + " " + originlng;
//                                Toast.makeText(this, "change location is "+originlocation, Toast.LENGTH_SHORT).show();
//                            }else{
//                                Toast.makeText(getApplicationContext(), "Unable to find current location please check internet connection or server problem", Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                    }
//                }//end of inner if condition of GPS Provider
//                //Network provider
//               else if (isNetworkEnabled) {
//                    locationManager_obj = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
//                    if (locationManager_obj != null) {
//                        location_obj = locationManager_obj.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
//                        if(location_obj!=null){
//                            originlat=location_obj.getLatitude();
//                            originlng=location_obj.getLongitude();
//                            originlocation = originlat + " " + originlng;
//                            Toast.makeText(this, "change location is "+originlocation, Toast.LENGTH_SHORT).show();
//                        }else{
//                            Toast.makeText(getApplicationContext(), "Unable to find current location please check internet connection or server problem", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                }//end of inner if condition of Network provider
//
//            }//end of inner else condition
            locationManager_obj = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
            Location location_obj=locationManager_obj.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if(location_obj!=null){
                originlat=location_obj.getLatitude();
                originlng=location_obj.getLongitude();
                originlocation = originlat + " " + originlng;
                Toast.makeText(this, "change location is "+originlocation, Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(getApplicationContext(), "Unable to find current location please check internet connection or server problem", Toast.LENGTH_SHORT).show();
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
