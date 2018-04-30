package com.example.amit.cabtrackingdemo.Activities;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class TrackingPath extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, OnMapReadyCallback, LocationListener, View.OnClickListener, GoogleApiClient.OnConnectionFailedListener {

    double trackoriginlat, trackoriginlng, trackdestlat, trackdestlng;
    String trackdestlocation, trackoriginlocation, track_API_KEY_VALUE = null;
    TextView trackdurationtext, trackdistancetext;
    ImageButton trackcurlocationfocusbtn;
    GoogleMap trackmGoogleMap_obj;
    UiSettings trackuisettings_obj;
    GoogleApiClient trackmGoogleApiClient_obj;
    LocationRequest trackmLocationRequest;
    Polyline trackpolyline;
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    ArrayList<String> track_duration_value = new ArrayList<>();
    ArrayList<String> track_distance_value = new ArrayList<>();
    SharedPreferences latlong_preference;
    SharedPreferences.Editor mainlatlong_preference;


    RetrofitintrfcFile retrofitinterfacefile_obj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracking_path);
        trackdistancetext = (TextView) findViewById(R.id.trackdistanceid);
        trackdurationtext = (TextView) findViewById(R.id.trackdurationid);
        trackcurlocationfocusbtn = (ImageButton) findViewById(R.id.trackfocusmarkertnid);

        //SharePreference work
        mainlatlong_preference = getSharedPreferences("latlongpreference", Context.MODE_PRIVATE).edit();
        latlong_preference = getSharedPreferences("latlongpreference", Context.MODE_PRIVATE);
        trackoriginlat = Double.parseDouble(latlong_preference.getString("originlat", "null"));
        trackoriginlng = Double.parseDouble(latlong_preference.getString("originlng", "null"));
        trackdestlat = Double.parseDouble(latlong_preference.getString("destlat", "null"));
        trackdestlng = Double.parseDouble(latlong_preference.getString("destlng", "null"));
        trackoriginlocation = trackoriginlat + " " + trackoriginlng;
        trackdestlocation = trackdestlat + "," + trackdestlng;
        track_API_KEY_VALUE = getString(R.string.google_maps_key);
        //Retrofit file work
        retrofitinterfacefile_obj = RetrofitInstancefile.retrofit_method().create(RetrofitintrfcFile.class);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.trackmap);
        mapFragment.getMapAsync(this);
        trackcurlocationfocusbtn.setOnClickListener(this);
    }//end of onCreate method

    @Override
    public void onMapReady(GoogleMap googleMap) {
        trackmGoogleMap_obj = googleMap;
        trackmGoogleMap_obj.setMapType(GoogleMap.MAP_TYPE_NORMAL);
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
        trackmGoogleMap_obj.setMyLocationEnabled(false);
        //Google map UI setting work
        trackuisettings_obj = trackmGoogleMap_obj.getUiSettings();
        trackuisettings_obj.setZoomControlsEnabled(true);
        trackuisettings_obj.setAllGesturesEnabled(true);
        trackuisettings_obj.setCompassEnabled(true);
        trackuisettings_obj.setMapToolbarEnabled(false);
        trackuisettings_obj.setMyLocationButtonEnabled(false);
        trackuisettings_obj.isCompassEnabled();
        trackuisettings_obj.setIndoorLevelPickerEnabled(true);

        //Initialize Google Play Services
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                //Location Permission already granted
                buildGoogleApiClient();
                markeroperations_method();
                allgoogleapitrackdata_method();
            } else {
                //Request Location Permission
                checkLocationPermission();
            }
        }
    }//end of onMapReady method

    protected synchronized void buildGoogleApiClient() {
        trackmGoogleApiClient_obj = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        trackmGoogleApiClient_obj.connect();
    }//end of buildGoogleApiClient method

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
                                ActivityCompat.requestPermissions(TrackingPath.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_LOCATION);
                            }
                        })
                        .create()
                        .show();
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_LOCATION);
            }
        }
    }//end of checkLocationPermission method

    public void markeroperations_method() {
        //Marker options initialize
        MarkerOptions trackdestmarkerOption_obj = new MarkerOptions();
        MarkerOptions trackoriginmarkerOption_obj = new MarkerOptions();
        //marker icon set
        trackdestmarkerOption_obj.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
        trackoriginmarkerOption_obj.icon(BitmapDescriptorFactory.fromResource(R.drawable.carnew));
        //Add markers
        trackmGoogleMap_obj.addMarker(trackdestmarkerOption_obj.position(new LatLng(trackdestlat, trackdestlng)).title("Drop here"));
        trackmGoogleMap_obj.addMarker(trackoriginmarkerOption_obj.position(new LatLng(trackoriginlat, trackoriginlng)).title("Driver"));
        //Move camera on origin location
        trackmGoogleMap_obj.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(trackoriginlat, trackoriginlng)));
        trackmGoogleMap_obj.animateCamera(CameraUpdateFactory.zoomTo(12));
    }//end of marker operat
    // ions method

    public void allgoogleapitrackdata_method() {
        Call<AllmapdataPOJO1> alldatacall_obj = retrofitinterfacefile_obj.trackapiintrfc_method(trackoriginlocation,
                trackdestlocation,track_API_KEY_VALUE);
        alldatacall_obj.enqueue(new Callback<AllmapdataPOJO1>() {
            @Override
            public void onResponse(Call<AllmapdataPOJO1> call, Response<AllmapdataPOJO1> response) {
                if (response.body() != null) {
                    //Distance and duration Arraylist work
                            track_distance_value.add(response.body().getRoutes().get(0).getLegs().get(0).getDistance().getText());
                            track_duration_value.add(response.body().getRoutes().get(0).getLegs().get(0).getDuration().getText());

                    if(track_duration_value!=null && track_duration_value.size()!=0 && track_distance_value!=null && track_distance_value.size()!=0){
                        trackdistancetext.setText(track_distance_value.get(0));
                        trackdurationtext.setText(track_duration_value.get(0));
                    }
                    List<LatLng> route_list = decodePoly(response.body().getRoutes().get(0).getOverviewPolyline().getPoints());
                    trackpolyline = trackmGoogleMap_obj.addPolyline(new PolylineOptions().addAll(route_list).width(22).color(Color.parseColor("#05b1fb")).geodesic(true));
                    List<LatLng> smallroute_list = decodePoly(response.body().getRoutes().get(0).getLegs().get(0).getSteps().get(0).getPolyline().getPoints());
                    trackpolyline = trackmGoogleMap_obj.addPolyline(new PolylineOptions().addAll(smallroute_list).width(22).color(Color.parseColor("#05b1fb")).geodesic(true));
                }//end of outer if condition
            }//end of onResponse method
            @Override
            public void onFailure(Call<AllmapdataPOJO1> call, Throwable t) {
                Toast.makeText(getApplication(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
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

    @Override
    public void onPause() {
        super.onPause();
        //stop location updates when Activity is no longer active
        if (trackmGoogleApiClient_obj != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(trackmGoogleApiClient_obj, this);
        }
    }//end of onPause method

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.trackfocusmarkertnid:
                trackfocuscurrentlocation_method();
                break;
        }//end of switch case
    }//end of onClick method

    private void trackfocuscurrentlocation_method() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        trackmGoogleMap_obj.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(trackoriginlat,trackoriginlng)));
        trackmGoogleMap_obj.animateCamera(CameraUpdateFactory.zoomTo(16));
        trackmGoogleMap_obj.setMyLocationEnabled(false);
    }//end of focus current location method

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }//end of onConnectionFailed method

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        trackmLocationRequest = new LocationRequest();
        trackmLocationRequest.setInterval(1000);
        trackmLocationRequest.setFastestInterval(1000);
        trackmLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(trackmGoogleApiClient_obj, trackmLocationRequest, this);
        }
    }//end of onConnercted method

    @Override
    public void onConnectionSuspended(int i) {

    }//end of onConnectionSuspended method

    @Override
    public void onLocationChanged(Location location) {
        trackmGoogleApiClient_obj.connect();
        trackmLocationRequest = new LocationRequest();
        trackmLocationRequest.setInterval(1000);
        trackmLocationRequest.setFastestInterval(1000);
        trackmLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(trackmGoogleApiClient_obj, trackmLocationRequest, this);
        }
        markeroperations_method();
        allgoogleapitrackdata_method();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        trackmGoogleMap_obj.setMyLocationEnabled(false);
    }//end of onLocationChanged method
}//end of main class
