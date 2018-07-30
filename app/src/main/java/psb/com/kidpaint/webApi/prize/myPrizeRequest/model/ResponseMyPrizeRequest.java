
package psb.com.kidpaint.webApi.prize.myPrizeRequest.model;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import psb.com.kidpaint.webApi.shareModel.Message;

public class ResponseMyPrizeRequest implements Serializable
{

    @SerializedName("ok")
    @Expose
    private Boolean ok;
    @SerializedName("messages")
    @Expose
    private List<Message> messages = null;
    @SerializedName("extra")
    @Expose
    private List<MyPrizeRequest> myPrizeRequest = null;
    private final static long serialVersionUID = 2288001873729899837L;

    public Boolean getOk() {
        return ok;
    }

    public void setOk(Boolean ok) {
        this.ok = ok;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public List<MyPrizeRequest> getMyPrizeRequest() {
        return myPrizeRequest;
    }

    public void setMyPrizeRequest(List<MyPrizeRequest> myPrizeRequest) {
        this.myPrizeRequest = myPrizeRequest;
    }

}
