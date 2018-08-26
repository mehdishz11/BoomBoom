package psb.com.kidpaint.utils;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.StrictMode;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.text.format.DateUtils;
import android.util.Log;
import android.widget.RemoteViews;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import psb.com.kidpaint.R;


public class NotificationCreator {

    public static void clearNotifications(Context context){
        NotificationManager notificationManager;
        notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancelAll();
    }

    public static void showTextNotification(Context context,Intent intent,int notificationId,int smallIcon,String contentTitle,String contentText){

        PackageManager pm = context.getPackageManager();
        NotificationCompat.Builder notificationBuilder;
        NotificationManager notificationManager;
        notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationBuilder = new NotificationCompat.Builder(context, "CHANEL_ID_1")
                .setSmallIcon(R.mipmap.icn_notification)
                .setColor(ContextCompat.getColor(context,R.color.colorAccent))
                .setColorized(true)
                .setSound(getSoundEffectUri(context))
                .setAutoCancel(true);



        if (intent!=null) {
             TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
             stackBuilder.addNextIntentWithParentStack(intent);
             PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
             notificationBuilder.setContentIntent(resultPendingIntent);


        }

        notificationBuilder.setProgress(0, 0, false);
        if (contentTitle!=null &&!contentTitle.isEmpty()) {
            notificationBuilder.setContentTitle(contentTitle);
        }
        if (contentText!=null &&!contentText.isEmpty()) {
            notificationBuilder.setContentText(contentText);
        }
        notificationManager.notify(notificationId, notificationBuilder.build());

    }

    public static void showBigTextStyleNotification(Context context,Intent intent,int notificationId,int smallIcon,String contentTitle,String contentText,String bigText){
        PackageManager pm = context.getPackageManager();
        NotificationCompat.Builder notificationBuilder;
        NotificationManager notificationManager;
        notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationBuilder = new NotificationCompat.Builder(context, "CHANEL_ID_1")
                .setSmallIcon(R.mipmap.icn_notification)
                .setColor(ContextCompat.getColor(context,R.color.colorAccent))
                .setColorized(true)
                .setSound(getSoundEffectUri(context))
                .setAutoCancel(true);




        if (intent!=null) {
             TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
             stackBuilder.addNextIntentWithParentStack(intent);
             PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
             notificationBuilder.setContentIntent(resultPendingIntent);


        }

        notificationBuilder.setProgress(0, 0, false);
        if (contentTitle!=null &&!contentTitle.isEmpty()) {
             notificationBuilder.setContentTitle(contentTitle);
        }
        if (contentText!=null &&!contentText.isEmpty()) {
             notificationBuilder.setContentText(contentText);
        }
        notificationBuilder.setStyle(new android.support.v4.app.NotificationCompat.BigTextStyle()
                .bigText(bigText));
        notificationManager.notify(notificationId, notificationBuilder.build());

    }

    public static void showBigPictureStyleNotification(Context context,Intent intent,int notificationId,int smallIcon,String contentTitle,String contentText,String bigImageUrl){
        PackageManager pm = context.getPackageManager();
        NotificationCompat.Builder notificationBuilder;
        NotificationManager notificationManager;
        notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationBuilder = new NotificationCompat.Builder(context, "CHANEL_ID_1")

                .setSound(getSoundEffectUri(context))
                .setSmallIcon(R.mipmap.icn_notification)
                .setColor(ContextCompat.getColor(context,R.color.colorAccent))
                .setColorized(true)
                .setAutoCancel(true);




        if (intent!=null) {
             TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
             stackBuilder.addNextIntentWithParentStack(intent);
             PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
             notificationBuilder.setContentIntent(resultPendingIntent);


        }

        notificationBuilder.setProgress(0, 0, false);
        if (contentTitle!=null &&!contentTitle.isEmpty()) {
             notificationBuilder.setContentTitle(contentTitle);
        }
        if (contentText!=null &&!contentText.isEmpty()) {
             notificationBuilder.setContentText(contentText);
        }
        if (bigImageUrl!=null && !bigImageUrl.isEmpty()) {
            Log.d("TAg", "showBigPictureStyleNotification: ");
             Bitmap bitmap=getBitmapFromURL(bigImageUrl);
             notificationBuilder.setStyle(new NotificationCompat.BigPictureStyle()
                     .bigPicture(bitmap));
        }
        notificationManager.notify(notificationId, notificationBuilder.build());

    }


     public static void showCustomNotification(Context context,Intent intent,int notificationId,int smallIcon,String contentTitle,String contentText) {

          RemoteViews expandedView = new RemoteViews(context.getPackageName(), R.layout.view_expanded_notification);
          expandedView.setTextViewText(R.id.timestamp, DateUtils.formatDateTime(context, System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME));
          expandedView.setTextViewText(R.id.notification_message, contentText);
          // adding action to left button


        /*  Intent leftIntent = new Intent(context, NotificationIntentService.class);
          leftIntent.setAction("left");
          expandedView.setOnClickPendingIntent(R.id.left_button, PendingIntent.getService(context, 0, leftIntent, PendingIntent.FLAG_UPDATE_CURRENT));
          // adding action to right button
          Intent rightIntent = new Intent(context, NotificationIntentService.class);
          rightIntent.setAction("right");
          expandedView.setOnClickPendingIntent(R.id.right_button, PendingIntent.getService(context, 1, rightIntent, PendingIntent.FLAG_UPDATE_CURRENT));
*/
          RemoteViews collapsedView = new RemoteViews(context.getPackageName(), R.layout.view_collapsed_notification);
          collapsedView.setTextViewText(R.id.timestamp, DateUtils.formatDateTime(context, System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME));

          NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "CHANEL_ID_1")
                  // these are the three things a NotificationCompat.Builder object requires at a minimum

                  .setSmallIcon(R.mipmap.icn_notification)
                  .setColor(ContextCompat.getColor(context,R.color.colorAccent))
                  .setColorized(true)

                  .setSound(getSoundEffectUri(context))
                  .setContentTitle(contentTitle)
                  .setContentText(contentText)
                  // notification will be dismissed when tapped
                  .setAutoCancel(true)
                  // tapping notification will open MainActivity

                  // setting the custom collapsed and expanded views
                  .setCustomContentView(collapsedView)
                  .setCustomBigContentView(expandedView)
                  // setting style to DecoratedCustomViewStyle() is necessary for custom views to display
                  .setStyle(new NotificationCompat.DecoratedCustomViewStyle());

          // retrieves android.app.NotificationManager


          if (intent!=null) {
               TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
               stackBuilder.addNextIntentWithParentStack(intent);
               PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
               builder.setContentIntent(resultPendingIntent);


          }
          NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

          notificationManager.notify(notificationId, builder.build());
     }

     public static Bitmap getBitmapFromURL(String strURL) {
          try {
               StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                       .permitAll().build();
               StrictMode.setThreadPolicy(policy);
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

     private static Uri getSoundEffectUri(Context context){
         return Uri.parse("android.resource://"+context.getPackageName()+"/raw/owl_small");
     }

}
