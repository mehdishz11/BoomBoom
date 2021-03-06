
package psb.com.kidpaint.webApi.paint.savePaints.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ParamsSavePaint implements Serializable
{

    @SerializedName("mobile")
    @Expose
    private String mobile;
    @SerializedName("title")
    @Expose
    private String title;

    private final static long serialVersionUID = 393642831312036733L;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
