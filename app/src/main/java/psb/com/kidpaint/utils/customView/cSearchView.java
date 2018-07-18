package psb.com.kidpaint.utils.customView;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.SearchView;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.EditText;


/**
 * Created by Mehdi on 8/2/2017 AD.
 */

public class cSearchView extends SearchView {
    public cSearchView(Context context) {
        super(context);
    }

    public cSearchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        EditText txtSearch = (findViewById(android.support.v7.appcompat.R.id.search_src_text));
        Typeface face = Typeface.createFromAsset(getContext().getAssets(), "fonts/IRANSansMobile(FaNum)_Light.ttf");
        txtSearch.setTypeface(face);
        txtSearch.setTextSize(TypedValue.COMPLEX_UNIT_SP,14);

    }

    public cSearchView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
