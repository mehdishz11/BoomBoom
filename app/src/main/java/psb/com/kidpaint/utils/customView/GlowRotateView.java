package psb.com.kidpaint.utils.customView;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.LinearInterpolator;
import android.widget.RelativeLayout;

public class GlowRotateView extends View {


    public GlowRotateView(Context context) {
        super(context);
        init();
    }

    public GlowRotateView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public GlowRotateView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {


        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int finalsizeWidth ;


        if (widthSize > heightSize) {
            finalsizeWidth = widthSize;
        } else {
            finalsizeWidth = heightSize;
        }


        int finalMeasureSpec = MeasureSpec.makeMeasureSpec(finalsizeWidth, MeasureSpec.EXACTLY);


        super.onMeasure(finalMeasureSpec, finalMeasureSpec);
    }


    private void init(){

        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {

                int c= (int) Math.sqrt((Math.pow(getWidth(),2))+(Math.pow(getHeight(),2)));

                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                        c,
                        c
                );

                params.setMargins(-((c-getWidth())/2), -((c-getHeight())/2), -((c-getWidth())/2), -((c-getHeight())/2));
                setLayoutParams(params);



                ObjectAnimator animRotate = ObjectAnimator.ofFloat(GlowRotateView.this ,"rotation", 0,360);

                animRotate.setRepeatMode(ValueAnimator.RESTART);
                animRotate.setDuration(25000); // miliseconds
                animRotate.setRepeatCount(ValueAnimator.INFINITE);
                animRotate.setInterpolator(new LinearInterpolator());

                animRotate.start();





                getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
    }


}
