package psb.com.kidpaint.webApi.paint.savePaints;

import android.graphics.Bitmap;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import psb.com.kidpaint.webApi.paint.iPaint;
import psb.com.kidpaint.webApi.paint.savePaints.model.ParamsSavePaint;
import psb.com.kidpaint.webApi.paint.savePaints.model.ResponseSavePaint;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;



public interface iSavePaints {

    void doSavePaint(ParamsSavePaint paramsSavePaint, Bitmap childBitmap);

    interface iResult {
        void onSuccessSavePaint(ResponseSavePaint responseSavePaint);

        void onFailedSavePaint(int errorId, String ErrorMessage);
    }

    interface apiRequest {
        @Multipart
        @POST(iPaint.apiAddress+"SavePaints")
        Call<ResponseSavePaint> postPaint(
                @PartMap() Map<String, RequestBody> model,
                @Part List<MultipartBody.Part> imageUrl
        );
    }
}
