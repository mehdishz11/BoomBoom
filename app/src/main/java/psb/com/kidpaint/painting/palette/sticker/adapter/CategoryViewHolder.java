package psb.com.kidpaint.painting.palette.sticker.adapter;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import psb.com.kidpaint.R;

public class CategoryViewHolder extends RecyclerView.ViewHolder {

    public ImageView imageViewCat;
   // public ImageView imageBgr;
    public ProgressBar progressBar;

    public CategoryViewHolder(View itemView) {
        super(itemView);

        imageViewCat=itemView.findViewById(R.id.img_cat);
       // imageBgr=itemView.findViewById(R.id.image_bgr_selected);
        progressBar=itemView.findViewById(R.id.prg);
    }
}
