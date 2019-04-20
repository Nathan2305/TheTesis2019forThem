package com.example.appforthem.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.example.appforthem.R;

public class Ajustes extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajustes);

    }

    public void Logout(View view) {
        Backendless.UserService.logout(new AsyncCallback<Void>() {
            @Override
            public void handleResponse(Void response) {
                //System.out.println("USER LOG OUT");
                Toast.makeText(getApplicationContext(),"USER LOG OUT",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void handleFault(BackendlessFault fault) {
               // System.out.println("ERROR LOGING OUT - " + fault.getMessage() );
                Toast.makeText(getApplicationContext(),"ERROR LOGING OUT - " + fault.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }

}
