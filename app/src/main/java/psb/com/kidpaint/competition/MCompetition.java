package psb.com.kidpaint.competition;

import android.content.Context;

import psb.com.kidpaint.utils.UserProfile;
import psb.com.kidpaint.webApi.paint.Paint;
import psb.com.kidpaint.webApi.paint.getAllPaints.iGetAllPaints;
import psb.com.kidpaint.webApi.paint.getAllPaints.model.ResponseGetAllPaints;
import psb.com.kidpaint.webApi.paint.getLeaderShip.iGetLeaderShip;
import psb.com.kidpaint.webApi.paint.getLeaderShip.model.ResponseGetLeaderShip;
import psb.com.kidpaint.webApi.paint.getMyPaints.iGetMyPaints;
import psb.com.kidpaint.webApi.paint.getMyPaints.model.ResponseGetMyPaints;

public class MCompetition implements IMCompetition {

    private Context context;
    private IPCompetition ipCompetition;
    private UserProfile userProfile;

    public MCompetition(IPCompetition ipCompetition) {
        this.ipCompetition = ipCompetition;
        this.context=ipCompetition.getContext();
        this.userProfile=new UserProfile(getContext());
    }

    @Override
    public Context getContext() {
        return context;
    }

    @Override
    public void onGetMyPaints() {
      new Paint().getMyPaints(new iGetMyPaints.iResult() {
          @Override
          public void onSuccessGetMyPaints(ResponseGetMyPaints responseGetMyPaints) {
              ipCompetition.onSuccessGetMyPaints(responseGetMyPaints);
          }

          @Override
          public void onFailedGetMyPaints(int errorId, String ErrorMessage) {
                 ipCompetition.onFailedGetMyPaints(errorId, ErrorMessage);
          }
      }).doGetMyPaints(userProfile.get_KEY_PHONE_NUMBER("0"));
    }

    @Override
    public void onGetAllPaints() {
      new Paint().getAllPaints(new iGetAllPaints.iResult() {
          @Override
          public void onSuccessGetAllPaints(ResponseGetAllPaints responseGetAllPaints) {
               ipCompetition.onSuccessGetAllPaints(responseGetAllPaints);
          }

          @Override
          public void onFailedGetAllPaints(int errorId, String ErrorMessage) {
              ipCompetition.onFailedGetAllPaints(errorId, ErrorMessage);

          }
      }).doGetAllPaints(null,1,20);
    }

    @Override
    public void onGetLeaderBoard() {
       new Paint().getLeaderShip(new iGetLeaderShip.iResult() {
           @Override
           public void onSuccessGetLeaderShip(ResponseGetLeaderShip responseGetLeaderShip) {
                ipCompetition.onSuccessGetLeaderBoard(responseGetLeaderShip);
           }

           @Override
           public void onFailedGetLeaderShip(int errorId, String ErrorMessage) {
               ipCompetition.onFailedGetLeaderBoard(errorId, ErrorMessage);

           }
       }).doGetLeaderShip(userProfile.get_KEY_PHONE_NUMBER(""),1,20);
    }
}
