package psb.com.kidpaint.competition.myPaints;

import android.content.Context;

import psb.com.kidpaint.webApi.paint.Paint;
import psb.com.kidpaint.webApi.paint.getAllPaints.iGetAllPaints;
import psb.com.kidpaint.webApi.paint.getAllPaints.model.AllPaintModel;
import psb.com.kidpaint.webApi.paint.getAllPaints.model.ResponseGetAllPaints;
import psb.com.kidpaint.webApi.paint.getMyPaints.model.MyPaint;
import psb.com.kidpaint.webApi.paint.getMyPaints.model.ResponseGetMyPaints;

public class MMyPaints implements IMMyPaints {

    private Context context;
    private IPMyPaints ipMyPaints;
    private ResponseGetMyPaints mResponseGetMyPaints;

    public MMyPaints(IPMyPaints ipMyPaints) {
        this.ipMyPaints = ipMyPaints;
        this.context= ipMyPaints.getContext();

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
    public void deleteMyPaints(int position) {

    }


    @Override
    public int getArrSizeMyPaints() {
        return mResponseGetMyPaints.getMyPaint().size();
    }

  @Override
    public MyPaint getMyPaintsPositionAt(int position) {
        return mResponseGetMyPaints.getMyPaint().get(position);
    }


}
