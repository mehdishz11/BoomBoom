package psb.com.kidpaint.utils.musicHelper;

import android.media.MediaPlayer;
import android.net.Uri;

import psb.com.kidpaint.App;
import psb.com.kidpaint.utils.Value;
import psb.com.kidpaint.utils.sharePrefrence.SharePrefrenceHelper;

public class MusicHelper {


    private static MediaPlayer mediaPlayer;

    public static void playMusic(int rsId) {
        try {
            if (!SharePrefrenceHelper.getMusic() || (mediaPlayer != null && mediaPlayer.isPlaying())) {
                return;
            }

            if (mediaPlayer == null) {
                mediaPlayer = MediaPlayer.create(App.getContext(), rsId);
                mediaPlayer.setVolume(0.5f, 0.5f);
                mediaPlayer.setLooping(true);
                mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mediaPlayer) {
                        MusicHelper.mediaPlayer.start();
                    }
                });
//                mediaPlayer.prepare();
//                mediaPlayer.start();
            } else {
                mediaPlayer.start();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void stopMusic() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }


    public static void playSingleMedia(int resId) {
        if (!SharePrefrenceHelper.getMusic()) return;
        MediaPlayer mediaPlayer = MediaPlayer.create(App.getContext(), resId);
        mediaPlayer.setVolume(1.0f, 1.0f);
        mediaPlayer.setLooping(false);
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mediaPlayer.start();
            }
        });
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                mediaPlayer.release();
            }
        });
    }

    public static void playSingleMedia(final String url) {
        try {

            MediaPlayer mediaPlayer = MediaPlayer.create(App.getContext(), Uri.parse(Value.encodeUrl(url)));
            mediaPlayer.setVolume(1.0f, 1.0f);
            mediaPlayer.setLooping(false);
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    mediaPlayer.start();
                }
            });
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    mediaPlayer.release();
                }
            });
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


}
