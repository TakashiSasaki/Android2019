package com.gmail.takashi316.mediasample;

import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

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

        Switch loopSwitch = findViewById(R.id.swLoop);
        loopSwitch.setOnCheckedChangeListener(
                new LoopSwitchChangedListener()
        );

        //SurfaceViewのSurfaceHolderにコールバックを設定
        SurfaceView surfaceView = findViewById(R.id.surfaceView);
        SurfaceHolder surfaceHolder = surfaceView.getHolder();
        surfaceHolder.addCallback(new SurfaceHolderCallback());

        String mediaFileUriStr = "android.resource://"
                + getPackageName() + "/"
                + R.raw.kanikan_small;

        //以下のような文字列が組み立てられている↑
        //mediaFileUriStr2は使わないけど例示のために残しておく
        String mediaFileUriStr2 = "android.resource://"
                + "com.gmail.takashi316.mediasample/"
                + Integer.parseInt("7f0b0000",16);

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
            if(!_player.isLooping()) {
                _btPlay.setText(R.string.bt_play_play);
            }//if
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
        if(_player != null && _player.isPlaying()) {
            _player.start();
        } else {
            //特になにもすることなし
        }//if
        _player = null;
    }//onDestroy

    public void onBackButtonClick(View view) {
        _player.seekTo(0);
    }

    public void onForwardButtonClick(View view){
        final int duration = _player.getDuration();
        _player.seekTo(duration);
        if(!_player.isPlaying()) {
            _player.start();
        }
    }//onForwardButtonClick

    private class LoopSwitchChangedListener
        implements CompoundButton.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged
                (CompoundButton compoundButton, boolean b) {
            _player.setLooping(b);
        }//onCheckedChanged
    }//LoopSwitchChangedListener

    // SurfaceView は準備ができたらSurfaceHolder.Callbackを呼び出す。
    //コールバックを受け取るためのクラスを用意おこうと思った。
    //ここでは無名プライベートクラスを定義してインスタンスを作る
    //onCreate のsetContentViewですでにSurfaceViewは作られている。
    //そのSurfaceViewに新たにSurfaceHolder.Callbackを設定するには？

    private class SurfaceHolderCallback
            implements SurfaceHolder.Callback {
        @Override
        public void surfaceCreated(SurfaceHolder surfaceHolder) {
            _player.setDisplay(surfaceHolder);
        }//surfaceCreated

        @Override
        public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
            //SurfaceView のサイズが変更されたときなどに呼び出される
            //とりあえず今は何もしない。
        }//surfaceChanged

        @Override
        public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
            //アクティビティ自身のonDestroyでnullにされているかも知れない
            if(_player != null) _player.release();
            //アクティビティ自身のonDestroyで解放するので必須ではない
            _player = null;
        }//surfaceDestroyed
    };//SurfaceHolderCallback

}//MediaControlActivity
