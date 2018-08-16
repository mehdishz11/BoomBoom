package psb.com.kidpaint.webApi.match;

import psb.com.kidpaint.webApi.match.Get.GetMatch;
import psb.com.kidpaint.webApi.match.Get.iGetMatch;

public class Match implements iMatch {
    @Override
    public GetMatch getMatch() {
        return new GetMatch();
    }

    @Override
    public GetMatch getMatch(iGetMatch.iResult iResult) {
        return new GetMatch(iResult);
    }
}
