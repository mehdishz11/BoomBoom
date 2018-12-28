package psb.com.kidpaint.utils.customView.dialog.prize;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import psb.com.kidpaint.R;

public class GuideRateFragment extends Fragment {

    private View pView;
    private RecyclerView recyclerView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        pView = inflater.inflate(R.layout.prize_guide_rate, container, false);
        recyclerView=pView.findViewById(R.id.recycler_images);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(new AdapterImages(new int[]{R.drawable.img_guide_rate_1,R.drawable.img_guide_rate_2,R.drawable.img_guide_rate_3}));

        return pView;
    }



}
