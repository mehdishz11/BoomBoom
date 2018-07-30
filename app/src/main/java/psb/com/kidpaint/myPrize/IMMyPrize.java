package psb.com.kidpaint.myPrize;

import android.content.Context;

import psb.com.kidpaint.webApi.prize.myPrizeRequest.model.MyPrizeRequest;

public interface IMMyPrize {
    Context getContext();

    void getMyPrize();

   int getArrSize();

   MyPrizeRequest getPositionAt(int position);



}
