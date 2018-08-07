package psb.com.kidpaint.utils.checkInternet;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import psb.com.kidpaint.utils.UserProfile;


/**
 * Created by Nmorteza on 11/12/2016.
 */

public class ServiceCheckInternet extends BroadcastReceiver {
    private Context context;
    private UserProfile userProfile;
    @Override
    public void onReceive(final Context context, final Intent intent) {
         this.context=context;
         userProfile=new UserProfile(context);
        String status = NetworkUtil.getConnectivityStatusString(context);
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null) { // connected to the internet
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                // connected to wifi
                Log.d("Network Available ", "WIFI YES");



            } else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                Log.d("Network Available ", "MOBILE YES");



            }
        } else {
            // not connected to the internet
            Log.d("Network Available ", "NO");
        }

    }





}
