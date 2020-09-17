package com.stone.mynotes.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import static com.stone.mynotes.view.CreateNoteActivity.PICK_IMAGE;

public class AppUtils {
    public static String getDateFormat() {
        return new SimpleDateFormat("EEEE, dd MMMM yyyy hh:mm:a", Locale.getDefault()).format(new Date());
    }
    public static boolean checkReadPermission(Activity context){
//        boolean checked=false;
//        if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_DENIED){
//            ActivityCompat.requestPermissions(context,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);
//            checked=true;
//        }else {
//            checked=false;
//        }
//        return checked;
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED) {
          //  Toast.makeText(getApplicationContext(),"Permission is granted2",Toast.LENGTH_SHORT).show();
            return true;
        } else {
            ActivityCompat.requestPermissions(context, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
            return false;
        }

    }
    public static boolean isWritePermission(Activity activity,Context context){
        if (ContextCompat.checkSelfPermission(context,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)

                != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                return true;
            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(activity,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        PICK_IMAGE);
                return false;

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            // Permission has already been granted
            return  true;
        }
    }


}
