package psb.com.kidpaint.webApi.paint.getAllPaints;

import android.util.Log;

import psb.com.kidpaint.utils.ErrorMessage;
import psb.com.kidpaint.utils.WebService;
import psb.com.kidpaint.webApi.paint.getAllPaints.model.ResponseGetAllPaints;
import psb.com.kidpaint.webApi.paint.getMyPaints.iGetMyPaints;
import psb.com.kidpaint.webApi.paint.getMyPaints.model.ResponseGetMyPaints;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by morteza on 1/29/2018 AD.
 */

public class GetAllPaints implements iGetAllPaints {

    private iResult iResult;

    public GetAllPaints() {
    }

    public GetAllPaints(iGetAllPaints.iResult iResult) {
        this.iResult = iResult;
    }

    @Override
    public void doGetAllPaints(String text,int page,int size,int matchId, int level) {
        Call<ResponseGetAllPaints> call = new WebService().getClient().create(apiRequest.class).getAllPaints(text, page, size,matchId,level);
        call.enqueue(new Callback<ResponseGetAllPaints>() {
            @Override
            public void onResponse(Call<ResponseGetAllPaints> call, Response<ResponseGetAllPaints> response) {
                if (response.code() == 200) {

                    if (response.body().getOk()) {
                        if (iResult != null) {
                            iResult.onSuccessGetAllPaints(response.body());
                        }
                    } else {
                        if(response.body().getMessages().size()>0) {
                            iResult.onFailedGetAllPaints(0, response.body().getMessages().get(0).getDescription());
                        }else{
                            iResult.onFailedGetAllPaints(0, ErrorMessage.ERROR_NETWORK_SERVER_SIDE.getErrorMessage());
                        }
                    }
                } else {
                    if (iResult != null) {
                        iResult.onFailedGetAllPaints(response.code(),ErrorMessage.getErrorByCode(response.code()));

                    }

                }
            }

            @Override
            public void onFailure(Call<ResponseGetAllPaints> call, Throwable t) {
                Log.d("TAG", "onFailure: ");
                t.printStackTrace();
                if (iResult != null && !call.isCanceled()) {
                    iResult.onFailedGetAllPaints(0, ErrorMessage.ERROR_NETWORK_UNAVALABLE.getErrorMessage());
                }
            }
        });
    }
}
