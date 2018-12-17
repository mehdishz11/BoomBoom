package psb.com.kidpaint.webApi.paint.getLeaderShip;

import psb.com.kidpaint.webApi.paint.getLeaderShip.model.ResponseGetLeaderShip;
import psb.com.kidpaint.webApi.paint.iPaint;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by morteza on 1/29/2018 AD.
 */

public interface iGetLeaderShip {

    void doGetLeaderShip(String mobile, int page, int size, int matchId, int level);

    interface iResult {
        void onSuccessGetLeaderShip(ResponseGetLeaderShip responseGetLeaderShip);

        void onFailedGetLeaderShip(int errorId, String ErrorMessage);
    }

    interface apiRequest {
        @GET(iPaint.apiAddress+"GetLeaderShip")
        Call<ResponseGetLeaderShip> getLeaderShip(@Query("mobile") String mobile, @Query("page") int page, @Query("size") int size,@Query("matchId") int matchId,@Query("level") int level);
    }
}
