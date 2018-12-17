package psb.com.kidpaint.competition.myPaints;

import android.content.Context;

import psb.com.kidpaint.webApi.paint.getMyPaints.model.ResponseGetMyPaints;
import psb.com.kidpaint.webApi.shareModel.PaintModel;

public interface IVMyPaints {

    Context getContext();


    void onSelectPaint(PaintModel paintModel);
    void onStartGetMyPaints(int loadMode);

    void onSuccessGetMyPaints(ResponseGetMyPaints responseGetMyPaints);
    void onFailedGetMyPaints(int errorCode,String errorMessage);

    void onFailedDeleteMyPaints(int errorCode,String errorMessage);
    void onSuccessDeleteMyPaints(int position);
    void onStartDeleteMyPaints();
    void showDialogDelete(int position);

}
