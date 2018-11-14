package psb.com.kidpaint.utils.musicHelper;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;

public class SingleMusicPlayer {

    public void playSingleMedia(final String url) {
        new LongOperation().execute(url);
    }

    class LongOperation extends AsyncTask<String, Void, Boolean> {

        long mseconds;

        @Override
        protected Boolean doInBackground(String... params) {
            try {
                MediaPlayer mediaPlayer = new MediaPlayer();
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

                mediaPlayer.setVolume(1.0f, 1.0f);
                mediaPlayer.setLooping(false);

                mediaPlayer.setDataSource(params[0]);
                mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mediaPlayer) {

                        long nowMseconds = System.currentTimeMillis();
                        if (nowMseconds - mseconds < 1000 * 2) {
                            mediaPlayer.start();
                        }
                    }
                });
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        mediaPlayer.release();

                    }
                });

                mediaPlayer.prepare();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return true;
        }

        @Override
        protected void onPostExecute(Boolean result) {

            // might want to change "executed" for the returned string passed
            // into onPostExecute() but that is upto you
        }

        @Override
        protected void onPreExecute() {
            mseconds = System.currentTimeMillis();

        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }
    }
}
