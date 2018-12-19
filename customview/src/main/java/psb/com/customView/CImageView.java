package psb.com.customView;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;


public class CImageView extends android.support.v7.widget.AppCompatImageView {
    public CImageView(Context context) {
        super(context);
    }

    public CImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setOnClickListener(@Nullable OnClickListener l) {
        super.setOnClickListener(l);
    }

}
