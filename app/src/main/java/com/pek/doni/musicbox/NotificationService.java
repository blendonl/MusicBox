package com.pek.doni.musicbox;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.session.MediaSession;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v4.media.app.NotificationCompat.MediaStyle;
import android.os.IBinder;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v7.app.NotificationCompat;
import android.support.v4.media.session.MediaButtonReceiver;
import android.support.v4.media.session.PlaybackStateCompat;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;

import com.pek.doni.musicbox.activities.NowPlayingActivity;
import com.pek.doni.musicbox.adapters.SongsListAdapter;
import com.pek.doni.musicbox.fragments.SongsFragment;
import com.squareup.picasso.Picasso;

import java.io.IOException;

/**
 * Created by USER on 3/9/2018.
 */

public class NotificationService  extends Service {

    Intent i;
    SongsListAdapter la = SongsFragment.la;
    MediaPlayer mp = NowPlayingActivity.mp;
    int pos;
    
    int position = NowPlayingActivity.position;


    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    Notification status;
    private final String LOG_TAG = "NotificationService";

    private void showNotification() {

// Using RemoteViews to bind custom layouts into Notification
        RemoteViews views = new RemoteViews(getPackageName(),
                R.layout.status_bar);
        RemoteViews bigViews = new RemoteViews(getPackageName(),
                R.layout.status_bar_expanded);

// showing default album image
        views.setImageViewBitmap(R.id.status_bar_album_art, Constants.getDefaultAlbumArt(this));
        bigViews.setImageViewBitmap(R.id.status_bar_album_art,
                Constants.getDefaultAlbumArt(this));

        Intent notificationIntent = new Intent(this, SongsFragment.class);
        notificationIntent.setAction(Constants.ACTION.MAIN_ACTION);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
                notificationIntent, 0);

        Intent previousIntent = new Intent(this, NotificationService.class);
        previousIntent.setAction(Constants.ACTION.PREV_ACTION);
        PendingIntent ppreviousIntent = PendingIntent.getService(this, 0,
                previousIntent, 0);

        Intent playIntent = new Intent(this, NotificationService.class);
        playIntent.setAction(Constants.ACTION.PLAY_ACTION);
        PendingIntent pplayIntent = PendingIntent.getService(this, 0,
                playIntent, 0);

        Intent nextIntent = new Intent(this, NotificationService.class);
        nextIntent.setAction(Constants.ACTION.NEXT_ACTION);
        PendingIntent pnextIntent = PendingIntent.getService(this, 0,
                nextIntent, 0);

        Intent closeIntent = new Intent(this, NotificationService.class);
        closeIntent.setAction(Constants.ACTION.STOPFOREGROUND_ACTION);
        PendingIntent pcloseIntent = PendingIntent.getService(this, 0,
                closeIntent, 0);

        views.setOnClickPendingIntent(R.id.status_bar_play, pplayIntent);
        bigViews.setOnClickPendingIntent(R.id.status_bar_play, pplayIntent);

        views.setOnClickPendingIntent(R.id.status_bar_next, pnextIntent);
        bigViews.setOnClickPendingIntent(R.id.status_bar_next, pnextIntent);

        views.setOnClickPendingIntent(R.id.status_bar_prev, ppreviousIntent);
        bigViews.setOnClickPendingIntent(R.id.status_bar_prev, ppreviousIntent);

        views.setOnClickPendingIntent(R.id.status_bar_collapse, pcloseIntent);
        bigViews.setOnClickPendingIntent(R.id.status_bar_collapse, pcloseIntent);

        views.setImageViewResource(R.id.status_bar_play,
                R.drawable.ic_play_arrow_black_24dp);
        bigViews.setImageViewResource(R.id.status_bar_play,
                R.drawable.ic_play_arrow_black_24dp);

        views.setTextViewText(R.id.status_bar_track_name,la.songlist.get(position).getTitle());
        bigViews.setTextViewText(R.id.status_bar_track_name, la.songlist.get(position).getTitle());

        views.setTextViewText(R.id.status_bar_artist_name, la.songlist.get(position).getAlbum());
        bigViews.setTextViewText(R.id.status_bar_artist_name, la.songlist.get(position).getAlbum());

        bigViews.setTextViewText(R.id.status_bar_album_name, la.songlist.get(position).getAlbum());

        NotificationChannel mChannel = new NotificationChannel(LOG_TAG, "Music", NotificationManager.IMPORTANCE_LOW);

        status = new Notification.Builder(this,LOG_TAG)
                .setVisibility(Notification.VISIBILITY_PUBLIC)
                .setContentIntent(pendingIntent)
                .setCustomContentView(views)
                .setCustomBigContentView(bigViews)
                .setDeleteIntent(MediaButtonReceiver.buildMediaButtonPendingIntent(
                        this,PlaybackStateCompat.ACTION_STOP))
                .setSmallIcon(R.drawable.ic_music_note_black_24dp)
                .setChannelId(LOG_TAG)
                .build();


        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.createNotificationChannel(mChannel);

        mNotificationManager.notify(1 , status);


        Picasso.with(this).load(getUri(this,String.valueOf(SongsFragment.la.songlist.get(pos).getAlbumId()))).into(bigViews,R.id.status_bar_album_art,1,status);

        Picasso.with(this).load(getUri(this,String.valueOf(SongsFragment.la.songlist.get(pos).getAlbumId()))).into(views,R.id.status_bar_album_art,1,status);





    }

    public Uri getUri(Context mContext, String album_id) {
        if (mContext != null) {
            Uri sArtworkUri = Uri.parse("content://media/external/audio/albumart");
            Uri imageUri = Uri.withAppendedPath(sArtworkUri, String.valueOf(album_id));
            return imageUri;
        }
        return null;
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        pos = intent.getIntExtra("Position",-1);




        if (intent.getAction().equals(Constants.ACTION.STARTFOREGROUND_ACTION)) {
            showNotification();


        }
        else if (intent.getAction().equals(Constants.ACTION.PREV_ACTION)) {

            try {
                if (position > 0) {

                    mp.stop();
                    mp.reset();
                    mp.setDataSource(la.getSonglist().get(position - 1).getPath());
                    position--;
                    mp.prepare();
                    mp.start();
                }
                else {

                    mp.stop();
                    mp.reset();
                    mp.setDataSource(la.getSonglist().get(la.getSonglist().size() - 1).getPath());
                    position = la.getSonglist().size() - 1;
                    mp.prepare();
                    mp.start();

                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if (intent.getAction().equals(Constants.ACTION.PLAY_ACTION)) {

            if(mp.isPlaying()){
                mp.pause();
            }else {
                mp.start();
            }

        }
        else if (intent.getAction().equals(Constants.ACTION.NEXT_ACTION)) {

            try {
                if (position < la.getSonglist().size() - 1) {

                    mp.reset();
                    mp.stop();
                    mp.setDataSource(la.getSonglist().get(position + 1).getPath());
                    position++;
                    mp.prepare();
                    mp.start();
                }
                else {
                    mp.reset();
                    mp.stop();
                    mp.setDataSource(la.getSonglist().get(la.getSonglist().size() - 1).getPath());
                    position = la.getSonglist().size() - 1;
                    mp.prepare();
                    mp.start();

                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }



        }
        else if (intent.getAction().equals(Constants.ACTION.STOPFOREGROUND_ACTION)) {

            stopForeground(true);
            stopSelf();

            mp.stop();

        }
        return START_STICKY;
    }
}

