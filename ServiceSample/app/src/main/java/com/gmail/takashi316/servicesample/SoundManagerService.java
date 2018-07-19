package com.gmail.takashi316.servicesample;

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

import java.io.IOException;

public class SoundManagerService extends Service {

    private MediaPlayer _player;

    //通知チャネルID
    private final static String notificationChannelId
            =  "soundmanagerservice_notification_channel";

    //public SoundManagerService() {
    //}

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }//onBind

    @Override
    public void onCreate() {
        super.onCreate();
        _player = new MediaPlayer();
        String name = getString(R.string.notification_channel_name);

        //通知チャネルを生成
        NotificationChannel channel =
                new NotificationChannel(notificationChannelId,
                        name, NotificationManager.IMPORTANCE_DEFAULT);

        NotificationManager manager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        //生成済みの通知チャネルをシステムの通知マネージャに登録
        manager.createNotificationChannel(channel);

    }//onCreate

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String mediaFileUriStr = "android.resource://"
                + this.getPackageName() + "/"
                + R.raw.mountain_stream;

        Uri mediaFileUri = Uri.parse(mediaFileUriStr);
        try {
            this._player.setDataSource(this.getApplicationContext(), mediaFileUri);
            this._player.setOnPreparedListener(new PlayerPreparedListener());
            this._player.setOnCompletionListener(new PlayerCompletionListener());
            this._player.prepareAsync();

        } catch(IOException e){
            e.printStackTrace();
        }

        //return super.onStartCommand(intent, flags, startId);
        return START_NOT_STICKY;
    }//onStartCommand

    private class PlayerPreparedListener implements MediaPlayer.OnPreparedListener{

        @Override
        public void onPrepared(MediaPlayer mediaPlayer) {
            mediaPlayer.start();
            Notification.Builder builder = new Notification.Builder(
                    getApplicationContext(), notificationChannelId);
            builder.setSmallIcon(android.R.drawable.ic_dialog_info);
            builder.setContentTitle(getString(R.string.msg_notification_title_start));
            builder.setContentText(getString(R.string.msg_notification_text_start));

            Intent intent = new Intent(getApplicationContext(), SoundServiceActivity.class);
            intent.putExtra("fromNotification", true);
            PendingIntent stopServiceIntent =  PendingIntent.getActivity
                    (getApplicationContext(), 0, intent,
                    PendingIntent.FLAG_CANCEL_CURRENT);
            builder.setContentIntent(stopServiceIntent);
            builder.setAutoCancel(true);
            Notification notification = builder.build();

            NotificationManager manager =
                    (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            manager.notify(1, notification);

        }//onPrepared

    }//PlayerPreparedListener

    private class PlayerCompletionListener implements MediaPlayer.OnCompletionListener{

        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            //SoundManagerService.this.stopSelf();
            //Support Libraryを使っている人はNotificationCompat
            Notification.Builder builder = new Notification.Builder(
                    getApplicationContext(), notificationChannelId);
            builder.setSmallIcon(android.R.drawable.ic_dialog_info);
            builder.setContentTitle(getString(R.string.msg_notification_title_finish));
            builder.setContentText(getString(R.string.msg_notification_text_finish));
            Notification notification = builder.build();
            NotificationManager manager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            manager.notify(0, notification);

            stopSelf();  //サービスを停止する

        }//onCompletion

    }//PlayerCompletionListener

    @Override
    public void onDestroy() {
        super.onDestroy();
    }//onDestroy

}//SoundManagerService
