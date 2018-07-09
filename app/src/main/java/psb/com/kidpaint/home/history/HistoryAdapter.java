package psb.com.kidpaint.home.history;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import psb.com.kidpaint.R;


/**
 * Created by AMiR Ehsan on 4/11/2017 AD.
 */

public class HistoryAdapter extends RecyclerView.Adapter<HistoryViewHolder> {

    private Context context;
    private int lastPosition = -1;

    public HistoryAdapter() {
    }

    @Override
    public HistoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_category, parent, false);
        HistoryViewHolder recViewHolderNews = new HistoryViewHolder(view);
        return recViewHolderNews;
    }

    @Override
    public void onBindViewHolder(HistoryViewHolder holder, int position) {
       // pCategory.onBindView(holder, position);
        //setAnimation(holder.itemView, position);
    }

    @Override
    public int getItemCount() {
        return 50;
    }
}
