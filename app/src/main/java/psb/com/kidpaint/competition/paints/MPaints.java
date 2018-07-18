package psb.com.kidpaint.competition.paints;

import android.content.Context;

import psb.com.kidpaint.webApi.paint.Paint;
import psb.com.kidpaint.webApi.paint.getAllPaints.iGetAllPaints;
import psb.com.kidpaint.webApi.paint.getAllPaints.model.AllPaintModel;
import psb.com.kidpaint.webApi.paint.getAllPaints.model.ResponseGetAllPaints;
import psb.com.kidpaint.webApi.paint.getMyPaints.model.MyPaint;
import psb.com.kidpaint.webApi.paint.getMyPaints.model.ResponseGetMyPaints;

public class MPaints implements IMPaints {

    private Context context;
    private IPPaints ipPaints;
    private ResponseGetAllPaints mResponseGetAllPaints;
    private ResponseGetMyPaints mResponseGetMyPaints;

    public MPaints(IPPaints ipPaints) {
        this.ipPaints = ipPaints;
        this.context=ipPaints.getContext();

    }

    @Override
    public Context getContext() {
        return context;
    }

    @Override
    public void setResponseGetMyPaints(ResponseGetMyPaints responseGetMyPaints) {
         mResponseGetMyPaints=responseGetMyPaints;
    }

    @Override
    public void onGetAllPaints(String text, final int page, int size) {
        new Paint().getAllPaints(new iGetAllPaints.iResult() {
            @Override
            public void onSuccessGetAllPaints(ResponseGetAllPaints responseGetAllPaints) {

                if (page==1) {
                    mResponseGetAllPaints=responseGetAllPaints;
                    ipPaints.onSuccessGetAllPaints(mResponseGetAllPaints);
                }else {
                    mResponseGetAllPaints.getExtra().getAllPaintModel().addAll(responseGetAllPaints.getExtra().getAllPaintModel());
                    ipPaints.onSuccessGetAllPaints(mResponseGetAllPaints);
                }
            }

            @Override
            public void onFailedGetAllPaints(int errorId, String ErrorMessage) {
                ipPaints.onFailedGetAllPaints(errorId, ErrorMessage);

            }
        }).doGetAllPaints(text,page,size);
    }

    @Override
    public void setResponseGetAllPaints(ResponseGetAllPaints responseGetAllPaints) {
        mResponseGetAllPaints=responseGetAllPaints;

    }

    @Override
    public void deleteMyPaints(int position) {

    }


    @Override
    public int getArrSizeMyPaints() {
        return mResponseGetMyPaints.getMyPaint().size();
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
    public MyPaint getMyPaintsPositionAt(int position) {
        return mResponseGetMyPaints.getMyPaint().get(position);
    }

    @Override
    public AllPaintModel getAllPaintsPositionAt(int position) {
        return mResponseGetAllPaints.getExtra().getAllPaintModel().get(position);
    }
}
