package psb.com.kidpaint.home;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import me.itangqi.waveloadingview.WaveLoadingView;
import psb.com.cview.CButton;
import psb.com.kidpaint.App;
import psb.com.kidpaint.R;
import psb.com.kidpaint.competition.ActivityCompetition;
import psb.com.kidpaint.home.history.HistoryFragment;
import psb.com.kidpaint.home.newPaint.NewPaintFragment;
import psb.com.kidpaint.home.splash.SplashFragment;
import psb.com.kidpaint.myMessages.ActivityMyMessages;
import psb.com.kidpaint.myPrize.ActivityMyPrize;
import psb.com.kidpaint.painting.PaintActivity;
import psb.com.kidpaint.score.DialogScorePackage;
import psb.com.kidpaint.user.edit.ActivityEditProfile;
import psb.com.kidpaint.user.register.ActivityRegisterUser;
import psb.com.kidpaint.utils.IntroEnum;
import psb.com.kidpaint.utils.UserProfile;
import psb.com.kidpaint.utils.Value;
import psb.com.kidpaint.utils.customView.BaseActivity;
import psb.com.kidpaint.utils.customView.dialog.CDialog;
import psb.com.kidpaint.utils.customView.dialog.MessageDialog;
import psb.com.kidpaint.utils.customView.intro.Intro;
import psb.com.kidpaint.utils.customView.intro.IntroPosition;
import psb.com.kidpaint.utils.customView.intro.showCase.FancyShowCaseQueue;
import psb.com.kidpaint.utils.customView.intro.showCase.FancyShowCaseView;
import psb.com.kidpaint.utils.customView.intro.showCase.OnCompleteListener;
import psb.com.kidpaint.utils.musicHelper.MusicHelper;
import psb.com.kidpaint.utils.soundHelper.SoundHelper;
import psb.com.kidpaint.utils.toolbarHandler.ToolbarHandler;
import psb.com.kidpaint.webApi.offerPackage.Get.model.ResponseGetOfferPackage;
import psb.com.kidpaint.webApi.paint.getLeaderShip.model.ResponseGetLeaderShip;
import psb.com.kidpaint.webApi.paint.getMyPaints.model.ResponseGetMyPaints;
import psb.com.kidpaint.webApi.paint.postPaint.model.ResponsePostPaint;
import psb.com.kidpaint.webApi.prize.Get.model.ResponsePrize;
import psb.com.kidpaint.webApi.prize.PrizeRequest.model.ParamsPrizeRequest;
import psb.com.kidpaint.webApi.prize.getDailyPrize.model.ResponseGetDailyPrize;
import psb.com.kidpaint.webApi.userScore.addScore.model.ResponseAddScore;

public class HomeActivity extends BaseActivity implements IV_Home,
        HistoryFragment.OnFragmentInteractionListener,
        NewPaintFragment.OnFragmentInteractionListener,
        SplashFragment.OnFragmentInteractionListener {

    public static int CODE_REGISTER_First = 106;
    public static int CODE_REGISTER = 107;
    public static int CODE_Competition = 108;
    public static int CODE_EDIT = 108;
    public static int CODE_PAINT_ACTIVITY = 109;
    private CButton btnNewPainting;
    private CButton btnHistory;
    private ImageView drawerIcon, btn_settings;

    private final String TAG_FRAGMENT_HISTORY = "TAG_FRAGMENT_HISTORY";
    private final String TAG_FRAGMENT_NEW_PAINTING = "TAG_FRAGMENT_NEW_PAINTING";
    private final String TAG_FRAGMENT_SPLASH = "TAG_FRAGMENT_SPLASH";

    private DrawerLayout drawer;
    private NavigationView navigationView;
    private RelativeLayout myPaint, relMessage, relMyPrize, relAbout;
    private TextView registerOrLogin;
    private ImageView userImage;
    private TextView editUser, logOut, unreadMessageCount;
    private boolean isFirstRegister = false;

    private ImageView img_winner_1, img_winner_2, img_winner_3;


    private UserProfile userProfile;
    private ProgressDialog progressDialog;
    private PHome pHome;

    private FrameLayout frameLayoutSplash;

    private AppBarLayout appBar;

    private ResponseGetLeaderShip responseGetLeaderShip;
    private ResponsePrize responsePrize;

    private ImageView imageViewPrizeLeft, imageViewPrizeCenter, imageViewRight;
    private TextView textViewPrizeLeftName, textViewPrizeCenterName, textViewPrizeRightName, text_user_rate;
    private TextView textViewPrizeLeftScore, textViewPrizeCenterScore, textViewPrizeRightScore;
    private WaveLoadingView waveLoadingViewLeft, waveLoadingViewCenter, waveLoadingViewRight;

    private LinearLayout relPrize;


    private Button buttonPrizeLeft, buttonPrizeCenter, buttonPrizeRight;

    private SplashFragment splashFragment;

    private int lastOffset = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        pHome = new PHome(this);
        userProfile = new UserProfile(this);

        Log.d("TAG", "onCreate token: " + userProfile.get_KEY_FCM("-"));

        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(HomeActivity.this, new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                String newToken = instanceIdResult.getToken();
                Log.e("newToken", newToken);
                userProfile.set_KEY_FCM(newToken);

            }
        });

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("لطفا کمی صبر کنید ...");

        img_winner_1 = findViewById(R.id.img_winner_1);
        img_winner_2 = findViewById(R.id.img_winner_2);
        img_winner_3 = findViewById(R.id.img_winner_3);
        text_user_rate = findViewById(R.id.text_user_rate);
        btn_settings = findViewById(R.id.btn_settings);

        splashFragment = new SplashFragment().newInstance();


        btnNewPainting = findViewById(R.id.btn_new_painting);
        frameLayoutSplash = findViewById(R.id.frameLayoutSplash);
        btnHistory = findViewById(R.id.btn_history);
        drawerIcon = findViewById(R.id.btn_more);
        appBar = findViewById(R.id.app_bar);

        appBar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {

                if (lastOffset > verticalOffset) {
                    hideLion();
                    showRooster();
                    Log.d(App.TAG, "onOffsetChanged: scrollUp");
                } else {
                    showLion();
                    hideRooster();
                    Log.d(App.TAG, "onOffsetChanged: scrollDown");
                }
                lastOffset = verticalOffset;
            }
        });

        btnNewPainting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SoundHelper.playSound(R.raw.new_paint);

                btnNewPainting.setBackgroundResource(R.drawable.img_icon_rectangle_half_selected);
                btnHistory.setBackgroundResource(R.drawable.btn_rectangle_toolbar_half);
                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new NewPaintFragment().newInstance(), TAG_FRAGMENT_NEW_PAINTING).commit();
            }
        });

        btnHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SoundHelper.playSound(R.raw.book);

                btnHistory.setBackgroundResource(R.drawable.img_icon_rectangle_half_selected);
                btnNewPainting.setBackgroundResource(R.drawable.btn_rectangle_toolbar_half);
                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new HistoryFragment(), TAG_FRAGMENT_HISTORY).commit();
            }
        });


        ToolbarHandler.makeTansluteToolbar(this, getWindow(), getWindow().getDecorView());

        Button btnCompetition = findViewById(R.id.competition);
        btnCompetition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(HomeActivity.this, ActivityCompetition.class), CODE_Competition);
            }
        });


