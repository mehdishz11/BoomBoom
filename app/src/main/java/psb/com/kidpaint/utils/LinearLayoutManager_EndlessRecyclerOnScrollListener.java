package psb.com.kidpaint.utils;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

/**
 * Created by Nmorteza on 12/18/2016.
 */

public abstract class LinearLayoutManager_EndlessRecyclerOnScrollListener extends RecyclerView.OnScrollListener {
    public static String TAG = LinearLayoutManager_EndlessRecyclerOnScrollListener.class.getSimpleName();

    private int previousTotal = 0; // The total number of items in the dataset after the last load
    private boolean loading = true; // True if we are still waiting for the last set of data to load.
    private int visibleThreshold = 4; // The minimum amount of items to have below your current scroll position before loading more.
    int lastVisibleItem, visibleItemCount, totalItemCount;

    private int current_page = 1;
    private int loadcount = 0;

    private LinearLayoutManager linearLayoutManager;


    public LinearLayoutManager_EndlessRecyclerOnScrollListener(LinearLayoutManager linearLayoutManager, int serverCount) {
        this.linearLayoutManager = linearLayoutManager;
        this.totalItemCount = serverCount;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        visibleItemCount = recyclerView.getChildCount();
        previousTotal = linearLayoutManager.getItemCount();
        lastVisibleItem = linearLayoutManager.findFirstVisibleItemPosition();

        if (loading) {
             Log.d(TAG," totalItemCount : "+totalItemCount);
             Log.d(TAG," previousTotal : "+previousTotal);

            if (totalItemCount > previousTotal) {
                loading = false;
                loadcount = (totalItemCount - previousTotal > 20 ? 20 : (totalItemCount - previousTotal));
                current_page= (int) Math.ceil((previousTotal+loadcount) / 20.0);

            }
        }
        if (!loading && ((lastVisibleItem +1)==previousTotal)&&(totalItemCount > previousTotal)) {
            Log.d(TAG," totalItemCount : "+totalItemCount);
            Log.d(TAG," lastVisibleItem : "+lastVisibleItem);
            Log.d(TAG," previousTotal : "+previousTotal);
            Log.d(TAG," getItemCount : "+previousTotal+" page : "+ current_page +" loadCount: "+loadcount);
            onLoadMore(loadcount,current_page);
            loading = true;
        }
    }

    public abstract void onLoadMore(int load_count,int page);
}
