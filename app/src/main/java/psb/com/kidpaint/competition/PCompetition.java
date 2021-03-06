package psb.com.kidpaint.competition;

import android.content.Context;

import psb.com.kidpaint.webApi.match.Get.model.ResponseGetMatch;
import psb.com.kidpaint.webApi.paint.getAllPaints.model.ResponseGetAllPaints;
import psb.com.kidpaint.webApi.paint.getLeaderShip.model.ResponseGetLeaderShip;
import psb.com.kidpaint.webApi.paint.getMyPaints.model.ResponseGetMyPaints;

public class PCompetition implements IPCompetition {
    private Context context;
    private IVCompetition ivCompetition;
    private MCompetition mCompetition;

    public PCompetition(IVCompetition ivCompetition) {
        this.ivCompetition = ivCompetition;
        this.context=ivCompetition.getContext();
        this.mCompetition=new MCompetition(this);
    }

    @Override
    public Context getContext() {
        return context;
    }

    @Override
    public void onGetMatch(int mode, int level) {
        ivCompetition.onStartGetMatch(mode);
        mCompetition.onGetMatch(level);
    }

    @Override
    public void onSuccessGetGetMatch(ResponseGetMatch responseGetMatch) {
         ivCompetition.onSuccessGetGetMatch(responseGetMatch);
    }

    @Override
    public void onFailedGetGetMatch(int errorCode, String errorMessage) {
         ivCompetition.onFailedGetGetMatch(errorCode, errorMessage);
    }

    @Override
    public void onGetMyPaints() {
        ivCompetition.onStartGetMyPaints();
        mCompetition.onGetMyPaints();
    }

    @Override
    public void onSuccessGetMyPaints(ResponseGetMyPaints responseGetMyPaints) {
       ivCompetition.onSuccessGetMyPaints(responseGetMyPaints);
    }

    @Override
    public void onFailedGetMyPaints(int errorCode,String errorMessage) {
        ivCompetition.onFailedGetMyPaints(errorCode, errorMessage);

    }

    @Override
    public void onGetAllPaints() {
        ivCompetition.onStartGetAllPaints();
        mCompetition.onGetAllPaints();
    }

    @Override
    public void onSuccessGetAllPaints(ResponseGetAllPaints responseGetAllPaints) {
        ivCompetition.onSuccessGetAllPaints(responseGetAllPaints);

    }

    @Override
    public void onFailedGetAllPaints(int errorCode,String errorMessage) {
        ivCompetition.onFailedGetAllPaints(errorCode, errorMessage);

    }

    @Override
    public void onGetLeaderBoard() {
        ivCompetition.onStartGetLeaderBoard();
        mCompetition.onGetLeaderBoard();
    }

    @Override
    public void onSuccessGetLeaderBoard(ResponseGetLeaderShip responseGetLeaderShip) {
        ivCompetition.onSuccessGetLeaderBoard(responseGetLeaderShip);

    }

    @Override
    public void onFailedGetLeaderBoard(int errorCode,String errorMessage) {
        ivCompetition.onFailedGetLeaderBoard(errorCode, errorMessage);

    }
}
