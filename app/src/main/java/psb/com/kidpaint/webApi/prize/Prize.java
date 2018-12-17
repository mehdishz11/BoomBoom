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

public class Prize implements iPrize {
    @Override
    public GetPrize getPrize() {
        return new GetPrize();
    }

    @Override
    public GetPrize getPrize(iGetPrize.iResult iResult) {
        return new GetPrize(iResult);
    }

    @Override
    public PrizeRequest requestPrize() {
        return new PrizeRequest();
    }

    @Override
    public PrizeRequest requestPrize(iPrizeRequest.iResult iResult) {
        return new PrizeRequest(iResult);
    }

    @Override
    public MyPrizeRequest myPrizeRequest() {
        return new MyPrizeRequest();
    }

    @Override
    public MyPrizeRequest myPrizeRequest(iMyPrizeRequest.iResult iResult) {
        return new MyPrizeRequest(iResult);
    }

    @Override
    public GetDailyPrize getDailyPrize() {
        return new GetDailyPrize();
    }

    @Override
    public GetDailyPrize getDailyPrize(iGetDailyPrize.iResult iResult) {
        return new GetDailyPrize(iResult);
    }

    @Override
    public BuyDailyPrize buyDailyPrize() {
        return new BuyDailyPrize();
    }

    @Override
    public BuyDailyPrize buyDailyPrize(iBuyDailyPrize.iResult iResult) {
        return new BuyDailyPrize(iResult);
    }
}
