package psb.com.kidpaint.webApi.match;

import psb.com.kidpaint.webApi.match.Get.GetMatch;
import psb.com.kidpaint.webApi.match.Get.iGetMatch;
import psb.com.kidpaint.webApi.offerPackage.Get.GetOfferPackage;
import psb.com.kidpaint.webApi.offerPackage.Get.iGetOfferPackage;
import psb.com.kidpaint.webApi.offerPackage.buy.BuyOfferPackage;
import psb.com.kidpaint.webApi.offerPackage.buy.iBuyOfferPackage;

public interface iMatch {


   GetMatch getMatch();
   GetMatch getMatch(iGetMatch.iResult iResult);

}
