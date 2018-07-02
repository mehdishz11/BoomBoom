package psb.com.kidpaint.paint;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import java.util.ArrayList;

import psb.com.kidpaint.R;
import psb.com.kidpaint.utils.Value;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class PaintActivity extends AppCompatActivity {


    private boolean isAnimating;
    private ArrayList<View> views = new ArrayList<>();
    private boolean isExpanded;
    private ImageView btnMore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_paint);
//        mContentView = findViewById(R.id.fullscreen_content);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

/*        show content
        mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);*/


        stackViews();

        btnMore=findViewById(R.id.btn_more);

        btnMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isExpanded){
                    animatePopRightExpand();
                }else{
                    animatePopRightCollapse();
                }
            }
        });


      /*  new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                animatePopRightExpand();
            }
        }, 1000);*/
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);


    }

    private void stackViews() {
        FrameLayout parentFrame = findViewById(R.id.rel_btn);
        for (int i = 0; i < parentFrame.getChildCount(); i++) {
            View childView = parentFrame.getChildAt(i);

            views.add(childView);

          /*  if (childView instanceof FloatingActionButton
                    || childView instanceof android.support.design.widget.FloatingActionButton) {
                if (i == 0) {
                    if (childView instanceof FloatingActionButton) {
                        FloatingActionButton fab = (FloatingActionButton) childView;
                        fab.setFabElevation(11);
                    } else if (childView instanceof android.support.design.widget.FloatingActionButton) {
                        android.support.design.widget.FloatingActionButton fab = (android.support.design.widget.FloatingActionButton) childView;
                        fab.setCompatElevation(11);
                    }
                } else {
                    childView.setVisibility(GONE);
                    if (childView instanceof FloatingActionButton) {
                        FloatingActionButton fab = (FloatingActionButton) childView;
                        fab.setFabElevation(9);
                    } else if (childView instanceof android.support.design.widget.FloatingActionButton) {
                        android.support.design.widget.FloatingActionButton fab = (android.support.design.widget.FloatingActionButton) childView;
                        fab.setCompatElevation(9);
                    }
                }

                childView.setLayoutParams(getGravity(childView));

                views.add(childView);
            } else {
                removeView(childView);
            }*/
        }
    }

    private void animatePopRightExpand() {
        btnMore.setImageResource(R.drawable.icon_more_pressed);
        ArrayList<Animator> animators = new ArrayList<>();
        isAnimating = true;
        for (int i = 0; i < views.size()-1; i++) {
            final View view = views.get(i);

//            float animationSize = 160f;
            float animationSize = Value.dp(40);
           /* if(i==0){
                animationSize = Value.dp(60);
            }*/
            ObjectAnimator viewAnimator = ObjectAnimator.ofFloat(view, "translationX", 0f, (i+1) * animationSize);
            viewAnimator.setDuration(250);
            viewAnimator.setInterpolator(new FastOutSlowInInterpolator());

            viewAnimator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationStart(Animator animation) {
                    super.onAnimationStart(animation);
                    view.setVisibility(View.VISIBLE);
                }
            });
            animators.add(viewAnimator);
        }

        AnimatorSet set = new AnimatorSet();
        set.playTogether(animators);/*Sequentially(animators);*/

        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);

                isAnimating = false;
                isExpanded = !isExpanded;
                /*if (onMenuExpandedListener != null) {
                    onMenuExpandedListener.onMenuExpanded();
                }*/
            }
        });

        set.start();
    }

    private void animatePopRightCollapse() {
        btnMore.setImageResource(R.drawable.btn_more);
        ArrayList<Animator> animators = new ArrayList<>();
        isAnimating = true;
        for (int i = views.size()-2 ; i >=0; i--) {
            final View view = views.get(i);
            float animationSize = Value.dp(40);

            ObjectAnimator viewAnimator = ObjectAnimator.ofFloat(view, "translationX", (i+1) * animationSize, 0f);
            viewAnimator.setDuration(250);
            viewAnimator.setInterpolator(new FastOutSlowInInterpolator());
            viewAnimator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    view.setVisibility(View.GONE);
                }
            });
            animators.add(viewAnimator);
        }

        AnimatorSet set = new AnimatorSet();
        set.playTogether(animators);

        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);

                isAnimating = false;
                isExpanded = !isExpanded;
                /*if (onMenuExpandedListener != null) {
                    onMenuExpandedListener.onMenuCollapsed();
                }*/
            }
        });

        set.start();
    }

}
