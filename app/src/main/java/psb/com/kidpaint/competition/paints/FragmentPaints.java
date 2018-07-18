package psb.com.kidpaint.competition.paints;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import jp.wasabeef.recyclerview.adapters.AnimationAdapter;
import jp.wasabeef.recyclerview.adapters.SlideInBottomAnimationAdapter;
import psb.com.kidpaint.R;
import psb.com.kidpaint.competition.leaderBoard.FragmentLeaderBoard;
import psb.com.kidpaint.competition.paints.adapter.Adapter_AllPaints;
import psb.com.kidpaint.competition.paints.adapter.Adapter_MyPaints;
import psb.com.kidpaint.utils.UserProfile;
import psb.com.kidpaint.webApi.paint.getAllPaints.model.ResponseGetAllPaints;
import psb.com.kidpaint.webApi.paint.getMyPaints.GetMyPaints;
import psb.com.kidpaint.webApi.paint.getMyPaints.model.ResponseGetMyPaints;


public class FragmentPaints extends Fragment implements IVPaints {

    private static final String ARG_MY_PAINTS = "ARG_MY_PAINTS";
    private static final String ARG_All_PAINTS = "ARG_LL_PAINTS";
    private ResponseGetMyPaints mResponseGetMyPaints;
    private ResponseGetAllPaints mResponseGetAllPaints;

    private OnFragmentInteractionListener mListener;

    private View view;
    private RecyclerView recyclerViewMyPaints, recyclerViewAllPaints;
    private SwipeRefreshLayout swipeRefreshLayout;
    private TextView emptyViewMyPaints, emptyViewAllPaints, userName;
    private ImageView userimage;
    private ProgressBar progressBarLoading;
    private SearchView searchView;
    private UserProfile userProfile;
    private PPaints pPaints;
     private Adapter_AllPaints adapter_allPaints;
     private Adapter_MyPaints adapter_myPaints;
    public FragmentPaints() {
        // Required empty public constructor
    }


    public static FragmentPaints newInstance(ResponseGetMyPaints responseGetMyPaints, ResponseGetAllPaints responseGetAllPaints) {
        FragmentPaints fragment = new FragmentPaints();
        Bundle args = new Bundle();
        args.putSerializable(ARG_MY_PAINTS, responseGetMyPaints);
        args.putSerializable(ARG_All_PAINTS, responseGetAllPaints);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mResponseGetMyPaints = (ResponseGetMyPaints) getArguments().getSerializable(ARG_MY_PAINTS);
            mResponseGetAllPaints = (ResponseGetAllPaints) getArguments().getSerializable(ARG_All_PAINTS);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_paints, container, false);
        userProfile = new UserProfile(getContext());
        pPaints = new PPaints(this);
        pPaints.setResponseGetAllPaints(mResponseGetAllPaints);
        pPaints.setResponseGetMyPaints(mResponseGetMyPaints);

        initView();
        return view;
    }

    void initView() {

        searchView = view.findViewById(R.id.search);
        userimage = view.findViewById(R.id.act_user_image);
        userName = view.findViewById(R.id.userName);
        emptyViewMyPaints = view.findViewById(R.id.emptyViewMyPaints);
        emptyViewAllPaints = view.findViewById(R.id.emptyViewAllPaints);
        recyclerViewAllPaints = view.findViewById(R.id.recyclerViewAllPaints);
        recyclerViewMyPaints = view.findViewById(R.id.recyclerViewMyPaints);
        progressBarLoading = view.findViewById(R.id.progressBar);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);


        userName.setText(userProfile.get_KEY_FIRST_NAME("") + " " + userProfile.get_KEY_LAST_NAME(""));


        if (!userProfile.get_KEY_IMG_URL("").isEmpty()) {
            Picasso.get().load(userProfile.get_KEY_IMG_URL("avatar")).placeholder(R.drawable.user_empty_gray).into(userimage, new Callback() {
                @Override
                public void onSuccess() {

                }

                @Override
                public void onError(Exception e) {
                    userimage.setImageResource(R.drawable.user_empty_gray);
                }
            });
        }

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pPaints.onGetAllPaints(searchView.getQuery().toString(), 1, 20);
            }
        });

        adapter_allPaints=new Adapter_AllPaints(pPaints);
        LinearLayoutManager linearLayoutManager = new GridLayoutManager(getContext(), 2);
        recyclerViewAllPaints.setLayoutManager(linearLayoutManager);
        AnimationAdapter animationAdapter = new SlideInBottomAnimationAdapter(adapter_allPaints);
        animationAdapter.setDuration(100);
        animationAdapter.setFirstOnly(false);
        recyclerViewAllPaints.setAdapter(animationAdapter);


        adapter_myPaints=new Adapter_MyPaints(pPaints);
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL,false);
        recyclerViewMyPaints.setLayoutManager(linearLayoutManager2);
        AnimationAdapter animationAdapter2 = new SlideInBottomAnimationAdapter(adapter_myPaints);
        animationAdapter.setDuration(100);
        animationAdapter.setFirstOnly(false);
        recyclerViewMyPaints.setAdapter(animationAdapter2);
        emptyViewMyPaints.setVisibility(adapter_myPaints.getItemCount()>0?View.GONE:View.VISIBLE);


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextChange(String newText) {
                int spaceCount = 0;
                char[] characters = newText.toCharArray();
                for (int i = 0; i < characters.length; i++) {
                    if (characters[i] == ' ') {
                        spaceCount++;
                    }
                }
                if (spaceCount == characters.length) {
                } else if (spaceCount < characters.length && characters[(characters.length - 1)] == ' ') {
                    pPaints.onGetAllPaints(newText.toString().trim(), 1, 20);
                    progressBarLoading.setVisibility(View.VISIBLE);

                }
                return true;
            }

            @Override
            public boolean onQueryTextSubmit(String query) {
                InputMethodManager inputMethodManager =
                        (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.toggleSoftInputFromWindow(
                        searchView.getApplicationWindowToken(),
                        InputMethodManager.SHOW_IMPLICIT, 0);
                pPaints.onGetAllPaints(query.trim(), 1, 20);
                progressBarLoading.setVisibility(View.VISIBLE);

                return true;
            }
        });
        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        searchView.setIconified(false);


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
    public void onStartGetAllPaints() {

    }

    @Override
    public void onSuccessGetAllPaints(ResponseGetAllPaints responseGetAllPaints) {

        swipeRefreshLayout.setRefreshing(false);
        progressBarLoading.setVisibility(View.GONE);
        mResponseGetAllPaints = responseGetAllPaints;
        if (mListener != null) {
            mListener.onSetResponseAllPaints(mResponseGetAllPaints);
        }
        recyclerViewAllPaints.getAdapter().notifyDataSetChanged();
        emptyViewAllPaints.setVisibility(recyclerViewAllPaints.getAdapter().getItemCount() > 0 ? View.GONE : View.VISIBLE);
    }

    @Override
    public void onFailedGetAllPaints(int errorCode, String errorMessage) {
        Toast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT).show();
        progressBarLoading.setVisibility(View.GONE);
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onSuccessDeleteMyPaints(int position) {
        recyclerViewMyPaints.getAdapter().notifyDataSetChanged();
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onSetResponseAllPaints(ResponseGetAllPaints responseGetAllPaints);
    }
}
