package psb.com.kidpaint.painting;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import psb.com.kidpaint.R;
import psb.com.kidpaint.painting.bucket.BucketCanvas;
import psb.com.kidpaint.painting.canvas.sticker.StickerCanvas;
import psb.com.kidpaint.painting.palette.adapter.PaletteViewPagerAdapter;
import psb.com.kidpaint.painting.palette.color.PaintType;
import psb.com.kidpaint.painting.palette.color.PaletteFragment;
import psb.com.kidpaint.painting.palette.sticker.StickerFragment;
import psb.com.kidpaint.utils.Utils;
import psb.com.kidpaint.utils.Value;
import psb.com.kidpaint.utils.customView.dialog.CDialog;
import psb.com.kidpaint.utils.customView.dialog.DialogSettings;
import psb.com.kidpaint.utils.customView.dialog.MessageDialog;
import psb.com.kidpaint.utils.customView.intro.Intro;
import psb.com.kidpaint.utils.customView.intro.IntroPosition;
import psb.com.kidpaint.utils.customView.intro.showCase.DismissListener;
import psb.com.kidpaint.utils.customView.intro.showCase.FancyShowCaseQueue;
import psb.com.kidpaint.utils.customView.intro.showCase.FancyShowCaseView;
import psb.com.kidpaint.utils.customView.intro.showCase.OnCompleteListener;
import psb.com.kidpaint.utils.customView.paintingBucket.QueueLinearFloodFiller;
import psb.com.kidpaint.utils.customView.stickerview.StickerView;
import psb.com.kidpaint.utils.musicHelper.MusicHelper;
import psb.com.kidpaint.utils.sharePrefrence.SharePrefrenceHelper;
import psb.com.kidpaint.utils.soundHelper.SoundHelper;
import psb.com.paintingview.BucketModel;
import psb.com.paintingview.DrawView;

import static android.view.View.LAYER_TYPE_HARDWARE;


