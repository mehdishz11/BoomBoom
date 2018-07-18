package psb.com.kidpaint.competition.paints;

import android.content.Context;

import psb.com.kidpaint.webApi.paint.getAllPaints.model.AllPaintModel;
import psb.com.kidpaint.webApi.paint.getAllPaints.model.ResponseGetAllPaints;
import psb.com.kidpaint.webApi.paint.getMyPaints.model.MyPaint;
import psb.com.kidpaint.webApi.paint.getMyPaints.model.ResponseGetMyPaints;

public interface IMPaints {

    Context getContext();

    void setResponseGetMyPaints(ResponseGetMyPaints responseGetMyPaints);

    void onGetAllPaints(String text, int page, int size);
    void setResponseGetAllPaints(ResponseGetAllPaints responseGetAllPaints);

     void deleteMyPaints(int position);

     int getArrSizeMyPaints();
     int getArrSizeAllPaints();
     int getServerAllPaintsSize();

    MyPaint getMyPaintsPositionAt(int position);
    AllPaintModel getAllPaintsPositionAt(int position);

}
