package psb.com.kidpaint.utils.firebase;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;


import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import psb.com.kidpaint.App;
import psb.com.kidpaint.R;
import psb.com.kidpaint.myMessages.ActivityMyMessages;
import psb.com.kidpaint.utils.NotificationCreator;
import psb.com.kidpaint.utils.firebase.model.Push;


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
        Gson gson = new Gson();
        Intent intent ;

        try {
            result = new JSONObject(remoteMessage.getData().get("message"));
            Log.d("TAG", "onMessageReceived: "+new Gson().toJson(result));
            if ("Push".equals(result.getString("Mode"))) {
                Push push = gson.fromJson(String.valueOf(result), Push.class);
                if (!push.getImageUrl().isEmpty() && !push.getBody().isEmpty()) {//showBigPictureStyleNotification
                    Log.d("TAG", "onMessageReceived 1: ");
                    if (push.getUrl().isEmpty()) {
                        intent = new Intent(App.getContext(), ActivityMyMessages.class);
                    }else{
                        intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse(push.getUrl()));
                    }
                    NotificationCreator.showBigPictureStyleNotification(App.getContext(),intent,push.getId(), R.mipmap.ic_launcher,push.getTitle(),push.getBody(),push.getImageUrl());

                }else if (!push.getImageUrl().isEmpty() && push.getBody().isEmpty()) {//showBigPictureStyleNotification
                    Log.d("TAG", "onMessageReceived 2: ");

                    if (push.getUrl().isEmpty()) {
                        intent = new Intent(App.getContext(), ActivityMyMessages.class);
                    }else{
                        intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse(push.getUrl()));
                    }
                    NotificationCreator.showBigPictureStyleNotification(App.getContext(),intent,push.getId(), R.mipmap.ic_launcher,push.getTitle(),push.getBody(),push.getImageUrl());

                }
                else if (push.getImageUrl().isEmpty() && !push.getBody().isEmpty()) {//showBigTextStyleNotification
                    Log.d("TAG", "onMessageReceived 3: ");

                    if (push.getUrl().isEmpty()) {
                        intent = new Intent(App.getContext(), ActivityMyMessages.class);
                    }else{
                        intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse(push.getUrl()));
                    }
                    NotificationCreator.showBigTextStyleNotification(App.getContext(),intent,push.getId(), R.mipmap.ic_launcher,push.getTitle(),null,push.getBody());

                }else if (push.getImageUrl().isEmpty() && push.getBody().isEmpty()) {//showTextNotification
                    Log.d("TAG", "onMessageReceived 4: ");

                    if (push.getUrl().isEmpty()) {
                        intent = new Intent(App.getContext(), ActivityMyMessages.class);
                    }else{
                        intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse(push.getUrl()));
                    }
                    NotificationCreator.showTextNotification(App.getContext(),intent,push.getId(), R.mipmap.ic_launcher,push.getTitle(),null);

                }


            }else if ("Chat".equals(result.getString("mode"))) {
                Push push = gson.fromJson(String.valueOf(result), Push.class);
                if (push.getUrl().isEmpty()) {
                    intent = new Intent(App.getContext(), ActivityMyMessages.class);
                }else{
                    intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(push.getUrl()));
                }
                NotificationCreator.showTextNotification(App.getContext(),intent,push.getId(), R.mipmap.ic_launcher,push.getTitle(),null);


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
