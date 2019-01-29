package psb.com.customView;

import android.content.Context;
import android.graphics.Typeface;
import androidx.annotation.Nullable;
import android.util.AttributeSet;

/**
 * Created by Mehdi on 4/11/2017 AD.
 */

public class CustomTextViewBold extends androidx.appcompat.widget.AppCompatTextView {
    public CustomTextViewBold(Context context) {
        super(context);
        Typeface face = Typeface.createFromAsset(context.getAssets(), "fonts/IRANSansDN_FaNum-Bold.ttf");
        this.setTypeface(face);
    }

    public CustomTextViewBold(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        Typeface face = Typeface.createFromAsset(context.getAssets(), "fonts/IRANSansDN_FaNum-Bold.ttf");
        this.setTypeface(face);
    }

    public CustomTextViewBold(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Typeface face = Typeface.createFromAsset(context.getAssets(), "fonts/IRANSansDN_FaNum-Bold.ttf");
        this.setTypeface(face);
    }
}