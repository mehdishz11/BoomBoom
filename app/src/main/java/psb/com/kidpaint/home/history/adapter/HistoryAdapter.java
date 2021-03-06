package psb.com.kidpaint.home.history.adapter;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import psb.com.kidpaint.R;
import psb.com.kidpaint.home.PHome;


/**
 * Created by AMiR Ehsan on 4/11/2017 AD.
 */

public class HistoryAdapter extends RecyclerView.Adapter<HistoryViewHolder> {


   private PHome pHistory;

    public HistoryAdapter(PHome pHistory) {
        this.pHistory = pHistory;
    }

    @Override
    public HistoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_history, parent, false);
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
