package psb.com.kidpaint.utils.customView;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.RelativeLayout;

import psb.com.kidpaint.R;
import psb.com.kidpaint.utils.soundHelper.SoundHelper;


/**
 * Created by Mehdi on 1/28/2018 AD.
 */

public class ScaleUpView extends RelativeLayout {

    int delay = 0;
    boolean scaleOnClick = false;
    boolean playSound=false;

    public ScaleUpView(Context context) {
        super(context);
        init();
    }

    public ScaleUpView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ScaleUpView);
        delay = a.getInt(R.styleable.ScaleUpView_startDelay, 0);
        scaleOnClick = a.getBoolean(R.styleable.ScaleUpView_scaleOnclick, false);
        playSound = a.getBoolean(R.styleable.ScaleUpView_playSound, false);
        a.recycle();
        init();
    }

    public ScaleUpView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ScaleUpView);
        delay = a.getInt(R.styleable.ScaleUpView_startDelay, 0);
        scaleOnClick = a.getBoolean(R.styleable.ScaleUpView_scaleOnclick, false);
        playSound = a.getBoolean(R.styleable.ScaleUpView_playSound, false);
        a.recycle();
        init();
    }


    @Override
    public void setOnClickListener(@Nullable final OnClickListener l) {
        final OnClickListener onClickListener = new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (playSound){
                    SoundHelper.playSound(R.raw.click_bubbles_1);
                }

                if (scaleOnClick) {
                    animateOnClick(l);
                }else if(l !=null){
                    l.onClick(view);
                }

            }
        };
        super.setOnClickListener(onClickListener);

    }

    private void animateOnClick(@Nullable final OnClickListener l) {
        ObjectAnimator animScaleX = ObjectAnimator.ofFloat(ScaleUpView.this, "scaleX", 1f, 1.1f, 0.9f, 1.0f);
        ObjectAnimator animScaleY = ObjectAnimator.ofFloat(ScaleUpView.this, "scaleY", 1f, 1.1f, 0.9f, 1.0f);
        animScaleY.setDuration(200);
        animScaleX.setDuration(200);

        final AnimatorSet scaleDown = new AnimatorSet();
        scaleDown.play(animScaleX).with(animScaleY);

        if (l != null) {
            scaleDown.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    l.onClick(ScaleUpView.this);
                }
            });
        }

        scaleDown.start();
    }

    public void init() {
        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                setPivotX(ScaleUpView.this.getMeasuredWidth() / 2);
                setPivotY(ScaleUpView.this.getMeasuredHeight() / 2);

                ScaleUpView.this.setScaleX(0.0f);
                ScaleUpView.this.setScaleY(0.0f);

                ObjectAnimator animScaleX = ObjectAnimator.ofFloat(ScaleUpView.this, "scaleX", 0f, 1.3f, 0.8f, 1.2f, 1.0f);
                ObjectAnimator animScaleY = ObjectAnimator.ofFloat(ScaleUpView.this, "scaleY", 0f, 1.3f, 0.8f, 1.2f, 1.0f);
                animScaleY.setDuration(800);
                animScaleX.setDuration(800);

                final AnimatorSet scaleDown = new AnimatorSet();
                scaleDown.setStartDelay(delay);
                scaleDown.play(animScaleX).with(animScaleY);

                scaleDown.start();


                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    getViewTreeObserver().removeOnGlobalLayoutListener(this);
                } else {
                    getViewTreeObserver().removeGlobalOnLayoutListener(this);
                }
            }

        });


    }


}
