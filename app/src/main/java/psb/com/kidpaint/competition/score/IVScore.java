package psb.com.kidpaint.competition.score;

import android.content.Context;

public interface IVScore {
    Context getContext();
    void onStartSendScore();
    void onSuccessSendScore();
    void onFailedSendScore(int errorCode,String errorMessage);
}
