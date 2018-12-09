package psb.com.kidpaint.myMessages;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import psb.com.kidpaint.utils.UserProfile;
import psb.com.kidpaint.utils.Utils;
import psb.com.kidpaint.utils.database.Database;
import psb.com.kidpaint.utils.database.TblMessage.TblMessage;
import psb.com.kidpaint.webApi.chat.Chat;
import psb.com.kidpaint.webApi.chat.Get.iGetChat;
import psb.com.kidpaint.webApi.chat.Get.model.Extra;
import psb.com.kidpaint.webApi.chat.Get.model.ResponseMyMessages;
import psb.com.kidpaint.webApi.chat.sendMessage.iSendMessage;
import psb.com.kidpaint.webApi.chat.sendMessage.model.ParamsSendMessage;
import psb.com.kidpaint.webApi.chat.sendMessage.model.ResponseSendMessage;

public class MMessages implements IMMessages {
    private Context context;
    private IPMessages ipMessages;
    private UserProfile userProfile;
    private List<Extra> messageList=new ArrayList<>();

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
        final String time=new Database().tblMessage(getContext()).getMessageLastUpdateTime();
        new Chat().getChat(new iGetChat.iResult() {
            @Override
            public void onSuccessGetChat(ResponseMyMessages responseMyMessages) {
             new Database().tblMessage(getContext()).insertMessageList(responseMyMessages.getExtra(), ("0".equals(time) ? true : false), new TblMessage.interFaceDB_InsertChats() {
                 @Override
                 public void onSuccessInsertChats(int size) {
                     ipMessages.onSuccessGetMessageFromServer();
                 }
             });
            }

            @Override
            public void onFailedGetChat(int errorId, String ErrorMessage) {
                ipMessages.onFailedGetMessageFromServer(errorId, ErrorMessage);

            }
        }).doGetChat(Utils.getDeviceId(getContext()),userProfile.get_KEY_PHONE_NUMBER(""),time );

    }

    @Override
    public void getMessageFromDb(int loadMode) {
        if (loadMode!=2 || messageList.size()<=0) {
            messageList=new Database().tblMessage(getContext()).getMessageList();
        }else {
          messageList.addAll(new Database().tblMessage(getContext()).getMessageListAfterThisId(messageList.get((messageList.size()-1)).getDbId()));
        }

      ipMessages.onSuccessGetMessageFromDb();

    }

    @Override
    public void sendMessage(String text) {
        final String time=Utils.getCurrentTime();
        final long dbId = new Database().tblMessage(getContext()).insertMyMessage(0,text, getMobileNumber(), "pending", time,"",true);
        messageList.add(new Database().tblMessage(getContext()).getInsertedMessage((int) dbId));

        ipMessages.onSuccessSendMessage((messageList.size()-1),false);
        final ParamsSendMessage paramsSendChatMessage = new ParamsSendMessage();

        paramsSendChatMessage.setSecurityCode(Utils.getDeviceId(getContext()));
        paramsSendChatMessage.setChatId((long) (messageList.size()>0?messageList.get(0).getChatId():0));
        paramsSendChatMessage.setDescription(text);
        paramsSendChatMessage.setMobileNumber(getMobileNumber());
        paramsSendChatMessage.setDbId(dbId);
        paramsSendChatMessage.setListPosition((messageList.size()-1));

        new Chat().sendMessage(new iSendMessage.iResult() {
            @Override
            public void onSuccessSendMessage(ResponseSendMessage responseSendMessage) {
                new Database().tblMessage(getContext()).updateMessage(
                        paramsSendChatMessage.getDbId(),
                        responseSendMessage.getExtra().getId(),
                        responseSendMessage.getExtra().getCreateDate(),
                        "success"
                );

                ipMessages.onSuccessSendMessage(findPositionById(paramsSendChatMessage.getDbId(),"success"),true);
            }

            @Override
            public void onFailedSendMessage(int errorId, String ErrorMessage) {
                new Database().tblMessage(getContext()).updateMessage(
                        paramsSendChatMessage.getDbId(),
                        -1,
                        time,
                        "failed"
                );
            ipMessages.onFailedSendMessage(errorId, ErrorMessage,findPositionById(paramsSendChatMessage.getDbId(),"failed"));
            }
        }).doSendMessage(paramsSendChatMessage);

    }

    @Override
    public int getArrSize() {
        return messageList.size();
    }

    @Override
    public int getRowType(int position) {

        return ("".equals(messageList.get(position).getUsername()) || getMobileNumber().equals(messageList.get(position).getUsername()))?0:1;
    }

    @Override
    public boolean userIsRegistered() {
        return userProfile.get_KEY_PHONE_NUMBER("").isEmpty()?false:true;
    }

    @Override
    public Extra getPositionAtMessage(int position) {

        return messageList.get(position);
    }

    @Override
    public String getUserName() {
        return userProfile.get_KEY_PHONE_NUMBER("")+" "+userProfile.get_KEY_LAST_NAME("");
    }

    public String getMobileNumber() {
        return userProfile.get_KEY_PHONE_NUMBER("");
    }

    @Override
    public String getUserImage() {
        return userProfile.get_KEY_IMG_URL("qwe");
    }

    @Override
    public void deleteMessage(long messageId,int position) {
        messageList.remove(position);
        new Database().tblMessage(getContext()).removeChatMessage(messageId);
        ipMessages.onSuccessDeleteMessage(position);

    }

    @Override
    public int getFirstUnreadMessagePosition() {
        return new Database().tblMessage(getContext()).getFirstUnreadChatMessagePosition();
    }

    @Override
    public void setAllMessageToRead() {
       new Database().tblMessage(getContext()).setAllWatchedChatMessage();
    }

    public int findPositionById(long dbId,String status){
        Log.d("TAG", "findPositionById: "+status);
        int position=-1;
        for (int i = messageList.size()-1; i > 0; i--) {
            if (messageList.get(i).getDbId()==dbId) {
                messageList.get(i).setStatus(status);
                position=i;
            }
        }
        return position;

    }

}
