package psb.com.kidpaint.utils.selector;

import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;

public class SelectorUtils {


    public static  StateListDrawable makeIconSelector(Drawable drawable){
        StateListDrawable states = new StateListDrawable();
        states.addState(new int[] {android.R.attr.state_pressed}, drawable);
        states.addState(new int[] {android.R.attr.state_focused}, drawable);
        states.addState(new int[] {android.R.attr.state_selected}, drawable);
        states.addState(new int[] { }, setGrayScale(drawable));
        return states;
    }

    private static  Drawable  setGrayScale(Drawable v)
    {
        ColorMatrix matrix = new ColorMatrix();
        matrix.setSaturation(0);  //0 means grayscale
        ColorMatrixColorFilter cf = new ColorMatrixColorFilter(matrix);
        Drawable drawable =v.getConstantState().newDrawable().mutate();
        drawable.setColorFilter(cf);
        return drawable;
    }
}
