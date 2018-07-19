package psb.com.kidpaint.competition.score;

import android.content.Context;

import psb.com.kidpaint.utils.UserProfile;
import psb.com.kidpaint.webApi.paint.Paint;
import psb.com.kidpaint.webApi.paint.score.iScore;
import psb.com.kidpaint.webApi.paint.score.model.ParamsScore;
import psb.com.kidpaint.webApi.paint.score.model.ResponseScore;

public class MScore implements IMScore {
    private Context context;
    private IPScore ipScore;
    private UserProfile userProfile;

    public MScore(IPScore ipScore) {
        this.ipScore = ipScore;
        this.context=ipScore.geContext();
        this.userProfile=new UserProfile(geContext());
    }

    @Override
    public Context geContext() {
        return context;
    }

    @Override
    public void onSendScore(int paintId) {
        ParamsScore paramsScore=new ParamsScore();
        paramsScore.setMobile(userProfile.get_KEY_PHONE_NUMBER(""));
        paramsScore.setPaintId(paintId);
        paramsScore.setScore(1);

        new Paint().score(new iScore.iResult() {
            @Override
            public void onSuccessScore(ResponseScore responseScore) {
                ipScore.onSuccessSendScore(responseScore);
            }

            @Override
            public void onFailedScore(int errorId, String ErrorMessage) {

                ipScore.onFailedSendScore(errorId, ErrorMessage);

            }
        }).doScore(paramsScore);


    }
}
