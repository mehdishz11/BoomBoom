package psb.com.kidpaint.utils.customView.dialog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.nightonke.jellytogglebutton.JellyToggleButton;
import com.nightonke.jellytogglebutton.State;

import psb.com.cview.IconFont;
import psb.com.kidpaint.R;
import psb.com.kidpaint.utils.musicHelper.MusicHelper;
import psb.com.kidpaint.utils.sharePrefrence.SharePrefrenceHelper;
import psb.com.kidpaint.utils.soundHelper.SoundHelper;

public class DialogSettings extends CDialog {


    private boolean defaultSound =SharePrefrenceHelper.getSoundEffect();
    private boolean defaultMusic =SharePrefrenceHelper.getMusic();

    public DialogSettings(@NonNull Context context) {
        super(context);
    }

    public DialogSettings(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected DialogSettings(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void inflateChildView(LinearLayout relParent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View inflatedLayout = inflater.inflate(R.layout.dialog_settings, null, false);
        relParent.addView(inflatedLayout);
        init();

    }

    @Override
    protected void onAccept() {

    }

    @Override
    protected void onCancel() {
        SharePrefrenceHelper.setSoundEffect(defaultSound);
        SharePrefrenceHelper.setMusic(defaultMusic);

        if(SharePrefrenceHelper.getMusic()) {
            MusicHelper.playMusic(R.raw.bgr_be_happy);
        }else{
            MusicHelper.stopMusic();
        }

    }

    @Override
    protected OnCLickListener getOnCLickListener() {
        return null;
    }


    private void init(){
        final JellyToggleButton toggleSound=findViewById(R.id.toggle_speaker);
        final JellyToggleButton toggleMusic=findViewById(R.id.toggle_sound);

        final IconFont iconSpeaker=findViewById(R.id.icon_speaker);
        final IconFont iconSound=findViewById(R.id.icon_sound);

        toggleSound.setCheckedImmediately(!SharePrefrenceHelper.getSoundEffect());
        iconSpeaker.setText(SharePrefrenceHelper.getSoundEffect()?"\uE905":"\uE904");

        toggleMusic.setCheckedImmediately(!SharePrefrenceHelper.getMusic());
        toggleMusic.setText(SharePrefrenceHelper.getMusic()?"\uE901":"\uE900");


        /*toggleSound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iconSpeaker.setText(toggleSound.isChecked()?"\uE904":"\uE905");
                SharePrefrenceHelper.setSoundEffect(!toggleSound.isChecked());
                toggleSound.setChecked(!toggleSound.isChecked());
                SoundHelper.playSound(R.raw.click_bubbles_1);
            }
        });*/

     /*   toggleMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iconSound.setText(!toggleMusic.isChecked()?"\uE901":"\uE900");

                SharePrefrenceHelper.setMusic(!toggleMusic.isChecked());
                toggleMusic.setChecked(!toggleMusic.isChecked());
                SoundHelper.playSound(R.raw.click_bubbles_1);
                if(SharePrefrenceHelper.getMusic()) {
                    MusicHelper.playMusic(R.raw.bgr_be_happy);
                }else{
                    MusicHelper.stopMusic();
                }
            }
        });*/

        toggleMusic.setOnStateChangeListener(new JellyToggleButton.OnStateChangeListener() {
            @Override
            public void onStateChange(float process, State state, JellyToggleButton jtb) {
                // process - current process of JTB, between [0, 1]
                // state   - current state of JTB, it is one of State.LEFT, State.LEFT_TO_RIGHT, State.RIGHT and State.RIGHT_TO_LEFT
                // jtb     - the JTB

                iconSound.setText(!toggleMusic.isChecked()?"\uE901":"\uE900");

                SharePrefrenceHelper.setMusic(!toggleMusic.isChecked());
               // toggleMusic.setChecked(!toggleMusic.isChecked());
                SoundHelper.playSound(R.raw.click_bubbles_1);
                if(SharePrefrenceHelper.getMusic()) {
                    MusicHelper.playMusic(R.raw.bgr_be_happy);
                }else{
                    MusicHelper.stopMusic();
                }
            }
        });


        toggleSound.setOnStateChangeListener(new JellyToggleButton.OnStateChangeListener() {
            @Override
            public void onStateChange(float process, State state, JellyToggleButton jtb) {
                // process - current process of JTB, between [0, 1]
                // state   - current state of JTB, it is one of State.LEFT, State.LEFT_TO_RIGHT, State.RIGHT and State.RIGHT_TO_LEFT
                // jtb     - the JTB

                iconSpeaker.setText(toggleSound.isChecked()?"\uE904":"\uE905");
                SharePrefrenceHelper.setSoundEffect(!toggleSound.isChecked());
               // toggleSound.setChecked(!toggleSound.isChecked());
                SoundHelper.playSound(R.raw.click_bubbles_1);
            }
        });


    }


}
