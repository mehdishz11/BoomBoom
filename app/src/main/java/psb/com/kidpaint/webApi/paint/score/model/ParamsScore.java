
package psb.com.kidpaint.webApi.paint.score.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

import psb.com.kidpaint.webApi.shareModel.Message;

public class ParamsScore implements Serializable
{

    @SerializedName("mobile")
    @Expose
    private String mobile;
    @SerializedName("paintId")
    @Expose
    private Integer paintId ;
    @SerializedName("score")
    @Expose
    private Integer score;
    private final static long serialVersionUID = 946241060542535005L;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Integer getPaintId() {
        return paintId;
    }

    public void setPaintId(Integer paintId) {
        this.paintId = paintId;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }
}
