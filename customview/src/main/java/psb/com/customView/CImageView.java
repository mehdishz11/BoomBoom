package psb.com.customView;

import android.content.Context;
import androidx.annotation.Nullable;
import android.util.AttributeSet;


public class CImageView extends androidx.appcompat.widget.AppCompatImageView {
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
