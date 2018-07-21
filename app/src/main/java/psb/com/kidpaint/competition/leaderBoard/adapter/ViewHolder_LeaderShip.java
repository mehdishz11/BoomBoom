package psb.com.kidpaint.competition.leaderBoard.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import psb.com.kidpaint.R;

public class ViewHolder_LeaderShip extends RecyclerView.ViewHolder {

    public ImageView imgUser;
    public ImageView imgPaint;
    public TextView textRate;
    public TextView textUserName;
    public TextView textUserPoints;
    public View parentView,viewBackground;

    public ViewHolder_LeaderShip(View itemView) {
        super(itemView);

        parentView=itemView;

        imgUser=itemView.findViewById(R.id.img_user);
        imgPaint=itemView.findViewById(R.id.img_paint);
        textRate=itemView.findViewById(R.id.text_rank);
        textUserName=itemView.findViewById(R.id.text_user_name);
        textUserPoints=itemView.findViewById(R.id.text_user_points);
        viewBackground=itemView.findViewById(R.id.viewBackground);

    }
}
