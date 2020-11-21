package co.com.ceiba.mobile.pruebadeingreso.util;

import android.app.ProgressDialog;
import android.content.Context;

public class ProgressView
{
    static ProgressDialog progressDialog;
    public static void Show(Context context)
    {
        Dismiss();
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Cargando...");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }
    public static void Dismiss()
    {
        if(progressDialog != null)
            progressDialog.dismiss();
    }
}
