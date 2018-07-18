package psb.com.kidpaint.home.history;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import psb.com.kidpaint.utils.UserProfile;
import psb.com.kidpaint.webApi.paint.PostPaint;
import psb.com.kidpaint.webApi.paint.iPostPaint;
import psb.com.kidpaint.webApi.paint.postPaint.ParamsPostPaint;
import psb.com.kidpaint.webApi.paint.postPaint.ResponsePostPaint;

public class MHistory implements IMHistory {
    private Context context;
    private IPHistory ipHistory;
    private List<File> imageList=new ArrayList<>();
    private UserProfile userProfile;

    public MHistory(IPHistory ipHistory) {
        this.ipHistory = ipHistory;
        this.context=ipHistory.getContext();
        this.userProfile=new UserProfile(ipHistory.getContext());
    }

    @Override
    public Context getContext() {
        return context;
    }

    @Override
    public void getMyPaintHistory() {
        String path = Environment.getExternalStorageDirectory()+"/kidPaint";
        File directory = new File(path);
        File[] files = directory.listFiles();
        Log.d("Files", "Size: "+ files.length);
        for (int i = 0; i < files.length; i++)
        {
            Log.d("Files", "FileName:" + files[i].getAbsolutePath());
            imageList.add(files[i]);
        }

        ipHistory.onGetMyPaintHistorySuccess();
    }

    @Override
    public void postPaint(int position) {
        ParamsPostPaint paramsPostPaint=new ParamsPostPaint();
        paramsPostPaint.setMobile(userProfile.get_KEY_PHONE_NUMBER("0"));
        paramsPostPaint.setTitle("");

        Bitmap bitmap= BitmapFactory.decodeFile(imageList.get(position).getAbsolutePath());

        new PostPaint(new iPostPaint.iResult() {
            @Override
            public void onSuccessPostPaint(ResponsePostPaint responsePostPaint) {
                ipHistory.onSuccessPostPaint(responsePostPaint);

            }

            @Override
            public void onFailedPostPaint(int errorId, String ErrorMessage) {
                ipHistory.onFailedPostPaint(errorId, ErrorMessage);

            }
        }).doPostPaint(paramsPostPaint,bitmap);

    }

    @Override
    public File getPositionAt(int position) {
        return imageList.get(position);
    }

    @Override
    public int getArrSize() {
        return imageList.size();
    }

    @Override
    public boolean userIsRegistered() {
        return userProfile.get_KEY_PHONE_NUMBER("").isEmpty()?false:true;
    }
}
