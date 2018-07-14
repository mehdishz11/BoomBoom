package psb.com.kidpaint.painting;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.view.ViewPager;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.ArrayList;

import psb.com.kidpaint.R;
import psb.com.kidpaint.painting.bucket.BucketCanvas;
import psb.com.kidpaint.painting.canvas.sticker.StickerCanvas;
import psb.com.kidpaint.painting.palette.adapter.PaletteViewPagerAdapter;
import psb.com.kidpaint.painting.palette.color.PaintType;
import psb.com.kidpaint.painting.palette.color.PaletteFragment;
import psb.com.kidpaint.utils.Value;
import psb.com.kidpaint.utils.customView.paintingBucket.QueueLinearFloodFiller;
import psb.com.kidpaint.utils.soundHelper.SoundHelper;
import psb.com.paintingview.DrawView;

import static android.view.View.LAYER_TYPE_HARDWARE;


public class PaintActivity extends AppCompatActivity implements
        PaletteFragment.OnFragmentInteractionListener,
        BucketCanvas.OnBucketPointSelected {


    private static final String FRAGMENT_PALETTE = "FRAGMENT_PALETTE";
    private boolean isAnimating;
    private ArrayList<View> views = new ArrayList<>();
    private boolean isExpanded;
    private ImageView btnMore;

    private ViewPager mPager;
    private PaletteViewPagerAdapter adapter;
    private ImageView btnLeft;
    private ImageView btnRight;

    private StickerCanvas stickerCanvas;
    private DrawView paintCanvas;

    private RelativeLayout relHandle;


    public static final String KEY_RESOURCE_OUTLINE = "KEY_RESOURCE_OUTLINE";
    private int outlineResource = 0;


    private BucketCanvas bucketCanvas;


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


        outlineResource = getIntent().getIntExtra(KEY_RESOURCE_OUTLINE, 0);

        stackViews();
        initView();
    }

    private void initView() {

        btnMore = findViewById(R.id.btn_more);
        stickerCanvas = findViewById(R.id.sticker_canvas);
        paintCanvas = findViewById(R.id.paint_canvas);
        bucketCanvas = findViewById(R.id.bucket_canvas);
        btnLeft = findViewById(R.id.btn_left);
        btnRight = findViewById(R.id.btn_right);
        relHandle = findViewById(R.id.rel_handle);


        RelativeLayout paletteBottomSheet = findViewById(R.id.bottom_sheet);

// init the bottom sheet behavior
        final BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(paletteBottomSheet);
        relHandle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(bottomSheetBehavior.getState()==BottomSheetBehavior.STATE_COLLAPSED){
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                }else{
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }
            }
        });


        btnLeft.setImageResource(R.drawable.icon_left_disable);

        btnLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mPager.getCurrentItem() > 0) {
                    mPager.setCurrentItem(mPager.getCurrentItem() - 1);
                    SoundHelper.playSound(R.raw.click_bubbles_1);
                } else {
                    SoundHelper.playSound(R.raw.lock_1);
                }
            }
        });

        btnRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mPager.getCurrentItem() < mPager.getAdapter().getCount() - 1) {
                    mPager.setCurrentItem(mPager.getCurrentItem() + 1);
                    SoundHelper.playSound(R.raw.click_bubbles_1);
                } else {
                    SoundHelper.playSound(R.raw.lock_1);
                }
            }
        });


        if (outlineResource != 0 && outlineResource != R.drawable.picture_0) {
            bucketCanvas.setImageResource(outlineResource);
            bucketCanvas.setOnBucketPointSelected(this);


        }

        paintCanvas.setLayerType(LAYER_TYPE_HARDWARE, null);
        paintCanvas.setBaseColor(Color.TRANSPARENT);

        ImageView btnUndo = findViewById(R.id.btn_save);

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
                btnRight.setImageResource(R.drawable.btn_right);
                btnLeft.setImageResource(R.drawable.btn_left);


                if (position == 0) {
                    btnLeft.setImageResource(R.drawable.icon_left_disable);

                    paintCanvas.setEnableDrawing(true);
                    bucketCanvas.removeTouchListener();

                    if (stickerCanvas.getVisibility() == View.VISIBLE) {
                      /*  stickerCanvas.hideShowController(false);
                        paintCanvas.drawCustomBitmap(getStickerBitmap());

                        stickerCanvas.removeAllStickers();*/
                        stickerCanvas.setVisibility(View.GONE);

                    }

                } else if (position == 1) {
                    btnRight.setImageResource(R.drawable.icon_right_disable);

                    paintCanvas.setEnableDrawing(false);
                    stickerCanvas.setVisibility(View.VISIBLE);

                /*    StickerImageView stickerImageView = new StickerImageView(PaintActivity.this);
                    stickerImageView.setImageResource(R.drawable.icon_plus_normal);
                    stickerCanvas.addSticker(stickerImageView);*/

//                        disable bucket
                    bucketCanvas.removeTouchListener();

                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
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

    private Bitmap getBucketBitmap() {

        bucketCanvas.setDrawingCacheEnabled(true);
        bucketCanvas.buildDrawingCache();
        Bitmap bitmapSticker = Bitmap.createBitmap(bucketCanvas.getDrawingCache());
        bucketCanvas.setDrawingCacheEnabled(false);

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

        paintCanvas.setEnableDrawing(true);
        bucketCanvas.removeTouchListener();
        paintCanvas.setMode(DrawView.Mode.DRAW);
        paintCanvas.setDrawer(DrawView.Drawer.PEN);

        if (stickerCanvas.getVisibility() == View.VISIBLE) {
            stickerCanvas.hideShowController(false);
            paintCanvas.drawCustomBitmap(getStickerBitmap());

            stickerCanvas.removeAllStickers();
            stickerCanvas.setVisibility(View.GONE);
        }


        if (paintType == PaintType.ERASER) {
            paintCanvas.setMode(DrawView.Mode.ERASER);
            paintCanvas.setDrawer(DrawView.Drawer.PEN);

        } else if (paintType == PaintType.BRUSH) {
            paintCanvas.drawBitmapOutline(getBucketBitmap());
            bucketCanvas.initOntouchListener();
            paintCanvas.setEnableDrawing(false);

        } else {

        }

    }

    @Override
    public void onPaintSizeSelected(int size) {
        paintCanvas.setPaintStrokeWidth(10.0f + (22.5f * size));
    }


    ///////////////////////////////////////////////////////////////////////////
    // Bucket implements
    ///////////////////////////////////////////////////////////////////////////
    @Override
    public void onPointSelected(int x, int y) {

        paintCanvas.setDrawingCacheEnabled(true);
        paintCanvas.buildDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(paintCanvas.getDrawingCache());
        paintCanvas.setDrawingCacheEnabled(false);


        QueueLinearFloodFiller filler = new QueueLinearFloodFiller(bitmap, bitmap.getPixel(x, y), paintCanvas.getPaintStrokeColor());
        filler.setTolerance(10);
        filler.floodFill(x, y);
        paintCanvas.drawCustomBitmap(bitmap);
    }
}
