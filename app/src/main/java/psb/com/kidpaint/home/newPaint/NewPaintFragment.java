package psb.com.kidpaint.home.newPaint;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import psb.com.kidpaint.R;
import psb.com.kidpaint.home.newPaint.adapter.OutlineAdapter;

public class NewPaintFragment extends Fragment {

    private View pView;
    private RecyclerView recyclerOutlines;
    private OnFragmentInteractionListener listener;


    public NewPaintFragment newInstance() {
        NewPaintFragment fragment = new NewPaintFragment();

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        pView = inflater.inflate(R.layout.fragment_new_paint, container, false);
        recyclerOutlines=pView.findViewById(R.id.recycler_outlines);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),2, LinearLayoutManager.VERTICAL,false);
        recyclerOutlines.setLayoutManager(gridLayoutManager);
        recyclerOutlines.setAdapter(new OutlineAdapter(getContext(), new OutlineAdapter.OnOutlineSelected() {
            @Override
            public void onSelected(int resId) {
                if (listener != null) {
                    listener.onOutlineSelected(resId);
                }
            }
        }));

        return pView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof OnFragmentInteractionListener){
            listener=(OnFragmentInteractionListener)context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener=null;
    }

    public interface OnFragmentInteractionListener {
        void onOutlineSelected(int resId);
    }
}
