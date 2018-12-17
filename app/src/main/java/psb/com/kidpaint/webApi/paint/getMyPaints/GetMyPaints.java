package psb.com.kidpaint.webApi.paint.getMyPaints;

import android.util.Log;

import psb.com.kidpaint.utils.ErrorMessage;
import psb.com.kidpaint.utils.WebService;
import psb.com.kidpaint.webApi.paint.getMyPaints.model.ResponseGetMyPaints;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by morteza on 1/29/2018 AD.
 */

public class GetMyPaints implements iGetMyPaints {

    private iResult iResult;

    public GetMyPaints() {
    }

    public GetMyPaints(iGetMyPaints.iResult iResult) {
        this.iResult = iResult;
    }

    @Override
    public void doGetMyPaints(String mobile, boolean isMatch) {
        Call<ResponseGetMyPaints> call = new WebService().getClient().create(iGetMyPaints.apiRequest.class).getMyPaints(mobile,isMatch);
        call.enqueue(new Callback<ResponseGetMyPaints>() {
            @Override
            public void onResponse(Call<ResponseGetMyPaints> call, Response<ResponseGetMyPaints> response) {
                if (response.code() == 200) {

                    if (response.body().getOk()) {
                        if (iResult != null) {
                            iResult.onSuccessGetMyPaints(response.body());
                        }
                    } else {
                        if(response.body().getMessages().size()>0) {
                            iResult.onFailedGetMyPaints(0, response.body().getMessages().get(0).getDescription());
                        }else{
                            iResult.onFailedGetMyPaints(0, ErrorMessage.ERROR_NETWORK_SERVER_SIDE.getErrorMessage());
                        }
                    }
                } else {
                    if (iResult != null) {
                        iResult.onFailedGetMyPaints(response.code(),ErrorMessage.getErrorByCode(response.code()));

                    }

                }
            }

            @Override
            public void onFailure(Call<ResponseGetMyPaints> call, Throwable t) {
                Log.d("TAg", "onFailure onFailedGetMyPaints: ");
                t.printStackTrace();
                if (iResult != null && !call.isCanceled()) {
                    iResult.onFailedGetMyPaints(0, ErrorMessage.ERROR_NETWORK_UNAVALABLE.getErrorMessage());
                }
            }
        });
    }
}
