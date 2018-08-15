package psb.com.kidpaint.webApi.offerPackage;

import psb.com.kidpaint.webApi.ScorePackage.GetScorePackage.GetScorePackage;
import psb.com.kidpaint.webApi.ScorePackage.GetScorePackage.iGetScorePackage;
import psb.com.kidpaint.webApi.ScorePackage.buy.Buy;
import psb.com.kidpaint.webApi.ScorePackage.buy.iBuy;
import psb.com.kidpaint.webApi.ScorePackage.iScorePackage;
import psb.com.kidpaint.webApi.offerPackage.Get.GetOfferPackage;
import psb.com.kidpaint.webApi.offerPackage.Get.iGetOfferPackage;
import psb.com.kidpaint.webApi.offerPackage.buy.BuyOfferPackage;
import psb.com.kidpaint.webApi.offerPackage.buy.iBuyOfferPackage;

public class OfferPackage implements iOfferPackage {


    @Override
    public GetOfferPackage getOfferPackage() {
        return new GetOfferPackage();
    }

    @Override
    public GetOfferPackage getOfferPackage(iGetOfferPackage.iResult iResult) {
        return new GetOfferPackage(iResult);
    }

    @Override
    public BuyOfferPackage buyOfferPackage() {
        return new BuyOfferPackage();
    }

    @Override
    public BuyOfferPackage buyOfferPackage(iBuyOfferPackage.iResult iResult) {
        return new BuyOfferPackage(iResult);
    }
}
