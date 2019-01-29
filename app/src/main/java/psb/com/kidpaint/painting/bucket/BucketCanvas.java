package psb.com.kidpaint.painting.bucket;

import android.content.Context;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class BucketCanvas extends androidx.appcompat.widget.AppCompatImageView {
    private OnBucketPointSelected onBucketPointSelected;
    public BucketCanvas(Context context) {
        super(context);
    }

    public BucketCanvas(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public BucketCanvas(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setOnBucketPointSelected(OnBucketPointSelected onBucketPointSelected) {
        this.onBucketPointSelected = onBucketPointSelected;
    }

    public void removeTouchListener(){
        setOnTouchListener(null);
    }

    public void initOntouchListener(){
        setOnTouchListener(onTouchListener);
    }

    OnTouchListener onTouchListener=new OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (motionEvent.getAction()==MotionEvent.ACTION_DOWN){
                if (onBucketPointSelected != null) {
                    onBucketPointSelected.onPointSelected((int)motionEvent.getX(),(int)motionEvent.getY());
                }
            }
            return true;
        }
    };


    public interface OnBucketPointSelected{
        void onPointSelected(int x,int y);
    }

}