//        set default values
        btnNewPainting.setBackgroundResource(R.drawable.img_icon_rectangle_half_selected);
        btnHistory.setBackgroundResource(R.drawable.btn_rectangle_toolbar_half);
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new NewPaintFragment().newInstance(), TAG_FRAGMENT_NEW_PAINTING).commit();

        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayoutSplash, splashFragment, TAG_FRAGMENT_SPLASH).commit();

        drawerIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawer.openDrawer(GravityCompat.END);
            }
        });

        btn_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*SoundHelper.playSound(R.raw.click_1);

                DialogSettings cDialog = new DialogSettings(HomeActivity.this);
                cDialog.show();

*/
         /*   Intent intent=new Intent(HomeActivity.this,HomeActivity.class);
               // NotificationCreator.showTextNotification(HomeActivity.this,null,1,R.drawable.icon_boy_normal,null," you a good boy");
              //  NotificationCreator.showBigTextStyleNotification(HomeActivity.this,null,2,R.drawable.icon_boy_normal,null," you a good boy","بعد از برشماری اهداف ، مجدداً بر کمک به\u200Cکشورهای در حال توسعه و کشورهای فقیر تأکید میشود و از لزوم راه اندازی پایگاه اینترنتی با هدف نشر اطلاعات هم سخن گفته میگوید ... حالا اینکه اهداف این سند می\u200Cتواند چه صدماتی به اهداف و آرزوهای جماعتی پست و حقیر وارد کند موضوعی\u200Cست که باید دربارۀ آن بدون تعصب فکر کرد ...");
              //  NotificationCreator.showBigPictureStyleNotification(HomeActivity.this,null,3,R.drawable.icon_boy_normal,null," you a good boy","http://79.175.155.143/Naghashi/Files/Paint/76420180721_114434462_image.jpg");
                NotificationCreator.showCustomNotification(HomeActivity.this,null,4,R.drawable.icon_boy_normal,null," you a good boy");
            */


           showDialogPackage();
            }
        });

        setupUserInfo();

        initAnimation();

        createHelperWnd();

    }

    void showDialogPackage(){
        DialogScorePackage cDialog = new DialogScorePackage(HomeActivity.this);
        cDialog.setShowBtnDiscardBuy(false);
        cDialog.show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        MusicHelper.stopMusic();
    }

    @Override
    protected void onResume() {
        super.onResume();
        ToolbarHandler.makeFullScreen(getWindow());
        MusicHelper.stopMusic();
        MusicHelper.playMusic(R.raw.bgr_happy_sunshine);
    }



    public void setupUserInfo() {
        Log.d("TAG", "setupUserInfo: ");
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        userImage = navigationView.findViewById(R.id.act_user_image);
        editUser = navigationView.findViewById(R.id.text_edit);
        logOut = navigationView.findViewById(R.id.act_sign_out);
        registerOrLogin = navigationView.findViewById(R.id.reg_or_login);

        relMessage = navigationView.findViewById(R.id.message);
        unreadMessageCount = navigationView.findViewById(R.id.unreadMessageCount);
        relMyPrize = navigationView.findViewById(R.id.prize_history);
        relAbout = navigationView.findViewById(R.id.about);

        imageViewPrizeLeft = navigationView.findViewById(R.id.img_prize_1);
        imageViewPrizeCenter = navigationView.findViewById(R.id.img_prize_2);
        imageViewRight = navigationView.findViewById(R.id.img_prize_3);

        textViewPrizeLeftName = navigationView.findViewById(R.id.text_prize_1_title);
        textViewPrizeCenterName = navigationView.findViewById(R.id.text_prize_2_title);
        textViewPrizeRightName = navigationView.findViewById(R.id.text_prize_3_title);

        textViewPrizeLeftScore = navigationView.findViewById(R.id.text_prize_1_point);
        textViewPrizeCenterScore = navigationView.findViewById(R.id.text_prize_2_point);
        textViewPrizeRightScore = navigationView.findViewById(R.id.text_prize_3_point);

        waveLoadingViewLeft = navigationView.findViewById(R.id.wave_prize_1);
        waveLoadingViewCenter = navigationView.findViewById(R.id.wave_prize_2);
        waveLoadingViewRight = navigationView.findViewById(R.id.wave_prize_3);
        relPrize = navigationView.findViewById(R.id.relPrize);


        buttonPrizeLeft = navigationView.findViewById(R.id.btn_prize_1);
        buttonPrizeCenter = navigationView.findViewById(R.id.btn_prize_2);
        buttonPrizeRight = navigationView.findViewById(R.id.btn_prize_3);

        waveLoadingViewLeft.setAnimDuration(2500);
        waveLoadingViewCenter.setAnimDuration(2500);
        waveLoadingViewRight.setAnimDuration(2500);

        setUnreadMessageCount();


        if (userProfile.get_KEY_PHONE_NUMBER("").isEmpty()) {
            logOut.setVisibility(View.GONE);
            relMyPrize.setVisibility(View.GONE);
            editUser.setVisibility(View.GONE);
            registerOrLogin.setText("ثبت نام | ورود");

        } else {
            editUser.setVisibility(View.VISIBLE);
            relMyPrize.setVisibility(View.VISIBLE);
            logOut.setVisibility(View.VISIBLE);
            registerOrLogin.setText(userProfile.get_KEY_FIRST_NAME("") + " " + userProfile.get_KEY_LAST_NAME(""));

        }
        if (!userProfile.get_KEY_IMG_URL("").isEmpty()) {
            Picasso.get().load(userProfile.get_KEY_IMG_URL("avatar")).placeholder(R.drawable.user_empty_gray).into(userImage, new Callback() {
                @Override
                public void onSuccess() {

                }

                @Override
                public void onError(Exception e) {
                    userImage.setImageResource(R.drawable.user_empty_gray);
                }
            });
        } else {
            Picasso.get().load(R.drawable.user_profile).placeholder(R.drawable.user_empty_gray).into(userImage, new Callback() {
                @Override
                public void onSuccess() {

                }

                @Override
                public void onError(Exception e) {
                    userImage.setImageResource(R.drawable.user_empty_gray);
                }
            });
        }


        editUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, ActivityEditProfile.class);
                startActivityForResult(intent, CODE_EDIT);
                drawer.closeDrawer(GravityCompat.END);
            }
        });

        relMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, ActivityMyMessages.class);
                startActivityForResult(intent, CODE_REGISTER);
                drawer.closeDrawer(GravityCompat.END);
            }
        });


        relMyPrize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, ActivityMyPrize.class);
                startActivityForResult(intent, CODE_REGISTER);
                drawer.closeDrawer(GravityCompat.END);
            }
        });


        registerOrLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!userProfile.get_KEY_PHONE_NUMBER("").isEmpty()) {
                    Intent intent = new Intent(HomeActivity.this, ActivityEditProfile.class);
                    startActivityForResult(intent, CODE_EDIT);
                    drawer.closeDrawer(GravityCompat.END);
                } else {
                    Intent intent = new Intent(HomeActivity.this, ActivityRegisterUser.class);
                    startActivityForResult(intent, CODE_REGISTER_First);
                }
                drawer.closeDrawer(GravityCompat.END);
            }
        });

        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawer.closeDrawer(GravityCompat.END);
                exitProfileDialog();
            }
        });
    }

    void setUnreadMessageCount() {
        if (pHome != null && unreadMessageCount != null) {
            int unread = pHome.getUnreadMessageCount();
            unreadMessageCount.setText(unread + "");
            unreadMessageCount.setVisibility(unread > 0 ? View.VISIBLE : View.GONE);
        }
    }
    @Override
    public void onOutlineSelected(int resId) {
        Intent intent = new Intent(HomeActivity.this, PaintActivity.class);
        intent.putExtra(PaintActivity.KEY_RESOURCE_OUTLINE, resId);
        startActivityForResult(intent, CODE_PAINT_ACTIVITY);
    }

    @Override
    public void onNewPaintSelected() {

    }

    @Override
    public void onStartAddScore() {

    }

    @Override
    public void onSuccessAddScore(ResponseAddScore responseAddScore) {

    }

    @Override
    public void onFailedAddScore(int errorCode, String errorMessage) {

    }

    @Override
    public void onSuccessDelete(int position) {

    }

    @Override
    public void onFailedDelete(String errorMessage) {

    }


    @Override
    public void onFragmentAttached(Fragment fragment) {
        if (fragment instanceof NewPaintFragment) {

        }
    }


    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void onStartLogout() {
        progressDialog.show();
    }//

    @Override
    public void onLogoutSuccess() {
        progressDialog.cancel();
        // finish();
        setupUserInfo();

        text_user_rate.setText("برای شرکت در رقابت ها ثبت نام کنید");
        text_user_rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(HomeActivity.this, ActivityRegisterUser.class), CODE_REGISTER_First);
            }
        });


    }

    @Override
    public void onLogoutFailed(String errorMessage) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
        progressDialog.dismiss();
    }

    @Override
    public void startGetPrizeRequest() {

    }

    @Override
    public void prizeRequestSuccess(int score) {
        responseGetLeaderShip.getExtra().setMyScore(score);
        setPrizeLayout();
        final MessageDialog dialog = new MessageDialog(getContext());

        dialog.setMessage(getString(R.string.prizedeliveryContact));
        dialog.setOnCLickListener(new CDialog.OnCLickListener() {
            @Override
            public void onPosetiveClicked() {
                //dialog.cancel();
            }

            @Override
            public void onNegativeClicked() {
                //dialog.cancel();
            }
        });
        dialog.setAcceptButtonMessage(getContext().getString(R.string.confirm));
        dialog.setTitle(getString(R.string.prizeRegistered));

        dialog.show();
    }

    @Override
    public void prizeRequestFailed(String msg) {

    }

    @Override
    public void onStartMyPaintHistory() {

    }

    @Override
    public void onGetMyPaintHistorySuccess() {

    }

    @Override
    public void onGetMyPaintHistoryFailed() {

    }

    @Override
    public void onSelecteditem(String filePath) {

    }

    @Override
    public void onStartPostPaint() {

    }

    @Override
    public void onSuccessPostPaint(ResponsePostPaint responsePostPaint) {

    }

    @Override
    public void onFailedPostPaint(int errorCode, String errorMessage) {

    }

    @Override
    public void showUserRegisterDialog(int position) {

    }

    @Override
    public void showDeleteDialog(int position) {

    }

    public void exitProfileDialog() {

        ////////////////
        final MessageDialog dialog = new MessageDialog(getContext());

        dialog.setMessage("آیا میخواهید از حساب کاربری خود خارج شوید؟");
        dialog.setOnCLickListener(new CDialog.OnCLickListener() {
            @Override
            public void onPosetiveClicked() {
                pHome.onStartLogout();
            }

            @Override
            public void onNegativeClicked() {
                //dialog.cancel();
            }
        });
        dialog.setAcceptButtonMessage(getContext().getString(R.string.yes));
        dialog.setTitle("خروج");

        dialog.show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
      /*  if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
        }*/
      refreshUserRank();
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("TAG", "onActivityResult home: " + requestCode);
        if (requestCode == CODE_REGISTER_First) {
            if (resultCode == Activity.RESULT_OK) {
                setupUserInfo();
                if (data.hasExtra("First")) {
                    isFirstRegister = data.getIntExtra("First", 1) == 0 ? true : false;
                }

                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayoutSplash, splashFragment, TAG_FRAGMENT_SPLASH).commit();


            } else if (resultCode == Activity.RESULT_CANCELED) {
                //finish();
            }
        }
        if (requestCode == CODE_REGISTER) {
            setUnreadMessageCount();
            if (resultCode == Activity.RESULT_OK) {
                setupUserInfo();
                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayoutSplash, splashFragment, TAG_FRAGMENT_SPLASH).commit();

            } else if (resultCode == Activity.RESULT_CANCELED) {
                //finish();
            }
        } else if (requestCode == CODE_EDIT) {
            if (resultCode == Activity.RESULT_OK) {
                setupUserInfo();
                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayoutSplash, splashFragment, TAG_FRAGMENT_SPLASH).commit();

            } else if (resultCode == Activity.RESULT_CANCELED) {
            }
        } else if (requestCode == CODE_Competition) {
            if (resultCode == Activity.RESULT_OK) {
                setupUserInfo();
                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayoutSplash, splashFragment, TAG_FRAGMENT_SPLASH).commit();

            } else if (resultCode == Activity.RESULT_CANCELED) {
            }
        } else if (requestCode == CODE_PAINT_ACTIVITY) {
            if (resultCode == Activity.RESULT_OK) {
                setupUserInfo();
                btnHistory.setBackgroundResource(R.drawable.img_icon_rectangle_half_selected);
                btnNewPainting.setBackgroundResource(R.drawable.btn_rectangle_toolbar_half);
                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new HistoryFragment(), TAG_FRAGMENT_HISTORY).commit();

            } else if (resultCode == Activity.RESULT_CANCELED) {
            }
        }
    }


    ///////////////////////////////////////////////////////////////////////////
    // splashFragment
    ///////////////////////////////////////////////////////////////////////////

    @Override
    public void startGetStickers() {

    }

    @Override
    public void splashSuccess() {
        frameLayoutSplash.setVisibility(View.GONE);
        getSupportFragmentManager().beginTransaction().remove(getSupportFragmentManager().findFragmentByTag(TAG_FRAGMENT_SPLASH)).commit();
        showIntro();
        if (isFirstRegister) {
            showIntroNewUser();
        }
    }

    @Override
    public void splashFailed(String msg) {
        frameLayoutSplash.setVisibility(View.GONE);
        getSupportFragmentManager().beginTransaction().remove(getSupportFragmentManager().findFragmentByTag(TAG_FRAGMENT_SPLASH)).commit();
        showIntro();
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void getRankStarted() {

    }

    @Override
    public void getRankSuccess(ResponseGetLeaderShip responseGetLeaderShip) {
        this.responseGetLeaderShip = responseGetLeaderShip;
        setWinners();
    }


    @Override
    public void getRankFailed() {

    }

    @Override
    public void onStartGetMyPaints() {

    }

    @Override
    public void getMyPaintsSuccess() {

    }

    @Override
    public void getMyPaintsFailed(int errorCode,String errorMessage) {

    }

    @Override
    public void setResponseOfferPackage(ResponseGetOfferPackage responseOfferPackage) {

    }

    @Override
    public void setResponseDailyPrize(ResponseGetDailyPrize responseGetDailyPrize) {

    }

    @Override
    public void setResponseMyPaints(ResponseGetMyPaints responseMyPaints) {

    }


    ///////////////////////////////////////////////////////////////////////////
    // Activity methods
    ///////////////////////////////////////////////////////////////////////////
    private void showIntro() {
        final View view = findViewById(R.id.intro_view_1);
        final View view2 = findViewById(R.id.intro_view_2);
        FancyShowCaseView fancyShowCaseView = Intro.addIntroTo(this, view, IntroEnum.getLayoutId(1), IntroPosition.TOP, IntroEnum.getSoundId(1), IntroEnum.getShareId(1), null, null);
        FancyShowCaseView fancyShowCaseView_2 = Intro.addIntroTo(this, view2, IntroEnum.getLayoutId(2), IntroPosition.TOP, IntroEnum.getSoundId(2), IntroEnum.getShareId(2), null, null);


        FancyShowCaseQueue fancyShowCaseQueue = new FancyShowCaseQueue();
        fancyShowCaseQueue.add(fancyShowCaseView);
        fancyShowCaseQueue.add(fancyShowCaseView_2);
        fancyShowCaseQueue.setCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete() {
                Log.d("TAG", "onComplete: ");
            }
        });
        fancyShowCaseQueue.show();
    }

    private void showIntroNewUser() {
        drawer.openDrawer(GravityCompat.END);

        final View view = findViewById(R.id.prizeHint);

        view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                FancyShowCaseView fancyShowCaseView = Intro.addIntroTo(HomeActivity.this, view, IntroEnum.getLayoutId(14), IntroPosition.TOP, IntroEnum.getSoundId(14), IntroEnum.getShareId(14), null, null);


                FancyShowCaseQueue fancyShowCaseQueue = new FancyShowCaseQueue();
                fancyShowCaseQueue.add(fancyShowCaseView);
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

    private void setPrizes() {
        if (responsePrize != null && responsePrize.getExtra() != null && responsePrize.getExtra().size() > 0) {

            if (!responsePrize.getExtra().get(0).getImageUrl().isEmpty()) {
                Picasso.get().load(responsePrize.getExtra().get(0).getImageUrl()).into(imageViewPrizeLeft);
                Picasso.get().load(responsePrize.getExtra().get(1).getImageUrl()).into(imageViewPrizeCenter);
                Picasso.get().load(responsePrize.getExtra().get(2).getImageUrl()).into(imageViewRight);


                textViewPrizeLeftName.setText(responsePrize.getExtra().get(0).getTitle());
                textViewPrizeCenterName.setText(responsePrize.getExtra().get(1).getTitle());
                textViewPrizeRightName.setText(responsePrize.getExtra().get(2).getTitle());

                textViewPrizeLeftScore.setText(responsePrize.getExtra().get(0).getNeedScore() + " " + getString(R.string.point));
                textViewPrizeCenterScore.setText(responsePrize.getExtra().get(1).getNeedScore() + " " + getString(R.string.point));
                textViewPrizeRightScore.setText(responsePrize.getExtra().get(2).getNeedScore() + " " + getString(R.string.point));

                if (responseGetLeaderShip != null) {
                    setPrizeLayout();
                }
            } else {
                relPrize.setVisibility(View.GONE);

            }
        } else {
            relPrize.setVisibility(View.GONE);

        }
    }

    void setWinners() {
        if (responseGetLeaderShip != null) {
            if (responseGetLeaderShip.getExtra().getLeaderModel().size() > 0) {

                if (responseGetLeaderShip.getExtra().getLeaderModel().get(0).getUser().getImageUrl() != null && !responseGetLeaderShip.getExtra().getLeaderModel().get(0).getUser().getImageUrl().isEmpty()) {
                    Picasso
                            .get()
                            .load(responseGetLeaderShip.getExtra().getLeaderModel().get(0).getUser().getImageUrl())
                            .resize(Value.dp(200), Value.dp(200))
                            .onlyScaleDown()
                            .into(img_winner_1);
                }

                if (responseGetLeaderShip.getExtra().getLeaderModel().get(1).getUser().getImageUrl() != null && !responseGetLeaderShip.getExtra().getLeaderModel().get(1).getUser().getImageUrl().isEmpty()) {
                    Picasso
                            .get()
                            .load(responseGetLeaderShip.getExtra().getLeaderModel().get(1).getUser().getImageUrl())
                            .resize(Value.dp(200), Value.dp(200))
                            .onlyScaleDown()
                            .into(img_winner_2);
                }
                if (responseGetLeaderShip.getExtra().getLeaderModel().get(2).getUser().getImageUrl() != null && !responseGetLeaderShip.getExtra().getLeaderModel().get(2).getUser().getImageUrl().isEmpty()) {
                    Picasso
                            .get()
                            .load(responseGetLeaderShip.getExtra().getLeaderModel().get(2).getUser().getImageUrl())
                            .resize(Value.dp(200), Value.dp(200))
                            .onlyScaleDown()
                            .into(img_winner_3);
                }
            }

            if (responseGetLeaderShip.getExtra().getMyRank() != null) {
                text_user_rate.setText("بهترین رتبه شما " + responseGetLeaderShip.getExtra().getMyRank().getRank());
                text_user_rate.setOnClickListener(null);

            } else {
                if (userProfile.get_KEY_PHONE_NUMBER("").isEmpty()) {
                    text_user_rate.setText("برای شرکت در رقابت ها ثبت نام کنید");
                    text_user_rate.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            startActivityForResult(new Intent(HomeActivity.this, ActivityRegisterUser.class), CODE_REGISTER_First);
                        }
                    });

                } else {
                    text_user_rate.setText("شما در رقابت ها شرکت نکرده اید");
                    text_user_rate.setOnClickListener(null);
                }
            }
        }


        if (responsePrize != null && responsePrize.getExtra() != null && responsePrize.getExtra().size() > 0) {
            if (!responsePrize.getExtra().get(0).getImageUrl().isEmpty()) {
                setPrizeLayout();

            } else {
                relPrize.setVisibility(View.GONE);
            }
        } else {
            relPrize.setVisibility(View.GONE);
        }
    }

    int enableColor = R.color.green_2;

    private void setPrizeLayout() {
        waveLoadingViewLeft.setProgressValue((responseGetLeaderShip.getExtra().getMyScore() * 100) / responsePrize.getExtra().get(0).getNeedScore());
        if (waveLoadingViewLeft.getProgressValue() >= 100) {
            waveLoadingViewLeft.setProgressValue(100);
            waveLoadingViewLeft.setWaveColor(getResources().getColor(enableColor));
            waveLoadingViewLeft.setBorderColor(getResources().getColor(enableColor));
            textViewPrizeLeftName.setTextColor(getResources().getColor(enableColor));
            textViewPrizeLeftScore.setTextColor(getResources().getColor(enableColor));
        } else {
            waveLoadingViewLeft.setWaveColor(getResources().getColor(R.color.md_grey_400));
            waveLoadingViewLeft.setBorderColor(getResources().getColor(R.color.md_grey_400));
            waveLoadingViewLeft.setProgressValue((responseGetLeaderShip.getExtra().getMyScore() * 100) / responsePrize.getExtra().get(0).getNeedScore());
            textViewPrizeLeftName.setTextColor(getResources().getColor(R.color.md_grey_400));
            textViewPrizeLeftScore.setTextColor(getResources().getColor(R.color.md_grey_400));
        }


        waveLoadingViewCenter.setProgressValue((responseGetLeaderShip.getExtra().getMyScore() * 100) / responsePrize.getExtra().get(1).getNeedScore());
        if (waveLoadingViewCenter.getProgressValue() >= 100) {
            waveLoadingViewCenter.setProgressValue(100);
            waveLoadingViewCenter.setWaveColor(getResources().getColor(enableColor));
            waveLoadingViewCenter.setBorderColor(getResources().getColor(enableColor));
            textViewPrizeCenterName.setTextColor(getResources().getColor(enableColor));
            textViewPrizeCenterScore.setTextColor(getResources().getColor(enableColor));
        } else {
            waveLoadingViewCenter.setWaveColor(getResources().getColor(R.color.md_grey_400));
            waveLoadingViewCenter.setBorderColor(getResources().getColor(R.color.md_grey_400));
            waveLoadingViewCenter.setProgressValue((responseGetLeaderShip.getExtra().getMyScore() * 100) / responsePrize.getExtra().get(1).getNeedScore());
            textViewPrizeCenterName.setTextColor(getResources().getColor(R.color.md_grey_400));
            textViewPrizeCenterScore.setTextColor(getResources().getColor(R.color.md_grey_400));
        }

        waveLoadingViewRight.setProgressValue((responseGetLeaderShip.getExtra().getMyScore() * 100) / responsePrize.getExtra().get(2).getNeedScore());
        if (waveLoadingViewRight.getProgressValue() >= 100) {
            waveLoadingViewRight.setProgressValue(100);
            waveLoadingViewRight.setWaveColor(getResources().getColor(enableColor));
            waveLoadingViewRight.setBorderColor(getResources().getColor(enableColor));
            textViewPrizeRightName.setTextColor(getResources().getColor(enableColor));
            textViewPrizeRightScore.setTextColor(getResources().getColor(enableColor));
        } else {
            waveLoadingViewRight.setWaveColor(getResources().getColor(R.color.md_grey_400));
            waveLoadingViewRight.setBorderColor(getResources().getColor(R.color.md_grey_400));
            waveLoadingViewRight.setProgressValue((responseGetLeaderShip.getExtra().getMyScore() * 100) / responsePrize.getExtra().get(2).getNeedScore());
            textViewPrizeRightName.setTextColor(getResources().getColor(R.color.md_grey_400));
            textViewPrizeRightScore.setTextColor(getResources().getColor(R.color.md_grey_400));
        }

        setPrizeButtonContent();
        relPrize.setVisibility(View.VISIBLE);
    }

    private void setPrizeButtonContent() {
        buttonPrizeLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isRegistered()) {
                    if (responseGetLeaderShip.getExtra().getMyScore() >= responsePrize.getExtra().get(0).getNeedScore()) {
                        dialogGivePrize(responsePrize.getExtra().get(0).getId());
                    } else {
                        setPrizeDialogLowScore(responsePrize.getExtra().get(0).getNeedScore() - responseGetLeaderShip.getExtra().getMyScore());
                    }
                } else {
                    dialogRegister();
                }
                drawer.closeDrawer(GravityCompat.END);
            }
        });

        buttonPrizeCenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isRegistered()) {
                    if (responseGetLeaderShip.getExtra().getMyScore() >= responsePrize.getExtra().get(1).getNeedScore()) {
                        dialogGivePrize(responsePrize.getExtra().get(1).getId());
                    } else {
                        setPrizeDialogLowScore(responsePrize.getExtra().get(1).getNeedScore() - responseGetLeaderShip.getExtra().getMyScore());
                    }
                } else {
                    dialogRegister();
                }
                drawer.closeDrawer(GravityCompat.END);
            }
        });

        buttonPrizeRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isRegistered()) {
                    if (responseGetLeaderShip.getExtra().getMyScore() >= responsePrize.getExtra().get(2).getNeedScore()) {
                        dialogGivePrize(responsePrize.getExtra().get(2).getId());
                    } else {
                        setPrizeDialogLowScore(responsePrize.getExtra().get(2).getNeedScore() - responseGetLeaderShip.getExtra().getMyScore());
                    }
                } else {
                    dialogRegister();
                }
                drawer.closeDrawer(GravityCompat.END);
            }
        });

        if (isRegistered()) {
            buttonPrizeLeft.setBackgroundResource(R.drawable.btn_rect);
            buttonPrizeCenter.setBackgroundResource(R.drawable.btn_rect);
            buttonPrizeRight.setBackgroundResource(R.drawable.btn_rect);
        } else {
            buttonPrizeLeft.setBackgroundResource(R.drawable.icon_rect_disable);
            buttonPrizeCenter.setBackgroundResource(R.drawable.icon_rect_disable);
            buttonPrizeRight.setBackgroundResource(R.drawable.icon_rect_disable);
        }

        if (responseGetLeaderShip.getExtra().getMyScore() >= responsePrize.getExtra().get(0).getNeedScore()) {
            buttonPrizeLeft.setBackgroundResource(R.drawable.btn_rect);
        } else {
            buttonPrizeLeft.setBackgroundResource(R.drawable.icon_rect_disable);
        }

        if (responseGetLeaderShip.getExtra().getMyScore() >= responsePrize.getExtra().get(1).getNeedScore()) {

            buttonPrizeCenter.setBackgroundResource(R.drawable.btn_rect);

        } else {

            buttonPrizeCenter.setBackgroundResource(R.drawable.icon_rect_disable);

        }

        if (responseGetLeaderShip.getExtra().getMyScore() >= responsePrize.getExtra().get(2).getNeedScore()) {

            buttonPrizeRight.setBackgroundResource(R.drawable.btn_rect);
        } else {
            buttonPrizeRight.setBackgroundResource(R.drawable.icon_rect_disable);
        }
    }

    private boolean isRegistered() {
        UserProfile userProfile = new UserProfile(this);
        Log.d("isRegistered", "isRegistered: " + userProfile.get_KEY_PHONE_NUMBER("-1"));
        if (!userProfile.get_KEY_PHONE_NUMBER("").equals("")) {
            return true;
        } else {
            return false;
        }
    }

    private void setPrizeDialogLowScore(int lowScore) {
        final MessageDialog dialog = new MessageDialog(getContext());

        dialog.setMessage("شما باید برای دریافت این جایزه " + lowScore + " امتیاز کم دارید!");
        dialog.setOnCLickListener(new CDialog.OnCLickListener() {
            @Override
            public void onPosetiveClicked() {
                //dialog.cancel();
            }

            @Override
            public void onNegativeClicked() {
                //dialog.cancel();
            }
        });
        dialog.setAcceptButtonMessage(getContext().getString(R.string.confirm));
        dialog.setTitle("امتیاز کافی نیست");

        dialog.show();
    }

    private void dialogGivePrize(final int prizeId) {
        final MessageDialog dialog = new MessageDialog(getContext());

        dialog.setMessage(getString(R.string.prizeRequestMsg));
        dialog.setOnCLickListener(new CDialog.OnCLickListener() {
            @Override
            public void onPosetiveClicked() {
                ParamsPrizeRequest paramsPrizeRequest = new ParamsPrizeRequest();
                paramsPrizeRequest.setMobile(userProfile.get_KEY_PHONE_NUMBER(""));
                paramsPrizeRequest.setPrizeId(prizeId);
                pHome.prizeRequest(paramsPrizeRequest);
                dialog.dismiss();
            }

            @Override
            public void onNegativeClicked() {
                dialog.cancel();
            }
        });
        dialog.setAcceptButtonMessage(getContext().getString(R.string.confirm));
        dialog.setTitle(getString(R.string.prizeGiven));

        dialog.show();
    }

    private void dialogRegister() {
        final MessageDialog dialog = new MessageDialog(getContext());

        dialog.setMessage(getString(R.string.registerBefore));
        dialog.setOnCLickListener(new CDialog.OnCLickListener() {
            @Override
            public void onPosetiveClicked() {
                dialog.cancel();
                Intent intent = new Intent(HomeActivity.this, ActivityRegisterUser.class);
                startActivityForResult(intent, CODE_REGISTER);
            }

            @Override
            public void onNegativeClicked() {
                dialog.cancel();
            }
        });
        dialog.setAcceptButtonMessage(getContext().getString(R.string.confirm));
        dialog.setTitle(getString(R.string.registerBeforeTitle));
        dialog.show();
    }

    public void initAnimation() {
        if (imageLion == null) {
            imageLion = findViewById(R.id.lion);
            imageLion.setPivotX(imageLion.getWidth() / 2);
            imageLion.setPivotY(imageLion.getHeight());
        }

        if (imageRooster == null) {
            imageRooster = findViewById(R.id.rooster);
            imageRooster.setPivotX(imageRooster.getWidth() / 2);
            imageRooster.setPivotY(imageRooster.getHeight());
        }

        imageRooster.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                hideRooster(0);
                imageRooster.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
    }


    private ImageView imageLion;
    private boolean isLionShow = true;
    private boolean isAnimationLion = false;

    private ImageView imageRooster;
    private boolean isRossterShow = true;
    private boolean isAnimationRooster = false;


    private void hideLion() {
        if (!isLionShow || isAnimationLion) return;

        ObjectAnimator animRotate = ObjectAnimator.ofFloat(imageLion, "rotation", 0.0f, -20, 0);
        ObjectAnimator animMove = ObjectAnimator.ofFloat(imageLion, "translationX", 0.0f, -(imageLion.getWidth() - imageLion.getLeft()));

        animRotate.setDuration(500); // miliseconds
        animMove.setDuration(500); // miliseconds

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(animRotate, animMove);


        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                isLionShow = false;
                isAnimationLion = false;
            }
        });
        isAnimationLion = true;
        animatorSet.start();
    }

    private void showLion() {
        if (isLionShow || isAnimationLion) return;

        ObjectAnimator animRotate = ObjectAnimator.ofFloat(imageLion, "rotation", -45, 0.0f);
        ObjectAnimator animMove = ObjectAnimator.ofFloat(imageLion, "translationX", -(imageLion.getWidth() - imageLion.getLeft()), 0.0f);

        animRotate.setDuration(500); // miliseconds
        animMove.setDuration(500); // miliseconds

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(animRotate, animMove);


        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                imageLion.setRotation(0);
                imageLion.setTranslationX(5);
                isLionShow = true;
                isAnimationLion = false;
            }
        });
        isAnimationLion = true;
        animatorSet.start();
    }

    private void hideRooster() {
        hideRooster(300);
    }

    private void hideRooster(int duration) {
        if (!isRossterShow || isAnimationRooster) return;

        ObjectAnimator animMove = ObjectAnimator.ofFloat(imageRooster, "translationY", 0.0f, imageRooster.getHeight());


        animMove.setDuration(duration); // miliseconds

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(animMove);


        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                isRossterShow = false;
                isAnimationRooster = false;
            }
        });
        isAnimationRooster = true;
        animatorSet.start();
    }

    private void showRooster() {
        if (isRossterShow || isAnimationRooster) return;

        ObjectAnimator animMove = ObjectAnimator.ofFloat(imageRooster, "translationY", imageRooster.getHeight(), 0.0f);


        animMove.setDuration(300); // miliseconds

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(animMove);


        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                isRossterShow = true;
                isAnimationRooster = false;
            }
        });
        isAnimationRooster = true;
        animatorSet.start();
    }

    @Override
    public void onBackPressed() {
        if (drawer != null && drawer.isDrawerOpen(GravityCompat.END)) {
            drawer.closeDrawer(GravityCompat.END);
        } else {
            super.onBackPressed();
        }
    }

    public void refreshUserRank() {
        if (userProfile != null) {
            text_user_rate.setText("بهترین رتبه شما " + userProfile.get_KEY_RANK(0));
            text_user_rate.setOnClickListener(null);

        } else {
            if (userProfile.get_KEY_PHONE_NUMBER("").isEmpty()) {
                text_user_rate.setText("برای شرکت در رقابت ها ثبت نام کنید");
                text_user_rate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivityForResult(new Intent(HomeActivity.this, ActivityRegisterUser.class), CODE_REGISTER_First);
                    }
                });

            } else {
                text_user_rate.setText("شما در رقابت ها شرکت نکرده اید");
                text_user_rate.setOnClickListener(null);
            }
        }
    }

    public void refreshUserPrize() {

        if (responseGetLeaderShip != null && responseGetLeaderShip.getExtra() != null) {
            responseGetLeaderShip.getExtra().setMyScore(userProfile.get_KEY_SCORE(0));

            if (responsePrize != null && responsePrize.getExtra() != null && responsePrize.getExtra().size() > 0) {
                if (!responsePrize.getExtra().get(0).getImageUrl().isEmpty()) {
                    setPrizeLayout();

                } else {
                    relPrize.setVisibility(View.GONE);
                }
            } else {
                relPrize.setVisibility(View.GONE);
            }
        } else relPrize.setVisibility(View.GONE);

    }
}
