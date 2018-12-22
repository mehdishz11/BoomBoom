package psb.com.kidpaint.painting.palette.sticker;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

import jp.wasabeef.picasso.transformations.GrayscaleTransformation;
import psb.com.kidpaint.R;
import psb.com.kidpaint.painting.palette.sticker.adapter.CategoryViewHolder;
import psb.com.kidpaint.painting.palette.sticker.adapter.StickersViewHolder;
import psb.com.kidpaint.utils.musicHelper.SingleMusicPlayer;
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
    public void showStickers(int catPosition) {
        ivStickers.showStickers(catPosition);
    }

    @Override
    public void unSelectCat(int position) {
        ivStickers.unSelectCat(position);
    }

    @Override
    public void getStickersFromServer() {
       ivStickers.startGetStickersFromServer();
       mStickers.getStickersFromServer();
    }

    @Override
    public void getStickersSuccessFromServer() {
       ivStickers.getStickersSuccessFromServer();
    }

    @Override
    public void getStickersFailedFromServer(int errorId, String ErrorMessage) {
       ivStickers.getStickersFailedFromServer(errorId, ErrorMessage);
    }

    public int getStickersSize(){
        return mStickers.getStickersSize();
    }

    public int getCategorysSize(){
        return mStickers.getCategorysSize();
    }


    public void onBindViewHolderStickers(final StickersViewHolder holder, final int position) {

        final Sticker sticker = mStickers.getStickerAtPos(position);

        if (sticker.getImageUrl() != null && !sticker.getImageUrl().isEmpty()) {

            RequestCreator requestCreator=Picasso.get().load(sticker.getImageUrl());

            if (sticker.getPrice()>0&& !mStickers.userIsRegistered()){
                //   holder.imageBgr.setBackgroundResource(R.drawable.circle_border_white);
                requestCreator.transform(new GrayscaleTransformation());
                holder.locked.setVisibility(View.VISIBLE);
            } else {
                //   holder.imageBgr.setBackgroundResource(0);
                holder.locked.setVisibility(View.GONE);

            }

            requestCreator.into(holder.imageViewStickers, new Callback() {
                @Override
                public void onSuccess() {
                    holder.progressBar.setVisibility(View.GONE);
                    holder.imageViewStickers.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            ivStickers.onStickerSelected(
                                    ((BitmapDrawable)holder.imageViewStickers.getDrawable()).getBitmap(),
                                    sticker.getPrice(),
                                    sticker.getSongUrl()
                            );
                        }
                    });
                }

                @Override
                public void onError(Exception e) {

                }
            });



        }else{
        }

    }

    public void onBindViewHolderCategory(final CategoryViewHolder holder, final int position) {

        final Category category = mStickers.getCategoryAtPos(position);

        if(category.getId()==-1) {

            holder.progressBar.setVisibility(View.GONE);
            holder.imageViewCat.setImageResource(R.drawable.icon_type);

            holder.imageViewCat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (ivStickers != null) {
                        ivStickers.onTextStickerClicked();
                    }
                }
            });

            return;
        }else  {

            RequestCreator requestCreator=Picasso.get().load(category.getImageUrl());
            if (category.isSelected()){
                holder.imageViewCat.setSelected(true);
            } else {
                requestCreator.transform(new GrayscaleTransformation());
                holder.imageViewCat.setSelected(false);
            }


            requestCreator.into(holder.imageViewCat, new Callback() {
                @Override
                public void onSuccess() {
                    holder.progressBar.setVisibility(View.GONE);
                }

                @Override
                public void onError(Exception e) {
                    holder.progressBar.setVisibility(View.GONE);

                }
            });
        }

        holder.imageViewCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mStickers.onCatSelected(category.getId(),position);
             //   Log.d("TAG", "onClick: "+category.getSongUrl());
                new SingleMusicPlayer().playSingleMedia(category.getSongUrl());

            }
        });
    }
}
