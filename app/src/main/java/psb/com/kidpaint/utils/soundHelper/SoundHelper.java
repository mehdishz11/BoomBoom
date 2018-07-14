package psb.com.kidpaint.utils.soundHelper;

import android.annotation.TargetApi;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.util.Log;

import java.util.ArrayList;

import psb.com.kidpaint.App;

import static android.content.Context.AUDIO_SERVICE;

public class SoundHelper {


    private static SoundPool sound;

    private static ArrayList<SoundModel> arrSound=new ArrayList<>();
    private static int periority=0;

    public SoundHelper() {
        createSound();
    }

    private static void createSound(){
        if (sound == null) {
            sound = createSoundPool();
            sound.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
                @Override
                public void onLoadComplete(SoundPool soundPool, int i, int i1) {
                    setSoundLoaded(i);
                    play(i);
                }
            });
        }
    }

    public static void playSound(int resId){
        createSound();
        int position=-1;
        for (int i=0;i<arrSound.size();i++) {
            if(arrSound.get(i).getResId()==resId){
                position=i;
                break;
            }
        }

        if(position==-1){
            int soundId=sound.load(App.getContext(), resId, 1);
            SoundModel soundModel=new SoundModel();
            soundModel.setResId(resId);
            soundModel.setSoundID(soundId);
            arrSound.add(soundModel);
        }else{
            if(arrSound.get(position).isLoaded()) {
                play(arrSound.get(position).getSoundID());
            }
        }
    }

    private static void setSoundLoaded(int soundId){
        for (int i=0;i<arrSound.size();i++) {
            if(arrSound.get(i).getSoundID()==soundId){
                arrSound.get(i).setLoaded(true);
                break;
            }
        }
    }


    protected static SoundPool createSoundPool() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return createNewSoundPool();
        } else {
            return createOldSoundPool();
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    protected static SoundPool createNewSoundPool() {
        AudioAttributes attributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();
        return new SoundPool.Builder()
                .setAudioAttributes(attributes)
                .build();
    }

    @SuppressWarnings("deprecation")
    protected static SoundPool createOldSoundPool() {
        return new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
    }

    protected static float getVolume() {
        AudioManager audioManager = (AudioManager) App.getContext().getSystemService(AUDIO_SERVICE);
        float actVolume = (float) audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        float maxVolume = (float) audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        float volume = actVolume / maxVolume;
        return volume;
    }

    protected static void play(int soundId) {
        Log.d(App.TAG, "play sound Id is : "+soundId);
        sound.play(soundId, getVolume(), getVolume(), 1, 0, 1F);
    }

}
