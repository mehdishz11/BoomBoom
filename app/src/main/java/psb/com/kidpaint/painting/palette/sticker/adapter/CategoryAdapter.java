package psb.com.kidpaint.painting.palette.sticker.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import psb.com.kidpaint.R;
import psb.com.kidpaint.painting.palette.sticker.P_Stickers;


/**
 * Created by AMiR Ehsan on 4/11/2017 AD.
 */

public class CategoryAdapter extends RecyclerView.Adapter<CategoryViewHolder> {


   private P_Stickers pStickers;

    public CategoryAdapter(P_Stickers pStickers) {
        this.pStickers = pStickers;
    }

    @Override
    public CategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_category_stickers, parent, false);
        CategoryViewHolder categoryViewHolder = new CategoryViewHolder(view);
        return categoryViewHolder;
    }

    @Override
    public void onBindViewHolder(CategoryViewHolder holder, int position) {
       // pCategory.onBindView(holder, position);
        //setAnimation(holder.itemView, position);
        pStickers.onBindViewHolderCategory(holder, position);
    }

    @Override
    public int getItemCount() {
        return pStickers.getCategorysSize();
    }



}
