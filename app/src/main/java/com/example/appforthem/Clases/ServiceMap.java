package com.example.appforthem.Clases;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.widget.ProgressBar;

import static com.example.appforthem.Activities.LoginActivity.backendlessUser;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessException;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.messaging.MessageStatus;
import com.example.appforthem.Activities.CustomProgressBar;

public class ServiceMap extends Service {
    LocationManager locationManager;
    LocationListener locationListener;
    long tStart = 0L;

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
                sendToChannel(location.getLatitude(), location.getLongitude());
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {
                Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
            }
        };

        locationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);

        //noinspection MissingPermission
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3000, 0, locationListener);
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (locationManager != null) {
            //noinspection MissingPermission
            locationManager.removeUpdates(locationListener);
        }
    }

    public void sendToChannel(double lat, double lon) {
        Backendless.Messaging.publish(backendlessUser.getEmail(), lat + " ; " + lon, null, new AsyncCallback<MessageStatus>() {
            @Override
            public void handleResponse(MessageStatus response) {

            }

            @Override
            public void handleFault(BackendlessFault fault) {

            }
        });
        Long tEnd = System.currentTimeMillis();
    }

    @SuppressLint("WrongConstant")
    private void showProgressBar() {
        tStart = System.currentTimeMillis();
        startActivity(new Intent(getApplicationContext(), CustomProgressBar.class));

    }
}
