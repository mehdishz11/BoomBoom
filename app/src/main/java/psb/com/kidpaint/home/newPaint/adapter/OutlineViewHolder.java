package psb.com.kidpaint.home.newPaint.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import psb.com.kidpaint.R;

public class OutlineViewHolder extends RecyclerView.ViewHolder {

    public RelativeLayout relParent;
    public ImageView imgOutline;

    public OutlineViewHolder(View itemView) {
        super(itemView);

        relParent=itemView.findViewById(R.id.rel_parent);
        imgOutline=itemView.findViewById(R.id.img_outline_template);

    }
}
