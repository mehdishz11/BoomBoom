package psb.com.kidpaint.webApi.paint.deletePaint;

import psb.com.kidpaint.webApi.paint.deletePaint.model.ResponseDeletePaint;
import psb.com.kidpaint.webApi.paint.iPaint;
import psb.com.kidpaint.webApi.paint.score.model.ParamsScore;
import psb.com.kidpaint.webApi.paint.score.model.ResponseScore;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by morteza on 1/29/2018 AD.
 */

public interface iDeletePaint {

    void doDelete(String mobile,int paintId);

    interface iResult {
        void onSuccessDelete(ResponseDeletePaint responseDeletePaint);

        void onFailedDelete(int errorId, String ErrorMessage);
    }

    interface apiRequest {
        @POST(iPaint.apiAddress+"Delete")
        Call<ResponseDeletePaint> delete(@Query("mobile") String mobile, @Query("paintId") int paintId);
    }
}
