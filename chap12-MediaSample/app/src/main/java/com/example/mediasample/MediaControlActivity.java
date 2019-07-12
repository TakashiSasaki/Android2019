package com.example.mediasample;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

import java.io.IOException;

public class MediaControlActivity extends AppCompatActivity {

    private MediaPlayer _player;
    private Button _btBack;
    private Button _btForward;
    private Button _btPlay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_control);
        _btBack = findViewById(R.id.btBack);
        _btForward = findViewById(R.id.btForward);
        _btPlay = findViewById(R.id.btPlay);
        Switch swLoop = findViewById(R.id.swLoop);
        swLoop.setOnCheckedChangeListener(new LoopSwitchChangedListener());
        _player = new MediaPlayer();

        SurfaceView surfaceView = findViewById(R.id.surfaceView);
        SurfaceHolder surfaceHolder = surfaceView.getHolder();
        surfaceHolder.addCallback(new SurfaceHolderCallback());



        String mediaFileUriStr =
                "android.resource://" +
                        getPackageName() +
                        "/" +
                        /*R.raw.mountain_stream*/
                R.raw.kanikan_large;
        Log.i("onCreate", mediaFileUriStr);
        Uri mediaFileUri = Uri.parse(mediaFileUriStr);
        try {
            _player.setDataSource(
                    getApplicationContext(),
                    mediaFileUri);
            _player.setOnPreparedListener(
                    new PlayerPreparedListener());
            _player.setOnCompletionListener(
                    new PlayerCompletionListener());
            _player.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
        }//try
    }//onClick

    private class PlayerPreparedListener
            implements MediaPlayer.OnPreparedListener {

        @Override
        public void onPrepared(MediaPlayer mp) {
            _btBack.setEnabled(true);
            _btForward.setEnabled(true);
            _btPlay.setEnabled(true);
        }
    }

    private class PlayerCompletionListener
            implements MediaPlayer.OnCompletionListener {

        @Override
        public void onCompletion(MediaPlayer mp) {
            if(!_player.isLooping() /*isPlaying()でもOK*/) {
                _btPlay.setText(R.string.bt_play_play);
            }
        }
    }

    void onPlayButtonClick(View view) {
        if (_player.isPlaying()) {
            _player.pause();
            _btPlay.setText(R.string.bt_play_play);
        } else {
            _player.start();
            _btPlay.setText(R.string.bt_play_pause);
        }
    }

    void onBackButtonClick(View view) {
        _player.seekTo(0);
    }

    void onForwardButtonClick(View view) {
        int duration = _player.getDuration();
        _player.seekTo(duration);
        if (!_player.isPlaying()) {
            _player.start();
        }//if
    }//onForwardButtonClick

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (_player.isPlaying()) {
            _player.stop();
        }
        _player.release();
        _player = null;
    }

    private class LoopSwitchChangedListener
            implements CompoundButton.OnCheckedChangeListener {

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            _player.setLooping(isChecked);
        }
    }

    private class SurfaceHolderCallback
     implements SurfaceHolder.Callback {

        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            _player.setDisplay(holder);
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {

        }
    }
}//class MediaControlActivity
