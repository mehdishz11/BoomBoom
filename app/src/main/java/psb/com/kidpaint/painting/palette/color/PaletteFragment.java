package psb.com.kidpaint.painting.palette.color;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import psb.com.kidpaint.R;
import psb.com.paintingview.CircleColorPicker;


public class PaletteFragment extends Fragment implements CircleColorPicker.ColorPickerClickListener {

    private View pView;
    private OnFragmentInteractionListener mListener;

    private CircleColorPicker[] arrCircleColorPicker = new CircleColorPicker[12];
    private ImageView btnIncreaseSize, btnDecreaseSize;
    private ImageView[] circleSize = new ImageView[5];
    private ImageView btnTypePencil, btnTypeBrush, btnTypeEraser;
    private PaintType paintType = PaintType.PENCIL;

    private int selectedSize = 3;
    private int colorResource;

    private Drawable selectedColoePickerDrawable;


    public PaletteFragment() {
        // Required empty public constructor
    }


    public static PaletteFragment newInstance() {
        PaletteFragment fragment = new PaletteFragment();

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
        pView = inflater.inflate(R.layout.fragment_palette, container, false);
        initView();

        return pView;
    }

    private void initView() {

        selectedColoePickerDrawable = getResources().getDrawable(R.drawable.color_2);

        for (int i = 0; i < arrCircleColorPicker.length; i++) {
            String buttonID = "color_" + i;
            int resID = getResources().getIdentifier(buttonID, "id", getContext().getPackageName());
            arrCircleColorPicker[i] = pView.findViewById(resID);
            arrCircleColorPicker[i].setColorPickerClickListener(this);
        }

        for (int i = 0; i < circleSize.length; i++) {
            String buttonID = "img_size_" + (i + 1);
            int resID = getResources().getIdentifier(buttonID, "id", getContext().getPackageName());
            circleSize[i] = pView.findViewById(resID);

            final int finalI = i;
            circleSize[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onSizeChanged(finalI);
                }
            });

        }

        // TODO: 7/4/2018 AD change resource with selected color in sharepreference

        btnIncreaseSize = pView.findViewById(R.id.btn_plus_thick);
        btnDecreaseSize = pView.findViewById(R.id.btn_minos_thick);

        btnIncreaseSize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedSize < 4) {
                    onSizeChanged(selectedSize + 1);
                }
            }
        });

        btnDecreaseSize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedSize > 0) {
                    onSizeChanged(selectedSize - 1);
                }
            }
        });

        btnTypePencil = pView.findViewById(R.id.image_type_pencil);
        btnTypeBrush = pView.findViewById(R.id.image_type_brush);
        btnTypeEraser = pView.findViewById(R.id.image_type_eraser);

        btnTypePencil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onTypeChanged(PaintType.PENCIL);
            }
        });

        btnTypeBrush.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onTypeChanged(PaintType.BRUSH);
            }
        });

        btnTypeEraser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onTypeChanged(PaintType.ERASER);
            }
        });

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else if (getParentFragment() instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) getParentFragment();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    private void onSizeChanged(int size) {
        selectedSize = size;

        for (int i = 0; i < circleSize.length; i++) {
            circleSize[i].setImageResource(R.drawable.bgr_color_picker);
        }

        for (int i = 0; i <= selectedSize; i++) {
            circleSize[i].setImageDrawable(selectedColoePickerDrawable);
        }


        if (mListener != null) {
            mListener.onPaintSizeSelected(size);

        }
    }

    private void onTypeChanged(PaintType paintType) {
        setTypeViews(paintType);
        if (mListener != null) {
            mListener.onPaintTypeSelected(paintType);
        }
    }

    private void setTypeViews(PaintType paintType) {
        this.paintType = paintType;

        btnTypeBrush.setImageResource(R.drawable.icon_type_brush_normal);
        btnTypePencil.setImageResource(R.drawable.icon_type_pencil_normal);
        btnTypeEraser.setImageResource(R.drawable.icon_type_eraser_normal);

        if (paintType == PaintType.PENCIL) {
            btnTypePencil.setImageResource(R.drawable.icon_type_pencil_selected);
        } else if (paintType == PaintType.BRUSH) {
            btnTypeBrush.setImageResource(R.drawable.icon_type_brush_selected);
        } else if (paintType == PaintType.ERASER) {
            btnTypeEraser.setImageResource(R.drawable.icon_type_eraser_selected);
        }
    }

    private PaintType getPaintType() {
        return this.paintType;
    }


    @Override
    public void onColorPickerClicked(View view, int colorResource) {
        selectedColoePickerDrawable = ((CircleColorPicker) view).getImageDrawable();
        onSizeChanged(selectedSize);


        this.colorResource = colorResource;

        if (getPaintType() == PaintType.ERASER) {
            setTypeViews(PaintType.PENCIL);
            if (mListener != null) {
                mListener.onPaintTypeSelected(getPaintType());
            }
        }

        if (mListener != null) {
            mListener.onColorSelected(colorResource);
        }
    }


    public interface OnFragmentInteractionListener {
        void onColorSelected(int color);

        void onPaintTypeSelected(PaintType paintType);

        void onPaintSizeSelected(int size);
    }
}
