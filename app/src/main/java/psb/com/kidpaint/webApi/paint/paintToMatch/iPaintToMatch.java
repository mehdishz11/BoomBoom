package psb.com.kidpaint.webApi.paint.paintToMatch;

import psb.com.kidpaint.webApi.paint.getMyPaints.model.ResponseGetMyPaints;
import psb.com.kidpaint.webApi.paint.iPaint;
import psb.com.kidpaint.webApi.paint.paintToMatch.model.ResponsePaintToMatch;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by morteza on 1/29/2018 AD.
 */

public interface iPaintToMatch {

    void doPaintToMatch(String mobile, int paintId);

    interface iResult {
        void onSuccessPaintToMatch(ResponsePaintToMatch responsePaintToMatch);

        void onFailedPaintToMatch(int errorId, String ErrorMessage);
    }

    interface apiRequest {
        @POST(iPaint.apiAddress+"PaintToMatch")
        Call<ResponsePaintToMatch> paintToMatch(@Query("mobile") String mobile, @Query("paintId") int paintId);
    }
}
