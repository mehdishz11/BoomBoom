package psb.com.kidpaint.competition.allPaint;

import android.content.Context;

import psb.com.kidpaint.webApi.shareModel.PaintModel;
import psb.com.kidpaint.webApi.paint.getAllPaints.model.ResponseGetAllPaints;

public interface IMAllPaints {

    Context getContext();


    void onGetAllPaints(String text, int page, int size);
    void setResponseGetAllPaints(ResponseGetAllPaints responseGetAllPaints);


     int getArrSizeAllPaints();
     int getServerAllPaintsSize();

    PaintModel getAllPaintsPositionAt(int position);

    void onSendScore(int position);
    boolean userIsRegistered();


}
