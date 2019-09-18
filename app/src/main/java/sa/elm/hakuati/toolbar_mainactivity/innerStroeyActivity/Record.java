package sa.elm.hakuati.toolbar_mainactivity.innerStroeyActivity;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Date;

import sa.elm.hakuati.toolbar_mainactivity.R;

public class Record extends AppCompatActivity implements View.OnClickListener {
    private MediaRecorder mediaRecorder;
    private static int records = 1;
    private Button stop;
    private Button start;
    private Button pause;
    private String outputFile;
    private AudioRecord recorder;


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recording_dialog);
        init();

        recorder = new AudioRecord(MediaRecorder.AudioSource.MIC,
                8000, AudioFormat.CHANNEL_CONFIGURATION_MONO,
                AudioFormat.ENCODING_PCM_16BIT, 500000);


        Date createdTime = new Date();
        outputFile = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" +"_rec.3gp";
        //mediaRecorder.setOutputFile(outputFile);


    }// on create

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.start_recording:

                //mediaRecorder.prepare();
                recorder.startRecording();


                Log.e("LOG", "recording!!!!!!!!!!!!");
                break;
            case R.id.stop_recording:


                recorder.stop();
                Log.e("LOG", "STOP!!!!!!!!!!!!");

                break;
            case R.id.pause_recording:
                mediaRecorder.pause();
                Log.e("LOG", "PAUSE!!!!!!!!!!!!");


                break;
        }// switch
    }


    private void init(){
        stop = findViewById(R.id.stop_recording);
        start = findViewById(R.id.start_recording);
        pause = findViewById(R.id.pause_recording);
        mediaRecorder = new MediaRecorder();

        stop.setOnClickListener(this);
        start.setOnClickListener(this);
        pause.setOnClickListener(this);

    }
}// end class
