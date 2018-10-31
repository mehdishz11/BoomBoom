package psb.com.kidpaint.utils.customView;

import android.content.Context;
import android.util.AttributeSet;

public class ImageView169 extends android.support.v7.widget.AppCompatImageView {

    public ImageView169(Context context) {
        super(context);
    }

    public ImageView169(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ImageView169(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    // some other necessary things

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int width = getMeasuredWidth();
        //force a 16:9 aspect ratio
        int height = Math.round(width * .5625f);



//        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
//        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        int heightSize = MeasureSpec.getSize(Math.round(widthSize * .5625f));

//        int size= widthSize < heightSize ? widthSize : heightSize;



        int finalMeasureSpecWidth = MeasureSpec.makeMeasureSpec(widthSize, MeasureSpec.EXACTLY);
        int finalMeasureSpecHeight = MeasureSpec.makeMeasureSpec(heightSize, MeasureSpec.EXACTLY);

        super.onMeasure(finalMeasureSpecWidth, finalMeasureSpecHeight);


    }

}