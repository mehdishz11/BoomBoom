package psb.com.kidpaint.home.history.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import psb.com.kidpaint.R;
import psb.com.kidpaint.home.history.PHistory;
import psb.com.kidpaint.home.history.adapter.HistoryViewHolder;


/**
 * Created by AMiR Ehsan on 4/11/2017 AD.
 */

public class HistoryAdapter extends RecyclerView.Adapter<HistoryViewHolder> {


   private PHistory pHistory;

    public HistoryAdapter(PHistory pHistory) {
        this.pHistory = pHistory;
    }



    @Override
    public HistoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_outline_template, parent, false);
        HistoryViewHolder recViewHolderNews = new HistoryViewHolder(view);
        return recViewHolderNews;
    }

    @Override
    public void onBindViewHolder(HistoryViewHolder holder, int position) {
       // pCategory.onBindView(holder, position);
        //setAnimation(holder.itemView, position);
        pHistory.onBindViewHolder(holder, position);
    }

    @Override
    public int getItemCount() {
        return pHistory.getArrSize();
    }
}
