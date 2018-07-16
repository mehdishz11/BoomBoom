package psb.com.paintingview;

import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.Path;

public class DrawHelper {

    private Path path;
    private Bitmap bitmap;
    private Paint paint;
    private BucketModel bucket;
    private boolean isUndoAble=true;

    public Path getPath() {
        return path;
    }

    public void setPath(Path path) {
        this.path = path;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public Paint getPaint() {
        return paint;
    }

    public void setPaint(Paint paint) {
        this.paint = paint;
    }

    public boolean isUndoAble() {
        return isUndoAble;
    }

    public void setUndoAble(boolean undoAble) {
        isUndoAble = undoAble;
    }

    public BucketModel getBucket() {
        return bucket;
    }

    public void setBucket(BucketModel bucket) {
        this.bucket = bucket;
    }
}
