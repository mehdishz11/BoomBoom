package psb.com.kidpaint.utils;
/*
 * Created by AMiR Ehsan on 7/22/2017.
 */

import android.Manifest;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.List;

import psb.com.kidpaint.App;

import static android.content.Context.ACTIVITY_SERVICE;

public class Utils {

    public static final int RECORD_REQUEST_CODE = 101;
    public static String keyPer = "listPos";
    public static String KEY_SETTING = "KEY_SETTING";
    public static String KEY_REGISTER = "KEY_REGISTER";
    public static String KEY_AUTO_UPDATE = "KEY_AUTO_UPDATE";





    public static String KEY_DAY_COUNT = "KEY_DAY_COUNT";
    public static String reference_code = "reference_code";
    public static String KEY_QUESTION = "KEY_QUESTION";
    public static String KEY_PHONENUMBER = "KEY_PHONENUMBER";
    public static String KEY_FCM = "KEY_FCM";
    public static String KEY_JWT = "KEY_JWT";


    public static String KEY_PLAYER = "KEY_PLAYER";
    public static String KEY_CURRENT_POSITION = "KEY_CURRENT_POSITION";


    public static void setIntegerPreference(Context context, String masterKey, String key, int value) {
        SharedPreferences settings = context.getSharedPreferences(masterKey, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public static int getIntegerPreference(Context context, String masterKey, String key, int defeult) {
        SharedPreferences settings = context.getSharedPreferences(masterKey, Context.MODE_PRIVATE);
        return settings.getInt(key, defeult);
    }

    public static void setStringPreference(Context context, String masterKey, String key, String value) {
        SharedPreferences settings = context.getSharedPreferences(masterKey, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static String getStringPreference(Context context, String masterKey, String key, String defeult) {
        SharedPreferences settings = context.getSharedPreferences(masterKey, Context.MODE_PRIVATE);
        return settings.getString(key, defeult);
    }

    public static void setBooleanPreference(Context context, String masterKey, String key, boolean value) {
        SharedPreferences settings = context.getSharedPreferences(masterKey, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public static boolean getBooleanPreference(Context context, String masterKey, String key, boolean defeult) {
        SharedPreferences settings = context.getSharedPreferences(masterKey, Context.MODE_PRIVATE);
        return settings.getBoolean(key, defeult);
    }

    public static boolean getPermission(Activity activity) {
        int ACCESS_FINE_LOCATION = ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION);
        int ACCESS_COARSE_LOCATION = ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION);
        int READ_EXTERNAL_STORAGE = ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE);
        int WRITE_EXTERNAL_STORAGE = ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (ACCESS_FINE_LOCATION != PackageManager.PERMISSION_GRANTED ||
                ACCESS_COARSE_LOCATION != PackageManager.PERMISSION_GRANTED ||
                READ_EXTERNAL_STORAGE != PackageManager.PERMISSION_GRANTED ||
                WRITE_EXTERNAL_STORAGE != PackageManager.PERMISSION_GRANTED) {
            return false;
        }
        return true;
    }

    public static boolean getSmsPermission(Activity activity) {
        int READ_SMS = ContextCompat.checkSelfPermission(activity, Manifest.permission.RECEIVE_SMS);
        if (READ_SMS != PackageManager.PERMISSION_GRANTED) {
            return false;
        }
        return true;
    }

    public static boolean gstoragePermissionIsGranted(Context context) {
        int READ_EXTERNAL_STORAGE = ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE);
        int WRITE_EXTERNAL_STORAGE = ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (READ_EXTERNAL_STORAGE != PackageManager.PERMISSION_GRANTED || WRITE_EXTERNAL_STORAGE != PackageManager.PERMISSION_GRANTED) {
            return false;
        }
        return true;
    }

    public static void requestPermission(Activity activity) {
        ActivityCompat.requestPermissions(activity,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE},
                RECORD_REQUEST_CODE);
    }

    public static boolean isPackageInstalled(String packageName, PackageManager packageManager) {
        try {
            packageManager.getPackageInfo(packageName, 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

 /*   public static void createNotification(final Context context, final int key, final String title, final String messageBody, final String imageUr, final int icon, String extra) {
        Intent intent = new Intent(context , ActivityHome.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        if(extra!=null){
            if(extra.contains("http") || extra.contains("https")){
                intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(extra));
            }else{
                intent.putExtra(extra,true);
            }
        }

        final PendingIntent resultIntent = PendingIntent.getActivity( context , 0, intent,
                PendingIntent.FLAG_ONE_SHOT);
        final Uri notificationSoundURI = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        new Thread(new Runnable() {
            @Override
            public void run() {
                NotificationCompat.Builder   mNotificationBuilder;
                mNotificationBuilder = new NotificationCompat.Builder( context)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle(title)
                        .setContentText(messageBody)
                        .setColor(context.getResources().getColor(R.color.md_black_1000))
                        .setColorized(true)
                        .setAutoCancel(true)
                        .setDefaults(DEFAULT_SOUND | DEFAULT_VIBRATE)
                        .setSound(notificationSoundURI)
                        .setContentIntent(resultIntent);

                Bitmap imageBitmap = null;
                if (imageUr != null && !imageUr.equals("")){
                    imageBitmap=getBitmapFromURL(imageUr);
                }

                if(imageBitmap!=null){
                    mNotificationBuilder.setStyle(new NotificationCompat.BigPictureStyle()
                            .bigPicture(imageBitmap)
                            .setSummaryText(messageBody)
                            .setBigContentTitle(title));
                }

                NotificationManager notificationManager =(NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.notify(key, mNotificationBuilder.build());
            }
        }).start();
    }
*/
    public static Bitmap getBitmapFromURL(String strURL) {
        try {
            URL url = new URL(strURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean isNumberMci(String phoneNumber){
        phoneNumber=phoneNumber.
                replace("۰","0").
                replace("۱","1").
                replace("۲","2").
                replace("۳","3").
                replace("۴","4").
                replace("۵","5").
                replace("۶","6").
                replace("۷","7").
                replace("۸","8").
                replace("۹","9");
        if (phoneNumber.startsWith("091") ||
                phoneNumber.startsWith("099")
                ){
            return true;
        }else{
            return false;
        }
    }

    public static String getDeviceId(Context context) {
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID) + 2;
    }








}