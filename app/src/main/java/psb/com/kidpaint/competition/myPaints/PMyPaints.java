package psb.com.kidpaint.competition.myPaints;

import android.content.Context;
import android.view.View;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.Random;

import psb.com.kidpaint.R;
import psb.com.kidpaint.competition.myPaints.adapter.ViewHolder_MyPaints;
import psb.com.kidpaint.utils.Value;
import psb.com.kidpaint.utils.soundHelper.SoundHelper;
import psb.com.kidpaint.webApi.paint.getMyPaints.model.ResponseGetMyPaints;
import psb.com.kidpaint.webApi.shareModel.PaintModel;

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
    public void onBindViewHolder_MyPaints(ViewHolder_MyPaints holder, final int position) {
        final PaintModel myPaint=mPaints.getMyPaintsPositionAt(position);
        if(position%2==0){
            holder.parentView.setRotation(new Random().nextInt(4));
        }else{
            holder.parentView.setRotation(-new Random().nextInt(4));
        }

        int valueInPixels = (int) getContext().getResources().getDimension(R.dimen.size_my_paint);

        Picasso
                .get()
                .load(myPaint.getUrl())
                .resize(Value.dp(valueInPixels),0)
                .onlyScaleDown()
                .into(holder.imgOutline, new Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError(Exception e) {

                        e.printStackTrace();
                    }
                });

        holder.parentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SoundHelper.playSound(R.raw.click_1);
                PaintModel paintModel=myPaint;
                paintModel.setUser(mPaints.getUser());
                ivMyPaints.onSelectPaint(paintModel);
            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SoundHelper.playSound(R.raw.click_1);
                ivMyPaints.showDialogDelete(position);
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

    @Override
    public void onFailedDeleteMyPaints(int errorCode, String errorMessage) {
        ivMyPaints.onFailedDeleteMyPaints(errorCode, errorMessage);
    }

    @Override
    public void onGetMyPaints(int loadMode) {
        ivMyPaints.onStartGetMyPaints(loadMode);
        mPaints.onGetMyPaints();
    }

    @Override
    public void onSuccessGetMyPaints(ResponseGetMyPaints responseGetMyPaints) {
        ivMyPaints.onSuccessGetMyPaints(responseGetMyPaints);
    }

    @Override
    public void onFailedGetMyPaints(int errorCode,String errorMessage) {
        ivMyPaints.onFailedGetMyPaints(errorCode, errorMessage);

    }

    @Override
    public void deleteMyPaints(int position) {
          ivMyPaints.onStartDeleteMyPaints();
          mPaints.deleteMyPaints(position);
    }


}
