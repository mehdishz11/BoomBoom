package psb.com.kidpaint.painting;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import psb.com.kidpaint.utils.UserProfile;
import psb.com.kidpaint.webApi.paint.Paint;
import psb.com.kidpaint.webApi.paint.postPaint.iPostPaint;
import psb.com.kidpaint.webApi.paint.postPaint.model.ParamsPostPaint;
import psb.com.kidpaint.webApi.paint.postPaint.model.ResponsePostPaint;
import psb.com.kidpaint.webApi.paint.savePaints.iSavePaints;
import psb.com.kidpaint.webApi.paint.savePaints.model.ParamsSavePaint;
import psb.com.kidpaint.webApi.paint.savePaints.model.ResponseSavePaint;
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

    @Override
    public void onSavePaint(Bitmap bitmap) {
        ParamsSavePaint paramsPostPaint = new ParamsSavePaint();
        paramsPostPaint.setMobile(userProfile.get_KEY_PHONE_NUMBER("0"));
        paramsPostPaint.setTitle("");

        new Paint().savePaints(new iSavePaints.iResult() {
            @Override
            public void onSuccessSavePaint(ResponseSavePaint responseSavePaint) {
                ipPaint.onSuccessSavePaint(responseSavePaint);
            }

            @Override
            public void onFailedSavePaint(int errorId, String ErrorMessage) {
               ipPaint.onFailedSavePaint(errorId, ErrorMessage);
            }
        }).doSavePaint(paramsPostPaint,bitmap);



    }
}
