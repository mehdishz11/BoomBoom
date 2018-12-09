package psb.com.kidpaint;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.helper.PaymentHelper;
import com.rasa.statistics.Statistics;

import net.jhoobin.jhub.CharkhoneSdkApp;

import ir.dorsa.totalpayment.tools.Utils;

public class App extends Application {
    protected static Context context = null;

    public static final String TAG="KidPainting";

    public static final  String appCode = "1557";
    public static final  String productCode = "boom970619";
    public static final  String irancellSku = "boomboomdorsa";

    public static final int MERKETER_ID=2;


    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        new PaymentHelper().init(getContext());
        try {
            CharkhoneSdkApp.initSdk(getApplicationContext(), Utils.getSecrets(this));
        } catch (Exception ex) {

        }

        new Statistics(context,MERKETER_ID).install();

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