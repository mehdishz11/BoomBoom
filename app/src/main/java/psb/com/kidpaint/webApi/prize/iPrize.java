package psb.com.kidpaint.webApi.prize;


import psb.com.kidpaint.webApi.prize.Get.GetPrize;
import psb.com.kidpaint.webApi.prize.Get.iGetPrize;
import psb.com.kidpaint.webApi.prize.PrizeRequest.PrizeRequest;
import psb.com.kidpaint.webApi.prize.PrizeRequest.iPrizeRequest;
import psb.com.kidpaint.webApi.prize.buyDailyPrize.BuyDailyPrize;
import psb.com.kidpaint.webApi.prize.buyDailyPrize.iBuyDailyPrize;
import psb.com.kidpaint.webApi.prize.getDailyPrize.GetDailyPrize;
import psb.com.kidpaint.webApi.prize.getDailyPrize.iGetDailyPrize;
import psb.com.kidpaint.webApi.prize.myPrizeRequest.MyPrizeRequest;
import psb.com.kidpaint.webApi.prize.myPrizeRequest.iMyPrizeRequest;

public interface iPrize {
    String apiAddress = "Prize/";

    GetPrize getPrize();
    GetPrize getPrize(iGetPrize.iResult iResult);

    PrizeRequest requestPrize();
    PrizeRequest requestPrize(iPrizeRequest.iResult iResult);

    MyPrizeRequest myPrizeRequest();
    MyPrizeRequest myPrizeRequest(iMyPrizeRequest.iResult iResult);

    GetDailyPrize getDailyPrize();
    GetDailyPrize getDailyPrize(iGetDailyPrize.iResult iResult);

    BuyDailyPrize buyDailyPrize();
    BuyDailyPrize buyDailyPrize(iBuyDailyPrize.iResult iResult);


}
