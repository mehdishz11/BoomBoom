package psb.com.kidpaint.webApi.ScorePackage;

import psb.com.kidpaint.webApi.ScorePackage.GetScorePackage.GetScorePackage;
import psb.com.kidpaint.webApi.ScorePackage.GetScorePackage.iGetScorePackage;
import psb.com.kidpaint.webApi.ScorePackage.buy.Buy;
import psb.com.kidpaint.webApi.ScorePackage.buy.iBuy;

public class ScorePackage implements iScorePackage {

    @Override
    public GetScorePackage getScorePackage() {
        return new GetScorePackage();
    }

    @Override
    public GetScorePackage getScorePackage(iGetScorePackage.iResult iResult) {
        return new GetScorePackage(iResult);
    }

    @Override
    public Buy buy() {
        return new Buy();
    }

    @Override
    public Buy buy(iBuy.iResult iResult) {
        return new Buy(iResult);
    }
}
