package psb.com.kidpaint.competition.leaderBoard.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import psb.com.kidpaint.R;
import psb.com.kidpaint.competition.leaderBoard.PLeaderShip;


/**
 * Created by morteza on 7/18/2018 AD.
 */

public class Adapter_LeaderShip extends RecyclerView.Adapter<ViewHolder_LeaderShip> {


   private PLeaderShip pPaints;

    public Adapter_LeaderShip(PLeaderShip pPaints) {
        this.pPaints = pPaints;
    }

    @Override
    public ViewHolder_LeaderShip onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_all_users, parent, false);
        ViewHolder_LeaderShip recViewHolderNews = new ViewHolder_LeaderShip(view);
        return recViewHolderNews;
    }

    @Override
    public void onBindViewHolder(ViewHolder_LeaderShip holder, int position) {

        pPaints.onBindViewHolder_GetLeaderShip(holder, position);
    }

    @Override
    public int getItemCount() {
        return pPaints.getArrSizeGetLeaderShip();
    }



}
