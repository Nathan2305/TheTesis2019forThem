package com.example.appforthem.Clases;

import android.app.AlertDialog;
import android.content.Context;

class DialogHelper {

    public static AlertDialog createErrorDialog(Context context, String title, String message )
    {
        return new AlertDialog.Builder( context )
                .setTitle( title )
                .setMessage( message )
                .setIcon( android.R.drawable.ic_dialog_alert )
                .create();
    }


}
