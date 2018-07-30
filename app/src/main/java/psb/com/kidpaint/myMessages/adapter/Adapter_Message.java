package psb.com.kidpaint.myMessages.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import psb.com.kidpaint.R;
import psb.com.kidpaint.competition.allPaint.PAllPaints;
import psb.com.kidpaint.competition.allPaint.adapter.ViewHolder_AllPaints;
import psb.com.kidpaint.myMessages.PMessages;


/**
 * Created by morteza on 7/18/2018 AD.
 */

public class Adapter_Message extends RecyclerView.Adapter<ViewHolder_Message> {


   private PMessages pMessages;

    public Adapter_Message(PMessages pMessages) {
        this.pMessages = pMessages;
    }
    @Override
    public ViewHolder_Message onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_all_users, parent, false);
        switch (viewType) {
            case 0://me
                itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_all_users, parent, false);
                break;
            case 1://admin
                itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_all_users, parent, false);
                break;
        }
        return new ViewHolder_Message(itemView);

    }

    @Override
    public void onBindViewHolder(ViewHolder_Message holder, int position) {

        pMessages.onBindView_Message(holder, position);
    }
    @Override
    public int getItemViewType(int position) {
        return pMessages.getRowType(position);
    }

    @Override
    public int getItemCount() {
        return pMessages.getArrSize();
    }



}
