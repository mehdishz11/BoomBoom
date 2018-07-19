package psb.com.kidpaint.competition.score;

import android.content.Context;

import psb.com.kidpaint.webApi.paint.score.model.ResponseScore;

public interface IVScore {
    Context getContext();
    void onStartSendScore();
    void onSuccessSendScore(ResponseScore responseScore);
    void onFailedSendScore(int errorCode,String errorMessage);
}
