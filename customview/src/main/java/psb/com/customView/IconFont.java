package psb.com.customView;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;


/**
 * Created by Mehdi on 6/29/2017 AD.
 */

public class IconFont extends androidx.appcompat.widget.AppCompatTextView {

    public IconFont(Context context) {
        super(context);
            Typeface face= Typeface.createFromAsset(context.getAssets(), "fonts/KidsPaint.ttf");

        this.setTypeface(face);

    }

    public IconFont(Context context, AttributeSet attrs) {
        super(context, attrs);
            Typeface face= Typeface.createFromAsset(context.getAssets(), "fonts/KidsPaint.ttf");

        this.setTypeface(face);
    }

    public IconFont(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
            Typeface face= Typeface.createFromAsset(context.getAssets(), "fonts/KidsPaint.ttf");

        this.setTypeface(face);
    }
}
