package com.example.appforthem.Activities;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.backendless.exceptions.BackendlessException;
import com.example.appforthem.Clases.Utils;
import com.example.appforthem.Clases.Constants;
import com.example.appforthem.Clases.CustomAdapterOptions;
import com.example.appforthem.Clases.ServiceMap;
import com.example.appforthem.Clases.UserSessionManager;
import com.example.appforthem.R;
import com.github.ybq.android.spinkit.style.MultiplePulseRing;

import java.io.File;

import static com.example.appforthem.Activities.LoginActivity.backendlessUser;

public class HomeActivity extends AppCompatActivity {
    public static String whereActivity = "";
    public static Button btn_alerta;
    private TextView datosUser;
    public static SharedPreferences sharedPreferences;
    public static SharedPreferences.Editor prefsEditor;
    public GridView opciones;
    public static ProgressBar progressBar;
    public static String FOLDER_AUDIO = "";
    private String sdCardState = "";
    public static int REQUEST_WRITE_STORAGE = 1;
    public static int REQUEST_GPS_PERMISSION = 2;
    public static TextView gpsStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        progressBar = findViewById(R.id.pbHome);
        MultiplePulseRing multiplePulseRing = new MultiplePulseRing();
        progressBar.setProgressDrawable(multiplePulseRing);
        btn_alerta = findViewById(R.id.alert);
        opciones = findViewById(R.id.opciones);
        datosUser = findViewById(R.id.datosUser);
        gpsStatus=findViewById(R.id.gpsStatus);
        requestGPSPermission();
        createFolderforAudio();
        initData();
        makeGridView();
    }

    private void requestGPSPermission() {
        if (Build.VERSION.SDK_INT >= 23 &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_GPS_PERMISSION);
        } else { //si ya tiene permisos
            enable_buttons();
        }
    }

    private void createFolderforAudio() {
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            requestWriteExternal();
        } else {
            sdCardState = Environment.getExternalStorageState();
            if (Environment.MEDIA_MOUNTED.equalsIgnoreCase(sdCardState)) {
                FOLDER_AUDIO = Environment.getExternalStorageDirectory().getAbsolutePath() + "/AppForThem/Audios";
                File file = new File(FOLDER_AUDIO);
                if (!file.exists()) {
                    try {
                        if (file.mkdirs()) {  //crea directios incluyendo la carpeta padre
                            Utils.showToast(getApplicationContext(), "Se creó el directorio " + FOLDER_AUDIO);
                        } else {
                            Utils.showToast(getApplicationContext(), "No se creó el directorio " + FOLDER_AUDIO);
                        }
                    } catch (SecurityException e) {
                        System.out.println("Excepcion reading File " + e.getMessage());
                    }
                }
            }
        }
    }

    private void requestWriteExternal() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_STORAGE);
    }

    private void enable_buttons() {
        btn_alerta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Constants.ENVIAR_ALERTA.equalsIgnoreCase(btn_alerta.getText().toString())) {
                    prefsEditor.putBoolean(Constants.BTN_ENABLED, false); //deshabilta boton
                    prefsEditor.putString(Constants.BTN_TXT_VALUE,"ENVIANDO ALERTA..");
                    prefsEditor.apply();
                    btn_alerta.setEnabled(sharedPreferences.getBoolean(Constants.BTN_ENABLED, true));
                    btn_alerta.setText(sharedPreferences.getString(Constants.BTN_TXT_VALUE, ""));
                    sendAlert();
                } else {
                    //stopAlert();
                }
            }
        });
    }

    private void sendAlert() {
        startService(new Intent(this, ServiceMap.class));
    }

    private void stopAlert() {
        stopService(new Intent(getApplicationContext(), ServiceMap.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.map) {
            startActivity(new Intent(getApplicationContext(), MapsActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_GPS_PERMISSION) {
            if (grantResults.length > 0 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                    grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                enable_buttons();
            } else {
                requestGPSPermission();
            }
        }
        if (requestCode == REQUEST_WRITE_STORAGE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                createFolderforAudio();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        whereActivity = HomeActivity.class.getSimpleName();
        if (sharedPreferences != null) {
            if (sharedPreferences.contains(Constants.BTN_TXT_VALUE)) {
                btn_alerta.setText(sharedPreferences.getString(Constants.BTN_TXT_VALUE, ""));
            }
            if (sharedPreferences.contains(Constants.BTN_ENABLED)) {
                btn_alerta.setEnabled(sharedPreferences.getBoolean(Constants.BTN_ENABLED, false));
            }
            if (sharedPreferences.contains("COLOR_BTN")) {
                btn_alerta.setBackgroundColor(sharedPreferences.getInt("COLOR_BTN", 0));
            }
        }
    }

    private void initData() {
        try {
            if (backendlessUser == null) {
                backendlessUser = UserSessionManager.getBackendlessUser();
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(backendlessUser.getProperty("name").toString()).
                    append(" " + backendlessUser.getProperty("last_name").toString());
            datosUser.setText(stringBuilder.toString());
        } catch (BackendlessException e) {
            Utils.showToast(this, "Exception trying to get BackendlessUser - " + e.getMessage());
        }
        sharedPreferences = getApplicationContext().getSharedPreferences("HOME", MODE_PRIVATE);
        prefsEditor = sharedPreferences.edit();
    }

    private void makeGridView() {
        int images[] = new int[]{R.drawable.ic_protector, R.drawable.ic_agresor, R.drawable.ic_action_name, R.drawable.ic_audio,
                R.drawable.woman_profile2, R.drawable.ic_setting};
        String titulo[] = new String[]{"Protector", "Agresores", "Ubicación", "Audios", "Opcion 5", "Ajustes"};
        CustomAdapterOptions adapterOptions = new CustomAdapterOptions(getApplicationContext(), images, titulo);
        opciones.setAdapter(adapterOptions);
        opciones.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        startActivity(new Intent(getApplicationContext(), Protector.class));
                        break;
                    case 1:
                        startActivity(new Intent(getApplicationContext(), ListaAgresoresActivity.class));
                        break;
                    case 2:
                        startActivity(new Intent(getApplicationContext(), MapsActivity.class));
                        break;
                    case 3:
                        startActivity(new Intent(getApplicationContext(), AudioActivity.class));
                        break;
                    case 5:
                        startActivity(new Intent(getApplicationContext(), Ajustes.class));
                        /*if (sharedPreferences.contains(Constants.PIN_ENABLED)) {
                            if (!sharedPreferences.getBoolean(Constants.PIN_ENABLED, true)) {
                                startActivity(new Intent(getApplicationContext(), ActivitySetPin.class));
                            } else {
                                startActivity(new Intent(getApplicationContext(), PinActivityNext.class));
                            }
                        } else {
                            startActivity(new Intent(getApplicationContext(), ActivitySetPin.class));
                        }*/
                        break;
                }
            }
        });
    }
}

