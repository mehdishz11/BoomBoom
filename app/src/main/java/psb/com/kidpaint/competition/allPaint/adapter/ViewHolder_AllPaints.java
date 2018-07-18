package psb.com.kidpaint.competition.allPaint.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import psb.com.kidpaint.R;

public class ViewHolder_AllPaints extends RecyclerView.ViewHolder {

    public RelativeLayout relParent;
    public ImageView imgOutline;
    public ImageView imgMask;
    public View parentView;
    public Button delete,edit,competition;

    public ViewHolder_AllPaints(View itemView) {
        super(itemView);

        parentView=itemView;
        relParent=itemView.findViewById(R.id.rel_parent);
        imgOutline=itemView.findViewById(R.id.img_outline_template);
        imgMask=itemView.findViewById(R.id.img_mask);
        delete=itemView.findViewById(R.id.btn_delete);
        edit=itemView.findViewById(R.id.btn_edit);
        competition=itemView.findViewById(R.id.btn_competition);

    }
}