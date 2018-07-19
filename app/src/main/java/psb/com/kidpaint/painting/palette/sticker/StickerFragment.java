package psb.com.kidpaint.painting.palette.sticker;

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

public class StickerFragment extends Fragment implements IV_Stickers{

    private View pView;
    private P_Stickers pStickers;
    private RecyclerView recyclerViewCat, recyclerViewStickers;
    private CategoryAdapter categoryAdapter;
    private StickersAdapter stickersAdapter;
    private LinearLayoutManager linearLayoutManagerCat, linearLayoutManagerStickers;

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
}
