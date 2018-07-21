package psb.com.kidpaint.home.history;

import android.content.Context;
import android.util.Log;
import android.view.View;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.Random;

import psb.com.kidpaint.home.history.adapter.HistoryViewHolder;
import psb.com.kidpaint.utils.Value;
import psb.com.kidpaint.webApi.paint.postPaint.model.ResponsePostPaint;

public class PHistory implements  IPHistory {
    private Context context;
    private IVHistory ivHistory;
    private MHistory mHistory;

    public PHistory(IVHistory ivHistory) {
        this.ivHistory = ivHistory;
        this.context=ivHistory.getContext();
        this.mHistory=new MHistory(this);
    }

    @Override
    public Context getContext() {
        return context;
    }

    @Override
    public void postPaint(int position) {
        ivHistory.onStartPostPaint();
        mHistory.postPaint(position);
    }

    @Override
    public void getMyPaintHistory() {
         ivHistory.onStartMyPaintHistory();
         mHistory.getMyPaintHistory();
    }

    @Override
    public void onGetMyPaintHistorySuccess() {
       ivHistory.onGetMyPaintHistorySuccess();
    }

    @Override
    public void onGetMyPaintHistoryFailed() {
     ivHistory.onGetMyPaintHistoryFailed();
    }

    @Override
    public void onBindViewHolder(HistoryViewHolder holder, final int position) {
     final File filePath=mHistory.getPositionAt(position);

        if(position%2==0){
            holder.parentView.setRotation(new Random().nextInt(8));
        }else{
            holder.parentView.setRotation(-new Random().nextInt(8));
        }
        Picasso.get().invalidate(filePath);
        Picasso
                .get()
                .load(filePath)
                .resize(Value.dp(200),0)
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


        holder.relParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               ivHistory.onSelecteditem(filePath.getAbsolutePath());
            }
        });  holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               ivHistory.onSelecteditem(filePath.getAbsolutePath());
            }
        });
//
        holder.competition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mHistory.userIsRegistered()) {
                    ivHistory.onStartPostPaint();
                    mHistory.postPaint(position);
                }else {
                    ivHistory.showUserRegisterDialog(position);
                }
            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ivHistory.showDeleteDialog(position);
            }
        });


    }

    @Override
    public int getArrSize() {
        return mHistory.getArrSize();
    }

    @Override
    public void onSuccessPostPaint(ResponsePostPaint responsePostPaint) {
        ivHistory.onSuccessPostPaint(responsePostPaint);
    }

    @Override
    public void onFailedPostPaint(int errorCode, String errorMessage) {
        ivHistory.onFailedPostPaint(errorCode, errorMessage);

    }

    @Override
    public void deletePaint(int position) {
        mHistory.deletePaint(position);
    }

}
