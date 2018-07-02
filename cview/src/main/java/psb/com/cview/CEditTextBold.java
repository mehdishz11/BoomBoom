package psb.com.cview;

/**
 * Created by mehdi on 1/19/16 AD.
 */

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;


public class CEditTextBold extends AppCompatEditText {

    public CEditTextBold(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public CEditTextBold(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CEditTextBold(Context context) {
        super(context);
        init();
    }

    private void init() {
        if (!isInEditMode()) {
            setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/IRANSansDN_FaNum-Bold.ttf"));
        }
    }
}