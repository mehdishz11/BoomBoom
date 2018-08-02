
package psb.com.kidpaint.webApi.chat.sendMessage.model;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Extra implements Serializable
{

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("chatId")
    @Expose
    private Integer chatId;
    @SerializedName("createDate")
    @Expose
    private String createDate;
    private final static long serialVersionUID = -7443433179678107030L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getChatId() {
        return chatId;
    }

    public void setChatId(Integer chatId) {
        this.chatId = chatId;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

}
