package psb.com.kidpaint.utils.customAnimation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.LinearInterpolator;

import psb.com.kidpaint.utils.Utils;

public class AnimationHelper {

    public void rightToLeft(final View view, int duration) {


        float distatnce1=Utils.getDeviceWidth() - view.getX();
        float distatnce2=Utils.getDeviceWidth() + view.getWidth();





        int duration1= (int) ((distatnce1*duration)/distatnce2);
        int duration2= duration;


        ObjectAnimator animMove = ObjectAnimator.ofFloat(view, "translationX", 0, Utils.getDeviceWidth() - view.getX());
        final ObjectAnimator animMoveFromStart = ObjectAnimator.ofFloat(view, "translationX", -(view.getX() + view.getWidth()), Utils.getDeviceWidth() - (view.getX()));

        final ObjectAnimator fadeIn = ObjectAnimator.ofFloat(view, "alpha", 0.0f, 1.0f);

        animMoveFromStart.setDuration(duration2);
        animMoveFromStart.setRepeatCount(ValueAnimator.INFINITE);
        animMoveFromStart.setInterpolator(new LinearInterpolator());


        fadeIn.setDuration(500);

        animMoveFromStart.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                view.setX(0);
                super.onAnimationEnd(animation);
            }
        });

        if(duration1<0){
            duration1*=-1;
        }

        animMove.setDuration(duration1); // miliseconds
        animMove.setInterpolator(new LinearInterpolator());

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(animMove);

        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                view.setX(0);
                AnimatorSet animatorSet = new AnimatorSet();

//                animatorSet.playTogether(animMoveFromStart,fadeIn);
                animMoveFromStart.start();
                super.onAnimationEnd(animation);
            }
        });
        animatorSet.start();
    }

    public void rotation(final int duration,final View view){
        view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {

                ObjectAnimator animRotate = ObjectAnimator.ofFloat(view ,"rotation", 0,360);

                animRotate.setRepeatMode(ValueAnimator.RESTART);
                animRotate.setDuration(duration); // miliseconds
                animRotate.setRepeatCount(ValueAnimator.INFINITE);
                animRotate.setInterpolator(new LinearInterpolator());

                animRotate.start();

                view.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
    }
}
