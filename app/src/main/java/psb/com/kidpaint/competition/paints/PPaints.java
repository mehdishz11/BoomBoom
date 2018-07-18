package psb.com.kidpaint.competition.paints;

import android.content.Context;
import android.util.Log;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.Random;

import psb.com.kidpaint.competition.paints.adapter.ViewHolder_AllPaints;
import psb.com.kidpaint.competition.paints.adapter.ViewHolder_MyPaints;
import psb.com.kidpaint.utils.Value;
import psb.com.kidpaint.webApi.paint.getAllPaints.model.AllPaintModel;
import psb.com.kidpaint.webApi.paint.getAllPaints.model.ResponseGetAllPaints;
import psb.com.kidpaint.webApi.paint.getMyPaints.model.MyPaint;
import psb.com.kidpaint.webApi.paint.getMyPaints.model.ResponseGetMyPaints;

public class PPaints implements IPPaints {

    private Context context;
    private IVPaints ivPaints;
    private MPaints mPaints;

    public PPaints(IVPaints ivPaints) {
        this.ivPaints = ivPaints;
        this.context=ivPaints.getContext();
        this.mPaints=new MPaints(this);
    }

    @Override
    public Context getContext() {
        return context;
    }

    @Override
    public void setResponseGetMyPaints(ResponseGetMyPaints responseGetMyPaints) {
        mPaints.setResponseGetMyPaints(responseGetMyPaints);
    }

    @Override
    public void onGetAllPaints(String text, int page, int size) {
       ivPaints.onStartGetAllPaints();
       mPaints.onGetAllPaints(text, page, size);
    }

    @Override
    public void setResponseGetAllPaints(ResponseGetAllPaints responseGetAllPaints) {
       mPaints.setResponseGetAllPaints(responseGetAllPaints);
    }

    @Override
    public void onSuccessGetAllPaints(ResponseGetAllPaints responseGetAllPaints) {
      ivPaints.onSuccessGetAllPaints(responseGetAllPaints);
    }

    @Override
    public void onFailedGetAllPaints(int errorCode, String errorMessage) {
    ivPaints.onFailedGetAllPaints(errorCode, errorMessage);
    }

    @Override
    public void onBindViewHolder_MyPaints(ViewHolder_MyPaints holder, int position) {
        MyPaint myPaint=mPaints.getMyPaintsPositionAt(position);
        if(position%2==0){
            holder.parentView.setRotation(new Random().nextInt(4));
        }else{
            holder.parentView.setRotation(-new Random().nextInt(4));
        }
        Picasso
                .get()
                .load(myPaint.getUrl())
                .resize(Value.dp(120),Value.dp(120))
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
    public int getArrSizeMyPaints() {
        return mPaints.getArrSizeMyPaints();
    }

    @Override
    public int getArrSizeAllPaints() {
        return mPaints.getArrSizeAllPaints();
    }

    @Override
    public void onSuccessDeleteMyPaints(int position) {
         ivPaints.onSuccessDeleteMyPaints(position);
    }

    @Override
    public int getServerAllPaintsSize() {
        return mPaints.getServerAllPaintsSize();
    }
}
