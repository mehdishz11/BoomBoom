package psb.com.kidpaint.utils.customView.dialog.prize;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.fragment.app.DialogFragment;
import androidx.core.content.ContextCompat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import psb.com.kidpaint.R;

public class DialogPrizeDescription extends DialogFragment {

    private static final String KEY_FRG_SEASON_DESC = "KEY_FRG_SEASON_DESC";
    private static final String KEY_FRG_JURY_DESC = "KEY_FRG_JURY_DESC";
    private static final String KEY_FRG_GUIDE_RATE = "KEY_FRG_GUIDE_RATE";
    private static final String KEY_FRG_GUIDE_COMPETITION = "KEY_FRG_GUIDE_COMPETITION";
    private View pView;

    private TextView textTab1;
    private TextView textTab2;
    private TextView textTab3;
    private TextView textTab4;

    private View line1;
    private View line2;
    private View line3;
    private View line4;

    private SeasonDescriptionPrizeFragment seasonDescriptionPrizeFragment;
    private JuryDescriptionPrizeFragment juryDescriptionPrizeFragment;
    private GuideRateFragment guideRateFragment;
    private GuideCompetitionFragment guideCompetitionFragment;

    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {

        // the content
        final RelativeLayout root = new RelativeLayout(getActivity());
        root.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        // creating the fullscreen dialog
        final Dialog dialog = new Dialog(getActivity());
      /*  dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(root);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
*/


        dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        dialog.getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);

        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));


        return dialog;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        pView = inflater.inflate(R.layout.content_dialog_prize, container, false);
        pView.findViewById(R.id.img_btn_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismissAllowingStateLoss();
            }
        });

        textTab1=pView.findViewById(R.id.text_tab_1);
        textTab2=pView.findViewById(R.id.text_tab_2);
        textTab3=pView.findViewById(R.id.text_tab_3);
        textTab4=pView.findViewById(R.id.text_tab_4);

        line1=pView.findViewById(R.id.line_tab_1);
        line2=pView.findViewById(R.id.line_tab_2);
        line3=pView.findViewById(R.id.line_tab_3);
        line4=pView.findViewById(R.id.line_tab_4);

        textTab1.setOnClickListener(new OnClickTab(1));
        textTab2.setOnClickListener(new OnClickTab(2));
        textTab3.setOnClickListener(new OnClickTab(3));
        textTab4.setOnClickListener(new OnClickTab(4));


        getChildFragmentManager().beginTransaction().add(R.id.frame_layout,new SeasonDescriptionPrizeFragment(),KEY_FRG_SEASON_DESC).commitAllowingStateLoss();

        return pView;
    }


    class OnClickTab implements View.OnClickListener{
            int index;
        public OnClickTab(int index) {
            this.index = index;
        }

        @Override
        public void onClick(View v) {
            onTabSelected(index);
        }
    }

    private void onTabSelected(int index){
        textTab1.setTextColor(ContextCompat.getColor(getContext(),R.color.brown_2));
        textTab2.setTextColor(ContextCompat.getColor(getContext(),R.color.brown_2));
        textTab3.setTextColor(ContextCompat.getColor(getContext(),R.color.brown_2));
        textTab4.setTextColor(ContextCompat.getColor(getContext(),R.color.brown_2));

        line1.setVisibility(View.INVISIBLE);
        line2.setVisibility(View.INVISIBLE);
        line3.setVisibility(View.INVISIBLE);
        line4.setVisibility(View.INVISIBLE);

        if(index==1){
            textTab1.setTextColor(ContextCompat.getColor(getContext(),R.color.brown_3));
            line1.setVisibility(View.VISIBLE);
            if (seasonDescriptionPrizeFragment == null) {
                seasonDescriptionPrizeFragment=new SeasonDescriptionPrizeFragment();
            }
            getChildFragmentManager().beginTransaction().replace(R.id.frame_layout,seasonDescriptionPrizeFragment,KEY_FRG_SEASON_DESC).commitAllowingStateLoss();
        }else if(index==2){
            textTab2.setTextColor(ContextCompat.getColor(getContext(),R.color.brown_3));
            line2.setVisibility(View.VISIBLE);
            if (juryDescriptionPrizeFragment == null) {
                juryDescriptionPrizeFragment=new JuryDescriptionPrizeFragment();
            }
            getChildFragmentManager().beginTransaction().replace(R.id.frame_layout,juryDescriptionPrizeFragment,KEY_FRG_JURY_DESC).commitAllowingStateLoss();
        }else if(index==3){
            textTab3.setTextColor(ContextCompat.getColor(getContext(),R.color.brown_3));
            line3.setVisibility(View.VISIBLE);
            if (guideRateFragment == null) {
                guideRateFragment=new GuideRateFragment();
            }
            getChildFragmentManager().beginTransaction().replace(R.id.frame_layout,guideRateFragment,KEY_FRG_GUIDE_RATE).commitAllowingStateLoss();
        }else if(index==4){
            textTab4.setTextColor(ContextCompat.getColor(getContext(),R.color.brown_3));
            line4.setVisibility(View.VISIBLE);
            if (guideCompetitionFragment == null) {
                guideCompetitionFragment=new GuideCompetitionFragment();
            }
            getChildFragmentManager().beginTransaction().replace(R.id.frame_layout,guideCompetitionFragment,KEY_FRG_GUIDE_COMPETITION).commitAllowingStateLoss();
        }

    }
}
