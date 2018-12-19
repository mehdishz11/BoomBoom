package psb.com.kidpaint.webApi.offerPackage;

import psb.com.kidpaint.webApi.offerPackage.Get.GetOfferPackage;
import psb.com.kidpaint.webApi.offerPackage.Get.iGetOfferPackage;
import psb.com.kidpaint.webApi.offerPackage.buy.BuyOfferPackage;
import psb.com.kidpaint.webApi.offerPackage.buy.iBuyOfferPackage;

public interface iOfferPackage {

   GetOfferPackage getOfferPackage();
   GetOfferPackage getOfferPackage(iGetOfferPackage.iResult iResult);

   BuyOfferPackage buyOfferPackage();
   BuyOfferPackage buyOfferPackage(iBuyOfferPackage.iResult iResult);
}
