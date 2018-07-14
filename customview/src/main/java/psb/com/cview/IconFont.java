package psb.com.cview;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;


/**
 * Created by Mehdi on 6/29/2017 AD.
 */

public class IconFont extends android.support.v7.widget.AppCompatTextView {

    public IconFont(Context context) {
        super(context);
            Typeface face= Typeface.createFromAsset(context.getAssets(), "fonts/icon_font.ttf");

        this.setTypeface(face);

    }

    public IconFont(Context context, AttributeSet attrs) {
        super(context, attrs);
            Typeface face= Typeface.createFromAsset(context.getAssets(), "fonts/icon_font.ttf");

        this.setTypeface(face);
    }

    public IconFont(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
            Typeface face= Typeface.createFromAsset(context.getAssets(), "fonts/icon_font.ttf");

        this.setTypeface(face);
    }
}
