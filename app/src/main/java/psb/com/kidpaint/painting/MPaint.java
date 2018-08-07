package psb.com.kidpaint.painting;

import android.content.Context;

import psb.com.kidpaint.utils.UserProfile;
import psb.com.kidpaint.webApi.userScore.UserScore;
import psb.com.kidpaint.webApi.userScore.buySticker.iBuySticker;
import psb.com.kidpaint.webApi.userScore.buySticker.model.ResponseBuySticker;

public class MPaint implements IMPaint {
    private Context context;
    private IPPaint ipPaint;
    private UserProfile userProfile;

    public MPaint(IPPaint ipPaint) {
        this.ipPaint = ipPaint;
        this.context=ipPaint.geContext();
        this.userProfile=new UserProfile(geContext());
    }

    @Override
    public Context geContext() {
        return context;
    }

    @Override
    public void doBuySticker(int usedCoinCount) {
        new UserScore().buySticker(new iBuySticker.iResult() {
            @Override
            public void onSuccessBuy(ResponseBuySticker responseBuySticker) {
                userProfile.set_KEY_SCORE(responseBuySticker.getExtra());
                ipPaint.onSuccessBuySticker(responseBuySticker);
            }

            @Override
            public void onFailedBuy(int errorCode, String ErrorMessage) {
               ipPaint.onFailedBuySticker(errorCode, ErrorMessage);
            }
        }).doBuy(userProfile.get_KEY_PHONE_NUMBER(""),usedCoinCount);


    }
}
