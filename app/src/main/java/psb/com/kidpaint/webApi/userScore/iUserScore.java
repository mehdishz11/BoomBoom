package psb.com.kidpaint.webApi.userScore;


import psb.com.kidpaint.webApi.userScore.addScore.AddScore;
import psb.com.kidpaint.webApi.userScore.addScore.iAddScore;
import psb.com.kidpaint.webApi.userScore.buySticker.BuySticker;
import psb.com.kidpaint.webApi.userScore.buySticker.iBuySticker;

public interface iUserScore {

  BuySticker buySticker();
  BuySticker buySticker(iBuySticker.iResult iResult);

  AddScore addScore();
  AddScore addScore(iAddScore.iResult iResult);

}
