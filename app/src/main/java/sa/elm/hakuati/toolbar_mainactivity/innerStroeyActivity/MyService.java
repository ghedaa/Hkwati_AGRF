package sa.elm.hakuati.toolbar_mainactivity.innerStroeyActivity;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.widget.Toast;

import androidx.annotation.NonNull;

import sa.elm.hakuati.toolbar_mainactivity.R;

public class MyService extends Service {
    public MediaPlayer mediaPlayer;


    @Override
    @NonNull
    public IBinder onBind(Intent intent) {
        return null;
    }


    public void onDestroy() {
        mediaPlayer.stop();
        Toast.makeText(getBaseContext(), "onDestroy", Toast.LENGTH_LONG).show();

    }


    public void onStart(Intent intent, int startId) {
        mediaPlayer.start();
        Toast.makeText(getBaseContext(), "onStart", Toast.LENGTH_LONG).show();
    }

    public void onCreate() {
        mediaPlayer = MediaPlayer.create(getBaseContext(), R.raw.child_story);
    }

     public int getDuration(){
        return mediaPlayer.getDuration();
     }
}

