package psb.com.kidpaint.competition.myPaints;

import android.content.Context;

import psb.com.kidpaint.webApi.paint.getAllPaints.model.ResponseGetAllPaints;

public interface IVMyPaints {

    Context getContext();


    void onSuccessDeleteMyPaints(int position);

}
