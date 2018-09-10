package com.pek.doni.musicbox.activities;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.pek.doni.musicbox.Methods;
import com.pek.doni.musicbox.Models.Song;
import com.pek.doni.musicbox.R;
import com.pek.doni.musicbox.ReadAllSongs;
import com.pek.doni.musicbox.adapters.SongsListAdapter;
import com.pek.doni.musicbox.fragments.SongsFragment;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class NowPlayingActivity extends AppCompatActivity implements Runnable {

    private ProgressBar progressBar;
    private TextView start;
    private TextView end;
    private TextView artist;
    private TextView song;
    private Button play_paues;
    private Button next;
    private Button previus;
    private ImageView imageView;
    public static int position;
    private Intent i;
    int duration;
    public static final MediaPlayer mp =  new MediaPlayer();
    private Thread thread;
    public SongsListAdapter s1;
    private Methods m = new Methods();




    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.player);

        i = getIntent();

        position = i.getIntExtra("Position",-1);



        imageView =(ImageView) findViewById(R.id.songImage);



        ReadAllSongs r = i.getParcelableExtra("ReadAllSongs");

        if(i.getStringExtra("Album").equals("All")){

            s1 = new SongsListAdapter(r.getAllSongs(),this);


        }
        else{

            s1 = new SongsListAdapter(r.getSongs(i.getStringExtra("Album")),this);

        }




        try {

            mp.stop();
            mp.reset();
            mp.setDataSource(s1.getSonglist().get(position).getPath());
            mp.prepare();
            mp.start();
        }catch (IOException e) {
            e.printStackTrace();
        }
        
      
        play_paues = findViewById(R.id.playorpause);
        next = findViewById(R.id.next);
        previus =  findViewById(R.id.previous);
      
        progressBar = findViewById(R.id.progressBar);


        song = findViewById(R.id.song);
        artist =  findViewById(R.id.artist);


        end =  findViewById(R.id.end_text);
        start = findViewById(R.id.start_text);

        song.setText(s1.getSonglist().get(position).getTitle());
        artist.setText(s1.getSonglist().get(position).getArtist());

        Uri u = m.getAlbumUri(this,String.valueOf(s1.getSonglist().get(position).getAlbumId()));

        Picasso.with(this).load(u).fit().into(imageView);


        duration = mp.getDuration();
                
        progressBar.setVisibility(ProgressBar.VISIBLE);
        progressBar.setProgress(0);
        progressBar.setMax(mp.getDuration());


        end.setText(getTimeString(duration));

       thread = new Thread(this);
       thread.start();


        play_paues.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(mp.isPlaying())
                {
                    Log.i("PLay","AA");
                    mp.pause();
                    play_paues.setBackground(getDrawable(R.drawable.ic_play_arrow_black_24dp));

                }
                else
                {
                    mp.start();
                    play_paues.setBackground(getDrawable(R.drawable.ic_pause_black_24dp));
                }

            }
        });



        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    if(position > 0) {

                        mp.stop();
                        mp.reset();
                        mp.setDataSource(s1.getSonglist().get(position - 1).getPath());
                        position--;
                        mp.prepare();
                        mp.start();
                    }
                    else
                    {

                        mp.stop();
                        mp.reset();
                        mp.setDataSource(s1.getSonglist().get(s1.getSonglist().size() - 1).getPath());
                        position = s1.getSonglist().size() - 1;
                        mp.prepare();
                        mp.start();

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        });


        previus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {

                    if(position < s1.getSonglist().size()) {

                        mp.stop();
                        mp.reset();
                        mp.setDataSource(s1.getSonglist().get(position + 1).getPath());
                        position++;
                        mp.prepare();
                        mp.start();
                    }
                    else
                    {

                        mp.stop();
                        mp.reset();
                        mp.setDataSource(s1.getSonglist().get(0).getPath());
                        position = 0;
                        mp.prepare();
                        mp.start();

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        });

    }

    @Override
    public void run() {

                int currentPosition= 0;
                int total = mp.getDuration();
                while (currentPosition < total) {
                    try {
                        Thread.sleep(1000);
                        currentPosition= mp.getCurrentPosition();
                    } catch (InterruptedException e) {
                        return;
                    } catch (Exception e) {
                        return;
                    }

                    progressBar.setProgress(currentPosition);

                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {

                            start.setText(getTimeString(mp.getCurrentPosition()));

                            song.setText(s1.getSonglist().get(position).getTitle());
                            artist.setText(s1.getSonglist().get(position).getArtist());

                            Uri u = m.getAlbumUri(getApplicationContext(),String.valueOf(s1.getSonglist().get(position).getAlbumId()));

                            Picasso.with(getApplicationContext()).load(u).fit().into(imageView);

                        }
                    });



                }



    }

    private String getTimeString(long millis) {
        StringBuffer buf = new StringBuffer();

        int minutes = (int) ((millis % (1000 * 60 * 60)) / (1000 * 60));
        int seconds = (int) (((millis % (1000 * 60 * 60)) % (1000 * 60)) / 1000);

        buf
                .append(String.format("%02d", minutes))
                .append(":")
                .append(String.format("%02d", seconds));

        return buf.toString();
    }
}
