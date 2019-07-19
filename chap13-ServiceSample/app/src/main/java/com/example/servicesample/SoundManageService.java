package com.example.servicesample;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
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
        //super.onCreate();
        this._player = new MediaPlayer();
        String id = "soundmanagerservice_notification_channel";
        String name = getString(R.string.notification_channel_name);
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel = new NotificationChannel(
                id, name, importance);
        NotificationManager manager =
                (NotificationManager) getSystemService
                        (Context.NOTIFICATION_SERVICE);
        manager.createNotificationChannel(channel);
    }//onCreate

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String mediaFileUriStr = "android.resource://" +
                getPackageName() + "/" + R.raw.mountain_stream;
        Uri mediaFileUri = Uri.parse(mediaFileUriStr);
        try {
            //setDataSourceはいろいろな引数を取るメソッドがオーバーロードされているので注意。
            //間違った引数を与えてもコンパイル時にはエラーにならず気づきにくい。
            this._player.setDataSource(getApplicationContext(), mediaFileUri);
            this._player.setOnPreparedListener(new PlayerPreparedListener());
            this._player.setOnCompletionListener(new PlayerCompletionListener());
            this._player.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //return super.onStartCommand(intent, flags, startId);
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        //super.onDestroy();
        if (_player.isPlaying()) {
            _player.stop();
        }
        _player.release();
        _player = null;
    }

    class PlayerPreparedListener
            implements MediaPlayer.OnPreparedListener {

        @Override
        public void onPrepared(MediaPlayer mp) {
            Log.d("onPrepared", "onPrepared");
            mp.start();
            NotificationCompat.Builder builder =
                    new NotificationCompat.Builder(
                            getApplicationContext(),
                            "soundmanagerservice_notification_channel"
                    );
            builder.setSmallIcon(android.R.drawable.ic_dialog_info);
            builder.setContentTitle(getString(R.string.msg_notification_title_start));
            builder.setContentText(getString(R.string.msg_notification_text_start));
            Intent intent = new Intent(
                    getApplicationContext(),
                    MainActivity.class
            );
            intent.putExtra("fromNotification", true);
            PendingIntent stopServiceIntent
                    = PendingIntent.getActivity(
                    getApplicationContext(),
                    0,
                    intent,
                    PendingIntent.FLAG_CANCEL_CURRENT
            );
            builder.setContentIntent(stopServiceIntent);
            builder.setAutoCancel(true);
            Notification notification = builder.build();
            NotificationManager manager =
                    (NotificationManager)
                            getSystemService(Context.NOTIFICATION_SERVICE);
            manager.notify(1, notification);
        }//onPrepared
    }//class PlayerPreparedListener

    class PlayerCompletionListener implements MediaPlayer.OnCompletionListener {

        @Override
        public void onCompletion(MediaPlayer mp) {
            NotificationCompat.Builder builder
                    = new NotificationCompat.Builder(
                    getApplicationContext(),
                    "soundmanagerservice_notification_channel");
            builder.setSmallIcon(android.R.drawable.ic_dialog_info);
            builder.setContentTitle(getString(R.string.msg_notification_title_finish));
            builder.setContentText(getString(R.string.msg_notification_text_finish));
            Notification notification = builder.build();
            NotificationManager manager = (NotificationManager)
                    getSystemService(Context.NOTIFICATION_SERVICE);
            manager.notify(0, notification);
            stopSelf(); // stop previously invoked service
        }
    }
}
