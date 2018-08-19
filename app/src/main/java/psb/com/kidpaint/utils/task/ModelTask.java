package psb.com.kidpaint.utils.task;

import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import psb.com.kidpaint.utils.Value;

public class ModelTask implements Serializable,Comparable<ModelTask> {

    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("step")
    @Expose
    private Integer step;

    @SerializedName("isShowed")
    @Expose
    private Boolean isShowed;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStep() {
        return step;
    }

    public void setStep(Integer step) {
        this.step = step;
    }

    public Boolean getShowed() {
        return isShowed;
    }

    public void setShowed(Boolean showed) {
        isShowed = showed;
    }

    @Override
    public int compareTo(@NonNull ModelTask modelTask) {
        if (getStep() == null || modelTask.getStep() == null) return 0;
        return getStep().compareTo(modelTask.getStep());
    }
}
