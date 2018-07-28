package psb.com.kidpaint.utils.firebase;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;


import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import psb.com.kidpaint.App;


/**
 * Created by mehdi on 8/15/16.
 */
public class ServiceGetMessage extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(final RemoteMessage remoteMessage) {
        Log.d(App.TAG, "FCM ----------> getMessage");
        Log.d(App.TAG, "FCM " + remoteMessage.getData().toString());
        for (Map.Entry<String, String> entry : remoteMessage.getData().entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            Log.d("Fcm", "hash map key:" + key + ":" + value);
        }
        JSONObject result = null;

        try {
            result = new JSONObject(remoteMessage.getData().get("message"));

            if ("ChangeStatus".equals(result.getString("mode"))) {

                final JSONObject finalResult1 = result;
               // String message ="فاکتور به شماره "+finalResult1.getInt("FallowingCode")+" "+ FactorStatus.getNotificationStatus(finalResult1.getInt("Status"));



            }else if ("newMessage".equals(result.getString("mode"))) {


            }else if ("Push".equals(result.getString("mode"))) {

            }else if ("RemovePush".equals(result.getString("mode"))) {

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }//

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        Log.e("NEW_TOKEN",s);
    }


}
