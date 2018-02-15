package memory.bestmemorygames.boulier;

import android.app.Activity;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.Log;


public class Son {
    private int sonId;
    private static final String TAG = "Son";
    private SoundPool soundPool;
    private boolean loaded = false;

    public Son(int idtoLoad, Activity vue) {
        vue.setVolumeControlStream(AudioManager.STREAM_MUSIC);
        soundPool = new SoundPool(20, AudioManager.STREAM_MUSIC, 0);

        AssetManager assetManager = vue.getAssets();

        AssetFileDescriptor descriptor = null;
        sonId = soundPool.load(vue, idtoLoad, 1);
        soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                Log.d(TAG, "Le son est prêt pour utilisation");
                loaded = true;
            }
        });
    }

    public void jouer() {
        if (loaded)
            soundPool.play(sonId, 100, 100, 0, 0, 1);
        else
            Log.d(TAG, "Le son n'est pas encore prêt");
    }

    public void jouerEnBoucle() {
        //s1.loop();
    }

    public void arreter() {
        //s1.stop();
    }
}
