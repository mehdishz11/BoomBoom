package psb.com.kidpaint.webApi.paint;

import android.graphics.Bitmap;

import com.google.gson.Gson;

import java.util.HashMap;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import psb.com.kidpaint.App;
import psb.com.kidpaint.utils.ErrorMessage;
import psb.com.kidpaint.utils.Value;
import psb.com.kidpaint.utils.WebService;
import psb.com.kidpaint.webApi.paint.postPaint.ParamsPostPaint;

import psb.com.kidpaint.webApi.paint.postPaint.ResponsePostPaint;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by morteza on 1/29/2018 AD.
 */

public class PostPaint implements iPostPaint {

    private iResult iResult;

    public PostPaint() {
    }

    public PostPaint(iPostPaint.iResult iResult) {
        this.iResult = iResult;
    }

    @Override
    public void doPostPaint(ParamsPostPaint paramsAddChild, Bitmap childBitmap) {
        RequestBody requestBody=RequestBody.create(MultipartBody.FORM, new Gson().toJson(paramsAddChild));
        final MultipartBody.Part userImage = Value.prepareFilePart(App.getContext(),"image",childBitmap);
        HashMap<String, RequestBody> model = new HashMap<>();
        model.put("model", requestBody);


        Call<ResponsePostPaint> call = new WebService().getClient().create(iPostPaint.apiRequest.class).postPaint(model,userImage);

        call.enqueue(new Callback<ResponsePostPaint>() {
            @Override
            public void onResponse(Call<ResponsePostPaint> call, Response<ResponsePostPaint> response) {
                if (response.code() == 200) {
                    if (response.body().getOk()) {
                        if (iResult != null) {
                            iResult.onSuccessPostPaint(response.body());
                        }
                    } else {
                        if(response.body().getMessages().size()>0) {
                            iResult.onFailedPostPaint(0, response.body().getMessages().get(0).getDescription());
                        }else{
                            iResult.onFailedPostPaint(0, ErrorMessage.ERROR_NETWORK_SERVER_SIDE.getErrorMessage());
                        }
                    }
                } else {
                    if (iResult != null) {
                        iResult.onFailedPostPaint(response.code(),ErrorMessage.getErrorByCode(response.code()));

                    }

                }
            }

            @Override
            public void onFailure(Call<ResponsePostPaint> call, Throwable t) {
                t.printStackTrace();
                if (iResult != null) {
                    iResult.onFailedPostPaint(0, ErrorMessage.ERROR_NETWORK_UNAVALABLE.getErrorMessage());
                }
            }
        });
    }
}
