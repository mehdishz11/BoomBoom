package psb.com.kidpaint.painting;

import android.content.Context;

import psb.com.kidpaint.webApi.userScore.buySticker.model.ResponseBuySticker;

public class PPaint implements IPPaint {
    private Context context;
    private IVPaint ivPaint;
    private MPaint mPaint;

    public PPaint(IVPaint ivPaint) {
        this.ivPaint = ivPaint;
        this.context=ivPaint.getContext();
        this.mPaint=new MPaint(this);
    }

    @Override
    public Context geContext() {
        return context;
    }

    @Override
    public void doBuySticker(int usedCoinCount) {
      ivPaint.startBuySticker();
      mPaint.doBuySticker(usedCoinCount);
    }

    @Override
    public void onSuccessBuySticker(ResponseBuySticker responseBuySticker) {
        ivPaint.onSuccessBuySticker(responseBuySticker);
    }

    @Override
    public void onFailedBuySticker(int errorCode, String errorMessage) {
       ivPaint.onFailedBuySticker(errorCode, errorMessage);
    }
}
