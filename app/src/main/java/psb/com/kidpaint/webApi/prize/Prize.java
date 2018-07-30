package psb.com.kidpaint.webApi.prize;

import psb.com.kidpaint.webApi.paint.getAllPaints.GetAllPaints;
import psb.com.kidpaint.webApi.paint.getAllPaints.iGetAllPaints;
import psb.com.kidpaint.webApi.paint.getLeaderShip.GetLeaderShip;
import psb.com.kidpaint.webApi.paint.getLeaderShip.iGetLeaderShip;
import psb.com.kidpaint.webApi.paint.getMyPaints.GetMyPaints;
import psb.com.kidpaint.webApi.paint.getMyPaints.iGetMyPaints;
import psb.com.kidpaint.webApi.paint.postPaint.PostPaint;
import psb.com.kidpaint.webApi.paint.postPaint.iPostPaint;
import psb.com.kidpaint.webApi.paint.score.Score;
import psb.com.kidpaint.webApi.paint.score.iScore;
import psb.com.kidpaint.webApi.prize.Get.GetPrize;
import psb.com.kidpaint.webApi.prize.Get.iGetPrize;
import psb.com.kidpaint.webApi.prize.PrizeRequest.PrizeRequest;
import psb.com.kidpaint.webApi.prize.PrizeRequest.iPrizeRequest;
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
}
