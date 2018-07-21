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

public class Prize implements iPrize {
    @Override
    public GetPrize getPrize() {
        return new GetPrize();
    }

    @Override
    public GetPrize getPrize(iGetPrize.iResult iResult) {
        return new GetPrize(iResult);
    }
}
