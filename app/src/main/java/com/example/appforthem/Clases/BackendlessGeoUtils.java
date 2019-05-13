package com.example.appforthem.Clases;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.geo.GeoPoint;
import com.backendless.messaging.MessageStatus;
import com.example.appforthem.Activities.HomeActivity;

import java.util.ArrayList;
import java.util.Map;

import static com.example.appforthem.Activities.LoginActivity.backendlessUser;

public class BackendlessGeoUtils {

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

    public static void sendtoChannel(String channelName, double lat, double lon) {
        if (lat != 0d && lon != 0d && !channelName.equalsIgnoreCase("")) {
            Backendless.Messaging.publish(channelName, lat + " ; " + lon, null, new AsyncCallback<MessageStatus>() {
                @Override
                public void handleResponse(MessageStatus response) {

                }

                @Override
                public void handleFault(BackendlessFault fault) {

                }
            });
        }
    }


}
