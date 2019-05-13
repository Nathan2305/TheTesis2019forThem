package com.example.appforthem.Clases;


import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import static com.example.appforthem.Activities.LoginActivity.backendlessUser;


public class ServiceMap extends Service {
    LocationManager locationManager;
    LocationListener locationListener;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void onCreate() { // the service is created for the first time
        super.onCreate();
    }

    @SuppressLint("MissingPermission")
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                if (backendlessUser != null) {
                    sendToChannel(backendlessUser.getEmail(), location.getLatitude(), location.getLongitude());
                }
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {
                System.out.println("onStatusChanged()");

            }

            @Override
            public void onProviderEnabled(String s) {
                System.out.println("onProviderEnabled()");
            }

            @Override
            public void onProviderDisabled(String s) {
                Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
            }
        };

        locationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
        if (locationListener != null) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3000, 0, locationListener);
        }
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (locationManager != null) {
            locationManager.removeUpdates(locationListener);
        }
    }

    public void sendToChannel(String channelName, double lat, double lon) {
        BackendlessGeoUtils.sendtoChannel(channelName, lat, lon);
    }
}
