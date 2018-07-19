package psb.com.kidpaint.painting.palette.sticker.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import psb.com.kidpaint.R;

public class CategoryViewHolder extends RecyclerView.ViewHolder {

    public ImageView imageViewCat;
    public ProgressBar progressBar;

    public CategoryViewHolder(View itemView) {
        super(itemView);

        imageViewCat=itemView.findViewById(R.id.img_cat);
        progressBar=itemView.findViewById(R.id.prg);
    }
}
