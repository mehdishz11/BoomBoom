
package psb.com.kidpaint.webApi.register.RegisterRequest.ModelRegisterRequest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

import psb.com.kidpaint.webApi.shareModel.Message;


public class ResponseRegisterRequest implements Serializable
{

    @SerializedName("ok")
    @Expose
    private Boolean ok;
    @SerializedName("messages")
    @Expose
    private List<Message> messages = null;
    @SerializedName("extra")
    @Expose
    private Extra extra;
    private final static long serialVersionUID = 7806543291261850940L;

    public Boolean getOk() {
        return ok;
    }

    public void setOk(Boolean ok) {
        this.ok = ok;
    }

    public ResponseRegisterRequest withOk(Boolean ok) {
        this.ok = ok;
        return this;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public ResponseRegisterRequest withMessages(List<Message> messages) {
        this.messages = messages;
        return this;
    }

    public Extra getExtra() {
        return extra;
    }

    public void setExtra(Extra extra) {
        this.extra = extra;
    }

    public ResponseRegisterRequest withExtra(Extra extra) {
        this.extra = extra;
        return this;
    }

}
