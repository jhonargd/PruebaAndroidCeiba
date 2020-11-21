package co.com.ceiba.mobile.pruebadeingreso.util;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;
import java.io.File;

public class Util {
    public static File dirApp() {
        File SDCardRoot = Environment.getExternalStorageDirectory();
        File dirApp = new File(SDCardRoot.getPath() + "/" + Const.NAMEDIRAPP);
        if (!dirApp.isDirectory()) dirApp.mkdirs();
        return dirApp;
    }
    public static boolean checkInternet(Context context)
    {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        return isConnected;
    }
    public static void makeToast(String message, Context context)
    {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
    public static boolean checkPermission(Context context) {
        int PERMISSION_ALL = 1;
        boolean estado = false;
        String[] PERMISSIONS = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (!hasPermissions(context, PERMISSIONS)) {
            ActivityCompat.requestPermissions((Activity) context, PERMISSIONS, PERMISSION_ALL);
            estado = false;
        }else{
            estado = true;
        }

        return estado;
    }
    public static boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {

                    return false;
                }
            }
        }
        return true;
    }
}
