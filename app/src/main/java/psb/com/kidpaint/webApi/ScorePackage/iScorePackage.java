package psb.com.kidpaint.webApi.ScorePackage;

import psb.com.kidpaint.webApi.ScorePackage.GetScorePackage.GetScorePackage;
import psb.com.kidpaint.webApi.ScorePackage.GetScorePackage.iGetScorePackage;
import psb.com.kidpaint.webApi.ScorePackage.buy.Buy;
import psb.com.kidpaint.webApi.ScorePackage.buy.iBuy;

public interface iScorePackage {

  GetScorePackage getScorePackage();
  GetScorePackage getScorePackage(iGetScorePackage.iResult iResult);

  Buy buy();
  Buy buy(iBuy.iResult iResult);
}
