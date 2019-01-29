package psb.com.kidpaint.painting.palette.sticker;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import psb.com.kidpaint.R;
import psb.com.kidpaint.painting.palette.sticker.adapter.CategoryAdapter;
import psb.com.kidpaint.painting.palette.sticker.adapter.StickersAdapter;
import psb.com.kidpaint.utils.customView.dialog.CDialog;
import psb.com.kidpaint.utils.customView.dialog.DialogStickerText;
import psb.com.kidpaint.utils.customView.dialog.MessageDialog;
import psb.com.kidpaint.utils.customView.stickerview.StickerImageView;
import psb.com.kidpaint.utils.customView.stickerview.StickerTextView;
import psb.com.kidpaint.utils.customView.stickerview.StickerView;

public class StickerFragment extends Fragment implements IV_Stickers{

    private View pView;
    private P_Stickers pStickers;
    private RecyclerView recyclerViewCat, recyclerViewStickers;
    private CategoryAdapter categoryAdapter;
    private StickersAdapter stickersAdapter;
    private LinearLayoutManager linearLayoutManagerCat, linearLayoutManagerStickers;
    private OnFragmentInteractionListener mListener;
    private ProgressDialog progressDialog;
    private Button refreshStickers;

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
        progressDialog=new ProgressDialog(getContext());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("لطفا کمی صبر کنید ...");
        setContent();
        return pView;
    }

    private void setContent(){
        recyclerViewCat = pView.findViewById(R.id.rec_cat);
        refreshStickers = pView.findViewById(R.id.refreshStickers);
        refreshStickers.setVisibility(View.GONE);
        linearLayoutManagerCat = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, true);
//        linearLayoutManagerCat.setStackFromEnd(true);
        categoryAdapter = new CategoryAdapter(pStickers);
        recyclerViewCat.setLayoutManager(linearLayoutManagerCat);
        recyclerViewCat.setAdapter(categoryAdapter);

        recyclerViewStickers = pView.findViewById(R.id.rec_stickers);
        stickersAdapter = new StickersAdapter(pStickers);
        linearLayoutManagerStickers = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, true);
        recyclerViewStickers.setLayoutManager(linearLayoutManagerStickers);
        recyclerViewStickers.setAdapter(stickersAdapter);
        pStickers.getStickers();

        refreshStickers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pStickers.getStickersFromServer();
            }
        });
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
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void getStickersSuccess() {
        stickersAdapter.notifyDataSetChanged();
        categoryAdapter.notifyDataSetChanged();

        if (stickersAdapter.getItemCount()>0){
            recyclerViewStickers.scrollToPosition(0);
        }

    }

    @Override
    public void getStickersFailed() {
        Toast.makeText(getContext(), "مشکل در دریافت استیکرها", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showStickers(int catPosition) {
        categoryAdapter.notifyItemChanged(catPosition);
        stickersAdapter.notifyDataSetChanged();
        refreshStickers.setVisibility(categoryAdapter.getItemCount()>0?View.GONE:View.VISIBLE);

        if (stickersAdapter.getItemCount()>0){
            recyclerViewStickers.scrollToPosition(0);
        }


    }

    @Override
    public void unSelectCat(int position) {
        categoryAdapter.notifyItemChanged(position);

    }

    @Override
    public void onStickerSelected(Bitmap stickerBitmap,int price,final String stickerSound) {
        if (mListener != null) {
            StickerImageView stickerImageView=new StickerImageView(getContext());
            stickerImageView.setImageBitmap(stickerBitmap);
            stickerImageView.setStickerPrice(price);
            stickerImageView.setStickerSound(stickerSound);
            mListener.onStickerSelected(stickerImageView);
        }
    }

    @Override
    public void onTextStickerClicked() {
        DialogStickerText dialogStickerText=new DialogStickerText(getContext());
        dialogStickerText.setOnClick(new DialogStickerText.OnClick() {
            @Override
            public void onAccept(String text, int color) {
                StickerTextView stickerTextView=new StickerTextView(getContext());
                stickerTextView.setText(text);
                stickerTextView.setTextColor(color);

                if (mListener != null) {
                    mListener.onStickerSelected(stickerTextView);
                }
            }
        });

        dialogStickerText.show();
    }

    @Override
    public void startGetStickersFromServer() {
        progressDialog.show();
    }

    @Override
    public void getStickersSuccessFromServer() {
        progressDialog.cancel();
        pStickers.getStickers();

    }

    @Override
    public void getStickersFailedFromServer(int errorId, String ErrorMessage) {
        progressDialog.cancel();
        progressDialog.cancel();
        final MessageDialog dialog = new MessageDialog(getContext());
        dialog.setMessage(ErrorMessage);
        dialog.setOnCLickListener(new CDialog.OnCLickListener() {
            @Override
            public void onPosetiveClicked() {
                dialog.cancel();

            }

            @Override
            public void onNegativeClicked() {
                dialog.cancel();

            }
        });

        dialog.setAcceptButtonMessage(getContext().getString(R.string.confirm));
        dialog.setTitle("دریافت استیکر ها");
        dialog.show();

    }

    public interface OnFragmentInteractionListener {

        void onStickerSelected(StickerView sticker);
    }


    }
