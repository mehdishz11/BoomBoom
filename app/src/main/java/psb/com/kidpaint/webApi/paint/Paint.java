package psb.com.kidpaint.webApi.paint;

import psb.com.kidpaint.webApi.paint.getAllPaints.GetAllPaints;
import psb.com.kidpaint.webApi.paint.getAllPaints.iGetAllPaints;
import psb.com.kidpaint.webApi.paint.getMyPaints.GetMyPaints;
import psb.com.kidpaint.webApi.paint.getMyPaints.iGetMyPaints;
import psb.com.kidpaint.webApi.paint.postPaint.PostPaint;
import psb.com.kidpaint.webApi.paint.postPaint.iPostPaint;

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
}
