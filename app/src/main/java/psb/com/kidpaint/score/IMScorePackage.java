package psb.com.kidpaint.score;

import android.content.Context;

import psb.com.kidpaint.webApi.ScorePackage.GetScorePackage.model.ResponseGetScorePackage;

public interface IMScorePackage {

    Context geContext();

    void getScorePackage();

    void doBuyScorePackage(int position);


}
