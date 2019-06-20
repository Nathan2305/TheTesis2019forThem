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
import android.util.Log;
import android.view.View;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;

import static com.example.appforthem.Activities.HomeActivity.FOLDER_AUDIO;
import static com.example.appforthem.Activities.HomeActivity.btn_alerta;
import static com.example.appforthem.Activities.HomeActivity.prefsEditor;
import static com.example.appforthem.Activities.HomeActivity.progressBar;
import static com.example.appforthem.Activities.HomeActivity.sharedPreferences;
import static com.example.appforthem.Activities.LoginActivity.backendlessUser;


public class ServiceMap extends Service {
    String nameAudio = "";
    MediaRecorder mediaRecorder;
    public LocationManager locationManager;
    public LocationListener locationListener;
    private static final String TAG = ServiceMap.class.getSimpleName();

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void onCreate() { // the service is created for the first time
        super.onCreate();
        Log.i(TAG, "onCreate()");
        locationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
        progressBar.setVisibility(View.VISIBLE);
        DateFormat sdf = DateFormat.getDateInstance();
        DateFormat sdf2 = DateFormat.getTimeInstance();
        Date date = new Date();
        nameAudio = sdf.format(date) + "_" + sdf2.format(date) + ".mp3";
        File file = new File(FOLDER_AUDIO, nameAudio);
        try {
            if (file.createNewFile()) {
                Utils.showToast(this, "Se creó el archivo :" + nameAudio);
            } else {
                Utils.showToast(this, "No se creó el archivo :" + nameAudio);
            }
        } catch (IOException e) {
            Utils.showToast(this, "Error creando archivo: " + e.getMessage());
        }
    }

    @SuppressLint("MissingPermission")
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                if (location.getLatitude() != 0 && location.getLongitude() != 0) {
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
                System.out.println("onProviderEnabled");  // NO EMTRA AL BUCLE
                progressBar.setVisibility(View.VISIBLE);
                if (!Constants.ENVIANDO_ALERTA.equalsIgnoreCase(sharedPreferences.getString(Constants.BTN_TXT_VALUE, ""))) {
                    prefsEditor.putString(Constants.BTN_TXT_VALUE, Constants.ENVIANDO_ALERTA);
                    prefsEditor.apply();
                    btn_alerta.setText(sharedPreferences.getString(Constants.BTN_TXT_VALUE, ""));
                }
            }

            @Override
            public void onProviderDisabled(String s) {
                System.out.println("onProviderDisabled"); // NO EMTRA AL BUCLE
                Utils.showToast(getApplicationContext(), "Activa el GPS para compartir tu ubicación!!!");
                progressBar.setVisibility(View.GONE);
                prefsEditor.putString(Constants.BTN_TXT_VALUE, Constants.ESPERANDO_GPS);
                prefsEditor.apply();
                btn_alerta.setText(sharedPreferences.getString(Constants.BTN_TXT_VALUE, ""));
                Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
            }
        };
        if (locationListener != null) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3000, 0, locationListener);
        }
        /*GRABACIÓN DE AUDIO*/
        /*try {
            OUTPUTFILE = FOLDER_AUDIO + "/" + nameAudio;
            mediaRecorder = new MediaRecorder();
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
            mediaRecorder.setOutputFile(OUTPUTFILE);
            mediaRecorder.prepare();
            mediaRecorder.start();
        } catch (IOException e) {
            Utils.showToast(this, e.getMessage());
        }*/
        /*FIN GRABACIÓN DE AUDIO*/
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (locationManager != null) {
            locationManager.removeUpdates(locationListener);
            prefsEditor.putString(Constants.BTN_TXT_VALUE, Constants.ENVIAR_ALERTA);
            prefsEditor.putBoolean(Constants.BTN_ENABLED, true);
            prefsEditor.putBoolean(Constants.ALARMA_ACTIVA, false);
            prefsEditor.apply();
            btn_alerta.setText(sharedPreferences.getString(Constants.BTN_TXT_VALUE, ""));
            btn_alerta.setEnabled(sharedPreferences.getBoolean(Constants.BTN_ENABLED, true));
            progressBar.setVisibility(View.GONE);
            locationManager=null;
        }
        if (mediaRecorder != null) {
            mediaRecorder.stop();
            mediaRecorder.release();
            mediaRecorder = null;
            Utils.showToast(this, "Servicio Detenido");
        } else {
            Utils.showToast(this, "Aún no has iniciado el servicio");
        }
        Log.i(TAG, "onDestroy()");
    }

    public void sendToChannel(String channelName, double lat, double lon) {
        BackendlessGeoUtils.sendtoChannel(this, channelName, lat, lon);
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);
        Log.i(TAG, "onTaskRemoved()");
    }

}
