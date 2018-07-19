package psb.com.kidpaint.competition.leaderBoard;

import android.content.Context;
import android.util.Log;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.Random;


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

        if(position%2==0){
            holder.parentView.setRotation(new Random().nextInt(4));
        }else{
            holder.parentView.setRotation(-new Random().nextInt(4));
        }
        Picasso
                .get()
                .load(leaderModel.getUrl())
                .resize(Value.dp(200),Value.dp(200))
                .onlyScaleDown()
                .into(holder.imgOutline, new Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError(Exception e) {

                        Log.d("TAG", "onError: ");
                        e.printStackTrace();
                    }
                });

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
