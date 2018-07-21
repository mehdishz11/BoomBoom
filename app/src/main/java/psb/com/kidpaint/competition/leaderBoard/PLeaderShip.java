package psb.com.kidpaint.competition.leaderBoard;

import android.content.Context;

import com.squareup.picasso.Picasso;

import psb.com.kidpaint.R;
import psb.com.kidpaint.competition.leaderBoard.adapter.ViewHolder_LeaderShip;
import psb.com.kidpaint.utils.Value;
import psb.com.kidpaint.webApi.paint.getLeaderShip.model.LeaderModel;
import psb.com.kidpaint.webApi.paint.getLeaderShip.model.ResponseGetLeaderShip;

public class PLeaderShip implements IPLeaderShip {

    private Context context;
    private IVLeaderShip ivLeaderShip;
    private MLeaderShip mPaints;

    public PLeaderShip(IVLeaderShip ivLeaderShip) {
        this.ivLeaderShip = ivLeaderShip;
        this.context= ivLeaderShip.getContext();
        this.mPaints=new MLeaderShip(this);
    }

    @Override
    public Context getContext() {
        return context;
    }



    @Override
    public void onGetLeaderShip(int page, int size) {
       ivLeaderShip.onStartGetLeaderShip();
       mPaints.onGetLeaderShip(page, size);
    }

    @Override
    public void setResponseGetLeaderShip(ResponseGetLeaderShip responseGetLeaderShip) {
       mPaints.setResponseGetLeaderShip(responseGetLeaderShip);
    }

    @Override
    public void onSuccessGetLeaderShip(ResponseGetLeaderShip responseGetLeaderShip) {
      ivLeaderShip.onSuccessGetLeaderShip(responseGetLeaderShip);
    }

    @Override
    public void onFailedGetLeaderShip(int errorCode, String errorMessage) {
    ivLeaderShip.onFailedGetLeaderShip(errorCode, errorMessage);
    }



    @Override
    public void onBindViewHolder_GetLeaderShip(ViewHolder_LeaderShip holder, int position) {
        LeaderModel leaderModel=mPaints.getLeaderShipPositionAt(position);

        if (leaderModel.getUrl() != null && !leaderModel.getUrl().isEmpty()) {
            Picasso
                    .get()
                    .load(leaderModel.getUrl())
                    .resize(Value.dp(200),Value.dp(200))
                    .onlyScaleDown()
                    .into(holder.imgPaint);
        }
        if(leaderModel.getUser().getImageUrl()!=null && !leaderModel.getUser().getImageUrl().isEmpty()){
            Picasso
                    .get()
                    .load(leaderModel.getUser().getImageUrl())
                    .resize(Value.dp(200),Value.dp(200))
                    .onlyScaleDown()
                    .into(holder.imgUser);
        }

//        holder.imgPaint.setRotation(new Random().nextInt(8)+1);

        holder.textRate.setText(""+leaderModel.getRank());
        holder.textUserName.setText(leaderModel.getUser().getFirstName()+" "+leaderModel.getUser().getLastName());
        holder.textUserPoints.setText((leaderModel.getScore())+" امتیاز");

        if (position==0) {
            holder.viewBackground.setBackgroundColor(getContext().getResources().getColor(R.color.md_yellow_300));
        }else if (position==1) {
            holder.viewBackground.setBackgroundColor(getContext().getResources().getColor(R.color.md_grey_300));

        }else if (position==2) {
            holder.viewBackground.setBackgroundColor(getContext().getResources().getColor(R.color.md_orange_300));

        }else  {
            holder.viewBackground.setBackgroundColor(getContext().getResources().getColor(R.color.brown_1));

        }
    }

    @Override
    public int getArrSizeGetLeaderShip() {
        return mPaints.getArrSizeLeaderShip();
    }

    @Override
    public int getServerGetLeaderShipSize() {
        return mPaints.getArrSizeLeaderShip();
    }



}
