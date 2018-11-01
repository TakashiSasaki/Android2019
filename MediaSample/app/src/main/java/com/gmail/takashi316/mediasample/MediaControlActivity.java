package com.gmail.takashi316.mediasample;

import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

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

        String mediaFileUriStr = "andorid.resource://"
                + getPackageName() + "/"
                + R.raw.kanikan_high;

        // 以下のような文字列が組み立てられている↑
        String mediaFileUriStr2 = "android.resource://"
                + "com.gmail.takashi316.mediasample/"
                + Integer.parseInt("0x7f0b0000",16);

        //URI文字列をURIオブジェクトに変換
        Uri mediaFileUri = Uri.parse(mediaFileUriStr);

        try {
            _player.setDataSource(MediaControlActivity.this, mediaFileUri);
        }catch (Exception e){

        }

    }//onCreate
}//MediaControlActivity

