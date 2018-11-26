package psb.com.kidpaint.competition.leaderBoard;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import psb.com.kidpaint.R;
import psb.com.kidpaint.competition.leaderBoard.adapter.ViewHolder_LeaderShip;
import psb.com.kidpaint.utils.UserProfile;
import psb.com.kidpaint.utils.Value;
import psb.com.kidpaint.utils.customView.dialog.DialogRate;
import psb.com.kidpaint.webApi.paint.getLeaderShip.model.LeaderModel;
import psb.com.kidpaint.webApi.paint.getLeaderShip.model.ResponseGetLeaderShip;
import psb.com.kidpaint.webApi.paint.score.iScore;
import psb.com.kidpaint.webApi.paint.score.model.ResponseScore;

public class PLeaderShip implements IPLeaderShip {

    private Context context;
    private IVLeaderShip ivLeaderShip;
    private MLeaderShip mPaints;

    public PLeaderShip(IVLeaderShip ivLeaderShip) {
        this.ivLeaderShip = ivLeaderShip;
        this.context = ivLeaderShip.getContext();
        this.mPaints = new MLeaderShip(this);
    }

    @Override
    public Context getContext() {
        return context;
    }


    @Override
    public void onGetLeaderShip(int page, int size, int matchId, int level) {
        ivLeaderShip.onStartGetLeaderShip();
        mPaints.onGetLeaderShip(page, size, matchId, level);
    }

    @Override
    public void setResponseGetLeaderShip(ResponseGetLeaderShip responseGetLeaderShip) {
        mPaints.setResponseGetLeaderShip(responseGetLeaderShip);
    }

    @Override
    public void onSuccessGetLeaderShip(ResponseGetLeaderShip responseGetLeaderShip) {
        ivLeaderShip.onSuccessGetLeaderShip(responseGetLeaderShip);
    }

    @Override
    public void onFailedGetLeaderShip(int errorCode, String errorMessage) {
        ivLeaderShip.onFailedGetLeaderShip(errorCode, errorMessage);
    }


    @Override
    public void onBindViewHolder_GetLeaderShip(ViewHolder_LeaderShip holder, final int position) {
        final LeaderModel leaderModel = mPaints.getLeaderShipPositionAt(position);

        if (leaderModel.getUrl() != null && !leaderModel.getUrl().isEmpty()) {
            Picasso
                    .get()
                    .load(leaderModel.getUrl())
                    .resize(Value.dp(200), 0)
                    .onlyScaleDown()
                    .into(holder.imgOutline);
        }

        if (leaderModel.getUser().getImageUrl() != null && !leaderModel.getUser().getImageUrl().isEmpty()) {
            Picasso.get().load(leaderModel.getUser().getImageUrl()).into(holder.imageUser);
        }

        holder.textUserName.setText(leaderModel.getUser().getFirstName() + " " + leaderModel.getUser().getLastName());
        holder.textImageCode.setText(context.getString(R.string.image_code) + " " + leaderModel.getCode());

        holder.parentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mPaints.userIsRegistered()) {
                    ivLeaderShip.onSelectPaint(leaderModel);
                } else {
                    Toast.makeText(getContext(), "برای دیدن جزئیات باید ثبت نام کنید", Toast.LENGTH_SHORT).show();
                }
            }
        });

        holder.like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mPaints.userIsRegistered()) {
                    if (mPaints.getLeaderShipPositionAt(position) != null && !new UserProfile(getContext()).get_KEY_PHONE_NUMBER("").isEmpty()) {
                        final DialogRate dialogRate = new DialogRate(getContext());
                        dialogRate.setPaintId(mPaints.getLeaderShipPositionAt(position).getId());
                        dialogRate.setPhoneNumber(new UserProfile(getContext()).get_KEY_PHONE_NUMBER(""));
                        dialogRate.setiResult(new iScore.iResult() {
                            @Override
                            public void onSuccessScore(ResponseScore responseScore) {
                                dialogRate.cancel();
                                ivLeaderShip.onSuccessSendScore(position);
                            }

                            @Override
                            public void onFailedScore(int errorId, String ErrorMessage) {
                                dialogRate.cancel();
                                ivLeaderShip.onFailedSendScore(errorId,ErrorMessage);
                            }
                        });
                        dialogRate.show();
                    }
                } else {
                    ivLeaderShip.showUserRegisterDialog(position);
                }
            }
        });

        holder.textRate.setText("" + leaderModel.getRank());
        holder.text_user_points.setText(getContext().getText(R.string.total_points) + "" + (leaderModel.getScore()));
///////////////////////////////////////////////////////////////////////////////////
        if (position == 0) {
           /* holder.viewBackground.setBackgroundColor(getContext().getResources().getColor(R.color.md_yellow_300));
            holder.imgPaint.setBorderColor(getContext().getResources().getColor(R.color.md_yellow_300));*/
            holder.imgLeaves.setVisibility(View.VISIBLE);
        } else if (position == 1) {
          /*  holder.viewBackground.setBackgroundColor(getContext().getResources().getColor(R.color.md_grey_300));
            holder.imgPaint.setBorderColor(getContext().getResources().getColor(R.color.md_grey_300));*/
            holder.imgLeaves.setVisibility(View.VISIBLE);
        } else if (position == 2) {
         /*   holder.viewBackground.setBackgroundColor(getContext().getResources().getColor(R.color.md_orange_300));
            holder.imgPaint.setBorderColor(getContext().getResources().getColor(R.color.md_orange_300));*/
            holder.imgLeaves.setVisibility(View.VISIBLE);
        } else {
            holder.imgLeaves.setVisibility(View.INVISIBLE);
         /*   holder.viewBackground.setBackgroundColor(getContext().getResources().getColor(R.color.brown_1));
            holder.imgPaint.setBorderColor(getContext().getResources().getColor(R.color.brown_1));*/
        }
    }

    @Override
    public int getArrSizeGetLeaderShip() {
        return mPaints.getArrSizeLeaderShip();
    }

    @Override
    public int getServerGetLeaderShipSize() {
        return mPaints.getServerLeaderShipSize();
    }

    @Override
    public void onSendScore(int position) {
        ivLeaderShip.onStartSendScore();

        mPaints.onSendScore(position);
    }

    @Override
    public void onSuccessSendScore(int position) {
        ivLeaderShip.onSuccessSendScore(position);
    }

    @Override
    public void onFailedSendScore(int errorCode, String errorMessage) {
        ivLeaderShip.onFailedSendScore(errorCode, errorMessage);
    }


}
