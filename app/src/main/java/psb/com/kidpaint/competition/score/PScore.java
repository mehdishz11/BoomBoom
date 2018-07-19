package psb.com.kidpaint.competition.score;

import android.content.Context;

import psb.com.kidpaint.webApi.paint.score.model.ResponseScore;

public class PScore implements IPScore {

    private Context context;
    private IVScore ivScore;
    private MScore mScore;

    public PScore(IVScore ivScore) {
        this.ivScore = ivScore;
        this.context=ivScore.getContext();
        this.mScore=new MScore(this);
    }

    @Override
    public Context geContext() {
        return context;
    }

    @Override
    public void onSendScore(int paintId) {
         ivScore.onStartSendScore();
         mScore.onSendScore(paintId);
    }

    @Override
    public void onSuccessSendScore(ResponseScore responseScore) {
      ivScore.onSuccessSendScore(responseScore);
    }

    @Override
    public void onFailedSendScore(int errorCode, String errorMessage) {
          ivScore.onFailedSendScore(errorCode, errorMessage);
    }
}
