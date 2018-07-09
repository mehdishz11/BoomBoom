package psb.com.kidpaint.painting.canvas.paint;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;

import psb.com.paintingview.DrawView;

public class PaintCanvas extends DrawView {



    public PaintCanvas(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public PaintCanvas(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PaintCanvas(Context context) {
        super(context);
        init();
    }

    private void init(){

        setLayerType(LAYER_TYPE_HARDWARE, null);
        setBaseColor(Color.TRANSPARENT);
    }

    public boolean hasUndo(){
        return undo();
    }

}
