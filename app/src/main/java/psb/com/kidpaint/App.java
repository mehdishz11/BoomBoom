package psb.com.kidpaint;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.helper.PaymentHelper;
import com.rasa.statistics.Statistics;

public class App extends Application {
    protected static Context context = null;

    public static final String TAG="KidPainting";

    public static final  String appCode = "1557";
    public static final  String productCode = "boom970619";
    public static final  String irancellSku = "boomboomdorsa";

    private static final int MERKET_ID_RASA_VAS=2;
    private static final int MERKET_ID_COFEBAZAAR=3;

    public static final int MERKETER_ID=MERKET_ID_RASA_VAS;



    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        new PaymentHelper().init(getContext());


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