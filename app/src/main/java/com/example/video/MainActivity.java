package com.example.video;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.MediaController;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.VideoView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    MediaPlayer mp;
    VideoView videoView;
    AudioManager audioManager;
    public void playAudio(View view){
        videoView.start();
        mp.start();
    }
    public void pauseAudio(View view){
        mp.pause();
        videoView.pause();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        videoView = (VideoView) findViewById(R.id.videoView);
        videoView.setVideoPath("android.resource://"+getPackageName()+"/"+R.raw.video);
        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);
        mp = MediaPlayer.create(this,R.raw.thunder);
        AudioManager audioManager=(AudioManager) getSystemService(Context.AUDIO_SERVICE);
        int maxVol = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int curVol=audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        SeekBar volctrl = (SeekBar) findViewById(R.id.seekBar);
        volctrl.setMax(maxVol);
        volctrl.setProgress(curVol);
        volctrl.setOnSeekBarChangeListener(new OnSeekBarChangeListener(){

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,progress,0);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        AudioManager am=(AudioManager) getSystemService(Context.AUDIO_SERVICE);
        int len;
        SeekBar tctrl=(SeekBar) findViewById(R.id.timingbar);
        tctrl.setMax(mp.getDuration());
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                tctrl.setProgress(mp.getCurrentPosition());
            }
        },0,10000);
        tctrl.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mp.seekTo(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                mp.pause();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mp.start();
            }
        });
    }
}