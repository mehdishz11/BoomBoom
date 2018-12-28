package psb.com.kidpaint.utils.customView.dialog.prize;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import psb.com.kidpaint.R;
import psb.com.kidpaint.competition.allPaint.adapter.ViewHolder_AllPaints;

public class AdapterImages extends RecyclerView.Adapter<AdapterImages.ViewHolderImage> {


    private int [] imagesList;

    public AdapterImages(int[] imagesList) {
        this.imagesList = imagesList;
    }

    @NonNull
    @Override
    public ViewHolderImage onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.row_image_guide, viewGroup, false);
        ViewHolderImage viewHolderImage = new ViewHolderImage(view);
        return viewHolderImage;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderImage viewHolderImage, int i) {
        viewHolderImage.image.setImageResource(imagesList[i]);
//        Picasso.get().load(imagesList[i]).into(viewHolderImage.image);
    }

    @Override
    public int getItemCount() {
        return imagesList.length;
    }

    public class ViewHolderImage extends RecyclerView.ViewHolder{
        private ImageView image;
        public ViewHolderImage(@NonNull View itemView) {
            super(itemView);
            image=itemView.findViewById(R.id.image);
        }
    }

}
