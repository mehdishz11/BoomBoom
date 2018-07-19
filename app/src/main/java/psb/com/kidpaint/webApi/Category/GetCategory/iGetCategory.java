package psb.com.kidpaint.webApi.Category.GetCategory;

import psb.com.kidpaint.webApi.Category.GetCategory.model.ResponseStickers;
import psb.com.kidpaint.webApi.Category.iCategory;
import psb.com.kidpaint.webApi.paint.getAllPaints.model.ResponseGetAllPaints;
import psb.com.kidpaint.webApi.paint.iPaint;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by morteza on 1/29/2018 AD.
 */

public interface iGetCategory {

    void doGetCategory();

    interface iResult {
        void onSuccessGetCategory(ResponseStickers responseStickers);
        void onFailedGetCategory(int errorId, String ErrorMessage);
    }

    interface apiRequest {
        @GET(iCategory.apiAddress+"GetCategory")
        Call<ResponseStickers> getCategory();
    }
}
