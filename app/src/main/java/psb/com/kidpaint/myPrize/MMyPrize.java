package psb.com.kidpaint.myPrize;

import android.content.Context;

import psb.com.kidpaint.utils.UserProfile;
import psb.com.kidpaint.webApi.prize.Prize;
import psb.com.kidpaint.webApi.prize.myPrizeRequest.iMyPrizeRequest;
import psb.com.kidpaint.webApi.prize.myPrizeRequest.model.MyPrizeRequest;
import psb.com.kidpaint.webApi.prize.myPrizeRequest.model.ResponseMyPrizeRequest;

public class MMyPrize implements IMMyPrize {

    private Context context;
    private IPMyPrize ipMyPrize;
    private UserProfile userProfile;
    private ResponseMyPrizeRequest mResponseMyPrizeRequest;

    public MMyPrize(IPMyPrize ipMyPrize) {
        this.ipMyPrize = ipMyPrize;
        this.context=ipMyPrize.getContext();
        this.userProfile=new UserProfile(getContext());

    }

    @Override
    public Context getContext() {
        return context;
    }

    @Override
    public void getMyPrize() {
    new Prize().myPrizeRequest(new iMyPrizeRequest.iResult() {
        @Override
        public void onSuccessGetMyPrizeRequest(ResponseMyPrizeRequest responseMyPrizeRequest) {
            mResponseMyPrizeRequest=responseMyPrizeRequest;
            ipMyPrize.onSuccessGetMyPrize();
        }

        @Override
        public void onFailedGetMyPrizeRequest(int errorId, String ErrorMessage) {
            ipMyPrize.onFailedGetMyPrize(errorId, ErrorMessage);

        }
    }).doGetMyPrizeRequest(userProfile.get_KEY_PHONE_NUMBER("0"));
    }

    @Override
    public int getArrSize() {
        return mResponseMyPrizeRequest==null?0:mResponseMyPrizeRequest.getMyPrizeRequest().size();
    }

    @Override
    public MyPrizeRequest getPositionAt(int position) {
        return mResponseMyPrizeRequest.getMyPrizeRequest().get(position);
    }
}
