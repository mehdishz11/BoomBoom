package psb.com.kidpaint.offerPackage;

import android.content.Context;

import psb.com.kidpaint.webApi.offerPackage.buy.model.ResponseBuyOfferPackage;

public class POfferPackage implements IPOfferPackage {

    private Context context;
    private IVOfferPackage ivOfferPackage;
    private MOfferPackage mOfferPackage;

    public POfferPackage(IVOfferPackage ivOfferPackage) {
        this.ivOfferPackage = ivOfferPackage;
        this.context=ivOfferPackage.getContext();
        this.mOfferPackage=new MOfferPackage(this);
    }

    @Override
    public Context geContext() {
        return context;
    }

    @Override
    public void doBuyOfferPackage(int offerPackageId) {
     ivOfferPackage.startBuyOfferPackage();
     mOfferPackage.doBuyOfferPackage(offerPackageId);
    }

    @Override
    public void onSuccessBuyOfferPackage(ResponseBuyOfferPackage responseBuyOfferPackage) {
        ivOfferPackage.onSuccessBuyOfferPackage(responseBuyOfferPackage);
    }

    @Override
    public void onFailedBuyOfferPackage(int errorCode, String errorMessage) {
        ivOfferPackage.onFailedBuyOfferPackage(errorCode, errorMessage);
    }
}
