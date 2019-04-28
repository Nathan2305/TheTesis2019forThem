package com.example.appforthem.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessException;
import com.backendless.exceptions.BackendlessFault;
import com.example.appforthem.Clases.BackendlessSettings;
import com.example.appforthem.Clases.UserSessionManager;
import com.example.appforthem.R;

import static com.example.appforthem.Activities.HomeActivity.sharedPreferences;
import static com.example.appforthem.Activities.HomeActivity.whereActivity;
import static com.example.appforthem.Activities.LoginActivity.backendlessUser;

public class Ajustes extends AppCompatActivity {
public static boolean isLogout=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajustes);

    }


}
