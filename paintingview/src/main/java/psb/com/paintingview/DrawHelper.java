package psb.com.paintingview;

import android.graphics.Bitmap;
import android.graphics.Path;

public class DrawHelper {

    private Path path;
    private Bitmap bitmap;

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
}
