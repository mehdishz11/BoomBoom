package psb.com.kidpaint.competition;

import android.content.Context;

public interface IMCompetition {

    Context getContext();

    void onGetMyPaints();

    void onGetAllPaints();

    void onGetLeaderBoard();


}
