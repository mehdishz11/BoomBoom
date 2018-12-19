package psb.com.kidpaint.utils.firebase;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;


import psb.com.kidpaint.App;
import psb.com.kidpaint.utils.UserProfile;


/**
 * Created by mehdi on 8/15/16.
 */
public class ServiceRefreshToken extends FirebaseInstanceIdService {
    @Override
    public void onTokenRefresh() {
       final String token= FirebaseInstanceId.getInstance().getToken();
        if(token!=null)sendTokenToServer(token);
        Log.d(App.TAG," token onTokenRefresh ");

    }
    private void sendTokenToServer(final String token){

        UserProfile userProfile=new UserProfile(App.getContext());

        Log.d(App.TAG," token sendTokenToServer ");

      //  String oldToken=sharedPrefrece.getString(G.TAG_FCM_token, "");

        if(!"".equals(token) ) {
            userProfile.set_KEY_FCM(token);
            doUpdateToken();
        }
    }

    public void doUpdateToken() {
       /* interfaceUpdateFcmToken apiService = Tools.getRetrofit().create(interfaceUpdateFcmToken.class);
        SharedPreferences sharedPrefrece = getApplicationContext().getSharedPreferences(KEY_SHP_USER, MODE_PRIVATE);
        SharedPreferences sharedPreferences_fcm = getApplicationContext().getSharedPreferences(KEY_FCM, MODE_PRIVATE);

        ParamsUpdateFcmToken paramsUpdateFcmToken=new ParamsUpdateFcmToken();
        paramsUpdateFcmToken.setUserName(sharedPrefrece.getString(KEY_SHP_USER_PHONE_NUMBER, ""));
        paramsUpdateFcmToken.setAppVersion(BuildConfig.VERSION_CODE+"");
        paramsUpdateFcmToken.setDeviceModel(Build.MODEL);
        paramsUpdateFcmToken.setOsVersion(Build.VERSION.SDK_INT + "");

        paramsUpdateFcmToken.setDeviceId(Values.getSecurityCode(getApplicationContext()));

        paramsUpdateFcmToken.setJwt(sharedPrefrece.getString(KEY_SHP_USER_TOKEN,""));
        paramsUpdateFcmToken.setOsType(0);//means android
        paramsUpdateFcmToken.setAppType(2);//means user app

        paramsUpdateFcmToken.setFcmToken(sharedPreferences_fcm.getString(KEY_FCM_TOKEN,""));

        Call<ResponseUpdateFcmToken> call = apiService.getUpdateFcmToken(paramsUpdateFcmToken);
        call.enqueue(new Callback<ResponseUpdateFcmToken>() {
            @Override
            public void onResponse(Call<ResponseUpdateFcmToken> call, Response<ResponseUpdateFcmToken> response) {

            }

            @Override
            public void onFailure(Call<ResponseUpdateFcmToken> call, Throwable t) {
            }
        });*/
    }

    private interface interfaceUpdateFcmToken {
       /* @Headers("Content-Type: application/json")
        @POST("Users/FcmToken")
        Call<ResponseUpdateFcmToken> getUpdateFcmToken(@Body ParamsUpdateFcmToken paramsUpdateFcmToken);*/
    }
}
