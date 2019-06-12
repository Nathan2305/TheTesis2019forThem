package com.example.appforthem.Clases;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.geo.GeoPoint;
import com.backendless.messaging.MessageStatus;
import com.example.appforthem.Activities.HomeActivity;
import com.example.appforthem.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.example.appforthem.Activities.LoginActivity.backendlessUser;

public class BackendlessGeoUtils {
    List<Agresor> lista = null;

    public BackendlessGeoUtils() {
    }

    public static void saveGeo(double lat, double longi, ArrayList<String> categoria, Map<String, Object> meta) {
        Backendless.Geo.savePoint(lat, longi, categoria, meta, new AsyncCallback<GeoPoint>() {
            @Override
            public void handleResponse(GeoPoint response) {

            }

            @Override
            public void handleFault(BackendlessFault fault) {

            }
        });
    }

    public static void sendtoChannel(final Context context, String channelName, final double lat, final double lon) {
        if (lat != 0d && lon != 0d && !channelName.isEmpty()) {
            Backendless.Messaging.publish(channelName, lat + " ; " + lon, null, new AsyncCallback<MessageStatus>() {
                @Override
                public void handleResponse(MessageStatus response) {
                    /*MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.alert_sound);
                    mediaPlayer.start();*/
                    BackendlessSettings.showToast(context, "Alerta envida en la posición " + "\n" +
                            "Latitud :" + lat + "\n" +
                            "Longitud :" + lon);
                }

                @Override
                public void handleFault(BackendlessFault fault) {
                    BackendlessSettings.showToast(context, "Algo salió mal con una alerta " + fault.getMessage());
                }
            });
        }
    }

    public List<Agresor> getAgresores() {
        Backendless.Data.of(Agresor.class).find(new AsyncCallback<List<Agresor>>() {
            @Override
            public void handleResponse(List<Agresor> response) {
                if (response.size() > 0) {
                    lista = response;
                }
            }

            @Override
            public void handleFault(BackendlessFault fault) {

            }
        });
        return lista;
    }
}
