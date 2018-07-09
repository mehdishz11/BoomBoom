package psb.com.paintingview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import psb.com.cview.SquareRelativeLayout;

public class CircleColorPicker extends SquareRelativeLayout {

    private String colorResource ;

    private Drawable imageDrawable;
    private ColorPickerClickListener colorPickerClickListener;
    private ImageView imgColorPicker;



    public CircleColorPicker(Context context) {
        super(context);

        initView();
    }

    public CircleColorPicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttr(attrs);
        initView();
    }

    public CircleColorPicker(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttr(attrs);
        initView();
    }

    public CircleColorPicker(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initAttr(attrs);
        initView();
    }

    public void setColorPickerClickListener(ColorPickerClickListener colorPickerClickListener) {
        this.colorPickerClickListener = colorPickerClickListener;
    }

    public int getColor() {
        return Color.parseColor(colorResource);
    }





    private void initAttr(AttributeSet attrs) {
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.CircleColorPicker);
        colorResource = a.getString(R.styleable.CircleColorPicker_colorResource);

        imageDrawable = a.getDrawable(R.styleable.CircleColorPicker_imageResource);
        a.recycle();
    }

    private void initView() {
        inflate(getContext(), R.layout.circle_color_picker, this);
        imgColorPicker = findViewById(R.id.img_color_picker);
//        imgColorPicker.setImageResource(imageResource);

        imgColorPicker.setImageDrawable(imageDrawable);

        imgColorPicker.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (colorPickerClickListener != null) {
                    colorPickerClickListener.onColorPickerClicked(CircleColorPicker.this,
                            getColor());
                }
            }
        });

    }

    public Drawable getImageDrawable(){
        return imgColorPicker.getDrawable();
    }


    public interface ColorPickerClickListener {
        void onColorPickerClicked(View view, int color);
    }

}
