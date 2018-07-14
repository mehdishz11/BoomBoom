package psb.com.kidpaint.webApi.register.logout;



import psb.com.kidpaint.utils.ErrorMessage;
import psb.com.kidpaint.utils.WebService;
import psb.com.kidpaint.webApi.register.logout.modelLogout.ResponseLogout;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by morteza on 6/11/2018 AD.
 */

public class Logout implements iLogout {

    private iResult iResult;

    public Logout() {
    }

    public Logout(iLogout.iResult iResult) {
        this.iResult = iResult;
    }


    @Override
    public void doLogout(String phoneNumber, String deviceId) {

        Call<ResponseLogout> call = new WebService().getClient().create(apiRequest.class).logout(phoneNumber, deviceId);
        call.enqueue(new Callback<ResponseLogout>() {
            @Override
            public void onResponse(Call<ResponseLogout> call, Response<ResponseLogout> response) {
                if (response.code() == 200) {
                    if (response.body().getOk()) {
                        if (iResult != null) {
                            iResult.onSuccessLogout();
                        }
                    } else {
                        if(response.body().getMessages().size()>0) {
                            iResult.onFailedLogout(0, response.body().getMessages().get(0).getDescription());
                        }else{
                            iResult.onFailedLogout(0, ErrorMessage.ERROR_NETWORK_SERVER_SIDE.getErrorMessage());
                        }
                    }
                } else {
                    if (iResult != null) {
                        iResult.onFailedLogout(response.code(),ErrorMessage.getErrorByCode(response.code()));

                    }

                }
            }

            @Override
            public void onFailure(Call<ResponseLogout> call, Throwable t) {
                if (iResult != null && !call.isCanceled()) {
                    iResult.onFailedLogout(0, "");
                }
            }
        });
    }
}
