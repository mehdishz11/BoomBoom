package psb.com.kidpaint.competition.myPaints.adapter;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import psb.com.kidpaint.R;
import psb.com.kidpaint.competition.myPaints.PMyPaints;


/**
 * Created by morteza on 7/18/2018 AD.
 */

public class Adapter_MyPaints extends RecyclerView.Adapter<ViewHolder_MyPaints> {


   private PMyPaints pPaints;

    public Adapter_MyPaints(PMyPaints pPaints) {
        this.pPaints = pPaints;
    }

    @Override
    public ViewHolder_MyPaints onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_competition_my_paint, parent, false);
        ViewHolder_MyPaints recViewHolderNews = new ViewHolder_MyPaints(view);
        return recViewHolderNews;
    }

    @Override
    public void onBindViewHolder(ViewHolder_MyPaints holder, int position) {

        pPaints.onBindViewHolder_MyPaints(holder, position);
    }

    @Override
    public int getItemCount() {
        return pPaints.getArrSizeMyPaints();
    }



}
