package com.gmail.takashi316.mediasample;

import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.IOException;

public class MediaControlActivity extends AppCompatActivity {

    private MediaPlayer _player;
    // mMediaPlayer とか mediaPlayer とか書く人もいる

    private Button _btPlay;
    private Button _btBack;
    private Button _btForward;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_control);

        this._btPlay = findViewById(R.id.btPlay);
        this._btBack = findViewById(R.id.btBack);
        this._btForward = findViewById(R.id.btForward);
        this._player = new MediaPlayer();

        String mediaFileUriStr = "android.resource://"
                + getPackageName() + "/"
                + R.raw.kanikan_high;

        // 以下のような文字列が組み立てられている↑
        //String mediaFileUriStr2 = "android.resource://"
        //        + "com.gmail.takashi316.mediasample/"
        //        + Integer.parseInt("7f0b0000",16);

        //URI文字列をURIオブジェクトに変換
        Uri mediaFileUri = Uri.parse(mediaFileUriStr);

        try {
            _player.setDataSource(MediaControlActivity.this, mediaFileUri);
            _player.setOnPreparedListener(new PlayerPreparedListener());
            _player.setOnCompletionListener(new PlayerCompletionListener());
            _player.prepareAsync();
        }catch (IOException e){
            e.printStackTrace();
        }//try

    }//onCreate

    private class PlayerPreparedListener
            implements MediaPlayer.OnPreparedListener {

        @Override
        public void onPrepared(MediaPlayer mediaPlayer) {
            _btBack.setEnabled(true);
            _btPlay.setEnabled(true);
            _btForward.setEnabled(true);
        }//onPrepared

    }//PlayerPreparedListener

    private class PlayerCompletionListener
        implements MediaPlayer.OnCompletionListener {

        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            _btPlay.setText(R.string.bt_play_play);
        }//onCompletion
    }//PlayerCompletionListener

    public void onPlayButtonClick(View view){
        if(_player.isPlaying()) {
            _player.pause();
            _btPlay.setText(R.string.bt_play_play);
        } else {
            _player.start();
            _btPlay.setText(R.string.bt_play_pause);
        }//if
    }//onPlayButtonClick

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(_player.isPlaying()) {
            _player.start();
        } else {
            //特になにもすることなし
        }//if
        _player = null;
    }//onDestroy
}//MediaControlActivity
