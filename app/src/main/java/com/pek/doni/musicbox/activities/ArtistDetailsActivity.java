package com.pek.doni.musicbox.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.pek.doni.musicbox.Methods;
import com.pek.doni.musicbox.R;
import com.pek.doni.musicbox.ReadAllSongs;
import com.pek.doni.musicbox.RecyclerTouchListener;
import com.pek.doni.musicbox.adapters.AlbumsListAdapter;
import com.squareup.picasso.Picasso;

/**
 * Created by USER on 2/25/2018.
 */

public class ArtistDetailsActivity extends AppCompatActivity {

    ReadAllSongs plm;
    Intent i;
    AlbumsListAdapter s;
    ImageView imageView;
    TextView t;
    TextView t1;
    RecyclerView list;
    Intent i1;
     final Methods m = new Methods();

    GridLayoutManager gridLayoutManager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.artist_details);
        
        gridLayoutManager = new GridLayoutManager(this,2);
        


        i = getIntent();

        plm = i.getParcelableExtra("ReadAllSongs");

        s = new AlbumsListAdapter(plm.getAlbums(i.getStringExtra("Artist")),this);

        s.notifyDataSetChanged();

        imageView  =(ImageView) findViewById(R.id.artistImg);

        t = (TextView) findViewById(R.id.song);
        t1 = (TextView) findViewById(R.id.artist);


        Uri u = m.getAlbumUri(this,i.getStringExtra("Image"));
        Picasso.with(this).load(u).fit().into(imageView);

        list = (RecyclerView) this.findViewById(R.id.recyclerList1);
        list.setLayoutManager(gridLayoutManager);
        list.setAdapter(s);

        i1 = new Intent(this,AlbumDetailsActivity.class);




        list.addOnItemTouchListener(new RecyclerTouchListener(this,
                list, new MainActivity.onClickListener() {
            @Override
            public void onClick(View view, int position)
            {

                
                i1.putExtra("Album",s.getAlbumList().get(position).getTitle().toString());
                i1.putExtra("Image",String.valueOf(s.getAlbumList().get(position).getId()));
                i1.putExtra("ReadAllSongs",plm);
                startActivity(i1);

            }


            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }

}
