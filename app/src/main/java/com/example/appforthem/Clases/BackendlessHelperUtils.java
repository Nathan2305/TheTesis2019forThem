package com.example.appforthem.Clases;

import android.content.Context;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.DataQueryBuilder;
import com.example.appforthem.Activities.Protector;

import java.util.ArrayList;
import java.util.List;
//Backendless.Data.mapTableToClass("Users", BackendlessUser.class);

public class BackendlessHelperUtils {

/*
    public static List<BackendlessUser> findHelpersFromBackend(Context context, List<Helper> list) {
       final List<BackendlessUser> helpers = null;
        Backendless.initApp(context, BackendlessSettings.APPLICATION_ID_HELPERS, BackendlessSettings.ANDROID_SECRET_KEY_HELPERS);
        Backendless.Data.of(BackendlessUser.class).find(new AsyncCallback<List<BackendlessUser>>() {
            @Override
            public void handleResponse(List<BackendlessUser> response) {
                if (response.size() > 0) {
                    helpers = response;
                } else {
                    System.out.println("NO DATA");
                }

            }

            @Override
            public void handleFault(BackendlessFault fault) {
                System.out.println("Error trying to get Data.. " + fault.getMessage());
            }
        });
        return helpers;
    }*/
}