package psb.com.kidpaint.webApi.register.Login;




import psb.com.kidpaint.utils.ErrorMessage;
import psb.com.kidpaint.utils.WebService;
import psb.com.kidpaint.webApi.register.Login.LoginModel.ParamsLogin;
import psb.com.kidpaint.webApi.register.Login.LoginModel.ResponseLogin;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by morteza on 6/11/2018 AD.
 */

public class Login implements iLogin {

    private iResult iResult;

    public Login() {
    }

    public Login(iLogin.iResult iResult) {
        this.iResult = iResult;
    }

    @Override
    public void doLogin(String userName, String password) {
        ParamsLogin paramsLogin=new ParamsLogin();
        paramsLogin.setUserName(userName);
        paramsLogin.setPassword(password);

        Call<ResponseLogin> call = new WebService().getClient().create(iLogin.apiRequest.class).login(paramsLogin);
        call.enqueue(new Callback<ResponseLogin>() {
            @Override
            public void onResponse(Call<ResponseLogin> call, Response<ResponseLogin> response) {
                if (response.code() == 200) {

                    if (response.body().getOk()) {
                        if (iResult != null) {
                            iResult.onSuccessLogin(response.body().getExtra());
                        }
                    } else {
                        if(response.body().getMessages().size()>0) {
                            iResult.onFailedLogin(0, response.body().getMessages().get(0).getDescription());
                        }else{
                            iResult.onFailedLogin(0, ErrorMessage.ERROR_NETWORK_SERVER_SIDE.getErrorMessage());
                        }
                    }
                } else {
                    if (iResult != null) {
                        iResult.onFailedLogin(response.code(),ErrorMessage.getErrorByCode(response.code()));

                    }

                }
            }

            @Override
            public void onFailure(Call<ResponseLogin> call, Throwable t) {
                if (iResult != null && !call.isCanceled()) {
                    iResult.onFailedLogin(0, "");
                }
            }
        });
    }
}
