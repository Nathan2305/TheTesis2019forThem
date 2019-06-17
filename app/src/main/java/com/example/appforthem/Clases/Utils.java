package com.example.appforthem.Clases;

import android.content.Context;
import android.widget.Toast;

import static com.example.appforthem.Activities.LoginActivity.backendlessUser;

public class Utils {
    public static final String APPLICATION_ID = "2E3D890E-D735-D791-FF48-139CA7949A00";
    public static final String ANDROID_SECRET_KEY = "25C58567-048D-E15C-FF22-EF0A4C2ECB00";

    public static final String APPLICATION_ID_HELPERS = "CE815FC5-2645-C90A-FF91-4749CFEA1800";
    public static final String ANDROID_SECRET_KEY_HELPERS = "3A9B10BF-7C49-AC90-FFFC-50A69504CE00";


    public static void showToast(Context context,String msg){
        Toast.makeText(context,msg,Toast.LENGTH_SHORT).show();
    }

}
