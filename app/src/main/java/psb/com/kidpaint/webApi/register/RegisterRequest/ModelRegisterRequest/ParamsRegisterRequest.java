
package psb.com.kidpaint.webApi.register.RegisterRequest.ModelRegisterRequest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ParamsRegisterRequest implements Serializable
{

    @SerializedName("mobileNumber")
    @Expose
    private String mobileNumber;
    private final static long serialVersionUID = -5792118207520535791L;

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public ParamsRegisterRequest withMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
        return this;
    }

}
