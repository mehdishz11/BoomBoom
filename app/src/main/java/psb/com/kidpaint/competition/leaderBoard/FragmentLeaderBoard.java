package psb.com.kidpaint.competition.leaderBoard;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import psb.com.kidpaint.R;
import psb.com.kidpaint.competition.leaderBoard.adapter.Adapter_LeaderShip;
import psb.com.kidpaint.user.register.ActivityRegisterUser;
import psb.com.kidpaint.utils.EndlessRecyclerViewScrollListener;
import psb.com.kidpaint.utils.UserProfile;
import psb.com.kidpaint.utils.customView.dialog.CDialog;
import psb.com.kidpaint.utils.customView.dialog.MessageDialog;
import psb.com.kidpaint.webApi.paint.getLeaderShip.model.LeaderModel;
import psb.com.kidpaint.webApi.paint.getLeaderShip.model.ResponseGetLeaderShip;


public class FragmentLeaderBoard extends Fragment implements IVLeaderShip {
    private static final String ARG_RESPONSE = "ARG_RESPONSE";
    private ResponseGetLeaderShip mResponseGetLeaderShip;
    private static final String ARG_LEVEL = "ARG_LEVEL";
    private static final String ARG_MATCHID = "ARG_MATCHID";
    int matchId=0;
    int level=1;

    private OnFragmentInteractionListener mListener;

    private View view;
    private RecyclerView recyclerView;
    private TextView emptyView;
    private ProgressBar progressBarLoading;

    private PLeaderShip pLeaderShip;
    private Adapter_LeaderShip adapter_leaderShip;

    EndlessRecyclerViewScrollListener scrollListener;
    private UserProfile userProfile;
    private int sendPosition=-1;
    private ProgressDialog progressDialog;
    private static final int REQUEST_CODE_REGISTER = 120;


    public FragmentLeaderBoard() {
        // Required empty public constructor
    }


    public static FragmentLeaderBoard newInstance(ResponseGetLeaderShip responseGetLeaderShip ,int matchId, int level) {
        FragmentLeaderBoard fragment = new FragmentLeaderBoard();
        Bundle args = new Bundle();
        args.putSerializable(ARG_RESPONSE, responseGetLeaderShip);
        args.putInt(ARG_LEVEL, level);
        args.putInt(ARG_MATCHID, matchId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mResponseGetLeaderShip = (ResponseGetLeaderShip) getArguments().getSerializable(ARG_RESPONSE);
            level=getArguments().getInt(ARG_LEVEL);
            matchId=getArguments().getInt(ARG_MATCHID);
            getArguments().clear();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_leader_board, container, false);

        pLeaderShip = new PLeaderShip(this);
        pLeaderShip.setResponseGetLeaderShip(mResponseGetLeaderShip);
        userProfile=new UserProfile(getContext());

        progressDialog=new ProgressDialog(getContext());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("لطفا کمی صبر کنید ...");
        initView();

        return view;
    }

    public void onGetLeaderShip(int matchId, int level){
        pLeaderShip.onGetLeaderShip(1, 20,matchId,level);

    }


