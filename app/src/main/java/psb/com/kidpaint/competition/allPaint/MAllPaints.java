package psb.com.kidpaint.competition.allPaint;

import android.content.Context;

import psb.com.kidpaint.webApi.paint.Paint;
import psb.com.kidpaint.webApi.paint.getAllPaints.iGetAllPaints;
import psb.com.kidpaint.webApi.paint.getAllPaints.model.AllPaintModel;
import psb.com.kidpaint.webApi.paint.getAllPaints.model.ResponseGetAllPaints;
import psb.com.kidpaint.webApi.paint.getMyPaints.model.MyPaint;
import psb.com.kidpaint.webApi.paint.getMyPaints.model.ResponseGetMyPaints;

public class MAllPaints implements IMAllPaints {

    private Context context;
    private IPAllPaints ipAllPaints;
    private ResponseGetAllPaints mResponseGetAllPaints;

    public MAllPaints(IPAllPaints ipAllPaints) {
        this.ipAllPaints = ipAllPaints;
        this.context= ipAllPaints.getContext();

    }

    @Override
    public Context getContext() {
        return context;
    }



    @Override
    public void onGetAllPaints(String text, final int page, int size) {
        new Paint().getAllPaints(new iGetAllPaints.iResult() {
            @Override
            public void onSuccessGetAllPaints(ResponseGetAllPaints responseGetAllPaints) {

                if (page==1) {
                    mResponseGetAllPaints=responseGetAllPaints;
                    ipAllPaints.onSuccessGetAllPaints(mResponseGetAllPaints);
                }else {
                    mResponseGetAllPaints.getExtra().getAllPaintModel().addAll(responseGetAllPaints.getExtra().getAllPaintModel());
                    ipAllPaints.onSuccessGetAllPaints(mResponseGetAllPaints);
                }
            }

            @Override
            public void onFailedGetAllPaints(int errorId, String ErrorMessage) {
                ipAllPaints.onFailedGetAllPaints(errorId, ErrorMessage);

            }
        }).doGetAllPaints(text,page,size);
    }

    @Override
    public void setResponseGetAllPaints(ResponseGetAllPaints responseGetAllPaints) {
        mResponseGetAllPaints=responseGetAllPaints;

    }





    @Override
    public int getArrSizeAllPaints() {
        return mResponseGetAllPaints.getExtra().getAllPaintModel().size();
    }

    @Override
    public int getServerAllPaintsSize() {
        return mResponseGetAllPaints.getExtra().getTotal();
    }


    @Override
    public AllPaintModel getAllPaintsPositionAt(int position) {
        return mResponseGetAllPaints.getExtra().getAllPaintModel().get(position);
    }
}
