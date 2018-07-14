
package psb.com.kidpaint.webApi.register.VerifyCode.ModelVerifyCode;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

import psb.com.kidpaint.webApi.shareModel.Message;


public class ResponseVerifyCode implements Serializable
{

    @SerializedName("ok")
    @Expose
    private Boolean ok;
    @SerializedName("messages")
    @Expose
    private List<Message> messages = null;
    @SerializedName("extra")
    @Expose
    private String extra;
    private final static long serialVersionUID = 5118963845542596002L;

    public Boolean getOk() {
        return ok;
    }

    public void setOk(Boolean ok) {
        this.ok = ok;
    }

    public ResponseVerifyCode withOk(Boolean ok) {
        this.ok = ok;
        return this;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public ResponseVerifyCode withMessages(List<Message> messages) {
        this.messages = messages;
        return this;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public ResponseVerifyCode withExtra(String extra) {
        this.extra = extra;
        return this;
    }

}
