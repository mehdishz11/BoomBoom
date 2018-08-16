package psb.com.kidpaint.webApi.paint.getLeaderShip;

import psb.com.kidpaint.utils.ErrorMessage;
import psb.com.kidpaint.utils.WebService;
import psb.com.kidpaint.webApi.paint.getAllPaints.iGetAllPaints;
import psb.com.kidpaint.webApi.paint.getAllPaints.model.ResponseGetAllPaints;
import psb.com.kidpaint.webApi.paint.getLeaderShip.model.ResponseGetLeaderShip;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by morteza on 1/29/2018 AD.
 */

public class GetLeaderShip implements iGetLeaderShip {

    private iResult iResult;

    public GetLeaderShip() {
    }

    public GetLeaderShip(iGetLeaderShip.iResult iResult) {
        this.iResult = iResult;
    }

    @Override
    public void doGetLeaderShip(String mobile,int page,int size,int matchId, int level) {
        Call<ResponseGetLeaderShip> call = new WebService().getClient().create(apiRequest.class).getLeaderShip(mobile, page, size,matchId,level);
        call.enqueue(new Callback<ResponseGetLeaderShip>() {
            @Override
            public void onResponse(Call<ResponseGetLeaderShip> call, Response<ResponseGetLeaderShip> response) {
                if (response.code() == 200) {

                    if (response.body().getOk()) {
                        if (iResult != null) {
                            iResult.onSuccessGetLeaderShip(response.body());
                        }
                    } else {
                        if(response.body().getMessages().size()>0) {
                            iResult.onFailedGetLeaderShip(0, response.body().getMessages().get(0).getDescription());
                        }else{
                            iResult.onFailedGetLeaderShip(0, ErrorMessage.ERROR_NETWORK_SERVER_SIDE.getErrorMessage());
                        }
                    }
                } else {
                    if (iResult != null) {
                        iResult.onFailedGetLeaderShip(response.code(),ErrorMessage.getErrorByCode(response.code()));

                    }

                }
            }

            @Override
            public void onFailure(Call<ResponseGetLeaderShip> call, Throwable t) {
                t.printStackTrace();
                if (iResult != null && !call.isCanceled()) {
                    iResult.onFailedGetLeaderShip(0, ErrorMessage.ERROR_NETWORK_UNAVALABLE.getErrorMessage());
                }
            }
        });
    }
}
