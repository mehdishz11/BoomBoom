package psb.com.kidpaint.competition.myPaints;

import android.content.Context;

import psb.com.kidpaint.utils.UserProfile;
import psb.com.kidpaint.webApi.paint.Paint;
import psb.com.kidpaint.webApi.paint.deletePaint.iDeletePaint;
import psb.com.kidpaint.webApi.paint.deletePaint.model.ResponseDeletePaint;
import psb.com.kidpaint.webApi.paint.getMyPaints.iGetMyPaints;
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
    public void deleteMyPaints(final int position) {

        new Paint().deletePaint(new iDeletePaint.iResult() {
            @Override
            public void onSuccessDelete(ResponseDeletePaint responseDeletePaint) {
                mResponseGetMyPaints.getMyPaint().remove(position);
                ipMyPaints.onSuccessDeleteMyPaints(position);
            }

            @Override
            public void onFailedDelete(int errorId, String ErrorMessage) {

                ipMyPaints.onFailedDeleteMyPaints(errorId, ErrorMessage);

            }
        }).doDelete(userProfile.get_KEY_PHONE_NUMBER(""),mResponseGetMyPaints.getMyPaint().get(position).getId());

    }


    @Override
    public int getArrSizeMyPaints() {
        return mResponseGetMyPaints!=null?mResponseGetMyPaints.getMyPaint().size():0;
    }

  @Override
    public PaintModel getMyPaintsPositionAt(int position) {
        return mResponseGetMyPaints.getMyPaint().get(position);
    }

    @Override
    public PaintModel getFirstPaintModel() {
        return mResponseGetMyPaints==null||mResponseGetMyPaints.getMyPaint().size()<=0?null:mResponseGetMyPaints.getMyPaint().get(0);
    }

    ////
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

    @Override
    public void onGetMyPaints() {
        new Paint().getMyPaints(new iGetMyPaints.iResult() {
            @Override
            public void onSuccessGetMyPaints(ResponseGetMyPaints responseGetMyPaints) {
                mResponseGetMyPaints=responseGetMyPaints;
                ipMyPaints.onSuccessGetMyPaints(responseGetMyPaints);
            }

            @Override
            public void onFailedGetMyPaints(int errorId, String ErrorMessage) {
                ipMyPaints.onFailedGetMyPaints(errorId, ErrorMessage);
            }
        }).doGetMyPaints(userProfile.get_KEY_PHONE_NUMBER("0"));
    }


}
