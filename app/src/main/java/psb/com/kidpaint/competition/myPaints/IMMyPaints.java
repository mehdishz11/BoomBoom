package psb.com.kidpaint.competition.myPaints;

import android.content.Context;

import psb.com.kidpaint.webApi.paint.getMyPaints.model.ResponseGetMyPaints;
import psb.com.kidpaint.webApi.shareModel.PaintModel;
import psb.com.kidpaint.webApi.shareModel.User;

public interface IMMyPaints {

    Context getContext();

    void setResponseGetMyPaints(ResponseGetMyPaints responseGetMyPaints);

     void deleteMyPaints(int position);

     int getArrSizeMyPaints();

    PaintModel getMyPaintsPositionAt(int position);
    PaintModel getFirstPaintModel();
    User getUser();

    void onGetMyPaints();


}
