package com.pek.doni.musicbox.adapters;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.pek.doni.musicbox.Models.Song;
import com.pek.doni.musicbox.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by USER on 2/8/2018.
 */

public class SongsListAdapter extends RecyclerView.Adapter<SongsListAdapter.NumberViewHolder> {

    public List<Song> songlist;
    private Context c;





    public SongsListAdapter(List<Song> s,Context context)
    {
         songlist = s;

         c = context;
    }

    @Override
    public NumberViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context c = parent.getContext();
        LayoutInflater lf = LayoutInflater.from(c);
        int i = R.layout.songs_list;



        View v = lf.inflate(i,parent,false);

        NumberViewHolder n = new NumberViewHolder(v);




        return n;
    }

    public List<Song> getSonglist() {
        return songlist;
    }

    @Override
    public void onBindViewHolder(NumberViewHolder holder, int position) {
        holder.songTitle.setText(songlist.get(position).getTitle());
        holder.artistName.setText(songlist.get(position).getArtist());

        Uri ImageUrl = getAlbumUri(c, String.valueOf(songlist.get(position).getAlbumId()));

        Log.d("SSAA", String.valueOf(songlist.get(position).getAlbumId()) + "      a");

        Picasso.with(c).load(ImageUrl).fit().into(holder.songImage);


    }


    public Uri getAlbumUri(Context mContext,String album_id) {
        if (mContext != null) {
            Uri sArtworkUri = Uri.parse("content://media/external/audio/albumart");
            Uri imageUri = Uri.withAppendedPath(sArtworkUri, String.valueOf(album_id));
            return imageUri;
        }
        return null;
    }



    @Override
    public int getItemCount() {
        return songlist.size();
    }




    class NumberViewHolder extends RecyclerView.ViewHolder
    {
        TextView songTitle;
        TextView artistName;
        ImageView songImage;

        public NumberViewHolder(View itemView) {
            super(itemView);

            songTitle = (TextView) itemView.findViewById(R.id.songTitle);
            songImage = (ImageView) itemView.findViewById(R.id.songImage);
            artistName = (TextView) itemView.findViewById(R.id.artistName);




        }






    }



}






