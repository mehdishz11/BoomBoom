package psb.com.kidpaint.competition.myPaints;

import android.content.Context;

import psb.com.kidpaint.competition.myPaints.adapter.ViewHolder_MyPaints;
import psb.com.kidpaint.webApi.paint.getAllPaints.model.ResponseGetAllPaints;
import psb.com.kidpaint.webApi.paint.getMyPaints.model.ResponseGetMyPaints;

public interface IPMyPaints {

    Context getContext();

    void setResponseGetMyPaints(ResponseGetMyPaints responseGetMyPaints);

     void onBindViewHolder_MyPaints(ViewHolder_MyPaints holder, int position);

     int getArrSizeMyPaints();

    void onSuccessDeleteMyPaints(int position);


}
