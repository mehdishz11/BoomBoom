package psb.com.kidpaint.home;

import android.content.Context;

import psb.com.kidpaint.utils.UserProfile;
import psb.com.kidpaint.utils.Utils;
import psb.com.kidpaint.utils.database.Database;
import psb.com.kidpaint.webApi.prize.PrizeRequest.PrizeRequest;
import psb.com.kidpaint.webApi.prize.PrizeRequest.iPrizeRequest;
import psb.com.kidpaint.webApi.prize.PrizeRequest.model.ParamsPrizeRequest;
import psb.com.kidpaint.webApi.prize.PrizeRequest.model.ResponsePrizeRequest;
import psb.com.kidpaint.webApi.register.Register;
import psb.com.kidpaint.webApi.register.logout.iLogout;


public class MHome implements IM_Home {

    private Context context;
    private IP_Home ip_home;
    private UserProfile userProfile;

    public MHome(IP_Home ip_home) {
        this.ip_home = ip_home;
        this.context = ip_home.getContext();
        this.userProfile=new UserProfile(context);
    }

    @Override
    public Context getContext() {
        return context;
    }


    @Override
    public void onStartLogout() {
        new Register().logout(new iLogout.iResult() {
            @Override
            public void onSuccessLogout() {
                userProfile.REMOVE_KEY_USER_INFO();
                ip_home.onLogoutSuccess();
            }

            @Override
            public void onFailedLogout(int errorId, String errorMessage) {
                ip_home.onLogoutFailed(errorMessage);

            }
        }).doLogout(userProfile.get_KEY_PHONE_NUMBER(""), Utils.getDeviceId(getContext()));
    }

    @Override
    public void prizeRequest(ParamsPrizeRequest paramsPrizeRequest) {
        new PrizeRequest(new iPrizeRequest.iResult() {
            @Override
            public void onSuccessPrizeRequest(ResponsePrizeRequest responsePrizeRequest) {
                ip_home.prizeRequestSuccess(responsePrizeRequest.getExtra());
            }

            @Override
            public void onFailedPrizeRequest(int errorId, String ErrorMessage) {
                ip_home.prizeRequestFailed(ErrorMessage);
            }
        }).doPrizeRequest(paramsPrizeRequest);
    }

    @Override
    public int getUnreadMessageCount() {
        return new Database().tblMessage(getContext()).getUnreadChatMessageCount();
    }
}
