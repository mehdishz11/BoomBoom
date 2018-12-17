package psb.com.customView;

/**
 * Created by AMiR Ehsan on 1/10/2018.
 */

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

public class CheckedTextViewC extends android.support.v7.widget.AppCompatCheckedTextView {

    public CheckedTextViewC(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub
    }
    public CheckedTextViewC(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
    }
    public CheckedTextViewC(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }
    public void setTypeface(Typeface tf, int style) {
        if(!this.isInEditMode()){
            Typeface normalTypeface = Typeface.createFromAsset(getContext().getAssets(), "fonts/IRANSansDN_FaNum_Light.ttf");
            Typeface boldTypeface = Typeface.createFromAsset(getContext().getAssets(), "fonts/IRANSansDN_FaNum_Light.ttf");

            if (style == Typeface.BOLD) {
                super.setTypeface(boldTypeface/*, -1*/);
            } else {
                super.setTypeface(normalTypeface/*, -1*/);
            }
        }

    }
}