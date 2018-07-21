package psb.com.kidpaint.competition.allPaint;

import android.content.Context;

import psb.com.kidpaint.webApi.paint.getAllPaints.model.ResponseGetAllPaints;
import psb.com.kidpaint.webApi.shareModel.PaintModel;

public interface IVAllPaints {

    Context getContext();

    void onStartGetAllPaints();
    void onSuccessGetAllPaints(ResponseGetAllPaints responseGetAllPaints);
    void onFailedGetAllPaints(int errorCode, String errorMessage);

     void onSelectPaint(PaintModel paintModel);

     void showUserRegisterDialog(int position);
    void onStartSendScore();

    void onSuccessSendScore(int position);
    void onFailedSendScore(int errorCode, String errorMessage);
}
