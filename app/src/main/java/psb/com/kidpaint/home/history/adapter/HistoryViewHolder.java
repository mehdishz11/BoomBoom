package psb.com.kidpaint.home.history.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import psb.com.kidpaint.R;

public class HistoryViewHolder extends RecyclerView.ViewHolder {

    public RelativeLayout relParent;
    public ImageView imgOutline;

    public HistoryViewHolder(View itemView) {
        super(itemView);

        relParent=itemView.findViewById(R.id.rel_parent);
        imgOutline=itemView.findViewById(R.id.img_outline_template);

    }
}
