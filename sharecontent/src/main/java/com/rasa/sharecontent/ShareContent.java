package com.rasa.sharecontent;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import androidx.core.content.FileProvider;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

public class ShareContent {

    private Context context;

    public ShareContent(Context context) {
        this.context = context;
    }

    public Intent getIntentShareImage(Bitmap imageBitmap,String message) {
        Uri bmpUri = getBitmapFromDrawable(imageBitmap);
        if (bmpUri != null) {
            // Construct a ShareIntent with link to image
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.putExtra(Intent.EXTRA_TEXT, message);
            shareIntent.putExtra(Intent.EXTRA_STREAM, bmpUri);
            shareIntent.setType("image/*");
            // Launch sharing dialog for image

            return Intent.createChooser(shareIntent, "ارسال توسط ؟");
        } else {
            Toast.makeText(context, context.getString(R.string.msg_error_share), Toast.LENGTH_SHORT).show();
        }

        return null;
    }

    public void doShareContent(String url,final String message) {
        final ProgressDialog pDialog = new ProgressDialog(context);
        pDialog.setMessage("چند لحظه صبر نمایید ...");
        pDialog.setCancelable(false);

        final FetchBitmap fetchBitmap = new FetchBitmap(new FetchBitmap.OnFinishedDownload() {
            @Override
            public void onSuccess(Bitmap bitmap) {
                pDialog.cancel();
                doShareContent(bitmap,message);
            }

            @Override
            public void onFailed() {
                pDialog.cancel();
                Toast.makeText(context, context.getString(R.string.msg_error_share), Toast.LENGTH_SHORT).show();
            }
        });


        pDialog.setButton(ProgressDialog.BUTTON_NEGATIVE,"انصراف", new ProgressDialog.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                fetchBitmap.cancel(true);
            }
        });

        pDialog.show();
        fetchBitmap.execute(url);
    }

    public void doShareContent(ImageView imageView,String message) {
        Uri bmpUri = getLocalBitmapUri(imageView);
        if (bmpUri != null) {
            // Construct a ShareIntent with link to image
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.putExtra(Intent.EXTRA_TEXT, message);
            shareIntent.putExtra(Intent.EXTRA_STREAM, bmpUri);
            shareIntent.setType("image/*");
            // Launch sharing dialog for image
            context.startActivity(Intent.createChooser(shareIntent, "ارسال توسط ؟"));
        } else {
            Toast.makeText(context, context.getString(R.string.msg_error_share), Toast.LENGTH_SHORT).show();
        }
    }

    public void doShareContent(Bitmap imageBitmap,String message) {
        Uri bmpUri = getBitmapFromDrawable(imageBitmap);
        if (bmpUri != null) {
            // Construct a ShareIntent with link to image
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.putExtra(Intent.EXTRA_TEXT, message);
            shareIntent.putExtra(Intent.EXTRA_STREAM, bmpUri);
            shareIntent.setType("image/*");
            // Launch sharing dialog for image
            context.startActivity(Intent.createChooser(shareIntent, "ارسال توسط ؟"));
        } else {
            Toast.makeText(context, context.getString(R.string.msg_error_share), Toast.LENGTH_SHORT).show();
        }
    }

    public Uri getLocalBitmapUri(ImageView imageView) {
        // Extract Bitmap from ImageView drawable
        Bitmap bmp = ((BitmapDrawable) imageView.getDrawable()).getBitmap();

        return getBitmapFromDrawable(bmp);
    }

    // Method when launching drawable within Glide
    public Uri getBitmapFromDrawable(Bitmap bitmap) {

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss" + new Random().nextInt(1024), Locale.US).format(new Date());


        File filesDir = getTempDir(context);
        File imageFile = new File(filesDir, timeStamp + "_ share_image.jpg");
        imageFile.deleteOnExit();


        OutputStream os;
        try {
            os = new FileOutputStream(imageFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);
            os.flush();
            os.close();
        } catch (Exception e) {
        }

        Uri bmpUri = FileProvider.getUriForFile(context, context.getPackageName() + ".provider", imageFile);  // use this version for API >= 24

        return bmpUri;
    }


    public static File getTempDir(Context context) {
        File directory = new File(context.getFilesDir(), "temp");

        if (!directory.exists()) {
            directory.mkdirs();
        }
        return directory;
    }
}
