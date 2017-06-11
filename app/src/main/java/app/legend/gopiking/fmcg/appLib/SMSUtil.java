package app.legend.gopiking.fmcg.appLib;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.telephony.SmsManager;
import android.widget.Toast;

import java.util.ArrayList;

import app.legend.gopiking.fmcg.R;

import static app.legend.gopiking.fmcg.appLib.Def_Lib.msg;

/**
 * Created by GopiKing on 6/3/2017.
 */

public class SMSUtil extends BroadcastReceiver {

    static Activity activity;

    public static final String SENT_SMS_ACTION_NAME = "SMS_SENT";
    public static final String DELIVERED_SMS_ACTION_NAME = "SMS_DELIVERED";


    public SMSUtil(Context context){

        activity = (Activity) context;
        new Def_Lib(activity);
    }

    public SMSUtil(){

    }

    @Override
    public void onReceive(Context context, Intent intent) {
        //Detect l'envoie de sms
        if (intent.getAction().equals(SENT_SMS_ACTION_NAME)) {
            switch (getResultCode()) {
                case Activity.RESULT_OK: // Sms sent
                    Toast.makeText(context, context.getString(R.string.sms_send), Toast.LENGTH_LONG).show();
                    break;
                case SmsManager.RESULT_ERROR_GENERIC_FAILURE: // generic failure
                    Toast.makeText(context, context.getString(R.string.sms_not_send), Toast.LENGTH_LONG).show();
                    break;
                case SmsManager.RESULT_ERROR_NO_SERVICE: // No service
                    Toast.makeText(context, context.getString(R.string.sms_not_send_no_service), Toast.LENGTH_LONG).show();
                    break;
                case SmsManager.RESULT_ERROR_NULL_PDU: // null pdu
                    Toast.makeText(context, context.getString(R.string.sms_not_send), Toast.LENGTH_LONG).show();
                    break;
                case SmsManager.RESULT_ERROR_RADIO_OFF: //Radio off
                    Toast.makeText(context, context.getString(R.string.sms_not_send_no_radio), Toast.LENGTH_LONG).show();
                    break;
            }
        }
        //detect la reception d'un sms
        else if (intent.getAction().equals(DELIVERED_SMS_ACTION_NAME)) {
            switch (getResultCode()) {
                case Activity.RESULT_OK:
                    Toast.makeText(context, context.getString(R.string.sms_receive), Toast.LENGTH_LONG).show();
                    break;
                case Activity.RESULT_CANCELED:
                    Toast.makeText(context, context.getString(R.string.sms_not_receive), Toast.LENGTH_LONG).show();
                    break;
            }
        }

    }

    public static boolean canSendSMS() {
        return activity.getPackageManager().hasSystemFeature(PackageManager.FEATURE_TELEPHONY);
    }

    public static void sendSMS(String phoneNumber, String message) {

        if (!canSendSMS()) {
            msg(activity.getString(R.string.cannot_send_sms));
        }

        PendingIntent sentPI = PendingIntent.getBroadcast(activity, 0, new Intent(SENT_SMS_ACTION_NAME), 0);
        PendingIntent deliveredPI = PendingIntent.getBroadcast(activity, 0, new Intent(DELIVERED_SMS_ACTION_NAME), 0);

        final SMSUtil smsUtils = new SMSUtil(activity);
        //register for sending and delivery
        activity.registerReceiver(smsUtils, new IntentFilter(SMSUtil.SENT_SMS_ACTION_NAME));
        activity.registerReceiver(smsUtils, new IntentFilter(DELIVERED_SMS_ACTION_NAME));

        SmsManager sms = SmsManager.getDefault();
        ArrayList<String> parts = sms.divideMessage(message);

        ArrayList<PendingIntent> sendList = new ArrayList<>();
        sendList.add(sentPI);

        ArrayList<PendingIntent> deliverList = new ArrayList<>();
        deliverList.add(deliveredPI);

        sms.sendMultipartTextMessage(phoneNumber, null, parts, sendList, deliverList);

        //we unsubscribed in 10 seconds
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                activity.unregisterReceiver(smsUtils);
            }
        }, 10000);

    }

}