    void initView() {

        emptyView = view.findViewById(R.id.emptyView);
        recyclerView = view.findViewById(R.id.recyclerView);
        progressBarLoading = view.findViewById(R.id.progressBar);


        adapter_leaderShip = new Adapter_LeaderShip(pLeaderShip);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        linearLayoutManager.setReverseLayout(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.setAdapter(adapter_leaderShip);

        emptyView.setVisibility(adapter_leaderShip.getItemCount()>0?View.GONE:View.VISIBLE);




         scrollListener = new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {

                if (pLeaderShip.getServerGetLeaderShipSize()>adapter_leaderShip.getItemCount()) {
                  int  loadcount = 20;
                  int  current_page= (int) Math.ceil((adapter_leaderShip.getItemCount()+loadcount) / 20.0);

                    Log.d("TAG", "onLoadMore: "+current_page);
                    progressBarLoading.setVisibility(View.VISIBLE);
                    pLeaderShip.onGetLeaderShip(current_page, 20,matchId,level);
                }
               // Log.d("TAG", "onLoadMore: "+page);
            }
        };
        // Adds the scroll listener to RecyclerView
        recyclerView.addOnScrollListener(scrollListener);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {

        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onStartGetLeaderShip() {

    }

    @Override
    public void onSuccessGetLeaderShip(ResponseGetLeaderShip responseGetLeaderShip) {

        progressBarLoading.setVisibility(View.GONE);
        mResponseGetLeaderShip = responseGetLeaderShip;
        if (mListener != null) {
            mListener.setResponseLeaderShip(mResponseGetLeaderShip);
        }
        recyclerView.getAdapter().notifyDataSetChanged();
        emptyView.setVisibility(recyclerView.getAdapter().getItemCount() > 0 ? View.GONE : View.VISIBLE);
        scrollListener.resetState();
    }

    @Override
    public void onFailedGetLeaderShip(int errorCode, String errorMessage) {
        progressBarLoading.setVisibility(View.GONE);
    }

    @Override
    public void onSuccessSendScore(int position) {
        progressDialog.cancel();
        recyclerView.getAdapter().notifyItemChanged(position);

        final MessageDialog dialog = new MessageDialog(getContext());
        dialog.setMessage("امتیاز شما با موفقیت ثبت شد.");
        dialog.setOnCLickListener(new CDialog.OnCLickListener() {
            @Override
            public void onPosetiveClicked() {
            }

            @Override
            public void onNegativeClicked() {
                sendPosition = -1;

            }
        });

        dialog.setAcceptButtonMessage(getContext().getString(R.string.confirm));
        dialog.setTitle("امتیاز دهی");
        dialog.show();
    }

    @Override
    public void onFailedSendScore(int errorCode, String errorMessage) {
        progressDialog.cancel();
        final MessageDialog dialog = new MessageDialog(getContext());
        dialog.setMessage(errorMessage);
        dialog.setOnCLickListener(new CDialog.OnCLickListener() {
            @Override
            public void onPosetiveClicked() {
            }

            @Override
            public void onNegativeClicked() {
                dialog.cancel();

            }
        });

        dialog.setAcceptButtonMessage(getContext().getString(R.string.confirm));
        dialog.setTitle("امتیاز دهی");
        dialog.show();
    }

    @Override
    public void showUserRegisterDialog(final int position) {
        sendPosition = position;
        final MessageDialog dialog = new MessageDialog(getContext());

        dialog.setMessage("برای امتیاز دهی به نقاشی باید ثبت نام کنید یا وارد شوید!");
        dialog.setOnCLickListener(new CDialog.OnCLickListener() {
            @Override
            public void onPosetiveClicked() {
                startActivityForResult(new Intent(getContext(), ActivityRegisterUser.class), REQUEST_CODE_REGISTER);

                dialog.cancel();
            }

            @Override
            public void onNegativeClicked() {
                sendPosition = -1;
                dialog.cancel();

            }
        });

        dialog.setAcceptButtonMessage(getContext().getString(R.string.enter));
        dialog.setTitle(getString(R.string.register_login));
        dialog.show();
    }

    @Override
    public void onStartSendScore() {
        progressDialog.show();
    }


    @Override
    public void onSelectPaint(LeaderModel paintModel) {
        if (mListener!=null) {
            mListener.onSelectPaint(paintModel);
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_CODE_REGISTER) {
            if (resultCode == Activity.RESULT_OK) {
                if (mListener!=null) {
                    mListener.onRefreshUserData();
                }
                if (sendPosition != -1) {
                    pLeaderShip.onSendScore(sendPosition);
                }
            }else{
                sendPosition = -1;

            }
        }
    }




    public interface OnFragmentInteractionListener {
        void setResponseLeaderShip(ResponseGetLeaderShip responseLeaderShip);
        void onRefreshUserData();
        void onSelectPaint(LeaderModel leaderModel);
    }
}
