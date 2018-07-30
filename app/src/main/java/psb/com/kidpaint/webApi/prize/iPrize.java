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

public interface iPrize {
    String apiAddress = "Prize/";

    GetPrize getPrize();
    GetPrize getPrize(iGetPrize.iResult iResult);

    PrizeRequest requestPrize();
    PrizeRequest requestPrize(iPrizeRequest.iResult iResult);

    MyPrizeRequest myPrizeRequest();
    MyPrizeRequest myPrizeRequest(iMyPrizeRequest.iResult iResult);


}
