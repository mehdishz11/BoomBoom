package psb.com.kidpaint.competition.allPaint;

import android.content.Context;
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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import jp.wasabeef.recyclerview.adapters.AnimationAdapter;
import jp.wasabeef.recyclerview.adapters.SlideInBottomAnimationAdapter;
import psb.com.kidpaint.R;
import psb.com.kidpaint.competition.allPaint.adapter.Adapter_AllPaints;
import psb.com.kidpaint.utils.UserProfile;
import psb.com.kidpaint.webApi.paint.getAllPaints.model.ResponseGetAllPaints;
import psb.com.kidpaint.webApi.paint.getMyPaints.model.ResponseGetMyPaints;
import psb.com.kidpaint.webApi.shareModel.PaintModel;


public class FragmentAllPaints extends Fragment implements IVAllPaints {

    private static final String ARG_All_PAINTS = "ARG_LL_PAINTS";
    private ResponseGetAllPaints mResponseGetAllPaints;

    private OnFragmentInteractionListener mListener;

    private View view;
    private RecyclerView recyclerViewMyPaints, recyclerViewAllPaints;
    private SwipeRefreshLayout swipeRefreshLayout;
    private TextView emptyViewAllPaints;
    private ProgressBar progressBarLoading;
    private SearchView searchView;
    private UserProfile userProfile;
    private PAllPaints pPaints;
     private Adapter_AllPaints adapter_allPaints;
    public FragmentAllPaints() {
        // Required empty public constructor
    }


    public static FragmentAllPaints newInstance(ResponseGetAllPaints responseGetAllPaints) {
        FragmentAllPaints fragment = new FragmentAllPaints();
        Bundle args = new Bundle();
        args.putSerializable(ARG_All_PAINTS, responseGetAllPaints);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mResponseGetAllPaints = (ResponseGetAllPaints) getArguments().getSerializable(ARG_All_PAINTS);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_all_paints, container, false);
        userProfile = new UserProfile(getContext());
        pPaints = new PAllPaints(this);
        pPaints.setResponseGetAllPaints(mResponseGetAllPaints);

        initView();
        return view;
    }

    void initView() {

        searchView = view.findViewById(R.id.search);
        emptyViewAllPaints = view.findViewById(R.id.emptyViewAllPaints);
        recyclerViewAllPaints = view.findViewById(R.id.recyclerViewAllPaints);
        progressBarLoading = view.findViewById(R.id.progressBar);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);



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
    public void onSelectPaint(PaintModel paintModel) {
        if (mListener!=null) {
            mListener.onSelectPaint(paintModel);
        }
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onSetResponseAllPaints(ResponseGetAllPaints responseGetAllPaints);
        void onSelectPaint(PaintModel paintModel);
    }
}
