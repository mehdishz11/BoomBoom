
package psb.com.kidpaint.webApi.paint.getAllPaints.model;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Extra implements Serializable
{

    @SerializedName("model")
    @Expose
    private List<AllPaintModel> allPaintModel = null;
    @SerializedName("total")
    @Expose
    private Integer total;
    private final static long serialVersionUID = -4953710761280784971L;

    public List<AllPaintModel> getAllPaintModel() {
        return allPaintModel;
    }

    public void setAllPaintModel(List<AllPaintModel> allPaintModel) {
        this.allPaintModel = allPaintModel;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

}
