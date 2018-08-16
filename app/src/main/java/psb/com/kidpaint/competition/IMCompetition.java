package psb.com.kidpaint.competition;

import android.content.Context;

public interface IMCompetition {

    Context getContext();

    void onGetMatch(int level);

    void onGetMyPaints();

    void onGetAllPaints();

    void onGetLeaderBoard();


}
