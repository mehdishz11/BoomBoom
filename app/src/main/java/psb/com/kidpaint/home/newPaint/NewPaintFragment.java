package psb.com.kidpaint.home.newPaint;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import jp.wasabeef.recyclerview.adapters.AnimationAdapter;
import jp.wasabeef.recyclerview.adapters.SlideInBottomAnimationAdapter;
import psb.com.kidpaint.R;
import psb.com.kidpaint.home.newPaint.adapter.OutlineAdapter;

public class NewPaintFragment extends Fragment {

    private View pView;
    public RecyclerView recyclerOutlines;
    private OnFragmentInteractionListener listener;


    public NewPaintFragment newInstance() {
        NewPaintFragment fragment = new NewPaintFragment();

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        pView = inflater.inflate(R.layout.fragment_new_paint, container, false);
        recyclerOutlines = pView.findViewById(R.id.recycler_outlines);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);

        recyclerOutlines.setLayoutManager(gridLayoutManager);

        RecyclerView.Adapter adapter = new OutlineAdapter(getContext(), new OutlineAdapter.OnOutlineSelected() {
            @Override
            public void onSelected(int resId) {
                if (listener != null) {
                    listener.onOutlineSelected(resId);
                }
            }
        });

        AnimationAdapter animationAdapter=new SlideInBottomAnimationAdapter(adapter);
        animationAdapter.setDuration(300);
        animationAdapter.setFirstOnly(false);

        recyclerOutlines.setAdapter(animationAdapter);

        if (listener != null) {
            listener.onFragmentAttached(this);
        }

        return pView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            listener = (OnFragmentInteractionListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    public interface OnFragmentInteractionListener {
        void onOutlineSelected(int resId);
        void onFragmentAttached(Fragment fragment);
    }
}
