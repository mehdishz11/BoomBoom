package psb.com.kidpaint.competition.myPaints;

import android.content.Context;

import psb.com.kidpaint.utils.UserProfile;
import psb.com.kidpaint.webApi.paint.getMyPaints.model.MyPaint;
import psb.com.kidpaint.webApi.paint.getMyPaints.model.ResponseGetMyPaints;
import psb.com.kidpaint.webApi.shareModel.PaintModel;
import psb.com.kidpaint.webApi.shareModel.User;

public class MMyPaints implements IMMyPaints {

    private Context context;
    private IPMyPaints ipMyPaints;
    private ResponseGetMyPaints mResponseGetMyPaints;
    private UserProfile userProfile;

    public MMyPaints(IPMyPaints ipMyPaints) {
        this.ipMyPaints = ipMyPaints;
        this.context= ipMyPaints.getContext();
        this.userProfile=new UserProfile(getContext());

    }

    @Override
    public Context getContext() {
        return context;
    }

    @Override
    public void setResponseGetMyPaints(ResponseGetMyPaints responseGetMyPaints) {
         mResponseGetMyPaints=responseGetMyPaints;
    }


    @Override
    public void deleteMyPaints(int position) {

    }


    @Override
    public int getArrSizeMyPaints() {
        return mResponseGetMyPaints.getMyPaint().size();
    }

  @Override
    public PaintModel getMyPaintsPositionAt(int position) {
        return mResponseGetMyPaints.getMyPaint().get(position);
    }

    @Override
    public User getUser() {
        User user=new User();

        user.setFirstName(userProfile.get_KEY_FIRST_NAME("i"));
        user.setLastName(userProfile.get_KEY_LAST_NAME("i"));
        user.setImageUrl(userProfile.get_KEY_IMG_URL("i"));
        user.setBirthday(userProfile.get_KEY_BRITH_DAY("2000-12-12"));
        user.setPhoneNumber(userProfile.get_KEY_PHONE_NUMBER("100"));
        return user;
    }


}
