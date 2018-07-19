package psb.com.kidpaint.competition.score;

import android.content.Context;

import psb.com.kidpaint.webApi.paint.score.model.ResponseScore;

public interface IMScore {
    Context geContext();

    void onSendScore(int paintId);

}
