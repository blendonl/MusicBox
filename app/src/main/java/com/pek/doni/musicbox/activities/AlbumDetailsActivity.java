package com.pek.doni.musicbox.activities;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.pek.doni.musicbox.Methods;
import com.pek.doni.musicbox.R;
import com.pek.doni.musicbox.ReadAllSongs;
import com.pek.doni.musicbox.RecyclerTouchListener;
import com.pek.doni.musicbox.adapters.SongsListAdapter;
import com.pek.doni.musicbox.fragments.SongsFragment;
import com.squareup.picasso.Picasso;

/**
 * Created by USER on 2/25/2018.
 */

public class AlbumDetailsActivity extends AppCompatActivity {

    private SongsListAdapter s;
    private Intent i;
    private ReadAllSongs plm;
    private ImageView imageView;
    private RecyclerView listView;
    private Intent i1;
    LinearLayoutManager linearLayoutManager;

    private Methods m = new Methods();


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.album_details);


        linearLayoutManager = new LinearLayoutManager(this);

        imageView =(ImageView) findViewById(R.id.img);

        Picasso.with(this).load(R.drawable.empty_music).centerCrop().resize(800,560).into(imageView);


        i = getIntent();

        plm = i.getParcelableExtra("ReadAllSongs");


        Uri u = m.getAlbumUri(this,String.valueOf(i.getStringExtra("Image")));


        Picasso.with(this).load(u).fit().into(imageView);

        s = new SongsListAdapter(plm.getSongs(i.getStringExtra("Album")),this);

        Log.d("AAa",i.getStringExtra("Album") + "    aa");


        s.notifyDataSetChanged();

        listView = (RecyclerView) findViewById(R.id.recyclerList);
        listView.setLayoutManager(linearLayoutManager);
        listView.setAdapter(s);


        i1 = new Intent(this, NowPlayingActivity.class);


        listView.addOnItemTouchListener(new RecyclerTouchListener(this,
                listView, new MainActivity.onClickListener() {

            @Override
            public void onClick(View view, int position)
            {

                String str = i.getStringExtra("Album");

                i1.putExtra("Position",position);

                i1.putExtra("Album",str);

                i1.putExtra("ReadAllSongs",plm);

                startActivity(i1);

            }


            @Override
            public void onLongClick(View view, int position) {

            }
        }));


    }
}
