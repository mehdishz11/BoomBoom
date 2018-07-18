package psb.com.kidpaint.competition.allPaint;

import android.content.Context;
import android.util.Log;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.Random;

import psb.com.kidpaint.competition.allPaint.adapter.ViewHolder_AllPaints;
import psb.com.kidpaint.utils.Value;
import psb.com.kidpaint.webApi.paint.getAllPaints.model.AllPaintModel;
import psb.com.kidpaint.webApi.paint.getAllPaints.model.ResponseGetAllPaints;

public class PAllPaints implements IPAllPaints {

    private Context context;
    private IVAllPaints ivAllPaints;
    private MAllPaints mPaints;

    public PAllPaints(IVAllPaints ivAllPaints) {
        this.ivAllPaints = ivAllPaints;
        this.context= ivAllPaints.getContext();
        this.mPaints=new MAllPaints(this);
    }

    @Override
    public Context getContext() {
        return context;
    }



    @Override
    public void onGetAllPaints(String text, int page, int size) {
       ivAllPaints.onStartGetAllPaints();
       mPaints.onGetAllPaints(text, page, size);
    }

    @Override
    public void setResponseGetAllPaints(ResponseGetAllPaints responseGetAllPaints) {
       mPaints.setResponseGetAllPaints(responseGetAllPaints);
    }

    @Override
    public void onSuccessGetAllPaints(ResponseGetAllPaints responseGetAllPaints) {
      ivAllPaints.onSuccessGetAllPaints(responseGetAllPaints);
    }

    @Override
    public void onFailedGetAllPaints(int errorCode, String errorMessage) {
    ivAllPaints.onFailedGetAllPaints(errorCode, errorMessage);
    }


    @Override
    public void onBindViewHolder_AllPaints(ViewHolder_AllPaints holder, int position) {
        AllPaintModel paintModel=mPaints.getAllPaintsPositionAt(position);

        if(position%2==0){
            holder.parentView.setRotation(new Random().nextInt(8));
        }else{
            holder.parentView.setRotation(-new Random().nextInt(8));
        }
        Picasso
                .get()
                .load(paintModel.getUrl())
                .resize(Value.dp(200),Value.dp(200))
                .onlyScaleDown()
                .into(holder.imgOutline, new Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError(Exception e) {

                        Log.d("TAG", "onError: ");
                        e.printStackTrace();
                    }
                });
    }


    @Override
    public int getArrSizeAllPaints() {
        return mPaints.getArrSizeAllPaints();
    }



    @Override
    public int getServerAllPaintsSize() {
        return mPaints.getServerAllPaintsSize();
    }
}
