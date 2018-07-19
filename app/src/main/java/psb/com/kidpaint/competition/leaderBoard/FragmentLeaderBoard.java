package psb.com.kidpaint.competition.leaderBoard;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import jp.wasabeef.recyclerview.adapters.AnimationAdapter;
import jp.wasabeef.recyclerview.adapters.SlideInBottomAnimationAdapter;
import psb.com.kidpaint.R;
import psb.com.kidpaint.competition.allPaint.PAllPaints;
import psb.com.kidpaint.competition.allPaint.adapter.Adapter_AllPaints;
import psb.com.kidpaint.competition.leaderBoard.adapter.Adapter_LeaderShip;
import psb.com.kidpaint.webApi.paint.getAllPaints.model.ResponseGetAllPaints;
import psb.com.kidpaint.webApi.paint.getLeaderShip.model.ResponseGetLeaderShip;


public class FragmentLeaderBoard extends Fragment implements IVLeaderShip {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_RESPONSE = "ARG_RESPONSE";
    private ResponseGetLeaderShip mResponseGetLeaderShip;


    private OnFragmentInteractionListener mListener;

    private View view;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private TextView emptyView;
    private ProgressBar progressBarLoading;

    private PLeaderShip pLeaderShip;
    private Adapter_LeaderShip adapter_leaderShip;

    public FragmentLeaderBoard() {
        // Required empty public constructor
    }


    public static FragmentLeaderBoard newInstance(ResponseGetLeaderShip responseGetLeaderShip) {
        FragmentLeaderBoard fragment = new FragmentLeaderBoard();
        Bundle args = new Bundle();
        args.putSerializable(ARG_RESPONSE, responseGetLeaderShip);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mResponseGetLeaderShip = (ResponseGetLeaderShip) getArguments().getSerializable(ARG_RESPONSE);
            getArguments().clear();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_leader_board, container, false);

        pLeaderShip = new PLeaderShip(this);
        pLeaderShip.setResponseGetLeaderShip(mResponseGetLeaderShip);


        initView();
        return view;
    }


    void initView() {

        emptyView = view.findViewById(R.id.emptyView);
        recyclerView = view.findViewById(R.id.recyclerView);
        progressBarLoading = view.findViewById(R.id.progressBar);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                 pLeaderShip.onGetLeaderShip(1, 20);
            }
        });

        adapter_leaderShip = new Adapter_LeaderShip(pLeaderShip);
        LinearLayoutManager linearLayoutManager = new GridLayoutManager(getContext(), 1);
        recyclerView.setLayoutManager(linearLayoutManager);
        AnimationAdapter animationAdapter = new SlideInBottomAnimationAdapter(adapter_leaderShip);
        animationAdapter.setDuration(100);
        animationAdapter.setFirstOnly(false);
        recyclerView.setAdapter(animationAdapter);

        emptyView.setVisibility(adapter_leaderShip.getItemCount()>0?View.GONE:View.VISIBLE);
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

        swipeRefreshLayout.setRefreshing(false);
        progressBarLoading.setVisibility(View.GONE);
        mResponseGetLeaderShip = responseGetLeaderShip;
        if (mListener != null) {
            mListener.setResponseLeaderShip(mResponseGetLeaderShip);
        }
        recyclerView.getAdapter().notifyDataSetChanged();
        emptyView.setVisibility(recyclerView.getAdapter().getItemCount() > 0 ? View.GONE : View.VISIBLE);
    }

    @Override
    public void onFailedGetLeaderShip(int errorCode, String errorMessage) {
        Toast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT).show();
        progressBarLoading.setVisibility(View.GONE);
        swipeRefreshLayout.setRefreshing(false);
    }




    public interface OnFragmentInteractionListener {
        void setResponseLeaderShip(ResponseGetLeaderShip responseLeaderShip);

    }
}
