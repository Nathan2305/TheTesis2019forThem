package com.example.appforthem.Clases;

import android.content.Context;
import android.content.SharedPreferences;

import com.backendless.BackendlessUser;
import com.google.gson.Gson;

import static android.content.Context.MODE_PRIVATE;

public class UserSessionManager {
    private SharedPreferences sp;
    private BackendlessUser backendlessUser;
    private Context context;
    private SharedPreferences.Editor prefsEditor;

    public UserSessionManager(Context context) {
        this.context = context;
        sp = context.getSharedPreferences("LOGIN", MODE_PRIVATE);
    }

    public boolean isLoggedIn() {
        return sp != null && sp.contains("userLoggedIn") && sp.getBoolean("userLoggedIn", true) ?  true :false;
    }

    public void saveLogin() {
        prefsEditor = sp.edit();
        prefsEditor.putBoolean("userLoggedIn", true);
        prefsEditor.apply();

    }

    public void saveBackendlessUser(BackendlessUser loggedInUser) {
        this.backendlessUser = loggedInUser;
        Gson gson = new Gson();
        String json = gson.toJson(backendlessUser);
        prefsEditor.putString("backendlessUser", json);
        prefsEditor.apply();

    }
    public String getUserObject(){
        return sp.getString("backendlessUser", "");
    }

    public BackendlessUser getBackendlessUser(){
        String userObject = getUserObject();
        Gson gson = new Gson();
        return gson.fromJson(userObject, BackendlessUser.class);
    }
}
