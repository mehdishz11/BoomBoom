package psb.com.kidpaint.offerPackage;

import android.content.Context;

import psb.com.kidpaint.score.IMScorePackage;
import psb.com.kidpaint.score.IPScorePackage;
import psb.com.kidpaint.utils.UserProfile;
import psb.com.kidpaint.webApi.ScorePackage.GetScorePackage.iGetScorePackage;
import psb.com.kidpaint.webApi.ScorePackage.GetScorePackage.model.ResponseGetScorePackage;
import psb.com.kidpaint.webApi.ScorePackage.ScorePackage;
import psb.com.kidpaint.webApi.ScorePackage.buy.iBuy;
import psb.com.kidpaint.webApi.ScorePackage.buy.model.ResponseBuyScorePackage;
import psb.com.kidpaint.webApi.offerPackage.OfferPackage;
import psb.com.kidpaint.webApi.offerPackage.buy.iBuyOfferPackage;
import psb.com.kidpaint.webApi.offerPackage.buy.model.ResponseBuyOfferPackage;

public class MOfferPackage implements IMOfferPackage {
    private Context context;
    private IPOfferPackage ipOfferPackage;
    private UserProfile userProfile;

    public MOfferPackage(IPOfferPackage ipOfferPackage) {
        this.ipOfferPackage = ipOfferPackage;
        this.context=ipOfferPackage.geContext();
        this.userProfile=new UserProfile(geContext());
    }

    @Override
    public Context geContext() {
        return context;
    }

    @Override
    public void doBuyOfferPackage(int offerPackageId) {
        new OfferPackage().buyOfferPackage(new iBuyOfferPackage.iResult() {
            @Override
            public void onSuccessBuyOfferPackage(ResponseBuyOfferPackage responseBuyOfferPackage) {
                userProfile.set_KEY_SCORE(responseBuyOfferPackage.getExtra());
                ipOfferPackage.onSuccessBuyOfferPackage(responseBuyOfferPackage);
            }

            @Override
            public void onFailedBuyOfferPackage(int errorCode, String ErrorMessage) {
             ipOfferPackage.onFailedBuyOfferPackage(errorCode, ErrorMessage);
            }
        }).doBuyOfferPackage(userProfile.get_KEY_PHONE_NUMBER(""),offerPackageId);
    }




}
