package psb.com.kidpaint.dailyPrize;

import android.content.Context;

import psb.com.kidpaint.utils.UserProfile;
import psb.com.kidpaint.webApi.prize.Prize;
import psb.com.kidpaint.webApi.prize.buyDailyPrize.iBuyDailyPrize;
import psb.com.kidpaint.webApi.prize.buyDailyPrize.model.ResponseBuyDailyPrize;


public class MDailyPrize implements IMDailyPrize {

    private Context context;
    private IPDailyPrize ipDailyPrize;
    private UserProfile userProfile;

    public MDailyPrize(IPDailyPrize ipDailyPrize) {
        this.ipDailyPrize = ipDailyPrize;
        this.context=ipDailyPrize.geContext();
        this.userProfile=new UserProfile(geContext());
    }

    @Override
    public Context geContext() {
        return context;
    }

    @Override
    public void doBuyDailyPrize(int id, final int prize) {
        new Prize().buyDailyPrize(new iBuyDailyPrize.iResult() {
            @Override
            public void onSuccessBuyDailyPrize(ResponseBuyDailyPrize responseBuyDailyPrize) {
                userProfile.set_KEY_SCORE(responseBuyDailyPrize.getExtra());
                 ipDailyPrize.onSuccessBuyDailyPrize(prize,responseBuyDailyPrize);
            }

            @Override
            public void onFailedBuyDailyPrize(int errorCode, String ErrorMessage) {
                ipDailyPrize.onFailedBuyDailyPrize(errorCode, ErrorMessage);

            }
        }).doBuyDailyPrize(userProfile.get_KEY_PHONE_NUMBER(""),id);

    }
}
