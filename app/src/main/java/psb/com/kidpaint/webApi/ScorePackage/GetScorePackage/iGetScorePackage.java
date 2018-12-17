package psb.com.kidpaint.webApi.ScorePackage.GetScorePackage;

import psb.com.kidpaint.webApi.ScorePackage.GetScorePackage.model.ResponseGetScorePackage;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by morteza on 1/29/2018 AD.
 */

public interface iGetScorePackage {

    void doGetScorePackage();

    interface iResult {
        void onSuccessGetScorePackage(ResponseGetScorePackage responseGetScorePackage);

        void onFailedGetScorePackage(int errorCode, String ErrorMessage);
    }

    interface apiRequest {
        @GET("ScorePackage/Get")
        Call<ResponseGetScorePackage> getScorePackage();
    }
}
