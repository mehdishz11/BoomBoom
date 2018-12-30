package psb.com.kidpaint.score;

import android.content.Context;

import psb.com.kidpaint.utils.UserProfile;
import psb.com.kidpaint.webApi.ScorePackage.GetScorePackage.iGetScorePackage;
import psb.com.kidpaint.webApi.ScorePackage.GetScorePackage.model.ResponseGetScorePackage;
import psb.com.kidpaint.webApi.ScorePackage.ScorePackage;
import psb.com.kidpaint.webApi.ScorePackage.buy.iBuy;
import psb.com.kidpaint.webApi.ScorePackage.buy.model.ResponseBuyScorePackage;

public class MScorePackage implements IMScorePackage {
    private Context context;
    private IPScorePackage ipScorePackage;
    private ResponseGetScorePackage mResponseGetScorePackage;
    private UserProfile userProfile;

    public MScorePackage(IPScorePackage ipScorePackage) {
        this.ipScorePackage = ipScorePackage;
        this.context=ipScorePackage.geContext();
        this.userProfile=new UserProfile(geContext());
    }

    @Override
    public Context geContext() {
        return context;
    }

    @Override
    public void getScorePackage() {
       new ScorePackage().getScorePackage(new iGetScorePackage.iResult() {
           @Override
           public void onSuccessGetScorePackage(ResponseGetScorePackage responseGetScorePackage) {
               mResponseGetScorePackage=responseGetScorePackage;
               ipScorePackage.onSuccessGetScorePackage(responseGetScorePackage);
           }

           @Override
           public void onFailedGetScorePackage(int errorCode, String ErrorMessage) {
             ipScorePackage.onFailedGetScorePackage(errorCode, ErrorMessage);
           }
       }).doGetScorePackage();
    }

    @Override
    public void doBuyScorePackage(int position) {

        if(userProfile.get_KEY_PHONE_NUMBER("").isEmpty()){
            userProfile.set_KEY_SCORE(userProfile.get_KEY_SCORE(0)+mResponseGetScorePackage.getExtra().get(position).getScore());
            ResponseBuyScorePackage responseBuyScorePackage=new ResponseBuyScorePackage();
            responseBuyScorePackage.setExtra(userProfile.get_KEY_SCORE(0));
            ipScorePackage.onSuccessBuyScorePackage(responseBuyScorePackage);
            return;
        }

        new ScorePackage().buy(new iBuy.iResult() {
            @Override
            public void onSuccessBuy(ResponseBuyScorePackage responseBuyScorePackage) {
                 userProfile.set_KEY_SCORE(responseBuyScorePackage.getExtra());
                 ipScorePackage.onSuccessBuyScorePackage(responseBuyScorePackage);
            }

            @Override
            public void onFailedBuy(int errorCode, String ErrorMessage) {
                ipScorePackage.onFailedBuyScorePackage(errorCode, ErrorMessage);
            }
        }).doBuy(userProfile.get_KEY_PHONE_NUMBER(""),mResponseGetScorePackage.getExtra().get(position).getId());

    }

    public psb.com.kidpaint.webApi.ScorePackage.GetScorePackage.model.Extra getScorePackageAt(int position){
        return mResponseGetScorePackage.getExtra().get(position);
    }
}
