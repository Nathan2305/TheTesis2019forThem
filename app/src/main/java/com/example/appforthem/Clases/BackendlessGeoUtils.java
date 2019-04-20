package com.example.appforthem.Clases;

import android.content.Context;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.geo.GeoPoint;

import java.util.ArrayList;
import java.util.Map;

public class BackendlessGeoUtils {

    public static void saveGeo(double lat, double longi, ArrayList<String> categoria, Map<String, Object> meta){
        Backendless.Geo.savePoint(lat, longi, categoria,meta, new AsyncCallback<GeoPoint>() {
            @Override
            public void handleResponse(GeoPoint response) {

            }

            @Override
            public void handleFault(BackendlessFault fault) {

            }
        });
    }


}
