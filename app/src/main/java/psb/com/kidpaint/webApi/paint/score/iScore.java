package psb.com.kidpaint.webApi.paint.score;

import psb.com.kidpaint.webApi.paint.getMyPaints.model.ResponseGetMyPaints;
import psb.com.kidpaint.webApi.paint.iPaint;
import psb.com.kidpaint.webApi.paint.score.model.ParamsScore;
import psb.com.kidpaint.webApi.paint.score.model.ResponseScore;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by morteza on 1/29/2018 AD.
 */

public interface iScore {

    void doScore(ParamsScore paramsScore);

    interface iResult {
        void onSuccessScore(ResponseScore responseScore);

        void onFailedScore(int errorId, String ErrorMessage);
    }

    interface apiRequest {
        @POST(iPaint.apiAddress+"Score")
        Call<ResponseScore> setScore(@Body() ParamsScore paramsScore);
    }
}
