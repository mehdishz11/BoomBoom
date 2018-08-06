package psb.com.kidpaint.score;

import android.content.Context;

import psb.com.kidpaint.webApi.ScorePackage.GetScorePackage.model.ResponseGetScorePackage;
import psb.com.kidpaint.webApi.ScorePackage.buy.model.ResponseBuyScorePackage;

public class PScorePackage implements IPScorePackage {
    private Context context;
    private IVScorePackage ivScorePackage;
    private MScorePackage mScorePackage;

    public PScorePackage(IVScorePackage ivScorePackage) {
        this.ivScorePackage = ivScorePackage;
        this.context=ivScorePackage.getContext();
        this.mScorePackage=new MScorePackage(this);
    }

    @Override
    public Context geContext() {
        return context;
    }

    @Override
    public void getScorePackage() {
        ivScorePackage.startGetScorePackage();
        mScorePackage.getScorePackage();
    }

    @Override
    public void onSuccessGetScorePackage(ResponseGetScorePackage responseGetScorePackage) {
         ivScorePackage.onSuccessGetScorePackage(responseGetScorePackage);
    }

    @Override
    public void onFailedGetScorePackage(int errorCode, String errorMessage) {
        ivScorePackage.onFailedGetScorePackage(errorCode, errorMessage);
    }

    @Override
    public void doBuyScorePackage(int position) {
        ivScorePackage.startBuyScorePackage();
        mScorePackage.doBuyScorePackage(position);
    }

    @Override
    public void onSuccessBuyScorePackage(ResponseBuyScorePackage responseBuyScorePackage) {
      ivScorePackage.onSuccessBuyScorePackage(responseBuyScorePackage);
    }

    @Override
    public void onFailedBuyScorePackage(int errorCode, String errorMessage) {
        ivScorePackage.onFailedBuyScorePackage(errorCode, errorMessage);
    }
}
