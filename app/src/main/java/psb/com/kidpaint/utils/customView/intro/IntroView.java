package psb.com.kidpaint.utils.customView.intro;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import psb.com.kidpaint.R;

public class IntroView extends RelativeLayout {

    FrameLayout frameTop;
    FrameLayout frameBottom;
    FrameLayout frameRight;
    FrameLayout frameLeft;

    View holderView;

    Button btnAccept;

    private OnAcceptPressed onAcceptPressed;


    public IntroView(Context context) {
        super(context);
        init();
    }

    public IntroView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public IntroView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.intro_layout, this);
        frameTop = findViewById(R.id.frm_top);
        frameBottom = findViewById(R.id.frm_bottom);
        frameRight = findViewById(R.id.frm_right);
        frameLeft = findViewById(R.id.frm_left);

        holderView = findViewById(R.id.holder);

        btnAccept=findViewById(R.id.btn_accept);

        btnAccept.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onAcceptPressed != null) {
                    onAcceptPressed.onAccept();
                }
            }
        });

    }
    int[] location=new int[2];

    public void setup(View showCase){

        showCase.getLocationOnScreen(location);

        RelativeLayout.LayoutParams paramsTop=new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,location[1]);
        paramsTop.addRule(ALIGN_PARENT_TOP,TRUE);
        frameTop.setLayoutParams(paramsTop);

        RelativeLayout.LayoutParams paramsLeft=new LayoutParams(location[0], ViewGroup.LayoutParams.MATCH_PARENT);
        paramsTop.addRule(ALIGN_PARENT_LEFT,TRUE);
        frameLeft.setLayoutParams(paramsLeft);


        RelativeLayout.LayoutParams paramsShowCase=new LayoutParams(showCase.getWidth(), showCase.getHeight());
        paramsShowCase.addRule(RelativeLayout.RIGHT_OF,frameLeft.getId());
        paramsShowCase.addRule(RelativeLayout.BELOW,frameTop.getId());
        holderView.setLayoutParams(paramsShowCase);



        RelativeLayout.LayoutParams paramsRight=new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        paramsRight.addRule(RelativeLayout.RIGHT_OF,holderView.getId());
        frameRight.setLayoutParams(paramsRight);



        RelativeLayout.LayoutParams paramsBottom=new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        paramsBottom.addRule(RelativeLayout.BELOW,holderView.getId());
        frameBottom.setLayoutParams(paramsBottom);

    }

    public void setOnAcceptPressed(OnAcceptPressed onAcceptPressed) {
        this.onAcceptPressed = onAcceptPressed;
    }

    public void addView(int layoutId, IntroPosition position) {

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View myView =  inflater.inflate(layoutId, null);
        RelativeLayout.LayoutParams params=new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,location[1]);

        switch (position) {
            case TOP:
                frameTop.addView(myView);
                break;
            case BOTTOM:
                frameBottom.addView(myView);
                break;
            case LEFT:
                frameLeft.addView(myView);
                break;
            case RIGHT:
                frameRight.addView(myView);
                break;
        }
    }

    public interface OnAcceptPressed{
        void onAccept();
    }
}
