package psb.com.kidpaint.competition.allPaint.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;

import psb.com.kidpaint.R;

public class ViewHolder_AllPaints extends RecyclerView.ViewHolder {

    public RelativeLayout relParent;
    public RoundedImageView imgOutline;
    public ImageView imgMask;
    public View parentView;
    public Button competition;
    public CardView cardBgr;
    public TextView textUserName;
    public TextView textImageCode;
    public ImageView imageUser;
    public RoundedImageView bgrBtnRate;

    public ViewHolder_AllPaints(View itemView) {
        super(itemView);

        parentView=itemView;
        relParent=itemView.findViewById(R.id.rel_parent);
        imgOutline=itemView.findViewById(R.id.img_outline_template);
        imgMask=itemView.findViewById(R.id.img_mask);
        competition=itemView.findViewById(R.id.btn_competition);
        cardBgr=itemView.findViewById(R.id.card_bgr);

        bgrBtnRate=itemView.findViewById(R.id.image_bgr_btn);

        imageUser=itemView.findViewById(R.id.img_user);

        textUserName=itemView.findViewById(R.id.text_user_name);
        textImageCode=itemView.findViewById(R.id.text_image_code);

    }
}
