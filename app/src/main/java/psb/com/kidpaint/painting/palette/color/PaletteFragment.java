package psb.com.kidpaint.painting.palette.color;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import psb.com.kidpaint.R;
import psb.com.kidpaint.utils.Value;
import psb.com.kidpaint.utils.soundHelper.SoundHelper;
import psb.com.paintingview.CircleColorPicker;


public class PaletteFragment extends Fragment implements CircleColorPicker.ColorPickerClickListener {

    private View pView;
    private OnFragmentInteractionListener mListener;

    private CircleColorPicker[] arrCircleColorPicker = new CircleColorPicker[12];
    private ImageView btnIncreaseSize, btnDecreaseSize;
    private ImageView[] circleSize = new ImageView[5];
    private ImageView btnTypePencil, btnTypeBucket, btnTypeEraser;
    private PaintType paintType = PaintType.PENCIL;

    private int soundResId[]=new int[]{R.raw.brown,R.raw.brown,R.raw.red,R.raw.orange,R.raw.yellow,R.raw.green,R.raw.green,R.raw.blue,R.raw.violet,R.raw.violet,R.raw.purple,R.raw.black};

    private int selectedSize = 3;
    private int colorResource;

    private RelativeLayout relOverlayPaletteColor;

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
            arrCircleColorPicker[i].setSoundResId(soundResId[i]);
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

        relOverlayPaletteColor= pView.findViewById(R.id.rel_overlay_color_picker);


        // TODO: 7/4/2018 AD change resource with selected color in sharepreference

        btnIncreaseSize = pView.findViewById(R.id.btn_plus_thick);
        btnDecreaseSize = pView.findViewById(R.id.btn_minos_thick);

        btnIncreaseSize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedSize < 4) {
                    onSizeChanged(selectedSize + 1);
                    SoundHelper.playSound(R.raw.click_1);
                }else{
                    SoundHelper.playSound(R.raw.lock_1);
                }
            }
        });

        btnDecreaseSize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedSize > 0) {
                    onSizeChanged(selectedSize - 1);
                    SoundHelper.playSound(R.raw.click_1);
                }else{
                    SoundHelper.playSound(R.raw.lock_1);
                }
            }
        });

        btnTypePencil = pView.findViewById(R.id.image_type_pencil);
        btnTypeBucket = pView.findViewById(R.id.image_type_brush);
        btnTypeEraser = pView.findViewById(R.id.image_type_eraser);

        btnTypePencil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onTypeChanged(PaintType.PENCIL);
            }
        });

        btnTypeBucket.setOnClickListener(new View.OnClickListener() {
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

        btnTypeBucket.setImageResource(R.drawable.icon_type_bucket_normal);
        btnTypePencil.setImageResource(R.drawable.icon_type_pencil_normal);
        btnTypeEraser.setImageResource(R.drawable.icon_type_eraser_normal);

        if (paintType == PaintType.PENCIL) {
            btnTypePencil.setImageResource(R.drawable.icon_type_pencil_selected);
        } else if (paintType == PaintType.BRUSH) {
            btnTypeBucket.setImageResource(R.drawable.icon_type_bucket_selected);
        } else if (paintType == PaintType.ERASER) {
            btnTypeEraser.setImageResource(R.drawable.icon_type_eraser_selected);
        }
    }

    private PaintType getPaintType() {
        return this.paintType;
    }


    @Override
    public void onColorPickerClicked(View view, int colorResource) {


        SoundHelper.playSound(((CircleColorPicker)view).getSoundResId());
        showAnimation(view);

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


    private void showAnimation(final View colorView){
        final ImageView imgColor=new ImageView(getContext());
        imgColor.setLayoutParams(new ViewGroup.LayoutParams(relOverlayPaletteColor.getHeight(),relOverlayPaletteColor.getHeight()));
        imgColor.setImageDrawable(((CircleColorPicker)colorView).getImageDrawable());
        imgColor.setScaleType(ImageView.ScaleType.FIT_CENTER);
        int location[]=new int[2];
        colorView.getLocationInWindow(location);

        final RelativeLayout relChild=(RelativeLayout)colorView.getParent();
        imgColor.setX(relChild.getX()-
                ((relOverlayPaletteColor.getHeight()-relChild.getWidth())/2)+
                Value.dp(5.0f));

        imgColor.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                imgColor.setPivotX(imgColor.getMeasuredWidth()/2);
                imgColor.setPivotY(imgColor.getMeasuredHeight()/2);

                float fromScale=(float)relChild.getWidth()/(float)imgColor.getWidth();

                imgColor.setScaleX(fromScale);
                imgColor.setScaleY(fromScale);

                ObjectAnimator animScaleX = ObjectAnimator.ofFloat(imgColor, "scaleX", fromScale, 1.0f, fromScale*0.7f,fromScale*0.9f,fromScale);
                ObjectAnimator animScaleY = ObjectAnimator.ofFloat(imgColor, "scaleY", fromScale, 1.0f, fromScale*0.7f,fromScale*0.9f,fromScale);
                animScaleY.setDuration(300);
                animScaleX.setDuration(300);

                final AnimatorSet scaleDown = new AnimatorSet();
//                scaleDown.setStartDelay(delay);
                scaleDown.play(animScaleX).with(animScaleY);

                scaleDown.start();
                scaleDown.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        relOverlayPaletteColor.removeAllViews();
                        colorView.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onAnimationStart(Animator animation) {
                        colorView.setVisibility(View.INVISIBLE);
                        super.onAnimationStart(animation);
                    }
                });




                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    imgColor.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }else{
                    imgColor.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                }
            }

        });

        relOverlayPaletteColor.addView(imgColor);
    }

    public interface OnFragmentInteractionListener {
        void onColorSelected(int color);

        void onPaintTypeSelected(PaintType paintType);

        void onPaintSizeSelected(int size);
    }
}
