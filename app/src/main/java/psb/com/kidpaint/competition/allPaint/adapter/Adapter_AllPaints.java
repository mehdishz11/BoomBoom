package psb.com.kidpaint.competition.allPaint.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import psb.com.kidpaint.R;
import psb.com.kidpaint.competition.allPaint.PAllPaints;


/**
 * Created by morteza on 7/18/2018 AD.
 */

public class Adapter_AllPaints extends RecyclerView.Adapter<ViewHolder_AllPaints> {


   private PAllPaints pPaints;

    public Adapter_AllPaints(PAllPaints pPaints) {
        this.pPaints = pPaints;
    }

    @Override
    public ViewHolder_AllPaints onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_all_users, parent, false);
        ViewHolder_AllPaints recViewHolderNews = new ViewHolder_AllPaints(view);
        return recViewHolderNews;
    }

    @Override
    public void onBindViewHolder(ViewHolder_AllPaints holder, int position) {

        pPaints.onBindViewHolder_AllPaints(holder, position);
    }

    @Override
    public int getItemCount() {
        return pPaints.getArrSizeAllPaints();
    }



}
