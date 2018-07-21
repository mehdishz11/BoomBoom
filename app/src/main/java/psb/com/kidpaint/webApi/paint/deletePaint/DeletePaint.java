package psb.com.kidpaint.webApi.paint.deletePaint;

import psb.com.kidpaint.utils.ErrorMessage;
import psb.com.kidpaint.utils.WebService;
import psb.com.kidpaint.webApi.paint.deletePaint.model.ResponseDeletePaint;
import psb.com.kidpaint.webApi.paint.score.iScore;
import psb.com.kidpaint.webApi.paint.score.model.ParamsScore;
import psb.com.kidpaint.webApi.paint.score.model.ResponseScore;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by morteza on 1/29/2018 AD.
 */

public class DeletePaint implements iDeletePaint {

    private iResult iResult;

    public DeletePaint() {
    }

    public DeletePaint(iDeletePaint.iResult iResult) {
        this.iResult = iResult;
    }



    @Override
    public void doDelete(String mobile, int paintId) {
        Call<ResponseDeletePaint> call = new WebService().getClient().create(apiRequest.class).delete(mobile, paintId);
        call.enqueue(new Callback<ResponseDeletePaint>() {
            @Override
            public void onResponse(Call<ResponseDeletePaint> call, Response<ResponseDeletePaint> response) {
                if (response.code() == 200) {

                    if (response.body().getOk()) {
                        if (iResult != null) {
                            iResult.onSuccessDelete(response.body());
                        }
                    } else {
                        if(response.body().getMessages().size()>0) {
                            iResult.onFailedDelete(0, response.body().getMessages().get(0).getDescription());
                        }else{
                            iResult.onFailedDelete(0, ErrorMessage.ERROR_NETWORK_SERVER_SIDE.getErrorMessage());
                        }
                    }
                } else {
                    if (iResult != null) {
                        iResult.onFailedDelete(response.code(),ErrorMessage.getErrorByCode(response.code()));

                    }

                }
            }

            @Override
            public void onFailure(Call<ResponseDeletePaint> call, Throwable t) {
                t.printStackTrace();
                if (iResult != null && !call.isCanceled()) {
                    iResult.onFailedDelete(0, ErrorMessage.ERROR_NETWORK_UNAVALABLE.getErrorMessage());
                }
            }
        });
    }
}
