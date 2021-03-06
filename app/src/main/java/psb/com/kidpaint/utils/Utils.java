package psb.com.kidpaint.utils;
/*
 * Created by AMiR Ehsan on 7/22/2017.
 */

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.Settings;
import androidx.core.content.ContextCompat;

import com.helper.PaymentHelper;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import psb.com.kidpaint.App;

public class Utils {
    public static boolean activitymyMessageIsRunning = false;

    public static final int RECORD_REQUEST_CODE = 101;
    public static String keyPer = "listPos";
    public static String KEY_SETTING = "KEY_SETTING";
    public static String KEY_REGISTER = "KEY_REGISTER";
    public static String KEY_AUTO_UPDATE = "KEY_AUTO_UPDATE";


    public static String KEY_DAY_COUNT = "KEY_DAY_COUNT";
    public static String reference_code = "reference_code";
    public static String KEY_QUESTION = "KEY_QUESTION";
    public static String KEY_PHONENUMBER = "KEY_PHONENUMBER";
    public static String KEY_JWT = "KEY_JWT";


    public static String KEY_PLAYER = "KEY_PLAYER";
    public static String KEY_CURRENT_POSITION = "KEY_CURRENT_POSITION";
    public static String FCM_BROADCAST_CHAT = "FCM_BROADCAST_CHAT";

    public static final String ALLOWED_URI_CHARS = "@#&=*+-_.,:!?()/~'%20";

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

    public static void closeKeyboard() {

    }

    public static String LongToCurrency(Long number) {
        return String.format("%,d", number);
    }


    public static String LongToCurrency(int number) {
        return String.format("%,d", number);
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

    public static boolean isNumberMci(String phoneNumber) {
        phoneNumber = phoneNumber.
                replace("۰", "0").
                replace("۱", "1").
                replace("۲", "2").
                replace("۳", "3").
                replace("۴", "4").
                replace("۵", "5").
                replace("۶", "6").
                replace("۷", "7").
                replace("۸", "8").
                replace("۹", "9");
        if (phoneNumber.startsWith("091") ||
                phoneNumber.startsWith("099")
                ) {
            return true;
        } else {
            return false;
        }
    }

    public static String getDeviceId(Context context) {
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID) + 2;
    }

    public static String getIraninTimeCommentSlash(String time) {

        String result = "";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(simpleDateFormat.parse(time));
            CalendarTool calendarTool = new CalendarTool(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH));
            result = calendarTool.getIranianDay() + "/" + calendarTool.getIranianMonth() + "/" + calendarTool.getIranianYear();

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String getCurrentTime() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String last_change_time = sdf.format(c.getTime());
        return last_change_time;
    }


    public static int getDeviceWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    public static int getDisplayHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;

    }

    public static boolean isPackageExisted(String targetPackage) {
        PackageManager pm = App.getContext().getPackageManager();
        try {
            PackageInfo info = pm.getPackageInfo(targetPackage, PackageManager.GET_META_DATA);
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
        return true;
    }

    public static boolean writeNoMediaFile(File directoryPath) {


        try {
            File noMedia = new File(directoryPath, ".nomedia");

            if (noMedia.exists()) {
                return true;
            }
            noMedia.createNewFile();

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }


        return true;

    }

    public static boolean isAgrigator() {
        return PaymentHelper.isAgrigator();
    }


}