public class PaintActivity extends AppCompatActivity implements
        PaletteFragment.OnFragmentInteractionListener,
        StickerFragment.OnFragmentInteractionListener,
        BucketCanvas.OnBucketPointSelected {

    private int REQUEST_STORAGE_PERMISSIONS = 100;

    private static final String FRAGMENT_PALETTE = "FRAGMENT_PALETTE";
    private boolean isAnimating;
    private ArrayList<View> views = new ArrayList<>();
    private boolean isExpanded;
    private ImageView btnMore;

    private ViewPager mPager;
    private PaletteViewPagerAdapter adapter;
    private ImageView btnLeft;
    private ImageView btnRight;
    private ImageView btnSave;
    private ImageView btnCancel;
    private ImageView btnUndo;
    private ImageView btnSettings;

    private StickerCanvas stickerCanvas;
    private DrawView paintCanvas;

    private RelativeLayout relHandle;

    private BottomSheetBehavior bottomSheetBehavior;



    public static final String KEY_RESOURCE_OUTLINE = "KEY_RESOURCE_OUTLINE";
    private int outlineResource = 0;
    private String editPath;

    private ImageView imageHistory;

    private BucketCanvas bucketCanvas;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_paint);

        editPath=getIntent().getStringExtra("Path");

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);


        outlineResource = getIntent().getIntExtra(KEY_RESOURCE_OUTLINE, 0);

        stackViews();
        initView();
       relHandle.performClick();
       showIntro();
    }

    @Override
    protected void onPause() {
        super.onPause();
        MusicHelper.stopMusic();
    }

    @Override
    protected void onResume() {
        super.onResume();

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

        MusicHelper.stopMusic();
        MusicHelper.playMusic(R.raw.bgr_be_happy);
    }

    private void initView() {

        btnMore = findViewById(R.id.btn_more);
        stickerCanvas = findViewById(R.id.sticker_canvas);
        paintCanvas = findViewById(R.id.paint_canvas);
        bucketCanvas = findViewById(R.id.bucket_canvas);
        btnLeft = findViewById(R.id.btn_left);
        btnRight = findViewById(R.id.btn_right);
        relHandle = findViewById(R.id.rel_handle);
        mPager = findViewById(R.id.view_pager);
        imageHistory = findViewById(R.id.image_history);

        btnCancel = findViewById(R.id.btn_cancel);
        btnSave = findViewById(R.id.btn_save);
        btnUndo = findViewById(R.id.btn_undo);
        btnSettings = findViewById(R.id.btn_settings);


        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SoundHelper.playSound(R.raw.click_bubbles_1);
                animatePopRightCollapse();

                MessageDialog dialog = new MessageDialog(PaintActivity.this);
                dialog.setMessage("مطمئنی می خوای خارج بشی ؟");
                dialog.setOnCLickListener(new CDialog.OnCLickListener() {
                    @Override
                    public void onPosetiveClicked() {
                        finish();
                    }

                    @Override
                    public void onNegativeClicked() {

                    }
                });
                dialog.setSoundId(R.raw.are_you_sure_exit);
                dialog.setAcceptButtonMessage(PaintActivity.this.getString(R.string.yes));
                dialog.setTitle(getString(R.string.exit));
                dialog.show();

                dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);

            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SoundHelper.playSound(R.raw.click_bubbles_1);
                if (Utils.gstoragePermissionIsGranted(PaintActivity.this)) {
                    saveTempBitmap(getPaintCanvasBitmap());

                } else {
                    requestForGrantStoragePermission();
                }

            }
        });
        btnUndo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SoundHelper.playSound(R.raw.undo);
                paintCanvas.undo();
            }
        });

        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SoundHelper.playSound(R.raw.click_1);
                animatePopRightCollapse();

                DialogSettings cDialog = new DialogSettings(PaintActivity.this);

                cDialog.show();
            }
        });

        btnMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SoundHelper.playSound(R.raw.click_1);
                if (!isExpanded) {
                    animatePopRightExpand();
                } else {
                    animatePopRightCollapse();
                }
            }
        });


        RelativeLayout paletteBottomSheet = findViewById(R.id.bottom_sheet);


        bottomSheetBehavior = BottomSheetBehavior.from(paletteBottomSheet);
        relHandle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_COLLAPSED) {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                } else {
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


        if (outlineResource != 0) {
            bucketCanvas.setImageResource(outlineResource);

        }else{
            bucketCanvas.setImageResource(R.drawable.picture_0);
        }

        bucketCanvas.setOnBucketPointSelected(this);

        paintCanvas.setLayerType(LAYER_TYPE_HARDWARE, null);
        paintCanvas.setBaseColor(Color.TRANSPARENT);
        paintCanvas.setPaintStrokeWidth(10.0f + (22.5f * SharePrefrenceHelper.getSize()));
        paintCanvas.setPaintStrokeColor(SharePrefrenceHelper.getColor());


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
                    onPaintTypeSelected(PaintType.PENCIL);

                } else if (position == 1) {
                    btnRight.setImageResource(R.drawable.icon_right_disable);
                    onPaintTypeSelected(PaintType.STICKER);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        if(editPath!=null) {
            Bitmap bitmap = BitmapFactory.decodeFile(editPath);
            imageHistory.setImageBitmap(bitmap);
        }


        onPaintTypeSelected(PaintType.PENCIL);

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);


    }

    ///////////////////////////////////////////////////////////////////////////
    // show intro
    ///////////////////////////////////////////////////////////////////////////

    private void showIntro() {
        final View view = findViewById(R.id.introViewCenter);
        final View view2 = findViewById(R.id.introViewRight);
        view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                FancyShowCaseView fancyShowCaseView= Intro.addIntroTo(PaintActivity.this, view, R.layout.intro_step_3, IntroPosition.TOP, R.raw.yellow,null);
                FancyShowCaseView fancyShowCaseView_2=Intro.addIntroTo(PaintActivity.this, view2, R.layout.intro_step_4, IntroPosition.TOP, R.raw.bgr_be_happy, new DismissListener() {
                    @Override
                    public void onDismiss(String id) {
                        btnRight.performClick();
                    }

                    @Override
                    public void onSkipped(String id) {
                        btnRight.performClick();

                    }
                });

                FancyShowCaseQueue fancyShowCaseQueue=new FancyShowCaseQueue();
                fancyShowCaseQueue.add(fancyShowCaseView);
                fancyShowCaseQueue.add(fancyShowCaseView_2);
                fancyShowCaseQueue.setCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete() {
                        Log.d("TAG", "onComplete: ");
                    }
                });
                fancyShowCaseQueue.show();
                view.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });

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

    private Bitmap getOutlineBitmap() {
        bucketCanvas.setDrawingCacheEnabled(true);
        bucketCanvas.buildDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(bucketCanvas.getDrawingCache());
        bucketCanvas.setDrawingCacheEnabled(false);

        return bitmap;

    }

    private Bitmap getCanvasBitmap() {
        return paintCanvas.getBitmap();
    }

    ///////////////////////////////////////////////////////////////////////////
    // save main bitmap
    ///////////////////////////////////////////////////////////////////////////
    private Bitmap getPaintCanvasBitmap() {

        RelativeLayout relPaint=findViewById(R.id.rel_drawing);
        relPaint.setDrawingCacheEnabled(true);
        relPaint.buildDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(relPaint.getDrawingCache());
        relPaint.setDrawingCacheEnabled(false);
/*
        Bitmap bitmapCanvas = getCanvasBitmap();
        Bitmap bitmapOutline = getOutlineBitmap();
        Bitmap bitmapStickers = getStickerBitmap();

        Canvas c = new Canvas(bitmapCanvas);

        c.drawBitmap(bitmapOutline, 0, 0, new Paint());
        c.drawBitmap(bitmapStickers, 0, 0, new Paint());
*/

        return bitmap;

    }

    void saveTempBitmap(Bitmap bitmap) {
        if (isExternalStorageWritable()) {
            saveImage(bitmap);
        } else {
            //prompt the user or do something
        }
    }

    void saveImage(Bitmap finalBitmap) {

        stickerCanvas.hideShowController(false);

        String folder_main = "kidPaint";
        File mydir = new File(Environment.getExternalStorageDirectory(), folder_main);
        if (!mydir.exists()) {
            mydir.mkdirs();
        }


        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String fname = "paint_" + timeStamp + ".jpg";
        File file = new File(mydir, fname);

        if (editPath != null) {
            fname=editPath;
             file = new File(fname);
        }

        if (file.exists()) file.delete();

        try {
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();

            finish();
        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    ///////////////////////////////////////////////////////////////////////////
    // Color palette view
    ///////////////////////////////////////////////////////////////////////////
    @Override
    public void onColorSelected(int color) {
        paintCanvas.setPaintStrokeColor(color);
        SharePrefrenceHelper.setColor(color);

    }

    @Override
    public void onPaintTypeSelected(final PaintType paintType) {

        if (adapter.getItem(0) != null) {
            ((PaletteFragment) adapter.getItem(0)).setTypeViews(paintType);
        }


        if (paintType == PaintType.ERASER) {
            SoundHelper.playSound(R.raw.sf_eraser);
            //enable and set paint canvas
            paintCanvas.setMode(DrawView.Mode.ERASER);
            paintCanvas.setDrawer(DrawView.Drawer.PEN);
            paintCanvas.setEnableDrawing(true);

            //disable bucket canvas
            bucketCanvas.removeTouchListener();

            // disable sticker canvas
            if (stickerCanvas.isClickable()) {
                stickerCanvas.enableDisableCanvas(false);
                stickerCanvas.hideShowController(false);
            }


        } else if (paintType == PaintType.BUCKET) {
            SoundHelper.playSound(R.raw.paint_bucket);
            //ENABLE BUCKET
            bucketCanvas.initOntouchListener();

            //DISABLE PAIN CANVAS
            paintCanvas.setEnableDrawing(false);

            // disable sticker canvas
            if (stickerCanvas.isClickable()) {
                stickerCanvas.enableDisableCanvas(false);
                stickerCanvas.hideShowController(false);
            }


        } else if (paintType == PaintType.STICKER) {

            //ENABLE STICKER
            stickerCanvas.enableDisableCanvas(true);

            //disable pain canvas
            paintCanvas.setEnableDrawing(false);

            //disable bucket
            bucketCanvas.removeTouchListener();

        } else if (paintType == PaintType.PENCIL) {
            SoundHelper.playSound(R.raw.pencil);
            //enable and set paint canvas
            paintCanvas.setMode(DrawView.Mode.DRAW);
            paintCanvas.setDrawer(DrawView.Drawer.PEN);
            paintCanvas.setEnableDrawing(true);

            //disable bucket canvas
            bucketCanvas.removeTouchListener();

            // disable sticker canvas
            if (stickerCanvas.isClickable()) {
                stickerCanvas.enableDisableCanvas(false);
                stickerCanvas.hideShowController(false);
            }

        } else {

        }

    }

    @Override
    public void onPaintSizeSelected(int size) {
        paintCanvas.setPaintStrokeWidth(10.0f + (22.5f * size));
        SharePrefrenceHelper.setSize(size);
    }


    ///////////////////////////////////////////////////////////////////////////
    // Bucket implements
    ///////////////////////////////////////////////////////////////////////////
    @Override
    public void onPointSelected(int x, int y) {

        Bitmap bitmapCanvas = getCanvasBitmap();
        Bitmap bitmapOutline = getOutlineBitmap();

        Canvas c = new Canvas(bitmapCanvas);

        c.drawBitmap(bitmapOutline, 0, 0, new Paint());

        QueueLinearFloodFiller filler = new QueueLinearFloodFiller(bitmapCanvas, bitmapCanvas.getPixel(x, y), paintCanvas.getPaintStrokeColor());
        filler.setTolerance(10);

        BucketModel bucketModel = filler.floodFill(x, y);
        paintCanvas.drawBucket(bucketModel);

    }


    ///////////////////////////////////////////////////////////////////////////
    // permission granted
    ///////////////////////////////////////////////////////////////////////////
    public void requestForGrantStoragePermission() {
        int permission = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        int permission2 = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permission != PackageManager.PERMISSION_GRANTED || permission2 != PackageManager.PERMISSION_GRANTED) {
            requestPermission();
        }
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_STORAGE_PERMISSIONS);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        if (requestCode == REQUEST_STORAGE_PERMISSIONS) {

            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                    && ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                saveTempBitmap(getPaintCanvasBitmap());

            } else {

            }

        }
    }


    ///////////////////////////////////////////////////////////////////////////
    // on sticker selected
    ///////////////////////////////////////////////////////////////////////////
    @Override
    public void onStickerSelected(StickerView sticker) {
        stickerCanvas.addSticker(sticker);
        if (bottomSheetBehavior != null) {
            if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
        }
    }
}
