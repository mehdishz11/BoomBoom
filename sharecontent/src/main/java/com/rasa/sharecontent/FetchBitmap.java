package com.rasa.sharecontent;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class FetchBitmap extends AsyncTask<String, Void, Bitmap>{

    private OnFinishedDownload onFinishedDownload;

    public FetchBitmap(OnFinishedDownload onFinishedDownload) {
        this.onFinishedDownload = onFinishedDownload;
    }

    @Override
        protected Bitmap doInBackground(String... params) {

            Bitmap bitmap = null;

            bitmap = downloadImage(params[0]);

            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (onFinishedDownload != null) {
                onFinishedDownload.onSuccess(bitmap);
            }else{
                onFinishedDownload.onFailed();
            }

        }


   private  Bitmap downloadImage(String url) {
        Bitmap bitmap = null;
        InputStream stream = null;
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inSampleSize = 1;

        try {
            stream = getHttpConnection(url);
            bitmap = BitmapFactory.decodeStream(stream, null, bmOptions);
            stream.close();
        }
        catch (IOException e1) {
            e1.printStackTrace();
            System.out.println("downloadImage"+ e1.toString());
        }
        return bitmap;
    }

    // Makes HttpURLConnection and returns InputStream

    public static  InputStream getHttpConnection(String urlString)  throws IOException {

        InputStream stream = null;
        URL url = new URL(urlString);
        URLConnection connection = url.openConnection();

        try {
            HttpURLConnection httpConnection = (HttpURLConnection) connection;
            httpConnection.setRequestMethod("GET");
            httpConnection.connect();

            if (httpConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                stream = httpConnection.getInputStream();
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("downloadImage" + ex.toString());
        }
        return stream;
    }

    public interface OnFinishedDownload{
            void onSuccess(Bitmap bitmap);
            void onFailed();
    }

}
