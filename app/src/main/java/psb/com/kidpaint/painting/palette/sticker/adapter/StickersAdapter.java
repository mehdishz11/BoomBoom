package psb.com.kidpaint.painting.palette.sticker.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import psb.com.kidpaint.R;
import psb.com.kidpaint.home.history.PHistory;
import psb.com.kidpaint.painting.palette.sticker.P_Stickers;


/**
 * Created by AMiR Ehsan on 4/11/2017 AD.
 */

public class StickersAdapter extends RecyclerView.Adapter<StickersViewHolder> {


   private P_Stickers pStickers;

    public StickersAdapter(P_Stickers pStickers) {
        this.pStickers = pStickers;
    }

    @Override
    public StickersViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_history, parent, false);
        StickersViewHolder recViewHolderNews = new StickersViewHolder(view);
        return recViewHolderNews;
    }

    @Override
    public void onBindViewHolder(StickersViewHolder holder, int position) {
       // pCategory.onBindView(holder, position);
        //setAnimation(holder.itemView, position);
        pStickers.onBindViewHolderStickers(holder, position);
    }

    @Override
    public int getItemCount() {
        return pStickers.getStickersSize();
    }



}
