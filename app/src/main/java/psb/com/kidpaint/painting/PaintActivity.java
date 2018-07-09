package psb.com.kidpaint.painting;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import java.util.ArrayList;

import psb.com.kidpaint.R;
import psb.com.kidpaint.painting.canvas.sticker.StickerCanvas;
import psb.com.kidpaint.painting.palette.adapter.PaletteViewPagerAdapter;
import psb.com.kidpaint.painting.palette.color.PaintType;
import psb.com.kidpaint.painting.palette.color.PaletteFragment;
import psb.com.kidpaint.utils.Value;
import psb.com.kidpaint.utils.customView.stickerview.StickerImageView;
import psb.com.paintingview.DrawView;

import static android.view.View.LAYER_TYPE_HARDWARE;


public class PaintActivity extends AppCompatActivity implements
        PaletteFragment.OnFragmentInteractionListener {


    private static final String FRAGMENT_PALETTE = "FRAGMENT_PALETTE";
    private boolean isAnimating;
    private ArrayList<View> views = new ArrayList<>();
    private boolean isExpanded;
    private ImageView btnMore;

    private ViewPager mPager;
    private PaletteViewPagerAdapter adapter;

    private StickerCanvas stickerCanvas;
    private DrawView paintCanvas;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_paint);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

        stackViews();
        initView();

    }

    private void initView() {

        btnMore = findViewById(R.id.btn_more);
        stickerCanvas = findViewById(R.id.sticker_canvas);
        paintCanvas = findViewById(R.id.paint_canvas);

        paintCanvas.setLayerType(LAYER_TYPE_HARDWARE, null);
        paintCanvas.setBaseColor(Color.TRANSPARENT);

        ImageView btnUndo=findViewById(R.id.btn_save);

        btnUndo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                paintCanvas.undo();
            }
        });

        btnMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isExpanded) {
                    animatePopRightExpand();
                } else {
                    animatePopRightCollapse();
                }
            }
        });

        mPager = findViewById(R.id.view_pager);
        adapter = new PaletteViewPagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(adapter);

        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    paintCanvas.setEnableDrawing(true);
                    if(stickerCanvas.getVisibility()==View.VISIBLE){
                        stickerCanvas.hideShowController(false);
                        paintCanvas.drawCustomBitmap(getStickerBitmap());
                        stickerCanvas.removeAllStickers();
                        stickerCanvas.setVisibility(View.GONE);
                    }
                } else {
                    paintCanvas.setEnableDrawing(false);
                    stickerCanvas.setVisibility(View.VISIBLE);

                    StickerImageView stickerImageView=new StickerImageView(PaintActivity.this);
                    stickerImageView.setImageResource(R.drawable.icon_plus_normal);
                    stickerCanvas.addSticker(stickerImageView);

                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

//        getSupportFragmentManager().beginTransaction().add(R.id.frame_palette, new PaletteFragment().newInstance(), FRAGMENT_PALETTE).commit();
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
        }
    }

    private void animatePopRightExpand() {
        btnMore.setImageResource(R.drawable.icon_more_pressed);
        ArrayList<Animator> animators = new ArrayList<>();
        isAnimating = true;
        for (int i = 0; i < views.size() - 1; i++) {
            final View view = views.get(i);

//            float animationSize = 160f;
            float animationSize = Value.dp(40);
           /* if(i==0){
                animationSize = Value.dp(60);
            }*/
            ObjectAnimator viewAnimator = ObjectAnimator.ofFloat(view, "translationX", 0f, (i + 1) * animationSize);
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
        for (int i = views.size() - 2; i >= 0; i--) {
            final View view = views.get(i);
            float animationSize = Value.dp(40);

            ObjectAnimator viewAnimator = ObjectAnimator.ofFloat(view, "translationX", (i + 1) * animationSize, 0f);
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

    private Bitmap getStickerBitmap() {

        stickerCanvas.setDrawingCacheEnabled(true);
        stickerCanvas.buildDrawingCache();
        Bitmap bitmapSticker = Bitmap.createBitmap(stickerCanvas.getDrawingCache());
        stickerCanvas.setDrawingCacheEnabled(false);

        return bitmapSticker;

    }


    ///////////////////////////////////////////////////////////////////////////
    // Color palette view
    ///////////////////////////////////////////////////////////////////////////
    @Override
    public void onColorSelected(int color) {
        paintCanvas.setPaintStrokeColor(color);

    }

    @Override
    public void onPaintTypeSelected(PaintType paintType) {
        if (paintType == PaintType.ERASER) {
            paintCanvas.setMode(DrawView.Mode.ERASER);
            paintCanvas.setDrawer(DrawView.Drawer.PEN);
        } else {
            paintCanvas.setMode(DrawView.Mode.DRAW);
            paintCanvas.setDrawer(DrawView.Drawer.PEN);
        }

    }

    @Override
    public void onPaintSizeSelected(int size) {
        paintCanvas.setPaintStrokeWidth(10.0f + (22.5f * size));
    }

}
