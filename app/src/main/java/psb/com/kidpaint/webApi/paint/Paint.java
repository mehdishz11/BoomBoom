package psb.com.kidpaint.webApi.paint;

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

public class Paint implements iPaint {
    @Override
    public PostPaint postPaint() {
        return new PostPaint();
    }

    @Override
    public PostPaint postPaint(iPostPaint.iResult iResult) {
        return new PostPaint(iResult);
    }

    @Override
    public GetMyPaints getMyPaints() {
        return new GetMyPaints();
    }

    @Override
    public GetMyPaints getMyPaints(iGetMyPaints.iResult iResult) {
        return new GetMyPaints(iResult);
    }

    @Override
    public GetAllPaints getAllPaints() {
        return new GetAllPaints();
    }

    @Override
    public GetAllPaints getAllPaints(iGetAllPaints.iResult iResult) {
        return new GetAllPaints(iResult);
    }

    @Override
    public GetLeaderShip getLeaderShip() {
        return new GetLeaderShip();
    }

    @Override
    public GetLeaderShip getLeaderShip(iGetLeaderShip.iResult result) {
        return new GetLeaderShip(result);
    }

    @Override
    public Score score() {
        return new Score();
    }

    @Override
    public Score score(iScore.iResult iResult) {
        return new Score(iResult);
    }
}
