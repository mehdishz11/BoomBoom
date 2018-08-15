package psb.com.kidpaint.home;

import android.content.Context;
import android.util.Log;
import android.view.View;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.Random;

import psb.com.kidpaint.R;
import psb.com.kidpaint.home.history.adapter.HistoryViewHolder;
import psb.com.kidpaint.utils.Value;
import psb.com.kidpaint.webApi.paint.postPaint.model.ResponsePostPaint;
import psb.com.kidpaint.webApi.prize.PrizeRequest.model.ParamsPrizeRequest;


public class PHome implements IP_Home {
    private IV_Home iv_home;
    private MHome mHome;
    private Context context;

    public PHome(IV_Home iv_home) {
        this.iv_home = iv_home;
        this.context=iv_home.getContext();
        this.mHome=new MHome(this);
    }

    @Override
    public Context getContext() {
        return context;
    }

    @Override
    public void onStartLogout() {
        iv_home.onStartLogout();
        mHome.onStartLogout();
    }

    @Override
    public void onLogoutSuccess() {
        iv_home.onLogoutSuccess();
    }

    @Override
    public void onLogoutFailed(String errorMessage) {
        iv_home.onLogoutFailed(errorMessage);

    }

    @Override
    public void prizeRequest(ParamsPrizeRequest paramsPrizeRequest) {
        iv_home.startGetPrizeRequest();
        mHome.prizeRequest(paramsPrizeRequest);
    }

    @Override
    public void prizeRequestSuccess(int score) {
        iv_home.prizeRequestSuccess(score);
    }

    @Override
    public void prizeRequestFailed(String msg) {
        iv_home.prizeRequestFailed(msg);
    }

    @Override
    public int getUnreadMessageCount() {
        return mHome.getUnreadMessageCount();
    }

    @Override
    public void postPaint(int position) {
        iv_home.onStartPostPaint();
        mHome.postPaint(position);
    }

    @Override
    public void getMyPaintHistory() {
        iv_home.onStartMyPaintHistory();
        mHome.getMyPaintHistory();
    }

    @Override
    public void onGetMyPaintHistorySuccess() {
        iv_home.onGetMyPaintHistorySuccess();
    }

    @Override
    public void onGetMyPaintHistoryFailed() {
        iv_home.onGetMyPaintHistoryFailed();
    }

    @Override
    public void onBindViewHolder(HistoryViewHolder holder, final int position) {
        final File filePath=mHome.getPositionAt(position);

        if(position%2==0){
            holder.parentView.setRotation(new Random().nextInt(8));
        }else{
            holder.parentView.setRotation(-new Random().nextInt(8));
        }

        if (filePath==null) {

            holder.relBtns.setVisibility(View.GONE);
            holder.relBtnsBg.setVisibility(View.GONE);
            holder.textViewnew.setVisibility(View.VISIBLE);
            holder.relParent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    iv_home.onOutlineSelected(R.drawable.small_picture_0);
                }
            });
            Picasso.get().invalidate("gfgfg");

            Picasso
                    .get()
                    .load("gfgfg")
                    .into(holder.imgOutline);

        }else {

            holder.relBtns.setVisibility(View.VISIBLE);
            holder.relBtnsBg.setVisibility(View.VISIBLE);
            holder.textViewnew.setVisibility(View.GONE);
            Picasso.get().invalidate(filePath);
            Picasso
                    .get()
                    .load(filePath)
                    .resize(Value.dp(200), 0)
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
                    iv_home.onSelecteditem(filePath.getAbsolutePath());
                }
            });
            holder.edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    iv_home.onSelecteditem(filePath.getAbsolutePath());
                }
            });
//
            holder.competition.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mHome.userIsRegistered()) {
                        iv_home.onStartPostPaint();
                        mHome.postPaint(position);
                    } else {
                        iv_home.showUserRegisterDialog(position);
                    }
                }
            });

            holder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    iv_home.showDeleteDialog(position);
                }
            });
        }

    }

    @Override
    public int getArrSize() {
        return mHome.getArrSize();
    }

    @Override
    public void onSuccessPostPaint(ResponsePostPaint responsePostPaint) {
        iv_home.onSuccessPostPaint(responsePostPaint);
    }

    @Override
    public void onFailedPostPaint(int errorCode, String errorMessage) {
        iv_home.onFailedPostPaint(errorCode, errorMessage);

    }

    @Override
    public void deletePaint(int position) {
        mHome.deletePaint(position);
    }

    @Override
    public File getLastPaintFile() {
        return mHome.getLastPaintFile();
    }
}
