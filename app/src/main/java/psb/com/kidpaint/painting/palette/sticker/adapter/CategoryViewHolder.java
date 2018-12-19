package psb.com.kidpaint.painting.palette.sticker.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import psb.com.kidpaint.R;
import psb.com.kidpaint.utils.selector.NetworkImageView;

public class CategoryViewHolder extends RecyclerView.ViewHolder {

    public NetworkImageView imageViewCat;
   // public ImageView imageBgr;
    public ProgressBar progressBar;

    public CategoryViewHolder(View itemView) {
        super(itemView);

        imageViewCat=itemView.findViewById(R.id.img_cat);
       // imageBgr=itemView.findViewById(R.id.image_bgr_selected);
        progressBar=itemView.findViewById(R.id.prg);
    }
}
