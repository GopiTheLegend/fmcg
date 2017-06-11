package app.legend.gopiking.fmcg.appLib;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Environment;
import android.os.StatFs;
import android.os.StrictMode;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import app.legend.gopiking.fmcg.R;

/**
 * Created by GopiKingMaker on 2/24/2017.
 */

public class Def_Lib{

    public static Activity activity;
    public static NotificationCompat.Builder nBuilder;
    public static Notification notification;

    public static String tag="res_Tag" ;

   public Def_Lib(Activity _activity){

       activity = _activity;

       StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
       StrictMode.setVmPolicy(builder.build());
    }

    //Intent handling

    public static void Formshow(Class form_name) {
        try {
            activity.startActivity(new Intent(activity, form_name));
        } catch (Exception e) {msg(e.toString());}
    }//use also Class.forName(String formName);

    public static void FormshowResult(Class formName, int RequestCode){
        try{

            activity.startActivityForResult(new Intent(activity,formName),RequestCode);

        }catch (Exception e){e.toString();}
    }

    //storage validation

    public static boolean isExternalStorageReadOnly() {
        String extStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(extStorageState)) {
            return true;
        }
        return false;
    }

    public static boolean isExternalStorageAvailable() {
        String extStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(extStorageState)) {
            return true;
        }
        return false;
    }

    private static long getAvailableStorage() {
        String root = Environment.getExternalStorageDirectory().getPath();
        StatFs statFs = new StatFs(root);
        long blockSize = statFs.getBlockSize();
        long availableBlocks = statFs.getAvailableBlocks();
        long availableSize = blockSize * availableBlocks;
        // Formatter.formatFileSize(context, availableSize);
        return availableSize;
    }

    public static boolean isStorageEnough(){
        if(!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED) && getAvailableStorage() > 1000000){
            return true;
        }
        return false;
    }

    void l(String message){
        Log.d(tag,message);}
    // SMSUtil and Alert functions

    public static void InfoAlert(String alertMessage) {
        try {

            AlertDialog.Builder adb = new AlertDialog.Builder(activity);
            adb.setTitle(activity.getTitle());
            adb.setIcon(R.drawable.denariusoft125);
            adb.setMessage(alertMessage);
            adb.setPositiveButton(R.string.alert_ButtonOk, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
            AlertDialog alert = adb.create();
            alert.setIcon(R.drawable.denariusoft125);
            alert.show();

        } catch (Exception e) {
            msg(e.toString());
        }
    }

    public static void InfoAlert(String alertMessage, final Runnable func) {
        try {

            AlertDialog.Builder adb = new AlertDialog.Builder(activity);
            adb.setTitle(activity.getTitle());
            adb.setIcon(R.drawable.denariusoft125);
            adb.setMessage(alertMessage);
            adb.setPositiveButton(R.string.alert_ButtonOk, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                    func.run();
                }
            });
            AlertDialog alert = adb.create();
            alert.setIcon(R.drawable.denariusoft125);
            alert.show();

        } catch (Exception e) {
            msg(e.toString());
        }
    }

    public static void Alert(String alertMessage, String negativeButtonName, final Runnable func) {
        try {

            AlertDialog.Builder adb = new AlertDialog.Builder(activity);
            adb.setTitle(activity.getTitle());
            adb.setIcon(R.drawable.denariusoft125);
            adb.setMessage(alertMessage);
            adb.setPositiveButton(R.string.alert_ButtonOk, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
            adb.setNegativeButton(negativeButtonName, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    func.run();
                }
            });
            AlertDialog alert = adb.create();
            alert.setIcon(R.drawable.denariusoft125);
            alert.show();

        } catch (Exception e) {
            msg(e.toString());
        }
    }

    public static void Alert(String alertMessage, String positiveButtonName, final Runnable fun1, String negativeButtonName, final Runnable fun2, String neutralButtonName, final Runnable fun3) {
        try {

            AlertDialog.Builder adb = new AlertDialog.Builder(activity);
            adb.setTitle(activity.getTitle());
            adb.setIcon(R.drawable.denariusoft125);
            adb.setMessage(alertMessage);
            adb.setPositiveButton(positiveButtonName, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    fun1.run();
                }
            });
            adb.setNegativeButton(negativeButtonName, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    fun2.run();
                }
            });
            adb.setNeutralButton(neutralButtonName, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    fun3.run();
                }
            });
            AlertDialog alert = adb.create();
            alert.setIcon(R.drawable.denariusoft125);
            alert.show();

        } catch (Exception e) {
            msg(e.toString());
        }
    }

    public static void Alert(String alertMessage, String positiveButtonName, final Runnable fun1, String negativeButtonName, final Runnable fun2, String neutralButtonName) {
        try {

            AlertDialog.Builder adb = new AlertDialog.Builder(activity);
            adb.setTitle(activity.getTitle());
            adb.setIcon(R.drawable.denariusoft125);
            adb.setMessage(alertMessage);
            adb.setPositiveButton(positiveButtonName, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    fun1.run();
                }
            });
            adb.setNegativeButton(negativeButtonName, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    fun2.run();
                }
            });
            adb.setNeutralButton(neutralButtonName, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            AlertDialog alert = adb.create();
            alert.setIcon(R.drawable.denariusoft125);
            alert.show();

        } catch (Exception e) {
            msg(e.toString());
        }
    }

    public static void Alert(String alertMessage, String positiveButtonName, final Runnable fun1, String negativeButtonName, final Runnable fun2) {
        try {

            AlertDialog.Builder adb = new AlertDialog.Builder(activity);
            adb.setTitle(activity.getTitle());
            adb.setIcon(R.drawable.denariusoft125);
            adb.setMessage(alertMessage);
            adb.setPositiveButton(positiveButtonName, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    fun1.run();
                }
            });
            adb.setNegativeButton(negativeButtonName, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    fun2.run();
                }
            });

            AlertDialog alert = adb.create();
            alert.setIcon(R.drawable.denariusoft125);
            alert.show();

        } catch (Exception e) {
            msg(e.toString());
        }
    }

    public static void Alert(String alertMessage, final Void function) {
        try {

            AlertDialog.Builder adb = new AlertDialog.Builder(activity);
            adb.setTitle(activity.getTitle());
            adb.setIcon(R.drawable.denariusoft125);
            adb.setMessage(alertMessage);
            adb.setPositiveButton(R.string.alert_ButtonOk, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                    function.getClass();
                }
            });
            AlertDialog alert = adb.create();
            alert.setIcon(R.drawable.denariusoft125);
            alert.show();

        } catch (Exception e) {
            msg(e.toString());
        }
    }

    public static void Alert(String alertMessage, final Runnable func) {
        try {

            AlertDialog.Builder adb = new AlertDialog.Builder(activity);
            adb.setTitle(activity.getTitle());
            adb.setIcon(R.drawable.denariusoft125);
            adb.setMessage(alertMessage);
            adb.setPositiveButton(R.string.alert_ButtonOk, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                    func.run();
                }
            });
            AlertDialog alert = adb.create();
            alert.setIcon(R.drawable.denariusoft125);
            alert.show();

        } catch (Exception e) {
            msg(e.toString());
        }
    }

    public static void ExitAlert(String AlertMessage) {
        try {

            AlertDialog.Builder adb = new AlertDialog.Builder(activity);
            adb.setTitle(activity.getTitle());
            adb.setIcon(R.drawable.denariusoft125);
            adb.setMessage(AlertMessage);
            adb.setPositiveButton(R.string.alert_ButtonYes, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    activity.finish();
                }
            });
            adb.setNegativeButton(R.string.alert_ButtonNo, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    dialogInterface.dismiss();
                }
            });
            AlertDialog ad = adb.create();
            ad.setIcon(R.drawable.denariusoft125);
            ad.show();

        } catch (Exception ex) {
        }

    }

    public static void BackAlert() {


        AlertDialog.Builder adb = new AlertDialog.Builder(activity);
        adb.setTitle(activity.getTitle());
        adb.setIcon(R.drawable.denariusoft125);
        adb.setMessage(activity.getString(R.string.somethinkwaitingtosave));
        adb.setPositiveButton(R.string.alert_ButtonYes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                NavUtils.navigateUpFromSameTask(activity);
            }
        });

        adb.setNegativeButton(R.string.alert_ButtonNo, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                dialogInterface.dismiss();
            }
        });
        AlertDialog ad = adb.create();
        ad.setIcon(R.drawable.denariusoft125);
        ad.show();
    }

    //Toast and snackBar

    public static void snackBar(String m, View view) {
        try {
            Snackbar.make(view, m, Snackbar.LENGTH_LONG).show();
        } catch (Exception e) {
        }
    }

    public static void msg(String m) {

        try{ Toast.makeText(activity, m, Toast.LENGTH_LONG).show();}catch (Exception e){
            Log.d("HOIHOI",e.toString());}
    }


    //notification

    public static void Notifi(String title, String contentText, Class form_name){
        try{

            nBuilder = new NotificationCompat.Builder(activity.getApplicationContext());

            nBuilder.setSmallIcon(R.drawable.ic_stock_report);
            nBuilder.setContentTitle(title);
            nBuilder.setContentText(contentText);
            try{
//                notification.sound = Uri.parse("android.resource://" + context.getPackageName() + "/" + R.raw.song);
                notification.defaults = Notification.DEFAULT_LIGHTS | Notification.DEFAULT_VIBRATE
                        | Notification.DEFAULT_SOUND | Notification.DEFAULT_ALL;
            }catch (Exception e){msg(e.toString());}

            try{
                Intent Notification_Intent = new Intent(activity.getApplicationContext(),form_name);
                PendingIntent contentIntent = PendingIntent.getActivity(activity.getApplicationContext(),0,Notification_Intent, PendingIntent.FLAG_UPDATE_CURRENT);
                nBuilder.setContentIntent(contentIntent);
            }catch (Exception e) {msg(e.toString());}

            try{
                NotificationManager nManager = (NotificationManager) activity.getSystemService(Context.NOTIFICATION_SERVICE);
                nManager.notify(0,nBuilder.build());
            }catch (Exception e) {msg(e.toString());}

        }catch (Exception e){msg(e.toString());}
    }

}


