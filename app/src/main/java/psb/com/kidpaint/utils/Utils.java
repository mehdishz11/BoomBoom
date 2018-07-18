package psb.com.kidpaint.utils;
/*
 * Created by AMiR Ehsan on 7/22/2017.
 */

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.Settings;
import android.support.v4.content.ContextCompat;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

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


    public static void closeKeyboard(){

    }

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

    public static String getIraninTimeCommentSlash(String time) {

        String result = "";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(simpleDateFormat.parse(time));
            CalendarTool calendarTool = new CalendarTool(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH));
            result = calendarTool.getIranianDay()+ " " + calendarTool.getIranianMonthName() + " "+calendarTool.getIranianYear();

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }








}