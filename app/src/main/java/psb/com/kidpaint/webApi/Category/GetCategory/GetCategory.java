package psb.com.kidpaint.webApi.Category.GetCategory;

import psb.com.kidpaint.utils.ErrorMessage;
import psb.com.kidpaint.utils.WebService;
import psb.com.kidpaint.webApi.Category.GetCategory.model.ResponseStickers;
import psb.com.kidpaint.webApi.paint.getAllPaints.model.ResponseGetAllPaints;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by morteza on 1/29/2018 AD.
 */

public class GetCategory implements iGetCategory {

    private iResult iResult;

    public GetCategory() {
    }

    public GetCategory(iGetCategory.iResult iResult) {
        this.iResult = iResult;
    }

    @Override
    public void doGetCategory(String fromDate) {
        Call<ResponseStickers> call = new WebService().getClient().create(apiRequest.class).getCategory(fromDate);
        call.enqueue(new Callback<ResponseStickers>() {
            @Override
            public void onResponse(Call<ResponseStickers> call, Response<ResponseStickers> response) {
                if (response.code() == 200) {

                    if (response.body().getOk()) {
                        if (iResult != null) {
                            iResult.onSuccessGetCategory(response.body());
                        }
                    } else {
                        if(response.body().getMessages().size()>0) {
                            iResult.onFailedGetCategory(0, response.body().getMessages().get(0).getDescription());
                        }else{
                            iResult.onFailedGetCategory(0, ErrorMessage.ERROR_NETWORK_SERVER_SIDE.getErrorMessage());
                        }
                    }
                } else {
                    if (iResult != null) {
                        iResult.onFailedGetCategory(response.code(),ErrorMessage.getErrorByCode(response.code()));

                    }

                }
            }

            @Override
            public void onFailure(Call<ResponseStickers> call, Throwable t) {
                if (iResult != null && !call.isCanceled()) {
                    iResult.onFailedGetCategory(0, ErrorMessage.ERROR_NETWORK_UNAVALABLE.getErrorMessage());
                }
            }
        });
    }
}
