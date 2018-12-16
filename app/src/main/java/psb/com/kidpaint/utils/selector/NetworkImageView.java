package psb.com.kidpaint.utils.selector;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import psb.com.kidpaint.App;

import static psb.com.kidpaint.utils.selector.SelectorUtils.makeIconSelector;


public class NetworkImageView extends ImageView {
    private String imageUrl;
    private boolean isLoaded=false;
    private Callback callback;
    private int viewId =-1;

    public NetworkImageView(Context context) {
        super(context);
    }

    public NetworkImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NetworkImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void load(int stickerId,String url,Callback callback){
        this.viewId =stickerId;
        load(url,callback);

    }

    public void load(String url,Callback callback){
        this.imageUrl=url;
        this.callback=callback;
        new AsyncGetImage().execute(imageUrl);
    }

    public void loadDrawable(int stickerId,Drawable result,Callback callback){
       this.viewId =stickerId;
       loadDrawable(result,callback);
    }

    public void loadDrawable(Drawable result,Callback callback){
        this.callback=callback;
        if (result != null) {
            setImageDrawable(makeIconSelector(result));
            isLoaded=true;
            if (callback!=null) {
                callback.onSuccess(viewId,result);
            }
        }
    }

    public void retry(){
        if(imageUrl!=null && !imageUrl.isEmpty()){
            new AsyncGetImage().execute(imageUrl);
        }
    }

    public boolean isLoaded() {
        return isLoaded;
    }

    public void setLoaded(boolean loaded) {
        isLoaded = loaded;
    }

    private class AsyncGetImage extends AsyncTask<String, Void, Drawable>{
        @Override
        protected Drawable doInBackground(String... params) {
            if(params!=null && params.length>0) {
                try {
                    HttpURLConnection connection = (HttpURLConnection) new URL(params[0]).openConnection();
                    connection.setRequestProperty("User-agent", "Mozilla/4.0");
                    connection.connect();
                    InputStream input = connection.getInputStream();
                    return new BitmapDrawable(App.getContext().getResources(), BitmapFactory.decodeStream(input));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Drawable result) {
            if (result != null) {
                setImageDrawable(makeIconSelector(result));
                isLoaded=true;
                if (callback!=null) {
                    callback.onSuccess(viewId,result);
                }
            }
        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onProgressUpdate(Void... values) {

        }
    }


    public interface Callback {
        void onSuccess(int viewId,Drawable result);

        void onError(Exception e);
    }


}



