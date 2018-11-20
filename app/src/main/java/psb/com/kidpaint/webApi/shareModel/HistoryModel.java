
package psb.com.kidpaint.webApi.shareModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.File;
import java.io.Serializable;

public class HistoryModel implements Serializable
{

    @SerializedName("paintModel")
    @Expose
    private PaintModel paintModel;
    @SerializedName("file")
    @Expose
    private File file;

    public PaintModel getPaintModel() {
        return paintModel;
    }

    public void setPaintModel(PaintModel paintModel) {
        this.paintModel = paintModel;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }
}
