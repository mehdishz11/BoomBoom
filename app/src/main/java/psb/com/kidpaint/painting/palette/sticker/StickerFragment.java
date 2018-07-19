package psb.com.kidpaint.painting.palette.sticker;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import psb.com.kidpaint.R;

public class StickerFragment extends Fragment implements IV_Stickers{
    private View pView;

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
        return pView;
    }

    @Override
    public void getStickersSuccess() {

    }

    @Override
    public void getStickersFailed() {

    }
}
