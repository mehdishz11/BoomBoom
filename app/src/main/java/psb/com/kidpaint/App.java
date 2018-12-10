package psb.com.kidpaint;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.support.multidex.MultiDex;
import android.util.Log;

import com.helper.PaymentHelper;
import com.rasa.statistics.Statistics;

import psb.com.kidpaint.home.HomeActivity_2;

public class App extends Application {
    protected static Context context = null;

    public static final String TAG="KidPainting";

    public static final  String appCode = "1557";
    public static final  String productCode = "boom970619";
    public static final  String irancellSku = "boomboomdorsa";

    private static final int MERKET_ID_RASA_VAS=2;
    private static final int MERKET_ID_COFEBAZAAR=3;

    public static final int MERKETER_ID=MERKET_ID_RASA_VAS;


    public static boolean isHomeActivityStarted=false;

    private ActivityLifecycleCallbacks activityLifecycleCallbacks;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        new PaymentHelper().init(getContext());


        new Statistics(context,MERKETER_ID).install();



        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

            }

            @Override
            public void onActivityStarted(Activity activity) {
            if(activity instanceof HomeActivity_2){
                isHomeActivityStarted=true;
                Log.d(TAG, "onActivityStarted: true");
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
                if(activity instanceof HomeActivity_2){
                    isHomeActivityStarted=false;
                    Log.d(TAG, "onActivityStarted: false Stopped");
                }
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                if(activity instanceof HomeActivity_2){
                    isHomeActivityStarted=false;
                    Log.d(TAG, "onActivityStarted: false Destroy");
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