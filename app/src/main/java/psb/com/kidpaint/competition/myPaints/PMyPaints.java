package psb.com.kidpaint.competition.myPaints;

import android.content.Context;
import android.util.Log;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.Random;

import psb.com.kidpaint.competition.myPaints.adapter.ViewHolder_MyPaints;
import psb.com.kidpaint.utils.Value;
import psb.com.kidpaint.webApi.paint.getAllPaints.model.AllPaintModel;
import psb.com.kidpaint.webApi.paint.getAllPaints.model.ResponseGetAllPaints;
import psb.com.kidpaint.webApi.paint.getMyPaints.model.MyPaint;
import psb.com.kidpaint.webApi.paint.getMyPaints.model.ResponseGetMyPaints;

public class PMyPaints implements IPMyPaints {

    private Context context;
    private IVMyPaints ivMyPaints;
    private MMyPaints mPaints;

    public PMyPaints(IVMyPaints ivMyPaints) {
        this.ivMyPaints = ivMyPaints;
        this.context= ivMyPaints.getContext();
        this.mPaints=new MMyPaints(this);
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
    public int getArrSizeMyPaints() {
        return mPaints.getArrSizeMyPaints();
    }

    @Override
    public void onSuccessDeleteMyPaints(int position) {
         ivMyPaints.onSuccessDeleteMyPaints(position);
    }

}
