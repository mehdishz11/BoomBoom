package psb.com.kidpaint.myMessages;

import android.content.Context;

import java.lang.reflect.GenericArrayType;

import psb.com.kidpaint.utils.UserProfile;

public class MMessages implements IMMessages {
    private Context context;
    private IPMessages ipMessages;
    private UserProfile userProfile;

    public MMessages(IPMessages ipMessages) {
        this.ipMessages = ipMessages;
        this.context=ipMessages.getContext();
        this.userProfile=new UserProfile(getContext());
    }

    @Override
    public Context getContext() {
        return context;
    }

    @Override
    public void getMessageFromServer() {

    }

    @Override
    public void getMessageFromDb() {

    }

    @Override
    public void sendMessage(String text) {

    }

    @Override
    public int getArrSize() {
        return 0;
    }

    @Override
    public int getRowType(int position) {
        return 0;
    }

    @Override
    public boolean userIsRegistered() {
        return userProfile.get_KEY_PHONE_NUMBER("").isEmpty()?false:true;
    }

    @Override
    public String getPositionAtMessage(int position) {
        return "";
    }
}
