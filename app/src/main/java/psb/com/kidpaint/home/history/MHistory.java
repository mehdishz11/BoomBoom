package psb.com.kidpaint.home.history;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MHistory implements IMHistory {
    private Context context;
    private IPHistory ipHistory;
    private List<File> imageList=new ArrayList<>();

    public MHistory(IPHistory ipHistory) {
        this.ipHistory = ipHistory;
        this.context=ipHistory.getContext();
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
    public File getPositionAt(int position) {
        return imageList.get(position);
    }

    @Override
    public int getArrSize() {
        return imageList.size();
    }
}
