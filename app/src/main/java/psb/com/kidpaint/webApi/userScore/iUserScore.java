package psb.com.kidpaint.webApi.userScore;


import psb.com.kidpaint.webApi.userScore.buySticker.BuySticker;
import psb.com.kidpaint.webApi.userScore.buySticker.iBuySticker;

public interface iUserScore {

  BuySticker buySticker();
  BuySticker buySticker(iBuySticker.iResult iResult);

}
