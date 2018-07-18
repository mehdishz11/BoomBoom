package psb.com.kidpaint.competition.allPaint;

import android.content.Context;

import psb.com.kidpaint.webApi.paint.getAllPaints.model.ResponseGetAllPaints;

public interface IVAllPaints {

    Context getContext();

    void onStartGetAllPaints();
    void onSuccessGetAllPaints(ResponseGetAllPaints responseGetAllPaints);
    void onFailedGetAllPaints(int errorCode, String errorMessage);



}
