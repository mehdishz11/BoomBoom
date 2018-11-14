package psb.com.kidpaint.painting;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
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
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import psb.com.cview.IconFont;
import psb.com.kidpaint.App;
import psb.com.kidpaint.R;
import psb.com.kidpaint.painting.bucket.BucketCanvas;
import psb.com.kidpaint.painting.canvas.sticker.StickerCanvas;
import psb.com.kidpaint.painting.palette.adapter.PaletteViewPagerAdapter;
import psb.com.kidpaint.painting.palette.color.PaintType;
import psb.com.kidpaint.painting.palette.color.PaletteFragment;
import psb.com.kidpaint.painting.palette.sticker.StickerFragment;
import psb.com.kidpaint.score.ActivityScorePackage;
import psb.com.kidpaint.user.register.ActivityRegisterUser;
import psb.com.kidpaint.utils.IntroEnum;
import psb.com.kidpaint.utils.UserProfile;
import psb.com.kidpaint.utils.Utils;
import psb.com.kidpaint.utils.Value;
import psb.com.kidpaint.utils.checkInternet.NetworkUtil;
import psb.com.kidpaint.utils.customView.BaseActivity;
import psb.com.kidpaint.utils.customView.dialog.CDialog;
import psb.com.kidpaint.utils.customView.dialog.DialogSettings;
import psb.com.kidpaint.utils.customView.dialog.MessageDialog;
import psb.com.kidpaint.utils.customView.intro.Intro;
import psb.com.kidpaint.utils.customView.intro.IntroPosition;
import psb.com.kidpaint.utils.customView.intro.showCase.DismissListener;
import psb.com.kidpaint.utils.customView.intro.showCase.FancyShowCaseQueue;
import psb.com.kidpaint.utils.customView.intro.showCase.FancyShowCaseView;
import psb.com.kidpaint.utils.customView.intro.showCase.OnCompleteListener;
import psb.com.kidpaint.utils.customView.intro.showCase.OnShowListener;
import psb.com.kidpaint.utils.customView.paintingBucket.QueueLinearFloodFiller;
import psb.com.kidpaint.utils.customView.stickerview.StickerView;
import psb.com.kidpaint.utils.musicHelper.MusicHelper;
import psb.com.kidpaint.utils.musicHelper.SingleMusicPlayer;
import psb.com.kidpaint.utils.sharePrefrence.SharePrefrenceHelper;
import psb.com.kidpaint.utils.soundHelper.SoundHelper;
import psb.com.kidpaint.webApi.userScore.buySticker.model.ResponseBuySticker;
import psb.com.paintingview.BucketModel;
import psb.com.paintingview.DrawView;

import static android.view.View.LAYER_TYPE_HARDWARE;


