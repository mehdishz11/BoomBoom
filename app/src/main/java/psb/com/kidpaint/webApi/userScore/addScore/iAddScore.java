package psb.com.kidpaint.webApi.userScore.addScore;

import psb.com.kidpaint.webApi.userScore.addScore.model.ResponseAddScore;
import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by morteza on 1/29/2018 AD.
 */

public interface iAddScore {

    void doAddScore(String mobile, int code);

    interface iResult {
        void onSuccessAddScore(ResponseAddScore responseAddScore);

        void onFailedAddScore(int errorCode, String ErrorMessage);
    }

    interface apiRequest {
        @POST("UserScore/AddScore")
        Call<ResponseAddScore> addScore(@Query("mobile") String mobile, @Query("code") int code);
    }
}
