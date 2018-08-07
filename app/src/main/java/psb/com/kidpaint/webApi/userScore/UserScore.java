package psb.com.kidpaint.webApi.userScore;


import psb.com.kidpaint.webApi.userScore.buySticker.BuySticker;
import psb.com.kidpaint.webApi.userScore.buySticker.iBuySticker;

public class UserScore implements iUserScore {


    @Override
    public BuySticker buySticker() {
        return new BuySticker();
    }

    @Override
    public BuySticker buySticker(iBuySticker.iResult iResult) {
        return new BuySticker(iResult);
    }
}
