package psb.com.kidpaint.webApi.match.Get;

import psb.com.kidpaint.webApi.match.Get.model.ResponseGetMatch;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by morteza on 1/29/2018 AD.
 */

public interface iGetMatch {

    void doGetGetMatch(int level);

    interface iResult {
        void onSuccessGetMatch(ResponseGetMatch responseGetMatch);

        void onFailedGetMatch(int errorCode, String ErrorMessage);
    }

    interface apiRequest {
        @GET("Match/Get")
        Call<ResponseGetMatch> getMatch(@Query("level") int level);
    }
}
