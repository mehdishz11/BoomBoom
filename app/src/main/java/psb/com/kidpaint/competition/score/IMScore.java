package psb.com.kidpaint.competition.score;

import android.content.Context;

public interface IMScore {
    Context geContext();

    void onSendScore(int paintId);

}
