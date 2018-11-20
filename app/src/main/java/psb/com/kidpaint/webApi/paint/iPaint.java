package psb.com.kidpaint.webApi.paint;


import psb.com.kidpaint.webApi.paint.deletePaint.DeletePaint;
import psb.com.kidpaint.webApi.paint.deletePaint.iDeletePaint;
import psb.com.kidpaint.webApi.paint.getAllPaints.GetAllPaints;
import psb.com.kidpaint.webApi.paint.getAllPaints.iGetAllPaints;
import psb.com.kidpaint.webApi.paint.getLeaderShip.GetLeaderShip;
import psb.com.kidpaint.webApi.paint.getLeaderShip.iGetLeaderShip;
import psb.com.kidpaint.webApi.paint.getMyPaints.GetMyPaints;
import psb.com.kidpaint.webApi.paint.getMyPaints.iGetMyPaints;
import psb.com.kidpaint.webApi.paint.paintToMatch.PaintToMatch;
import psb.com.kidpaint.webApi.paint.paintToMatch.iPaintToMatch;
import psb.com.kidpaint.webApi.paint.postPaint.PostPaint;
import psb.com.kidpaint.webApi.paint.postPaint.iPostPaint;
import psb.com.kidpaint.webApi.paint.savePaints.SavePaints;
import psb.com.kidpaint.webApi.paint.savePaints.iSavePaints;
import psb.com.kidpaint.webApi.paint.score.Score;
import psb.com.kidpaint.webApi.paint.score.iScore;

public interface iPaint {
    String apiAddress = "Paint/";

   PostPaint postPaint();
   PostPaint postPaint(iPostPaint.iResult iResult);

   GetMyPaints getMyPaints();
   GetMyPaints getMyPaints(iGetMyPaints.iResult iResult);

   GetAllPaints getAllPaints();
   GetAllPaints getAllPaints(iGetAllPaints.iResult iResult);

    GetLeaderShip getLeaderShip();
    GetLeaderShip getLeaderShip(iGetLeaderShip.iResult result);

    Score score();
    Score score(iScore.iResult iResult);

    DeletePaint deletePaint();
    DeletePaint deletePaint(iDeletePaint.iResult iResult);

    SavePaints savePaints();
    SavePaints savePaints(iSavePaints.iResult iResult);

    PaintToMatch paintToMatch();
    PaintToMatch paintToMatch(iPaintToMatch.iResult iResult);

}
