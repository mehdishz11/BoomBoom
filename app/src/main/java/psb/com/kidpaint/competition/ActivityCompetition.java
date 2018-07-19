package psb.com.kidpaint.competition;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import psb.com.kidpaint.R;
import psb.com.kidpaint.competition.allPaint.FragmentAllPaints;
import psb.com.kidpaint.competition.leaderBoard.FragmentLeaderBoard;
import psb.com.kidpaint.competition.myPaints.FragmentMyPaints;
import psb.com.kidpaint.competition.score.FragmentScore;
import psb.com.kidpaint.utils.customView.ProgressView;
import psb.com.kidpaint.utils.toolbarHandler.ToolbarHandler;
import psb.com.kidpaint.webApi.paint.getAllPaints.model.ResponseGetAllPaints;
import psb.com.kidpaint.webApi.paint.getLeaderShip.model.ResponseGetLeaderShip;
import psb.com.kidpaint.webApi.paint.getMyPaints.model.ResponseGetMyPaints;
import psb.com.kidpaint.webApi.shareModel.PaintModel;


public class ActivityCompetition extends AppCompatActivity implements IVCompetition,
        FragmentMyPaints.OnFragmentInteractionListener,
        FragmentScore.OnFragmentInteractionListener,
        FragmentAllPaints.OnFragmentInteractionListener,
        FragmentLeaderBoard.OnFragmentInteractionListener {
    private static final String TAG_FRAGMENT_PAINTS = "TAG_FRAGMENT_PAINTS";
    private static final String TAG_FRAGMENT_All_PAINTS = "TAG_FRAGMENT_All_PAINTS";
    private static final String TAG_FRAGMENT_LEADER_BOARD = "TAG_FRAGMENT_LEADER_BOARD";
    private static final String TAG_FRAGMENT_SCORE = "TAG_FRAGMENT_SCORE";

    private TabLayout tabLayout;
    private ImageView back;
    private PCompetition pCompetition;
    private ProgressView progressView;

    private ResponseGetMyPaints mResponseGetMyPaints;
    private ResponseGetAllPaints mResponseGetAllPaints;
    private ResponseGetLeaderShip mResponseGetLeaderShip;

    private FrameLayout frameLayoutScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_competition);

        ToolbarHandler.setToolbarColor(this,getWindow(),getWindow().getDecorView(),R.color.blue_3,false);

        pCompetition = new PCompetition(this);
        setViewContent();

        pCompetition.onGetMyPaints();
    }

    private void changeTabsFont() {

        ViewGroup vg = (ViewGroup) tabLayout.getChildAt(0);
        int tabsCount = vg.getChildCount();
        for (int j = 0; j < tabsCount; j++) {
            ViewGroup vgTab = (ViewGroup) vg.getChildAt(j);
            int tabChildsCount = vgTab.getChildCount();
            for (int i = 0; i < tabChildsCount; i++) {
                View tabViewChild = vgTab.getChildAt(i);
                if (tabViewChild instanceof TextView) {
                    ((TextView) tabViewChild).setTypeface(Typeface.createFromAsset(getAssets(), "fonts/IRANSansMobile(FaNum)_Light.ttf"));
                }
            }
        }
    }


    void setFragment(int position) {

        if (position == 0) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new FragmentMyPaints().newInstance(mResponseGetMyPaints), TAG_FRAGMENT_PAINTS).commit();
        } else if (position == 1) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new FragmentLeaderBoard().newInstance(mResponseGetLeaderShip), TAG_FRAGMENT_LEADER_BOARD).commit();
        } else if (position == 2) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new FragmentAllPaints().newInstance(mResponseGetAllPaints), TAG_FRAGMENT_All_PAINTS).commit();
        }

    }

    private void setViewContent() {


//        back = findViewById(R.id.icon_drawer);
        progressView = findViewById(R.id.progressView);
        frameLayoutScore = findViewById(R.id.frameLayoutScore);
        frameLayoutScore.setVisibility(View.GONE);
        // UIHelper.changeToolbarFont(toolbar, this);
        tabLayout = findViewById(R.id.tabLayout);
        /*back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });*/

    }

    void setTabLayout() {

        tabLayout.addTab(tabLayout.newTab().setText("عکس های من"));
        tabLayout.addTab(tabLayout.newTab().setText("رده بندی"), true);
        tabLayout.addTab(tabLayout.newTab().setText("همه عکس ها"));
        changeTabsFont();

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                setFragment(tab.getPosition());

                LinearLayout tabLayoutL = (LinearLayout) ((ViewGroup) tabLayout.getChildAt(0)).getChildAt(tab.getPosition());
                TextView tabTextView = (TextView) tabLayoutL.getChildAt(1);
                tabTextView.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/IRANSansMobile(FaNum)_Medium.ttf"));
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                LinearLayout tabLayoutL = (LinearLayout) ((ViewGroup) tabLayout.getChildAt(0)).getChildAt(tab.getPosition());
                TextView tabTextView = (TextView) tabLayoutL.getChildAt(1);
                tabTextView.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/IRANSansMobile(FaNum)_Light.ttf"));
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        setFragment(1);

    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void onStartGetMyPaints() {
        progressView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onSuccessGetMyPaints(ResponseGetMyPaints responseGetMyPaints) {
        mResponseGetMyPaints = responseGetMyPaints;
        pCompetition.onGetAllPaints();

    }

    @Override
    public void onFailedGetMyPaints(int errorCode, String errorMessage) {
        progressView.showError(errorMessage, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pCompetition.onGetMyPaints();
            }
        });
    }

    @Override
    public void onStartGetAllPaints() {
        progressView.setVisibility(View.VISIBLE);

    }

    @Override
    public void onSuccessGetAllPaints(ResponseGetAllPaints responseGetAllPaints) {
        mResponseGetAllPaints = responseGetAllPaints;
         pCompetition.onGetLeaderBoard();


    }

    @Override
    public void onFailedGetAllPaints(int errorCode, String errorMessage) {
        progressView.showError(errorMessage, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pCompetition.onGetAllPaints();
            }
        });
    }

    @Override
    public void onStartGetLeaderBoard() {
        progressView.setVisibility(View.VISIBLE);

    }

    @Override
    public void onSuccessGetLeaderBoard(ResponseGetLeaderShip responseGetLeaderShip) {
        mResponseGetLeaderShip=responseGetLeaderShip;
        progressView.setVisibility(View.GONE);
        setTabLayout();
    }

    @Override
    public void onFailedGetLeaderBoard(int errorCode, String errorMessage) {
        progressView.showError(errorMessage, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pCompetition.onGetLeaderBoard();
            }
        });
    }

    @Override
    public void onSetResponseAllPaints(ResponseGetAllPaints responseGetAllPaints) {
        mResponseGetAllPaints = responseGetAllPaints;
    }

    @Override
    public void onSelectPaint(PaintModel paintModel) {
        frameLayoutScore.setVisibility(View.VISIBLE);
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayoutScore, new FragmentScore().newInstance(paintModel), TAG_FRAGMENT_SCORE).commit();

    }

    @Override
    public void setResponseLeaderShip(ResponseGetLeaderShip responseLeaderShip) {
        mResponseGetLeaderShip=responseLeaderShip;
    }

    @Override
    public void onBackPressed() {

        if ( getSupportFragmentManager().findFragmentByTag(TAG_FRAGMENT_SCORE)!=null) {
            getSupportFragmentManager().beginTransaction().remove(getSupportFragmentManager().findFragmentByTag(TAG_FRAGMENT_SCORE)).commit();
            frameLayoutScore.setVisibility(View.GONE);

        }else{
            super.onBackPressed();
        }
    }
}
