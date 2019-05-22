package com.example.appforthem.Clases;


import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;

import static com.example.appforthem.Activities.HomeActivity.FOLDER_AUDIO;
import static com.example.appforthem.Activities.HomeActivity.locationManager;
import static com.example.appforthem.Activities.HomeActivity.progressBar;
import static com.example.appforthem.Activities.LoginActivity.backendlessUser;


public class ServiceMap extends Service {
    private static String OUTPUTFILE = "";
    String nameAudio = "";
    MediaRecorder mediaRecorder;
    LocationListener locationListener;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void onCreate() { // the service is created for the first time
        super.onCreate();
        DateFormat sdf = DateFormat.getDateInstance();
        DateFormat sdf2 = DateFormat.getTimeInstance();
        Date date = new Date();
        nameAudio = sdf.format(date) + "_" + sdf2.format(date) + ".mp3";
        File file = new File(FOLDER_AUDIO, nameAudio);
        try {
            if (file.createNewFile()) {
                BackendlessSettings.showToast(this,"Se creó el archivo :" + nameAudio);
            } else {
                BackendlessSettings.showToast(this,"No se creó el archivo :" + nameAudio);
            }
        } catch (IOException e) {
            BackendlessSettings.showToast(this,"Error creando archivo: " + e.getMessage());
        }
    }

    @SuppressLint("MissingPermission")
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                if (location.getLatitude()!=0 && location.getLongitude()!=0) {
                    if (backendlessUser != null) {
                        sendToChannel(backendlessUser.getEmail(), location.getLatitude(), location.getLongitude());
                    }
                }
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {
            }

            @Override
            public void onProviderEnabled(String s) {
               /* Intent i = new Intent(Constants.GPS_ON);
                i.putExtra(Constants.GPS_ON, "on");
                sendBroadcast(i);*/
            }

            @Override
            public void onProviderDisabled(String s) {
                System.out.println("onProviderDisabled()");
                Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
            }
        };
        if (locationListener != null) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3000, 0, locationListener);
            progressBar.setVisibility(View.GONE);
        }

        /*GRABACIÓN DE AUDIO*/
        try {
            OUTPUTFILE = FOLDER_AUDIO + "/" + nameAudio;
            mediaRecorder = new MediaRecorder();
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
            mediaRecorder.setOutputFile(OUTPUTFILE);
            mediaRecorder.prepare();
            mediaRecorder.start();
        } catch (IOException e) {
            BackendlessSettings.showToast(this,e.getMessage());
        }
        /*FIN GRABACIÓN DE AUDIO*/
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (locationManager != null) {
            locationManager.removeUpdates(locationListener);
        }
        if (mediaRecorder != null) {
            mediaRecorder.stop();
            mediaRecorder.release();
            mediaRecorder=null;
            BackendlessSettings.showToast(this, "Servicio Detenido");
        }else{
            BackendlessSettings.showToast(this,"Aún no has iniciado el servicio");
        }
    }

    public void sendToChannel(String channelName, double lat, double lon) {
        BackendlessGeoUtils.sendtoChannel(channelName, lat, lon);
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);
    }
}