public class PaintActivity extends BaseActivity implements
        PaletteFragment.OnFragmentInteractionListener,
        StickerFragment.OnFragmentInteractionListener,
        BucketCanvas.OnBucketPointSelected,
        StickerCanvas.OnStickerListener, IVPaint {

    private int REQUEST_STORAGE_PERMISSIONS = 100;
    private static final int REQUEST_CODE_REGISTER_STICKER = 120;
    private static final int REQUEST_CODE_REGISTER_BTN_SAVE = 121;
    private static final int REQUEST_CODE_SCORE_PACKAGE = 122;
    private int REQUEST_SHOP=26;


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
    private TextView coinCount;

    private StickerCanvas stickerCanvas;
    private DrawView paintCanvas;

    private RelativeLayout relHandle;

    private BottomSheetBehavior bottomSheetBehavior;
    private UserProfile userProfile;
    private int localCoinCount = 0;
    private int localUsedCoinCount = 0;
    private boolean SaveWithWaterMark = false;
    private String sendShowMode="";

    private IconFont iconAddCoin;
    private RelativeLayout rel_user_coin;


    public static final String KEY_RESOURCE_OUTLINE = "KEY_RESOURCE_OUTLINE";
    private int outlineResource = 0;
    private String editPath;

    private ImageView imageHistory;

    private BucketCanvas bucketCanvas;
    private PPaint pPaint;

    private ProgressDialog progressDialog;

    private boolean isFirstRun=true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_paint);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("لطفا کمی صبر کنید ...");
        progressDialog.setCancelable(false);

        Log.d(App.TAG, "onCreate: onCreate");
        editPath = getIntent().getStringExtra("Path");

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);


        outlineResource = getIntent().getIntExtra(KEY_RESOURCE_OUTLINE, 0);
        userProfile = new UserProfile(PaintActivity.this);

        localCoinCount = userProfile.get_KEY_SCORE(0);
        stackViews();
        initView();
        showIntro();

        createHelperWnd();

        pPaint = new PPaint(this);
    }

    @Override
    protected void onPause() {
        MusicHelper.stopMusic();
        Log.d(App.TAG, "onCreate: onPause");
        super.onPause();
    }

    @Override
    protected void onResume() {
        Log.d(App.TAG, "onCreate: onResume");

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

        MusicHelper.stopMusic();
        MusicHelper.playMusic(R.raw.bgr_be_happy);

        super.onResume();
    }

    @Override
    public void onBackPressed() {

        if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        } else {
            MessageDialog dialog = new MessageDialog(PaintActivity.this);
            dialog.setMessage(getString(R.string.message_are_u_sure_exit));
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
    }

    private void initView() {

        btnMore = findViewById(R.id.btn_more);
        coinCount = findViewById(R.id.coinCount);
        stickerCanvas = findViewById(R.id.sticker_canvas);
        paintCanvas = findViewById(R.id.paint_canvas);
        bucketCanvas = findViewById(R.id.bucket_canvas);
        btnLeft = findViewById(R.id.btn_left);
        btnRight = findViewById(R.id.btn_right);
        relHandle = findViewById(R.id.rel_handle);
        mPager = findViewById(R.id.view_pager);
        imageHistory = findViewById(R.id.image_history);
        rel_user_coin = findViewById(R.id.rel_user_coin);
        iconAddCoin = findViewById(R.id.icon_add_coin);

        btnCancel = findViewById(R.id.btn_cancel);
        btnSave = findViewById(R.id.btn_save);
        btnUndo = findViewById(R.id.btn_undo);
        btnSettings = findViewById(R.id.btn_settings);

        coinCount.setText(localCoinCount + "");

        iconAddCoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SoundHelper.playSound(R.raw.click_1);

                if (userProfile.get_KEY_PHONE_NUMBER("").isEmpty()) {
                    showRegisterDialog(getString(R.string.msg_login_for_add_coin),REQUEST_SHOP);

                }else {
                    showDialogPackage("", "Continue");
                }

            }
        });


        rel_user_coin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SoundHelper.playSound(R.raw.click_1);

                if (userProfile.get_KEY_PHONE_NUMBER("").isEmpty()) {
                    showRegisterDialog(getString(R.string.msg_login_for_add_coin),REQUEST_SHOP);

                }else {
                    showDialogPackage("", "Continue");
                }

            }
        });

        stickerCanvas.setOnStickerListener(this);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                animatePopRightCollapse();

                MessageDialog dialog = new MessageDialog(PaintActivity.this);
                dialog.setMessage(getString(R.string.message_are_u_sure_exit));
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

                if ("Available".equals(NetworkUtil.getConnectivityStatusString(PaintActivity.this))) {
                    if (userProfile.get_KEY_PHONE_NUMBER("").isEmpty()) {
                        showUserRegisterDialog("برای ذخیره نقاشی باید ثبت نام کنید یا وارد شوید!",REQUEST_CODE_REGISTER_BTN_SAVE);

                    }else {
                    validateUsedCoinWithTotalCoin();
                    }
                } else {
                    showDialogNoInternet();
                }


            }
        });
        btnUndo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SoundHelper.playSound(R.raw.back);
                paintCanvas.undo();
            }
        });

        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SoundHelper.playSound(R.raw.setting);
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

        } else {
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

        if (editPath != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(editPath);
            imageHistory.setImageBitmap(bitmap);
        }


        onPaintTypeSelected(PaintType.PENCIL);

    }

    private void saveFinalPaint(String mode) {

        stickerCanvas.hideShowController(false);

        SaveWithWaterMark = "SaveWithWaterMark".equals(mode);
        SoundHelper.playSound(R.raw.save);
        if (Utils.gstoragePermissionIsGranted(PaintActivity.this)) {
            saveTempBitmap(getPaintCanvasBitmap(SaveWithWaterMark));

        } else {
            requestForGrantStoragePermission();
        }
    }

    private void validateUsedCoinWithTotalCoin() {

        if (localCoinCount >= 0) {

            if (localUsedCoinCount > 0) {
                //  send Used Coin to server

                pPaint.doBuySticker(localUsedCoinCount);
            } else {
                //  save image
                saveFinalPaint("SaveWithOutWaterMark");
            }
        } else {

            //   show dialog score
            String mess = "شما به تعداد " + Math.abs(localCoinCount) + " کم دارید ";
            showDialogPackage(mess, "SaveWithWaterMark");

        }

    }

    private void showDialogNoInternet() {
        MessageDialog dialog = new MessageDialog(PaintActivity.this);
        dialog.setMessage(getString(R.string.error_network_connection));
        dialog.setOnCLickListener(new CDialog.OnCLickListener() {
            @Override
            public void onPosetiveClicked() {
            }

            @Override
            public void onNegativeClicked() {

            }
        });
        //dialog.setSoundId(R.raw.are_you_sure_exit);
        dialog.setAcceptButtonMessage(PaintActivity.this.getString(R.string.confirm));
        dialog.setTitle(getString(R.string.internet));
        dialog.show();

        dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
    }

    void showDialogPackage(final String message, final String showMode) {
        sendShowMode=showMode;
        Intent intent=new Intent(PaintActivity.this, ActivityScorePackage.class);
        intent.putExtra("dialogMessage",message);
        intent.putExtra("dialogMode",showMode);
        intent.putExtra("showBtnDiscardBuy",true);
        startActivityForResult(intent,REQUEST_CODE_SCORE_PACKAGE);

/*
        DialogScorePackage cDialog = new DialogScorePackage(PaintActivity.this);
        cDialog.setDialogMessage(message);
        cDialog.setDialogMode(showMode);
        cDialog.setShowBtnDiscardBuy(true);
        cDialog.setScorePackageDiscardBtnListener(new DialogScorePackage.ScorePackageDiscardBtnListener() {
            @Override
            public void btnDiscardBuySelect(String mode) {
                switch (mode) {
                    case "SaveWithWaterMark":
                        Log.d("TAG", "btnDiscardBuySelect SaveWithWaterMark: ");
                        saveFinalPaint("SaveWithWaterMark");

                        break;
                    case "Continue":
                        break;
                }
            }

            @Override
            public void onSuccessBuyScorePackage(int totalCoin) {
                localCoinCount = totalCoin - localUsedCoinCount;
                coinCount.setText(localCoinCount + "");
                switch (showMode) {
                    case "SaveWithWaterMark":
                        Log.d("TAG", "btnDiscardBuySelect SaveWithWaterMark: ");
                        saveFinalPaint("SaveWithOutWaterMark");

                        break;
                    case "Continue":
                        break;
                }
            }

            @Override
            public void onFailedBuyScorePackage() {
                //btnSave.performClick();
            }
        });
        cDialog.show();*/
    }

    ///////////////////////////////////////////////////////////////////////////
    // show intro
    ///////////////////////////////////////////////////////////////////////////
    private void showIntro() {
        final View view = findViewById(R.id.introViewCenter);
        final View view2 = findViewById(R.id.introViewRight);
        final View view3 = findViewById(R.id.introViewMore);
        view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                FancyShowCaseView fancyShowCaseView = Intro.addIntroTo(PaintActivity.this, view, IntroEnum.getLayoutId(3), IntroPosition.TOP, IntroEnum.getSoundId(3), IntroEnum.getShareId(3), null, new OnShowListener() {
                    @Override
                    public void onShow() {
                        relHandle.performClick();

                    }
                });
                FancyShowCaseView fancyShowCaseView_2 = Intro.addIntroTo(PaintActivity.this, view2, IntroEnum.getLayoutId(4), IntroPosition.TOP, IntroEnum.getSoundId(4), IntroEnum.getShareId(4), new DismissListener() {
                    @Override
                    public void onDismiss(String id) {
                        btnRight.performClick();
                    }

                    @Override
                    public void onSkipped(String id) {

                    }
                }, null);
                FancyShowCaseView fancyShowCaseView_3 = Intro.addIntroTo(PaintActivity.this, view3, IntroEnum.getLayoutId(5), IntroPosition.RIGHT, IntroEnum.getSoundId(5), IntroEnum.getShareId(5), new DismissListener() {
                    @Override
                    public void onDismiss(String id) {
                        btnMore.performClick();
                    }

                    @Override
                    public void onSkipped(String id) {

                    }
                }, null);

                FancyShowCaseQueue fancyShowCaseQueue = new FancyShowCaseQueue();
                fancyShowCaseQueue.add(fancyShowCaseView);
                fancyShowCaseQueue.add(fancyShowCaseView_2);
                fancyShowCaseQueue.add(fancyShowCaseView_3);
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
    private Bitmap getPaintCanvasBitmap(boolean SaveWithWaterMark) {
        Log.d("TAG", "getPaintCanvasBitmap: " + SaveWithWaterMark);
        RelativeLayout relPaint = findViewById(R.id.rel_drawing);
        relPaint.setDrawingCacheEnabled(true);
        relPaint.buildDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(relPaint.getDrawingCache());
        relPaint.setDrawingCacheEnabled(false);


        if (SaveWithWaterMark) {
            Bitmap bitmapWaterMark = BitmapFactory.decodeResource(getResources(),
                    R.drawable.coin);

            Canvas c = new Canvas(bitmap);
            c.drawBitmap(bitmapWaterMark, 0, 0, new Paint());
        }

        return bitmap;

    }

    void saveTempBitmap(Bitmap bitmap) {
        if (progressDialog != null && !progressDialog.isShowing()) {
            progressDialog.show();
        }
        if (isExternalStorageWritable()) {
            saveImage(bitmap);
        } else {
            //prompt the user or do something
        }
    }

    void saveImage(Bitmap finalBitmap) {

        String folder_main = "kidPaint";
        File mydir = new File(Environment.getExternalStorageDirectory(), folder_main);
        if (!mydir.exists()) {
            mydir.mkdirs();
        }

        boolean no= Utils.writeNoMediaFile(mydir);
        Log.d("TAG", "saveImage: "+no);
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String fname = "paint_" + timeStamp + ".jpg";
        File file = new File(mydir, fname);

        if (editPath != null) {
            fname = editPath;
            file = new File(fname);
        }

        if (file.exists()) file.delete();

        try {
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.cancel();
            }
            Intent intent = new Intent();
            setResult(Activity.RESULT_OK, intent);
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
            if(!isFirstRun)SoundHelper.playSound(R.raw.sf_eraser);
            isFirstRun=false;
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
            if(!isFirstRun)SoundHelper.playSound(R.raw.paint_bucket);
            isFirstRun=false;
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
            if(!isFirstRun)SoundHelper.playSound(R.raw.pencil);
            isFirstRun=false;
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
                saveTempBitmap(getPaintCanvasBitmap(SaveWithWaterMark));

            } else {

            }

        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // on sticker selected
    ///////////////////////////////////////////////////////////////////////////
    @Override
    public void onStickerSelected(StickerView sticker) {

        if (userProfile.get_KEY_PHONE_NUMBER("").isEmpty()) {
            showUserRegisterDialog("برای استفاده از استیکر ها باید ثبت نام کنید یا وارد شوید!",REQUEST_CODE_REGISTER_STICKER);

        }else  {
            stickerCanvas.hideShowController(false);
            stickerCanvas.addSticker(sticker);

            localCoinCount -= sticker.getStickerPrice();
            localUsedCoinCount += sticker.getStickerPrice();
            coinCount.setText(localCoinCount + "");

            if (localCoinCount < 0 && !userProfile.get_KEY_SHOW_FIRST_SCORE_PACKAGE_IN_PAINT_ACTIVITY(false)) {
                userProfile.set_KEY_SHOW_FIRST_SCORE_PACKAGE_IN_PAINT_ACTIVITY(true);
                String mess = "تعداد سکه های شما به پایان رسید";
                showDialogPackage(mess, "Continue");
            }

            new SingleMusicPlayer().playSingleMedia(sticker.getStickerSound());

            if (bottomSheetBehavior != null) {
                if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }
            }
        }
    }

    @Override
    public void onStickerRemoved(StickerView stickerView) {
        localCoinCount += stickerView.getStickerPrice();
        localUsedCoinCount -= stickerView.getStickerPrice();
        coinCount.setText(localCoinCount + "");
        Log.d("TAG", "onStickerRemoved: " + stickerView.getStickerPrice());

    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void startBuySticker() {
        progressDialog.show();
    }

    @Override
    public void onSuccessBuySticker(ResponseBuySticker responseBuySticker) {
        saveFinalPaint("");
    }

    @Override
    public void onFailedBuySticker(int errorCode, String errorMessage) {
        progressDialog.cancel();

        Toast.makeText(this, errorCode, Toast.LENGTH_SHORT).show();
    }


    public void showUserRegisterDialog(String message, final int Code) {

        final MessageDialog dialog = new MessageDialog(getContext());
        dialog.setMessage(message);
        dialog.setOnCLickListener(new CDialog.OnCLickListener() {
            @Override
            public void onPosetiveClicked() {
                startActivityForResult(new Intent(getContext(), ActivityRegisterUser.class), Code);

                dialog.cancel();
            }

            @Override
            public void onNegativeClicked() {
                dialog.cancel();

            }
        });

        dialog.setAcceptButtonMessage(getContext().getString(R.string.enter));
        dialog.setTitle(getString(R.string.register_login));
        dialog.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_CODE_REGISTER_STICKER) {
            if (resultCode == Activity.RESULT_OK) {
                Intent intent = new Intent();
                setResult(Activity.RESULT_OK, intent);
            }else{

            }
        }else  if (requestCode == REQUEST_CODE_REGISTER_BTN_SAVE) {

            if (resultCode == Activity.RESULT_OK) {
                Intent intent = new Intent();
                setResult(Activity.RESULT_OK, intent);
                 btnSave.performClick();
            }else{

            }

        }else  if (requestCode == REQUEST_CODE_SCORE_PACKAGE) {

            if (resultCode == Activity.RESULT_OK) {
                if (data.hasExtra("dialogMode")) {
                    String dialogMode=data.getStringExtra("dialogMode");
                    switch (dialogMode) {
                        case "SaveWithWaterMark":
                            Log.d("TAG", "btnDiscardBuySelect SaveWithWaterMark: ");
                            saveFinalPaint("SaveWithWaterMark");

                            break;
                        case "Continue":
                            break;
                    }
                }

                if (data.hasExtra("SuccessBuy")) {
                    int totalCoin=data.getIntExtra("SuccessBuy",0);
                    localCoinCount = totalCoin - localUsedCoinCount;
                    coinCount.setText(localCoinCount + "");
                    switch (sendShowMode) {
                        case "SaveWithWaterMark":
                            Log.d("TAG", "btnDiscardBuySelect SaveWithWaterMark: ");
                            saveFinalPaint("SaveWithOutWaterMark");

                            break;
                        case "Continue":
                            break;
                    }
                }
            }else{

            }

        }else if (requestCode == REQUEST_SHOP && resultCode==Activity.RESULT_OK) {
            Intent intent = new Intent();
            setResult(Activity.RESULT_OK, intent);
            localCoinCount = userProfile.get_KEY_SCORE(0) - localUsedCoinCount;
            coinCount.setText(localCoinCount + "");
            showDialogPackage("", "Continue");
        }
    }

    private void showRegisterDialog(String message, final int requestCode){
        MessageDialog dialog=new MessageDialog(this);
        dialog.setTitle(getString(R.string.register_login));
        dialog.setMessage(message);
        dialog.setOnCLickListener(new CDialog.OnCLickListener() {
            @Override
            public void onPosetiveClicked() {
                if(requestCode!=-1){
                    startActivityForResult(new Intent(PaintActivity.this, ActivityRegisterUser.class), requestCode);
                }
            }

            @Override
            public void onNegativeClicked() {

            }
        });
        dialog.setAcceptButtonMessage(getString(R.string.accept));
        dialog.show();
    }

}
