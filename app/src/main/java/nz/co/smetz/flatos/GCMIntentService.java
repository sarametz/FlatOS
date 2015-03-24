package nz.co.smetz.flatos;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;

/**
 * GCM Intent service
 * Receives GCM intents
 * Created by Sara on 3/3/2015.
 */
public class GCMIntentService extends IntentService {
    private static final String TAG = "GCMIntentService";
    public static final int NOTIFICATION_ID = 1;

    public GCMIntentService() {
        super("GCMIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Bundle extras = intent.getExtras();
        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
        // The getMessageType() intent parameter must be the intent you received
        // in your BroadcastReceiver.
        String messageType = gcm.getMessageType(intent);

        if (!extras.isEmpty()) {  // has effect of unparcelling Bundle
            /*
             * Filter messages based on message type. Since it is likely that GCM
             * will be extended in the future with new message types, just ignore
             * any message types you're not interested in, or that you don't
             * recognize.
             */
            switch (messageType){
                case GoogleCloudMessaging.MESSAGE_TYPE_SEND_ERROR:
                    Log.d("GCM Send error: " , extras.toString());
                    break;
                case GoogleCloudMessaging.MESSAGE_TYPE_DELETED:
                    Log.d("Deleted GCM messages:" ,extras.toString());
                    break;
                case GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE:
                    // Post notification of received message.
                    sendNotification(extras.getString("message",""));
                    Log.i(TAG, "Received: " + extras.toString());
                    break;
                default:
                    Log.e(TAG, "Unknown messageType "+messageType+": "+extras.toString());
            }
        }
        // Release the wake lock provided by the WakefulBroadcastReceiver.
        GCMBroadcastReceiver.completeWakefulIntent(intent);
    }

    // Put the message into a notification and post it.
    // This is just one simple example of what you might choose to do with
    // a GCM message.
    private void sendNotification(String title) {
        NotificationManager mNotificationManager = (NotificationManager)
                this.getSystemService(Context.NOTIFICATION_SERVICE);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        WifiManager wm = (WifiManager)this.getSystemService(Context.WIFI_SERVICE);
        String currWifi = wm.getConnectionInfo().getSSID().substring(1,wm.getConnectionInfo().getSSID().length()-1);
        String wifiSetting = prefs.getString("notification_limit_wifi", "None");
        Log.d(TAG, "Wifi Setting SSID:"+wifiSetting+" current:"+currWifi);
        if (prefs.getBoolean("notifications", true) && (currWifi.equals(wifiSetting) || wifiSetting.equals("None"))) {
            Log.d(TAG, "Notifications on");
            PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                    new Intent(this, MainActivity.class), 0);

            NotificationCompat.Builder mBuilder =
                    new NotificationCompat.Builder(this)
                            .setSmallIcon(R.drawable.ic_stat_planet_express)
                            .setContentTitle(title)
                            .setStyle(new NotificationCompat.BigTextStyle()
                                    .bigText(""))
                            .setContentText("");
            mBuilder.setLights(Color.CYAN, 3000, 3000);
            if (prefs.getBoolean("notification_vibrate", true)) {
                mBuilder.setVibrate(new long[]{1000, 1000, 1000, 1000, 1000});
            } else {
                Log.d(TAG, "Vibrate OFF");
            }
            String tone = prefs.getString("notification_tone", "");
            Log.d(TAG, "Tone:"+tone);
            Uri uri = Uri.parse(tone);
            mBuilder.setSound(uri);

            mBuilder.setContentIntent(contentIntent);
            mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
        } else {
            Log.d(TAG, "Not sending notification as notifications OFF or not connected to "+wifiSetting);
        }
    }
}

