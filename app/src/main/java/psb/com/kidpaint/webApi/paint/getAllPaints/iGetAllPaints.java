package psb.com.kidpaint.webApi.paint.getAllPaints;

import psb.com.kidpaint.webApi.paint.getAllPaints.model.ResponseGetAllPaints;
import psb.com.kidpaint.webApi.paint.getMyPaints.model.ResponseGetMyPaints;
import psb.com.kidpaint.webApi.paint.iPaint;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by morteza on 1/29/2018 AD.
 */

public interface iGetAllPaints {

    void doGetAllPaints(String text,int page,int size,int matchId, int level);

    interface iResult {
        void onSuccessGetAllPaints(ResponseGetAllPaints responseGetAllPaints);

        void onFailedGetAllPaints(int errorId, String ErrorMessage);
    }

    interface apiRequest {
        @GET(iPaint.apiAddress+"GetAll")
        Call<ResponseGetAllPaints> getAllPaints(@Query("text") String mobile,@Query("page") int page,@Query("size") int size,@Query("matchId") int matchId,@Query("level") int level);
    }
}
