package psb.com.kidpaint.home.history;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import psb.com.kidpaint.R;


/**
 * Created by AMiR Ehsan on 4/11/2017 AD.
 */

public class AdapterReccategory extends RecyclerView.Adapter<ViewHolderCategory> {

    private Context context;
    private int lastPosition = -1;

    public AdapterReccategory() {
    }

    @Override
    public ViewHolderCategory onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_category, parent, false);
        ViewHolderCategory recViewHolderNews = new ViewHolderCategory(view);
        return recViewHolderNews;
    }

    @Override
    public void onBindViewHolder(ViewHolderCategory holder, int position) {
       // pCategory.onBindView(holder, position);
        //setAnimation(holder.itemView, position);
    }

    @Override
    public int getItemCount() {
        return 50;
    }
}
