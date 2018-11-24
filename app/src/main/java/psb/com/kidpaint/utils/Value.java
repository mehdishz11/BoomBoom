package psb.com.kidpaint.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.DisplayMetrics;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import psb.com.kidpaint.App;

public class Value {

    private static final String ALLOWED_URI_CHARS = "@#&=*+-_.,:!?()/~'%20";

    public static int dp(float value) {
        if (value == 0) {
            return 0;
        }
        float density = App.getContext().getResources().getDisplayMetrics().density;
        return (int) Math.ceil(density * value);
    }

    public static MultipartBody.Part prepareFilePart(Context context, String partName, Bitmap bitmap) {
        File file = makeImageFileFromBitmap(context, bitmap, partName);
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        return MultipartBody.Part.createFormData(partName, file.getName(), requestFile);
    }

    public static File makeImageFileFromBitmap(Context context, Bitmap bitmap, String name) {
        return makeImageFileFromBitmap(context,bitmap,name,true);
    }

    public static File makeImageFileFromBitmap(Context context, Bitmap bitmap, String name, boolean deleteOnExit) {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss" + new Random().nextInt(1024), Locale.US).format(new Date());
        File filesDir = getPaintsDir(context);
        File imageFile = new File(filesDir, timeStamp + "_" + name + ".jpg");

        if(deleteOnExit){
            filesDir = getTempDir(context);
            imageFile = new File(filesDir, timeStamp + "_" + name + ".jpg");
            imageFile.deleteOnExit();
        }

        OutputStream os;
        try {
            os = new FileOutputStream(imageFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);
            os.flush();
            os.close();
        } catch (Exception e) {
        }
        return imageFile;
    }

    public static File editImageFileFromBitmap(Bitmap bitmap, String editPath) {

        File imageFile = new File(editPath);
        if (editPath != null) {
            imageFile = new File(editPath);
        }

        if (imageFile.exists()) imageFile.delete();

        OutputStream os;
        try {
            os = new FileOutputStream(imageFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);
            os.flush();
            os.close();
        } catch (Exception e) {
        }
        return imageFile;
    }

    public static File getPaintsDir(Context context){
        File directory=new File(context.getFilesDir(),"kidDirectory");

        if (!directory.exists()) {
            directory.mkdirs();
        }
        return directory;
    }
    public static File getTempDir(Context context){
        File directory=new File(context.getFilesDir(),"temp");

        if (!directory.exists()) {
            directory.mkdirs();
        }
        return directory;
    }
    public static int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }

    public static int height_16_9(int width) {
        return (int) ((float) width * 0.5625);
    }

    public static float pixelToDp(float px) {
        return px / ((float) App.getContext().getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }

    public static float dpToPixel(int dp) {
        return (dp * Resources.getSystem().getDisplayMetrics().density);
    }

    public static String encodeUrl(String url) {
        return Uri.encode(url, ALLOWED_URI_CHARS);
    }
}
