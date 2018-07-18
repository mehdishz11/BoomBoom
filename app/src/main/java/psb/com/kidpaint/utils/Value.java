package psb.com.kidpaint.utils;

import android.content.Context;
import android.graphics.Bitmap;

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

    public static int dp(float value) {
        if (value == 0) {
            return 0;
        }
        float density = App.getContext().getResources().getDisplayMetrics().density;
        return (int) Math.ceil(density * value);
    }

    public static MultipartBody.Part prepareFilePart(Context context, String partName, Bitmap bitmap) {
        File file = makeImageFileFromBitmap(context,bitmap, partName);
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        return MultipartBody.Part.createFormData(partName, file.getName(), requestFile);
    }

    public static File makeImageFileFromBitmap(Context context, Bitmap bitmap, String name) {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss"+new Random().nextInt(1024), Locale.US).format(new Date());
        File filesDir = context.getFilesDir();
        File imageFile = new File(filesDir, timeStamp + "_" + name + ".jpg");
        imageFile.deleteOnExit();

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
}
