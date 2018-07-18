package psb.com.kidpaint.competition.paints;

import android.content.Context;

import psb.com.kidpaint.competition.paints.adapter.ViewHolder_AllPaints;
import psb.com.kidpaint.competition.paints.adapter.ViewHolder_MyPaints;
import psb.com.kidpaint.home.history.adapter.HistoryViewHolder;
import psb.com.kidpaint.webApi.paint.getAllPaints.model.ResponseGetAllPaints;
import psb.com.kidpaint.webApi.paint.getMyPaints.model.ResponseGetMyPaints;

public interface IPPaints {

    Context getContext();

    void setResponseGetMyPaints(ResponseGetMyPaints responseGetMyPaints);

    void onGetAllPaints(String text,int page,int size);
    void setResponseGetAllPaints(ResponseGetAllPaints responseGetAllPaints);
    void onSuccessGetAllPaints(ResponseGetAllPaints responseGetAllPaints);
    void onFailedGetAllPaints(int errorCode, String errorMessage);

     void onBindViewHolder_MyPaints(ViewHolder_MyPaints holder, int position);
     void onBindViewHolder_AllPaints(ViewHolder_AllPaints holder, int position);

     int getArrSizeMyPaints();
     int getArrSizeAllPaints();

    void onSuccessDeleteMyPaints(int position);

    int getServerAllPaintsSize();

}
