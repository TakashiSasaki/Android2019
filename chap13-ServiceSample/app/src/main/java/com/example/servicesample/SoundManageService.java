package com.example.servicesample;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.IBinder;
import android.util.Log;

import java.io.IOException;

public class SoundManageService extends Service {
    //public SoundManageService() {
    //}

    private MediaPlayer _player;

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        this._player = new MediaPlayer();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String mediaFileUriStr = "android.resource://" +
                getPackageName() +  "/" + R.raw.mountain_stream;
        Uri mediaFileUri = Uri.parse(mediaFileUriStr);
        try {
            //setDataSourceはいろいろな引数を取るメソッドがオーバーロードされているので注意。
            //間違った引数を与えてもコンパイル時にはエラーにならず気づきにくい。
            this._player.setDataSource(getApplicationContext(), mediaFileUri);
            this._player.setOnPreparedListener(new PlayerPreparedListener());
            this._player.setOnCompletionListener(new PlayerCompletionListener());
            this._player.prepareAsync();
        }catch(IOException e){
            e.printStackTrace();
        }

        //return super.onStartCommand(intent, flags, startId);
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(_player.isPlaying()){
            _player.stop();
        }
        _player.release();
        _player = null;
    }

    class PlayerPreparedListener implements MediaPlayer.OnPreparedListener{

        @Override
        public void onPrepared(MediaPlayer mp) {
            Log.d("onPrepared", "onPrepared");
            mp.start();
        }
    }

    class PlayerCompletionListener implements MediaPlayer.OnCompletionListener{

        @Override
        public void onCompletion(MediaPlayer mp) {
            stopSelf(); // stop previously invoked service
        }
    }
}
