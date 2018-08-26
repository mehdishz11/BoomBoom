package psb.com.kidpaint.competition.leaderBoard.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;

import psb.com.kidpaint.R;

public class ViewHolder_LeaderShip extends RecyclerView.ViewHolder {

    public RelativeLayout relParent;
    public RoundedImageView imgOutline;
    public ImageView imgMask;
    public View parentView;
    public Button competition,like;
    public CardView cardBgr;
    public TextView textUserName;
    public TextView textImageCode;
    public ImageView imageUser;
    public RoundedImageView bgrBtnRate;
    public TextView textRate;
    public TextView text_user_points;
    public ImageView imgLeaves;


    public ViewHolder_LeaderShip(View itemView) {
        super(itemView);

        parentView=itemView;


        textRate=itemView.findViewById(R.id.text_rank);
        text_user_points=itemView.findViewById(R.id.text_user_points);
        relParent=itemView.findViewById(R.id.rel_parent);
        imgOutline=itemView.findViewById(R.id.img_outline_template);
        imgMask=itemView.findViewById(R.id.img_mask);
        competition=itemView.findViewById(R.id.btn_competition);
        cardBgr=itemView.findViewById(R.id.card_bgr);
        bgrBtnRate=itemView.findViewById(R.id.image_bgr_btn);
        imageUser=itemView.findViewById(R.id.img_user);
        like=itemView.findViewById(R.id.btn_like);
        textUserName=itemView.findViewById(R.id.text_user_name);
        textImageCode=itemView.findViewById(R.id.text_image_code);
        imgLeaves=itemView.findViewById(R.id.img_leaves);

    }
}
