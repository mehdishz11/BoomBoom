package psb.com.kidpaint.painting.palette.sticker;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import psb.com.kidpaint.R;
import psb.com.kidpaint.painting.palette.sticker.adapter.CategoryViewHolder;
import psb.com.kidpaint.painting.palette.sticker.adapter.StickersViewHolder;
import psb.com.kidpaint.webApi.Category.GetCategory.model.Category;
import psb.com.kidpaint.webApi.Category.GetCategory.model.Sticker;

public class P_Stickers implements IP_Stickers {

    private Context context;
    private IV_Stickers ivStickers;
    private M_Stickers mStickers;

    public P_Stickers(IV_Stickers ivStickers){
        this.context = ivStickers.getContext();
        this.ivStickers = ivStickers;
        mStickers = new M_Stickers(this);
    }

    @Override
    public Context getContext() {
        return context;
    }

    @Override
    public void getStickers() {
        mStickers.getStickers();
    }

    @Override
    public void getStickersSuccess() {
        ivStickers.getStickersSuccess();
    }

    @Override
    public void getStickersFailed() {
        ivStickers.getStickersFailed();
    }

    @Override
    public void showStickers() {
        ivStickers.showStickers();
    }

    public int getStickersSize(){
        return mStickers.getStickersSize();
    }

    public int getCategorysSize(){
        return mStickers.getCategorysSize();
    }


    public void onBindViewHolderStickers(final StickersViewHolder holder, int position) {
        final Sticker sticker = mStickers.getStickerAtPos(position);
        Picasso.get().load(sticker.getImageUrl()).into(holder.imageViewStickers, new Callback() {
            @Override
            public void onSuccess() {
                holder.progressBar.setVisibility(View.GONE);
                holder.imageViewStickers.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ivStickers.onStickerSelected(((BitmapDrawable)holder.imageViewStickers.getDrawable()).getBitmap());
                    }
                });
            }

            @Override
            public void onError(Exception e) {

            }
        });
    }

    public void onBindViewHolderCategory(final CategoryViewHolder holder, int position) {
        final Category category = mStickers.getCategoryAtPos(position);
        Picasso.get().load(category.getImageUrl()).into(holder.imageViewCat, new Callback() {
            @Override
            public void onSuccess() {
                holder.progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onError(Exception e) {

            }
        });

        if (category.isSelected()){
            holder.imageViewCat.setBackgroundResource(R.color.green_1);
        } else {
            holder.imageViewCat.setBackgroundResource(0);
        }

        holder.imageViewCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mStickers.onCatSelected(category.getId());
            }
        });
    }
}
