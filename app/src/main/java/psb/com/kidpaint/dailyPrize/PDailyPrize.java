package psb.com.kidpaint.dailyPrize;

import android.content.Context;

import psb.com.kidpaint.webApi.prize.buyDailyPrize.model.ResponseBuyDailyPrize;

public class PDailyPrize implements IPDailyPrize {

    private Context context;
    private IVDailyPrize ivDailyPrize;
    private MDailyPrize mDailyPrize;

    public PDailyPrize(IVDailyPrize ivDailyPrize) {
        this.ivDailyPrize = ivDailyPrize;
        this.context=ivDailyPrize.getContext();
        this.mDailyPrize=new MDailyPrize(this);
    }

    @Override
    public Context geContext() {
        return context;
    }

    @Override
    public void doBuyDailyPrize(int id, int prize) {
      ivDailyPrize.startBuyDailyPrize();
      mDailyPrize.doBuyDailyPrize(id, prize);
    }

    @Override
    public void onSuccessBuyDailyPrize(int prize, ResponseBuyDailyPrize responseBuyDailyPrize) {
         ivDailyPrize.onSuccessBuyDailyPrize(prize, responseBuyDailyPrize);
    }

    @Override
    public void onFailedBuyDailyPrize(int errorCode, String errorMessage) {
       ivDailyPrize.onFailedBuyDailyPrize(errorCode, errorMessage);
    }
}
