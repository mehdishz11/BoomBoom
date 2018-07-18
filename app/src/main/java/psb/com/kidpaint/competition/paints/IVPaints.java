package psb.com.kidpaint.competition.paints;

import android.content.Context;

import psb.com.kidpaint.webApi.paint.getAllPaints.model.ResponseGetAllPaints;
import psb.com.kidpaint.webApi.paint.getMyPaints.model.ResponseGetMyPaints;

public interface IVPaints {

    Context getContext();

    void onStartGetAllPaints();
    void onSuccessGetAllPaints(ResponseGetAllPaints responseGetAllPaints);
    void onFailedGetAllPaints(int errorCode, String errorMessage);


    void onSuccessDeleteMyPaints(int position);

}
