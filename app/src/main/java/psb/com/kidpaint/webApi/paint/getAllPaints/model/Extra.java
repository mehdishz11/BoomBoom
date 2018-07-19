
package psb.com.kidpaint.webApi.paint.getAllPaints.model;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import psb.com.kidpaint.webApi.shareModel.PaintModel;

public class Extra implements Serializable
{

    @SerializedName("model")
    @Expose
    private List<PaintModel> paintModel = null;
    @SerializedName("total")
    @Expose
    private Integer total;
    private final static long serialVersionUID = -4953710761280784971L;

    public List<PaintModel> getPaintModel() {
        return paintModel;
    }

    public void setPaintModel(List<PaintModel> paintModel) {
        this.paintModel = paintModel;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

}
