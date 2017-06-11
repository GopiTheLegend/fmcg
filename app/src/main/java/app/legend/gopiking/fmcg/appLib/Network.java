package app.legend.gopiking.fmcg.appLib;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import app.legend.gopiking.fmcg.R;

/**
 * Created by GopiKingMaker on 3/31/2017.
 */

public class Network {

    private static Network instance = new Network();
    static Context context;
    ConnectivityManager connectivityManager;
    boolean connected = false;

    static final String TAG = "Network_State";

    public static Network getInstance(Context ctx) {
        context = ctx.getApplicationContext();
        return instance;
    }

    public boolean isOnline() {
        try {
            connectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            connected = networkInfo != null && networkInfo.isAvailable() &&
                    networkInfo.isConnected();
            return connected;


        } catch (Exception e) {
            System.out.println("CheckConnectivity Exception: " + e.getMessage());
            Log.v("connectivity", e.toString());
        }
        return connected;
    }


    public static void Check_Internet(final Context context, String title, String message){
        try{

            AlertDialog.Builder iadb = new AlertDialog.Builder(context);
            iadb.setTitle(title);
            iadb.setIcon(R.drawable.denariusoft64);
            iadb.setMessage(message);
            iadb.setNeutralButton("Enable Mobile Data", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
//                    getMobileDataState();
//                    setMobileDataState(getMobileDataState());
//                    createNetErrorDialog();
                    try {
                        setMobileDataEnabled(context,true);
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    } catch (NoSuchFieldException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            });
            iadb.setPositiveButton("Enable WIFI", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    try{

                        WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                        wifi.setWifiEnabled(true);

                    }catch (Exception e){
                        Def_Lib.msg(e.toString());}
                }
            });
            AlertDialog iad = iadb.create();
            iad.setIcon(R.drawable.denariusoft64);
            iad.show();

        }catch (Exception ex){}
    }

    public static void setMobileDataEnabled(Context context, boolean enabled) throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {

        final ConnectivityManager conman = (ConnectivityManager)  context.getSystemService(Context.CONNECTIVITY_SERVICE);
        final Class conmanClass = Class.forName(conman.getClass().getName());
        final Field connectivityManagerField = conmanClass.getDeclaredField("mService");
        connectivityManagerField.setAccessible(true);
        final Object connectivityManager = connectivityManagerField.get(conman);
        final Class connectivityManagerClass =  Class.forName(connectivityManager.getClass().getName());
        final Method setMobileDataEnabledMethod = connectivityManagerClass.getDeclaredMethod("setMobileDataEnabled", Boolean.TYPE);
        setMobileDataEnabledMethod.setAccessible(true);

        try {
            setMobileDataEnabledMethod.invoke(connectivityManager, enabled);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public static void sendMail(Context context, String subject, String body){
        try{

            Intent email = new Intent(Intent.ACTION_SEND);
            email.setType("text/plain");
            email.putExtra(Intent.EXTRA_SUBJECT,subject);
            email.putExtra(Intent.EXTRA_TEXT, body);
            context.startActivity(Intent.createChooser(email,"Select E-Mail Client"));

        }catch (Exception e){
            Def_Lib.msg(e.toString());}
    }
}
