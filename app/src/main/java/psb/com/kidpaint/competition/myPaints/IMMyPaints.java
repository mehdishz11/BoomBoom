package psb.com.kidpaint.competition.myPaints;

import android.content.Context;

import psb.com.kidpaint.webApi.paint.getAllPaints.model.AllPaintModel;
import psb.com.kidpaint.webApi.paint.getAllPaints.model.ResponseGetAllPaints;
import psb.com.kidpaint.webApi.paint.getMyPaints.model.MyPaint;
import psb.com.kidpaint.webApi.paint.getMyPaints.model.ResponseGetMyPaints;

public interface IMMyPaints {

    Context getContext();

    void setResponseGetMyPaints(ResponseGetMyPaints responseGetMyPaints);

     void deleteMyPaints(int position);

     int getArrSizeMyPaints();

    MyPaint getMyPaintsPositionAt(int position);

}
