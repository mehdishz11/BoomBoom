package psb.com.kidpaint.competition.allPaint;

import android.content.Context;
import android.view.View;

import com.squareup.picasso.Picasso;

import psb.com.kidpaint.R;
import psb.com.kidpaint.competition.allPaint.adapter.ViewHolder_AllPaints;
import psb.com.kidpaint.utils.Value;
import psb.com.kidpaint.webApi.paint.getAllPaints.model.ResponseGetAllPaints;
import psb.com.kidpaint.webApi.shareModel.PaintModel;

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
    public void onBindViewHolder_AllPaints(final ViewHolder_AllPaints holder, int position) {
        final PaintModel paintModel=mPaints.getAllPaintsPositionAt(position);

        if (paintModel.getUrl()!=null && !paintModel.getUrl().isEmpty()) {
            Picasso
                    .get()
                    .load(paintModel.getUrl())
                    .resize(Value.dp(200), Value.dp(200))
                    .onlyScaleDown()
                    .into(holder.imgOutline);
        }

        if (paintModel.getUser().getImageUrl()!=null && !paintModel.getUser().getImageUrl().isEmpty()) {
            Picasso.get().load(paintModel.getUser().getImageUrl()).into(holder.imageUser);
        }

        holder.textUserName.setText(paintModel.getUser().getFirstName()+" "+paintModel.getUser().getLastName());
        holder.textImageCode.setText(context.getString(R.string.image_code)+" "+paintModel.getCode());

        holder.parentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ivAllPaints.onSelectPaint(paintModel);
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
