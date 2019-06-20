package com.example.appforthem.Clases;

import android.content.Context;
import android.media.MediaPlayer;
import android.view.View;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.messaging.MessageStatus;
import com.example.appforthem.R;

import java.util.List;

import static com.example.appforthem.Activities.HomeActivity.btn_alerta;
import static com.example.appforthem.Activities.HomeActivity.prefsEditor;
import static com.example.appforthem.Activities.HomeActivity.progressBar;
import static com.example.appforthem.Activities.HomeActivity.sharedPreferences;

public class BackendlessGeoUtils {
    static int count = 0;


    public static void sendtoChannel(final Context context, String channelName, final double lat, final double lon) {
        if (lat != 0d && lon != 0d && !channelName.isEmpty()) {
            /*MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.alert_sound);
            mediaPlayer.start();*/
            Backendless.Messaging.publish(channelName, lat + " ; " + lon, null, new AsyncCallback<MessageStatus>() {
                @Override
                public void handleResponse(MessageStatus response) {
                    progressBar.setVisibility(View.GONE);
                    count++;
                    if (count == 1) {
                        if (!Constants.ENVIANDO_ALERTA.equalsIgnoreCase(sharedPreferences.getString(Constants.BTN_TXT_VALUE, ""))) {
                            prefsEditor.putString(Constants.BTN_TXT_VALUE, Constants.ENVIANDO_ALERTA);
                            prefsEditor.apply();
                            btn_alerta.setText(sharedPreferences.getString(Constants.BTN_TXT_VALUE, ""));
                        }
                        progressBar.setVisibility(View.GONE);
                        //count = 0;
                    }
                    Utils.showToast(context,
                            "Enviando alerta en la posición " + "\n" +
                            "Latitud :" + lat + "\n" +
                            "Longitud :" + lon);
                }

                @Override
                public void handleFault(BackendlessFault fault) {
                    Utils.showToast(context, "Algo salió mal con una alerta " + fault.getMessage());
                }
            });
        }
    }
}
