package psb.com.kidpaint.myPrize.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import psb.com.kidpaint.R;
import psb.com.kidpaint.competition.allPaint.PAllPaints;
import psb.com.kidpaint.competition.allPaint.adapter.ViewHolder_AllPaints;
import psb.com.kidpaint.myPrize.PMyPrize;


/**
 * Created by morteza on 7/18/2018 AD.
 */

public class Adapter_MyPrize extends RecyclerView.Adapter<ViewHolder_MyPrize> {


   private PMyPrize pMyPrize;

    public Adapter_MyPrize(PMyPrize pMyPrize) {
        this.pMyPrize = pMyPrize;
    }

    @Override
    public ViewHolder_MyPrize onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_all_users, parent, false);
        ViewHolder_MyPrize recViewHolderNews = new ViewHolder_MyPrize(view);
        return recViewHolderNews;
    }

    @Override
    public void onBindViewHolder(ViewHolder_MyPrize holder, int position) {

        pMyPrize.onBindView_MyPrize(holder, position);
    }

    @Override
    public int getItemCount() {
        return pMyPrize.getArrSize();
    }



}
