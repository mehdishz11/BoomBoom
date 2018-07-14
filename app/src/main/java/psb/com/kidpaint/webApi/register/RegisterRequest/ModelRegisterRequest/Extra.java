
package psb.com.kidpaint.webApi.register.RegisterRequest.ModelRegisterRequest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Extra implements Serializable
{

    @SerializedName("mobileNumber")
    @Expose
    private String mobileNumber;
    @SerializedName("verifyCode")
    @Expose
    private String verifyCode;
    @SerializedName("used")
    @Expose
    private Boolean used;
    @SerializedName("count")
    @Expose
    private Long count;
    @SerializedName("id")
    @Expose
    private Long id;
    @SerializedName("createDate")
    @Expose
    private String createDate;
    @SerializedName("lastEditDate")
    @Expose
    private String lastEditDate;
    @SerializedName("deleted")
    @Expose
    private Boolean deleted;
    private final static long serialVersionUID = 3846522934301945384L;

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public Extra withMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
        return this;
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }

    public Extra withVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
        return this;
    }

    public Boolean getUsed() {
        return used;
    }

    public void setUsed(Boolean used) {
        this.used = used;
    }

    public Extra withUsed(Boolean used) {
        this.used = used;
        return this;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public Extra withCount(Long count) {
        this.count = count;
        return this;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Extra withId(Long id) {
        this.id = id;
        return this;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public Extra withCreateDate(String createDate) {
        this.createDate = createDate;
        return this;
    }

    public String getLastEditDate() {
        return lastEditDate;
    }

    public void setLastEditDate(String lastEditDate) {
        this.lastEditDate = lastEditDate;
    }

    public Extra withLastEditDate(String lastEditDate) {
        this.lastEditDate = lastEditDate;
        return this;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Extra withDeleted(Boolean deleted) {
        this.deleted = deleted;
        return this;
    }

}
