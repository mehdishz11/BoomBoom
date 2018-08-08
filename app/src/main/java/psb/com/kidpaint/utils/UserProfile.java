package psb.com.kidpaint.utils;
/*
 * Created by AMiR Ehsan on 7/22/2017.
 */

import android.content.Context;
import android.content.SharedPreferences;

import psb.com.kidpaint.webApi.register.registerUserInfo.model.UserInfo;


public class UserProfile {

    private static Context context;

    private static String KEY_USER = "KEY_USER";

    private static String KEY_PHONE_NUMBER = "KEY_PHONE_NUMBER";
    private static String KEY_FIRST_NAME = "KEY_FIRST_NAME";
    private static String KEY_LAST_NAME = "KEY_LAST_NAME";
    private static String KEY_IMG_URL = "KEY_IMG_URL";
    private static String KEY_FCM = "KEY_FCM";
    private static String KEY_JWT = "KEY_JWT";
    private static String KEY_SEX = "KEY_SEX";
    private static String KEY_BRITH_DAY = "KEY_BRITH_DAY";
    private static String KEY_USER_RANK = "KEY_USER_RANK";
    private static String KEY_USER_SCORE = "KEY_USER_SCORE";
    private static String KEY_USER_LEVEL = "KEY_USER_LEVEL";
    private static String KEY_SHOW_FIRST_SCORE_PACKAGE_IN_PAINT_ACTIVITY = "KEY_SHOW_FIRST_SCORE_PACKAGE_IN_PAINT_ACTIVITY";

    ///////////////////////////////////////////////////////////////////////////
    // getter
    ///////////////////////////////////////////////////////////////////////////


    public UserProfile(Context context){
        this.context = context;
    }

