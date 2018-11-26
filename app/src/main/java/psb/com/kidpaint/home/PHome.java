package psb.com.kidpaint.home;

import android.content.Context;
import android.view.View;

import com.rasa.sharecontent.ShareContent;
import com.squareup.picasso.Picasso;

import java.util.Random;

import psb.com.kidpaint.home.history.adapter.HistoryViewHolder;
import psb.com.kidpaint.utils.Value;
import psb.com.kidpaint.webApi.paint.getLeaderShip.model.ResponseGetLeaderShip;
import psb.com.kidpaint.webApi.paint.getMyPaints.model.ResponseGetMyPaints;
import psb.com.kidpaint.webApi.paint.postPaint.model.ResponsePostPaint;
import psb.com.kidpaint.webApi.prize.PrizeRequest.model.ParamsPrizeRequest;
import psb.com.kidpaint.webApi.shareModel.HistoryModel;
import psb.com.kidpaint.webApi.userScore.addScore.model.ResponseAddScore;


public class PHome implements IP_Home {
    private IV_Home iv_home;
    private MHome mHome;
    private Context context;

    public PHome(IV_Home iv_home) {
        this.iv_home = iv_home;
        this.context = iv_home.getContext();
        this.mHome = new MHome(this);
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
    public void onBindViewHolder(final HistoryViewHolder holder, final int position) {
        final HistoryModel historyModel = mHome.getPositionAt(holder.getAdapterPosition());

        if (position % 2 == 0) {
            holder.parentView.setRotation(new Random().nextInt(8));
        } else {
            holder.parentView.setRotation(-new Random().nextInt(8));
        }

        holder.imgRibbonCompetition.setVisibility(View.GONE);

        if (historyModel == null) {
            holder.imgRibbonCompetition.setVisibility(View.GONE);
            holder.relBtns.setVisibility(View.GONE);
            holder.textViewnew.setVisibility(View.VISIBLE);
            holder.relParent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    iv_home.onNewPaintSelected();
                }
            });
            Picasso.get().invalidate("gfgfg");

            Picasso
                    .get()
                    .load("gfgfg")
                    .into(holder.imgOutline);

        } else {

            holder.relBtns.setVisibility(View.VISIBLE);
            holder.textViewnew.setVisibility(View.GONE);
            holder.iconCompetition.setText("\uE907");


            holder.relParent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    iv_home.onSelecteditem(historyModel.getFile() != null ? historyModel.getFile().getAbsolutePath() : historyModel.getPaintModel().getUrl());
                }
            });
            holder.edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    iv_home.onSelecteditem((historyModel.getFile() != null ? historyModel.getFile().getAbsolutePath() : historyModel.getPaintModel().getUrl()));
                }
            });

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

            if (historyModel.getFile() != null) {
                Picasso.get().invalidate(historyModel.getFile());
                Picasso
                        .get()
                        .load(historyModel.getFile())
                        .resize(Value.getScreenHeight() / 3, 0)
                        .centerCrop()
                        .into(holder.imgOutline);
            } else {
                Picasso.get().invalidate(historyModel.getPaintModel().getUrl());
                Picasso
                        .get()
                        .load(historyModel.getPaintModel().getUrl())
                        .resize(Value.getScreenHeight() / 3, 0)
                        .centerCrop()
                        .into(holder.imgOutline);

                if (historyModel.getPaintModel().getCode().isEmpty()) {
                } else {
                    //share competition image to others
                    holder.imgRibbonCompetition.setVisibility(View.VISIBLE);
                    holder.iconCompetition.setText("\uE918");
                    holder.competition.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            new ShareContent(getContext()).doShareContent(historyModel.getPaintModel().getUrl()
                            ,"با انتخاب لینک زیر به نقاشی من امتیاز بدین تا برنده بشم و جایزه بگیرم(ممنون)\nhttp://www.getBoomBoom.ir"
                            );
                        }
                    });


                }
            }

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
    public void doAddScore(int addScoreMode) {
        iv_home.onStartAddScore();
        mHome.doAddScore(addScoreMode);
    }

    @Override
    public void onSuccessAddScore(ResponseAddScore responseAddScore) {
        iv_home.onSuccessAddScore(responseAddScore);
    }

    @Override
    public void onFailedAddScore(int errorCode, String errorMessage) {
        iv_home.onFailedAddScore(errorCode, errorMessage);
    }

    @Override
    public void onSuccessDelete(int position) {
        iv_home.onSuccessDelete(position);
    }

    @Override
    public void getRank() {
        iv_home.getRankStarted();
        mHome.getRank();
    }

    @Override
    public void getRankSuccess(ResponseGetLeaderShip responseGetLeaderShip) {
        iv_home.getRankSuccess(responseGetLeaderShip);
    }

    @Override
    public void getRankFailed(String msg) {
        iv_home.getRankFailed();
    }

    @Override
    public void setResponseMyPaints(ResponseGetMyPaints responseMyPaints) {
        mHome.setResponseMyPaints(responseMyPaints);
    }

    @Override
    public void getMyPaints() {
        iv_home.onStartGetMyPaints();
        mHome.getMyPaints();
    }

    @Override
    public void getMyPaintsSuccess() {
        iv_home.getMyPaintsSuccess();
    }

    @Override
    public void getMyPaintsFailed(int errorCode, String errorMessage) {

        iv_home.getMyPaintsFailed(errorCode, errorMessage);

    }

    @Override
    public void onFailedDelete(String errorMessage) {
        iv_home.onFailedDelete(errorMessage);
    }

}
