package psb.com.kidpaint.webApi.paint;


import psb.com.kidpaint.webApi.paint.getAllPaints.GetAllPaints;
import psb.com.kidpaint.webApi.paint.getAllPaints.iGetAllPaints;
import psb.com.kidpaint.webApi.paint.getMyPaints.GetMyPaints;
import psb.com.kidpaint.webApi.paint.getMyPaints.iGetMyPaints;
import psb.com.kidpaint.webApi.paint.postPaint.PostPaint;
import psb.com.kidpaint.webApi.paint.postPaint.iPostPaint;

public interface iPaint {
    String apiAddress = "Paint/";

   PostPaint postPaint();
   PostPaint postPaint(iPostPaint.iResult iResult);

   GetMyPaints getMyPaints();
   GetMyPaints getMyPaints(iGetMyPaints.iResult iResult);

   GetAllPaints getAllPaints();
   GetAllPaints getAllPaints(iGetAllPaints.iResult iResult);

}
