package psb.com.kidpaint.competition.myPaints;

import android.content.Context;

import psb.com.kidpaint.webApi.paint.getAllPaints.model.ResponseGetAllPaints;
import psb.com.kidpaint.webApi.shareModel.PaintModel;

public interface IVMyPaints {

    Context getContext();


    void onSuccessDeleteMyPaints(int position);
    void onSelectPaint(PaintModel paintModel);

}
