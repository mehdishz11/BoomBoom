package psb.com.kidpaint;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.multidex.MultiDex;

import com.helper.PaymentHelper;
import com.rasa.statistics.Statistics;

import psb.com.kidpaint.activityMessage.MessageActivity;
import psb.com.kidpaint.home.HomeActivity_2;

public class App extends Application {
    protected static Context context = null;

    public static final String TAG = "KidPainting";

    public static final String appCode = "1557";
    public static final String productCode = "boom970619";
    public static final String irancellSku = "boomboomdorsa";


    public static final int MARKET_ID = BuildConfig.marketerId;


    public static boolean isHomeActivityStarted = false;
    public static boolean isMessagingActivityRunn = false;

    public static final boolean isEnableIrancell=true;

    public static String sharedUrl="http://www.getBoomBoom.ir/BoomBoom.apk";

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();

        if(BuildConfig.marketerId==2){
            sharedUrl="http://www.2rsa.ir/BoomBoom.apk";
        }

        new PaymentHelper().init(getContext());


        new Statistics(context, MARKET_ID).install();


        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                if (activity instanceof HomeActivity_2) {
                    AudioManager mgr = (AudioManager) activity.getSystemService(Context.AUDIO_SERVICE);
                    mgr.setStreamVolume(AudioManager.STREAM_MUSIC, mgr.getStreamMaxVolume(AudioManager.STREAM_MUSIC), 0);
                }
            }

            @Override
            public void onActivityStarted(Activity activity) {
                if (activity instanceof HomeActivity_2) {
                    isHomeActivityStarted = true;
                    activity.setVolumeControlStream(AudioManager.STREAM_MUSIC);
               /* AudioManager audioManager = (AudioManager)activity.getSystemService(Context.AUDIO_SERVICE);
                audioManager.setMode(AudioManager.STREAM_MUSIC);*/

                } else if (activity instanceof MessageActivity) {
                    isMessagingActivityRunn = true;
                }

            }

            @Override
            public void onActivityResumed(Activity activity) {

            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                if (activity instanceof HomeActivity_2) {
                    isHomeActivityStarted = false;
                } else if (activity instanceof MessageActivity) {
                    isMessagingActivityRunn = false;
                }
            }
        });

    }


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public static Context getContext() {
        return context;
    }
}