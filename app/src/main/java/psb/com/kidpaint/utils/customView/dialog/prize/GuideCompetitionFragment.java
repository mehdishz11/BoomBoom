package psb.com.kidpaint.utils.customView.dialog.prize;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import psb.com.kidpaint.R;

public class GuideCompetitionFragment extends Fragment {

    private View pView;
    private RecyclerView recyclerView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        pView = inflater.inflate(R.layout.prize_guide_rate, container, false);
        recyclerView=pView.findViewById(R.id.recycler_images);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(new AdapterImages(new int[]{R.drawable.img_guide_compatition_1,R.drawable.img_guide_compatition_2}));

        return pView;
    }



}