    public  void set_KEY_USER_INFO(UserInfo userInfo) {
        SharedPreferences settings = context.getSharedPreferences(KEY_USER, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(KEY_FIRST_NAME, userInfo.getFirstName());
        editor.putString(KEY_LAST_NAME, userInfo.getLastName());
        editor.putString(KEY_PHONE_NUMBER, userInfo.getPhoneNumber());
        editor.putString(KEY_IMG_URL, userInfo.getImageUrl());
        editor.putString(KEY_BRITH_DAY, userInfo.getBirthDay());
        editor.putBoolean(KEY_SEX, userInfo.getMale());
        editor.putInt(KEY_USER_RANK, userInfo.getRank());
        editor.putInt(KEY_USER_SCORE, userInfo.getScore());
        editor.putInt(KEY_USER_LEVEL, userInfo.getLevel());
        editor.apply();
    }

    public  void set_KEY_PHONE_NUMBER(String value) {
        SharedPreferences settings = context.getSharedPreferences(KEY_USER, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(KEY_PHONE_NUMBER, value);
        editor.apply();
    }
    public  void set_KEY_SHOW_FIRST_SCORE_PACKAGE_IN_PAINT_ACTIVITY(boolean value) {
        SharedPreferences settings = context.getSharedPreferences(KEY_USER, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean(KEY_SHOW_FIRST_SCORE_PACKAGE_IN_PAINT_ACTIVITY, value);
        editor.apply();
    }

    public  void set_KEY_KEY_FIRST_NAME(String value) {
        SharedPreferences settings = context.getSharedPreferences(KEY_USER, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(KEY_FIRST_NAME, value);
        editor.apply();
    }

    public  void set_KEY_LAST_NAME(String value) {
        SharedPreferences settings = context.getSharedPreferences(KEY_USER, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(KEY_LAST_NAME, value);
        editor.apply();
    }

    public  void set_KEY_IMG_URL(String value) {
        SharedPreferences settings = context.getSharedPreferences(KEY_USER, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(KEY_IMG_URL, value);
        editor.apply();
    }

    public  void set_KEY_FCM(String value) {
        SharedPreferences settings = context.getSharedPreferences(KEY_USER, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(KEY_FCM, value);
        editor.apply();
    }
    public  void set_KEY_BIRTH_DAY(String value) {
        SharedPreferences settings = context.getSharedPreferences(KEY_USER, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(KEY_BRITH_DAY, value);
        editor.apply();
        editor.commit();
    }

    public  void set_KEY_JWT(String value) {
        SharedPreferences settings = context.getSharedPreferences(KEY_USER, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(KEY_JWT, value);
        editor.apply();
    }
    public  void set_KEY_SCORE(int value) {
        SharedPreferences settings = context.getSharedPreferences(KEY_USER, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt(KEY_USER_SCORE, value);
        editor.apply();
    }
    public  void set_KEY_LEVEL(int value) {
        SharedPreferences settings = context.getSharedPreferences(KEY_USER, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt(KEY_USER_LEVEL, value);
        editor.apply();
    }


    public  void REMOVE_KEY_USER_INFO() {
        SharedPreferences settings = context.getSharedPreferences(KEY_USER, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.remove(KEY_FIRST_NAME);
        editor.remove(KEY_LAST_NAME);
        editor.remove(KEY_PHONE_NUMBER);
        editor.remove(KEY_IMG_URL);
        editor.remove(KEY_JWT);
        editor.remove(KEY_FCM);
        editor.remove(KEY_BRITH_DAY);
        editor.remove(KEY_SEX);
        editor.remove(KEY_USER_SCORE);
        editor.remove(KEY_USER_LEVEL);
        editor.remove(KEY_SHOW_FIRST_SCORE_PACKAGE_IN_PAINT_ACTIVITY);
        editor.apply();
    }

    ///////////////////////////////////////////////////////////////////////////
    // setter
    ///////////////////////////////////////////////////////////////////////////

    public  String get_KEY_PHONE_NUMBER(String defeult) {
        SharedPreferences settings = context.getSharedPreferences(KEY_USER, Context.MODE_PRIVATE);
        return settings.getString(KEY_PHONE_NUMBER, defeult);
    }

    public  String get_KEY_FIRST_NAME(String defeult) {
        SharedPreferences settings = context.getSharedPreferences(KEY_USER, Context.MODE_PRIVATE);
        return settings.getString(KEY_FIRST_NAME, defeult);
    }

    public  String get_KEY_LAST_NAME(String defeult) {
        SharedPreferences settings = context.getSharedPreferences(KEY_USER, Context.MODE_PRIVATE);
        return settings.getString(KEY_LAST_NAME, defeult);
    }

    public  String get_KEY_IMG_URL(String defeult) {
        SharedPreferences settings = context.getSharedPreferences(KEY_USER, Context.MODE_PRIVATE);
        return settings.getString(KEY_IMG_URL, defeult);
    }

    public  String get_KEY_FCM(String defeult) {
        SharedPreferences settings = context.getSharedPreferences(KEY_USER, Context.MODE_PRIVATE);
        return settings.getString(KEY_FCM, defeult);
    }

    public  String get_KEY_JWT(String defeult) {
        SharedPreferences settings = context.getSharedPreferences(KEY_USER, Context.MODE_PRIVATE);
        return settings.getString(KEY_JWT, defeult);
    }

    public  String get_KEY_BRITH_DAY(String defeult) {
        SharedPreferences settings = context.getSharedPreferences(KEY_USER, Context.MODE_PRIVATE);
        return settings.getString(KEY_BRITH_DAY, defeult);
    }
    public  boolean get_KEY_SEX(boolean defeult) {
        SharedPreferences settings = context.getSharedPreferences(KEY_USER, Context.MODE_PRIVATE);
        return settings.getBoolean(KEY_SEX, defeult);
    }
    public  Integer get_KEY_RANK(int defeult) {
        SharedPreferences settings = context.getSharedPreferences(KEY_USER, Context.MODE_PRIVATE);
        return settings.getInt(KEY_USER_RANK, defeult);
    }
    public  Integer get_KEY_SCORE(int defeult) {
        SharedPreferences settings = context.getSharedPreferences(KEY_USER, Context.MODE_PRIVATE);
        return settings.getInt(KEY_USER_SCORE, defeult);
    }

  public  Integer get_KEY_LEVEL(int defalt) {
        SharedPreferences settings = context.getSharedPreferences(KEY_USER, Context.MODE_PRIVATE);
        return settings.getInt(KEY_USER_LEVEL, defalt);
    }

   public  boolean get_KEY_SHOW_FIRST_SCORE_PACKAGE_IN_PAINT_ACTIVITY(boolean defeult) {
        SharedPreferences settings = context.getSharedPreferences(KEY_USER, Context.MODE_PRIVATE);
        return settings.getBoolean(KEY_SHOW_FIRST_SCORE_PACKAGE_IN_PAINT_ACTIVITY, defeult);
    }

}