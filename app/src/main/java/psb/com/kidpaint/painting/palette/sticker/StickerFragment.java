package psb.com.kidpaint.painting.palette.sticker;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import psb.com.kidpaint.R;
import psb.com.kidpaint.painting.palette.sticker.adapter.CategoryAdapter;
import psb.com.kidpaint.painting.palette.sticker.adapter.StickersAdapter;
import psb.com.kidpaint.utils.customView.stickerview.StickerImageView;
import psb.com.kidpaint.utils.customView.stickerview.StickerView;

public class StickerFragment extends Fragment implements IV_Stickers{

    private View pView;
    private P_Stickers pStickers;
    private RecyclerView recyclerViewCat, recyclerViewStickers;
    private CategoryAdapter categoryAdapter;
    private StickersAdapter stickersAdapter;
    private LinearLayoutManager linearLayoutManagerCat, linearLayoutManagerStickers;
    private OnFragmentInteractionListener mListener;

    public static StickerFragment newInstance() {
        StickerFragment fragment = new StickerFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        pView = inflater.inflate(R.layout.fragment_sticker, container, false);
        pStickers = new P_Stickers(this);
        setContent();
        return pView;
    }

    private void setContent(){
        recyclerViewCat = pView.findViewById(R.id.rec_cat);
        linearLayoutManagerCat = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        categoryAdapter = new CategoryAdapter(pStickers);
        recyclerViewCat.setLayoutManager(linearLayoutManagerCat);
        recyclerViewCat.setAdapter(categoryAdapter);

        recyclerViewStickers = pView.findViewById(R.id.rec_stickers);
        stickersAdapter = new StickersAdapter(pStickers);
        linearLayoutManagerStickers = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewStickers.setLayoutManager(linearLayoutManagerStickers);
        recyclerViewStickers.setAdapter(stickersAdapter);
        pStickers.getStickers();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof OnFragmentInteractionListener){
            mListener=(OnFragmentInteractionListener)context;
        }else if (getParentFragment() instanceof OnFragmentInteractionListener){
            mListener=(OnFragmentInteractionListener)getParentFragment();
        }
    }

    @Override
    public void getStickersSuccess() {
        stickersAdapter.notifyDataSetChanged();
        categoryAdapter.notifyDataSetChanged();
    }

    @Override
    public void getStickersFailed() {
        Toast.makeText(getContext(), "مشکل در دریافت استیکرها", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showStickers() {
        categoryAdapter.notifyDataSetChanged();
        stickersAdapter.notifyDataSetChanged();
    }

    @Override
    public void onStickerSelected(Bitmap stickerBitmap) {
        if (mListener != null) {
            StickerImageView stickerImageView=new StickerImageView(getContext());
            stickerImageView.setImageBitmap(stickerBitmap);
            mListener.onStickerSelected(stickerImageView);
        }
    }

    public interface OnFragmentInteractionListener {

        void onStickerSelected(StickerView sticker);
    }


    }
