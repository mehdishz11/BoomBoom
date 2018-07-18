package psb.com.kidpaint.competition.allPaint;

import android.content.Context;

import psb.com.kidpaint.webApi.paint.getAllPaints.model.AllPaintModel;
import psb.com.kidpaint.webApi.paint.getAllPaints.model.ResponseGetAllPaints;
import psb.com.kidpaint.webApi.paint.getMyPaints.model.MyPaint;
import psb.com.kidpaint.webApi.paint.getMyPaints.model.ResponseGetMyPaints;

public interface IMAllPaints {

    Context getContext();


    void onGetAllPaints(String text, int page, int size);
    void setResponseGetAllPaints(ResponseGetAllPaints responseGetAllPaints);


     int getArrSizeAllPaints();
     int getServerAllPaintsSize();

    AllPaintModel getAllPaintsPositionAt(int position);

}
